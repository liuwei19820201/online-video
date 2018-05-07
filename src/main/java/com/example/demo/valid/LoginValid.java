package com.example.demo.valid;

import com.example.demo.exception.BadRequestException;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.service.ClickManage;
import com.example.demo.service.MemberManage;
import com.example.demo.swagger.model.ClickVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginValid extends BaseValid {

    @Autowired
    MemberManage memberManage;

    /**
     * 验证： 登录
     * @param userName
     * @param password
     */
    public void loginValid(String userName, String password){
        if(StringUtils.isBlank(userName)){
            throw new BadRequestException("用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadRequestException("密码不能为空");
        }
    }

    /**
     * 验证： 注册
     * @param userName
     * @param password
     */
    public void registerValid(String userName, String password){
        if(StringUtils.isBlank(userName)){
            throw new BadRequestException("用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            throw new BadRequestException("密码不能为空");
        }
        TMember member = memberManage.getByAccount(userName);
        if(member != null){
            throw new BadRequestException("用户名已存在");
        }
    }
}
