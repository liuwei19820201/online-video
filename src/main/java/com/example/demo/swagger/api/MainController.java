package com.example.demo.swagger.api;

import com.example.demo.mybatis.domain.TMember;
import com.example.demo.service.MemberManage;
import com.example.demo.swagger.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api")
public class MainController {

    @Autowired
    private MemberManage memberManage;

    @GetMapping("/main")
    String toMain(){
        return "main";
    }

    @GetMapping("/information")
    TMember information(){
        TMember member = memberManage.findById(1);
        return member;
    }


}
