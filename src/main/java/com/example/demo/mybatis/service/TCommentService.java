package com.example.demo.mybatis.service;

import com.example.demo.mybatis.mapper.TCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class TCommentService<T,Q,PK extends Serializable> extends CommonsService<T, Q, PK> {
    @Autowired
    TCommentMapper<T, Q, PK> mapper;
    @Override
    public TCommentMapper<T, Q, PK> getMapper() {
        return mapper;
    }
}
