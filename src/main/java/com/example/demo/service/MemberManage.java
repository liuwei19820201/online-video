package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.BusinessException;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.mybatis.domain.TMemberQuery;
import com.example.demo.mybatis.mapper.TMemberMapper;
import com.example.demo.mybatis.service.TMemberService;
import com.example.demo.util.JsonMapper;
import com.example.demo.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class MemberManage extends BaseManage<Integer>{

    @Autowired
    TMemberService<TMember, TMemberQuery, Long> service;

    @Override
    public TMemberService<TMember, TMemberQuery, Long> getService() {
        return service;
    }

    @Autowired
    TMemberMapper tMemberMapper;

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    /**
     * 获取管理员列表
     * @return
     */
    public List<TMember> getTMembersByRoleADMIN(){
        TMemberQuery tMemberQuery = new TMemberQuery();
        tMemberQuery.createCriteria().andRoleEqualTo("ADMIN");
        List<TMember> list = tMemberMapper.selectByExample(tMemberQuery);
        return list;
    }

    /**
     * 获取会员列表
     * @return
     */
    public List<TMember> getTMembersByRoleMEMBER(){
        TMemberQuery tMemberQuery = new TMemberQuery();
        tMemberQuery.createCriteria().andRoleEqualTo("MEMBER");
        List<TMember> list = tMemberMapper.selectByExample(tMemberQuery);
        return list;
    }

    /**
     * 添加管理员
     */
    public void saveTMemberRoleADMIN(TMember tMember){
        if (getByAccount(tMember.getAccount()) != null) {
            throw new BusinessException("用户名已存在，请修改");
        }
        try {
            tMember.setCreateTime(new Date());
            tMember.setRole("ADMIN");
            tMember.setDel(0);
            tMember.setPassword(MD5Util.getMD5String(tMember.getPassword()));
            tMemberMapper.insert(tMember);
        }catch (Exception e){
            logger.error("添加管理员 saveTMemberRoleADMIN error:" + tMember.getAccount(), e);
            throw new BusinessException("新增管理员失败");
        }
    }

    /**
     * 修改管理员
     */
    public void updateTMemberRoleADMIN(TMember tMember){
        if (getByAccount(tMember.getAccount(), tMember.getId()) != null) {
            throw new BusinessException("用户名已存在，请修改");
        }
        try {
            TMember byAccount = getByAccount(tMember.getAccount());
            TMemberQuery tMemberQuery = new TMemberQuery();
            tMemberQuery.createCriteria().andIdEqualTo(tMember.getId());
            tMember.setPassword(MD5Util.getMD5String(tMember.getPassword()));
            tMember.setDel(byAccount.getDel());
            tMemberMapper.updateByExampleSelective(tMember, tMemberQuery);
        } catch (Exception e) {
            logger.error("修改管理员 updateTMemberRoleADMIN error:" + tMember.getId(), e);
            throw new BusinessException("修改管理员失败");
        }
    }

    /**
     * 删除管理员
     */
    public void deleteTMemberRoleADMIN(String id){
        try {
            TMemberQuery tMemberQuery = new TMemberQuery();
            tMemberQuery.createCriteria().andIdEqualTo(Integer.parseInt(id));
            List<TMember> list = tMemberMapper.selectByExample(tMemberQuery);
            TMember tMember = list.get(0);
            tMember.setDel(1);
            tMemberMapper.updateByExampleSelective(tMember, tMemberQuery);
        }catch (Exception e){
            logger.warn("删除管理员 deleteTMemberRoleADMIN error:" + id, e);
            throw new BusinessException("删除管理员失败");
        }
    }

    public TMember getByAccount(String account){
        TMemberQuery query = new TMemberQuery();
        TMemberQuery.Criteria criteria = query.createCriteria();
        criteria.andAccountEqualTo(account);
        List<TMember> members = getService().findBy(query);
        if(members != null && members.size() > 0){
            return members.get(0);
        }
        return null;
    }

    public TMember getByAccount(String account, Integer id){
        TMemberQuery query = new TMemberQuery();
        TMemberQuery.Criteria criteria = query.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andIdNotEqualTo(id);
        List<TMember> members = getService().findBy(query);
        if(members != null && members.size() > 0){
            return members.get(0);
        }
        return null;
    }

    /**
     * 获取个人信息
     * @return
     */
    public TMember getMemberDetail(HttpSession session){
        TMember member = (TMember)session.getAttribute("member");
        if(member == null){
            throw new BadRequestException("获取当前用户失败！");
        }
        String account = member.getAccount();
        if(account == null || "".equals(account)){
            throw new BadRequestException("获取当前用户失败！");
        }
        TMemberQuery tMemberQuery = new TMemberQuery();
        tMemberQuery.createCriteria().andAccountEqualTo(account);
        List<TMember> list = tMemberMapper.selectByExample(tMemberQuery);
        if(list == null || list.size()<1){
            throw new BadRequestException("当前用户不存在！");
        }
        TMember tMember = list.get(0);
        return tMember;
    }
    /**
     * 修改个人信息
     * @returnt
     */
    public void updateMemberDetail(TMember tMember){
        try {
            TMemberQuery tMemberQuery = new TMemberQuery();
            tMemberQuery.createCriteria().andIdEqualTo(tMember.getId());

            TMember dbTMember = (TMember)tMemberMapper.selectByExample(tMemberQuery).get(0);
            if(dbTMember == null){
                throw new BadRequestException("获取当前用户失败！");
            }

            if(tMember.getPassword()!=null && !"".equals(tMember.getPassword())){
                tMember.setPassword(MD5Util.getMD5String(tMember.getPassword()));
                dbTMember.setPassword(tMember.getPassword());
            }

            dbTMember.setName(tMember.getName());
            dbTMember.setSex(tMember.getSex());
            dbTMember.setEmail(tMember.getEmail());
            dbTMember.setIdNum(tMember.getIdNum());
            dbTMember.setMobile(tMember.getMobile());
            tMemberMapper.updateByExample(dbTMember, tMemberQuery);
        }catch (Exception e){
            logger.warn("修改个人信息 updateTMember error:" + tMember, e);
        }
    }
}
