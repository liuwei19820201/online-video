package com.example.demo.valid;

import com.example.demo.exception.BadRequestException;
import com.example.demo.service.ClickManage;
import com.example.demo.swagger.model.ClickVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClickValid extends BaseValid {
    @Autowired
    ClickManage clickManage;

    /**
     * 验证：新增接口
     * @param clickVo
     */
    public void saveValid(ClickVo clickVo){
        if(clickVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        if(clickVo.getVideoId() == null){
            throw new BadRequestException("视频ID不能为空");
        }
    }

    /**
     * 验证：编辑接口
     * @param clickVo
     */
    public void updateValid(ClickVo clickVo){
        if(clickVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        if(clickVo.getId() == null){
            throw new BadRequestException("ID不能为空");
        }
    }
}
