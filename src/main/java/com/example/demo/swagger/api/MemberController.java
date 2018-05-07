package com.example.demo.swagger.api;

import com.example.demo.exception.BadRequestException;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.service.MemberManage;
import com.example.demo.swagger.model.BaseModel;
import com.example.demo.swagger.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController extends BaseModel {

    @Autowired
    private MemberManage memberManage;

    @GetMapping("/adminList")
    String adminList(){
        return "adminList";
    }

    @ApiOperation(value = "获取会员列表", notes = "获取会员列表")
    @RequestMapping(value = "/api/adminList/getTMembersByRoleMEMBER", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ResponseEntity<ResponseModel> getTMembersByRoleMEMBER(){
        List<TMember> list = memberManage.getTMembersByRoleMEMBER();
        int totalCount = CollectionUtils.isEmpty(list) ? 0 : list.size();
        return new ResponseEntity<ResponseModel>(successModel("获取会员列表",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
    @RequestMapping(value = "/api/adminList/getTMembersByRoleADMIN", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ResponseEntity<ResponseModel> getTMembersByRoleADMIN(){
        List<TMember> list = memberManage.getTMembersByRoleADMIN();
        int totalCount = CollectionUtils.isEmpty(list) ? 0 : list.size();
        return new ResponseEntity<ResponseModel>(successModel("获取管理员列表",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "添加管理员", notes = "添加管理员")
    @RequestMapping(value = "/api/adminList/saveTMemberRoleADMIN", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ResponseEntity<ResponseModel> saveTMemberRoleADMIN(TMember tMember){
        if (StringUtils.isEmpty(tMember.getAccount())) {
            throw new BadRequestException("用户名不能为空");
        }
        if (StringUtils.isEmpty(tMember.getPassword())) {
            throw new BadRequestException("密码不能为空");
        }
        memberManage.saveTMemberRoleADMIN(tMember);
        return new ResponseEntity<ResponseModel>(successModel("添加管理员",null), HttpStatus.OK);
    }

    @ApiOperation(value = "修改管理员", notes = "修改管理员")
    @RequestMapping(value = "/api/adminList/updateTMemberRoleADMIN", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ResponseEntity<ResponseModel> updateTMemberRoleADMIN(TMember tMember){
        if (tMember.getId() == null) {
            throw new BadRequestException("ID不能为空");
        }
        if (StringUtils.isEmpty(tMember.getAccount())) {
            throw new BadRequestException("用户名不能为空");
        }
        if (StringUtils.isEmpty(tMember.getPassword())) {
            throw new BadRequestException("密码不能为空");
        }
        memberManage.updateTMemberRoleADMIN(tMember);
        return new ResponseEntity<ResponseModel>(successModel("修改管理员",null), HttpStatus.OK);
    }

    @ApiOperation(value = "删除管理员", notes = "删除管理员")
    @RequestMapping(value = "/api/adminList/deleteTMemberRoleADMIN", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    ResponseEntity<ResponseModel> deleteTMemberRoleADMIN(String id){
        if (StringUtils.isEmpty(id)) {
            throw new BadRequestException("ID不能为空");
        }
        memberManage.deleteTMemberRoleADMIN(id);
        return new ResponseEntity<ResponseModel>(successModel("删除管理员",null), HttpStatus.OK);
    }
    @ApiOperation(value = "保存个人信息", notes = "保存个人信息")
    @RequestMapping(value = "/updateMemberDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> updateMemberDetail(TMember tMember,HttpServletRequest request){
        memberManage.updateMemberDetail(tMember);

        return new ResponseEntity<ResponseModel>(successModel("保存个人信息",null), HttpStatus.OK);
    }
    @ApiOperation(value = "获取个人信息", notes = "获取个人信息")
    @RequestMapping(value = "/getMemberDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getMemberDetail(HttpServletRequest request){
        HttpSession session = request.getSession();
        TMember tMember = memberManage.getMemberDetail(session);
        return new ResponseEntity<ResponseModel>(successModel("获取个人信息",tMember), HttpStatus.OK);
    }
}
