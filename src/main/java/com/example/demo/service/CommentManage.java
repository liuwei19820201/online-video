package com.example.demo.service;

import com.example.demo.mybatis.domain.TComment;
import com.example.demo.mybatis.domain.TCommentQuery;
import com.example.demo.mybatis.service.CommonsService;
import com.example.demo.mybatis.service.TCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CommentManage extends BaseManage<Integer>{
    
    @Autowired
    TCommentService<TComment, TCommentQuery, Long> service;

    @Override
    public TCommentService<TComment, TCommentQuery, Long> getService() {
        return service;
    }

    /**
     * 保存评论
     * @param videoId
     * @param content
     * @return
     */
    public String saveTComment(Integer videoId,String content){
        try {
            log.info("视频列表-添加评论");
            TComment  t= new TComment();
            t.setVideoId(videoId);
            t.setContent(content);
            t.setCommentator(2);
            t.setCommentTime(new Date());
            t.setDel(0);
            getService().save(t);
            return "success";
        }catch (Exception e){
            log.error("视频列表-添加评论异常",e);
            return "fail";
        }
    }
    
}
