package com.example.demo.mybatis.service;

import com.example.demo.mybatis.mapper.TClickMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TClickService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TClickMapper<T, Q, PK> mapper;
    @Override
    public TClickMapper<T, Q, PK> getMapper() {
        return mapper;
    }
}
