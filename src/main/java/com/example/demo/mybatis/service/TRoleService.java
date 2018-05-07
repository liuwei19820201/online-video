package com.example.demo.mybatis.service;

import com.example.demo.mybatis.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TRoleService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TRoleMapper<T, Q, PK> mapper;
    @Override
    public TRoleMapper<T, Q, PK> getMapper() {
        return mapper;
    }
}
