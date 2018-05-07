package com.example.demo.swagger.api;

//import com.sun.istack.internal.NotNull;


import com.example.demo.exception.BusinessException;
import com.example.demo.service.UploadManage;
import com.example.demo.swagger.model.BaseModel;
import com.example.demo.swagger.model.ResponseModel;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Controller
public class UploadController extends BaseModel{
    @Autowired
    UploadManage uploadManage;

    @ApiOperation(value = "文件上传", notes = "流程图", response = ResponseModel.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = ResponseModel.class),
            @ApiResponse(code = 200, message = "unexpected error", response = ResponseModel.class) })
    @RequestMapping(value = "/upload",
            produces = { "application/json" },
            consumes = { "multipart/form-data" },
            method = RequestMethod.POST)
    ResponseEntity<ResponseModel> fileupload(@ApiParam(value = "file") @RequestPart("file") MultipartFile file){
        Map<String, Object> resultMap  = uploadManage.upload(file);
        return new ResponseEntity<ResponseModel>(successModel(resultMap), HttpStatus.OK);
    }
}
