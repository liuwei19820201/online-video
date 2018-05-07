package com.example.demo.swagger.api;

import com.example.demo.mybatis.domain.TClick;
import com.example.demo.service.ClickManage;
import com.example.demo.swagger.model.BaseModel;
import com.example.demo.swagger.model.ClickSearch;
import com.example.demo.swagger.model.ClickVo;
import com.example.demo.swagger.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/click")
public class ClickController extends BaseModel {

    @Autowired
    ClickManage clickManage;

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> getByPage(ClickSearch clickSearch){
        List<TClick> list = clickManage.getByPage(clickSearch);
        int totalCount = clickManage.getPageCount(clickSearch);
        return new ResponseEntity<ResponseModel>(successModel("查询",page(list, totalCount)), HttpStatus.OK);
    }

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody ClickVo clickVo){
        if(clickManage.save(clickVo)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> update(@RequestBody ClickVo clickVo){
        if(clickManage.update(clickVo)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "删除", notes = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> delete(@ApiParam(value = "主键") @Valid @RequestParam(value = "id", required = true) Integer id){
        if(clickManage.removeById(id) == 1){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }

}
