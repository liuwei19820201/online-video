package com.example.demo.swagger.api;

import com.example.demo.mybatis.domain.TClick;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.service.LoginManage;
import com.example.demo.swagger.model.BaseModel;
import com.example.demo.swagger.model.ClickSearch;
import com.example.demo.swagger.model.ResponseModel;
import groovy.util.logging.Log;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController extends BaseModel{

    @Autowired
    LoginManage loginManage;

    @GetMapping("/login")
    String toLogin(){
        return "login";
    }

    @GetMapping("/register")
    String toRegister(){
        return "register";
    }

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> login(@ApiParam(value = "用户名") @Valid @RequestParam(value = "userName", required = false) String userName,
                                        @ApiParam(value = "密码") @Valid @RequestParam(value = "password", required = false) String password,
                                        HttpServletRequest request){
        TMember member = loginManage.login(userName, password, request);
        return new ResponseEntity<ResponseModel>(successModel("登录", member), HttpStatus.OK);
    }


    @ApiOperation(value = "注册", notes = "注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> register(@ApiParam(value = "用户名") @Valid @RequestParam(value = "userName", required = false) String userName,
                                           @ApiParam(value = "密码") @Valid @RequestParam(value = "password", required = false) String password,
                                           HttpServletRequest request){
        TMember member = loginManage.register(userName, password, request);
        if(member != null){
            return new ResponseEntity<ResponseModel>(successModel("注册", member), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel("注册"), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "退出", notes = "退出")
        @RequestMapping(value = "api/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> logout(HttpServletRequest request){
        loginManage.logout(request);
        return new ResponseEntity<ResponseModel>(successModel("退出"), HttpStatus.OK);
    }

}
