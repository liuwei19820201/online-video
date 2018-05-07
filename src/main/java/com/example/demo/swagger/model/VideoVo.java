package com.example.demo.swagger.model;

import lombok.Data;

/**
 * 保存视频信息入参
 *
 * @author liu wei
 * @create 2018-04-28 11:41
 * @since v1.0
 */
@Data
public class VideoVo {
    private String realName;
    private String suffix;
    private String md5;
}
