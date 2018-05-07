package com.example.demo.swagger.model;

import lombok.Data;

/**
 * 查询接口入参
 *
 * @author liu wei
 * @create 2018-04-28 11:41
 * @since v1.0
 */
@Data
public class ClickSearch {
    private Integer start;
    private Integer length;
    private Integer id;
    private Integer videoId;
}
