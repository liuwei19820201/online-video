package com.example.demo.mybatis.service;

import com.example.demo.mybatis.mapper.TMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TMemberService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TMemberMapper<T, Q, PK> mapper;
    @Override
    public TMemberMapper<T, Q, PK> getMapper() {
        return mapper;
    }
}
