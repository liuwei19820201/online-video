package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.mybatis.domain.TClick;
import com.example.demo.mybatis.domain.TClickQuery;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.mybatis.domain.TMemberQuery;
import com.example.demo.mybatis.service.TClickService;
import com.example.demo.swagger.model.ClickSearch;
import com.example.demo.swagger.model.ClickVo;
import com.example.demo.util.MD5Util;
import com.example.demo.util.TransferUtil;
import com.example.demo.valid.ClickValid;
import com.example.demo.valid.LoginValid;
import org.apache.http.HttpRequest;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class LoginManage{

    @Autowired
    LoginValid loginValid;
    @Autowired
    MemberManage memberManage;

    public TMember login(String userName, String password, HttpServletRequest request){
        loginValid.loginValid(userName, password);
        TMemberQuery query = new TMemberQuery();
        TMemberQuery.Criteria criteria = query.createCriteria();
        criteria.andAccountEqualTo(userName);
        criteria.andPasswordEqualTo(MD5Util.getMD5String(password));
        List<TMember> members = memberManage.getService().findBy(query);
        if(members.size() != 1){
            throw new BadRequestException("用户名或密码不正确");
        }
        TMember member = members.get(0);
        HttpSession session = request.getSession();
        session.setAttribute("member", member);
        return member;
    }

    public TMember register(String userName, String password, HttpServletRequest request){
        loginValid.registerValid(userName, password);
        TMember member = new TMember();
        member.setAccount(userName);
        member.setPassword(MD5Util.getMD5String(password));
        member.setName("");
        member.setSex("");
        member.setDel(0);
        member.setRole("MEMBER");
        member.setCreateTime(new Date());
        int i = memberManage.create(member);
        if(i == 1){
            return login(userName, password, request);
        }else{
            return null;
        }
    }

    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("member");
    }

}
