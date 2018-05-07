package com.example.demo.mybatis.service;

import com.example.demo.mybatis.mapper.TVideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TVideoService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TVideoMapper<T, Q, PK> mapper;
    @Override
    public TVideoMapper<T, Q, PK> getMapper() {
        return mapper;
    }
}
