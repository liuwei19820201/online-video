package com.example.demo.service;

import com.example.demo.mybatis.domain.TRole;
import com.example.demo.mybatis.domain.TRoleQuery;
import com.example.demo.mybatis.service.TRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleManage extends BaseManage<Integer>{

    @Autowired
    TRoleService<TRole, TRoleQuery, Long> service;

    @Override
    public TRoleService<TRole, TRoleQuery, Long> getService() {
        return service;
    }





}
