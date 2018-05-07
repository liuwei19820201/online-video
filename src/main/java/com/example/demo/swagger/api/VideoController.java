package com.example.demo.swagger.api;

import com.example.demo.mybatis.domain.*;
import com.example.demo.service.ClickManage;
import com.example.demo.service.CommentManage;
import com.example.demo.service.MemberManage;
import com.example.demo.service.VideoManage;
import com.example.demo.swagger.model.BaseModel;
import com.example.demo.swagger.model.ResponseModel;
import com.example.demo.swagger.model.VideoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class VideoController extends BaseModel{

    @Autowired
    VideoManage videoManage;
    @Autowired
    CommentManage commentManage;
    @Autowired
    ClickManage clickManage;
    @Autowired
    private MemberManage memberManage;

    @ApiOperation(value = "下载", notes = "下载 ", response = ResponseModel.class, tags = {})
    @RequestMapping(value = "/download/{id}",
            method = RequestMethod.GET)
    void download(@ApiParam(value = "id", required = true) @PathVariable("id") Integer id, HttpServletResponse response){
        videoManage.downloadLocal(id, response);
    }

    //------------------------视频列表--------------前端代码---------------------------------------------------\\

    /**
     * 获取视频列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/",
            method = RequestMethod.GET)
    String getVideoList (ModelMap model){
        log.info("获取视频列表");
        model.addAttribute("videoList",videoManage.getVideoList());
        return "index";
    }

    /**
     * 获取评论列表
     * 每点击一次视频列表某个视频,会增加一次点击率
     * 如果当前视频点击率为NULL,需要添加数据
     * @param model
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/tComment",
            method = RequestMethod.GET)
    @ResponseBody
    String getTComment (ModelMap model, @Null @ApiParam(value = "视频ID",required = false)
                                        @RequestParam(value = "videoId")Integer videoId){
        try {
            log.info("点击视频列表,获取评论列表");
            //根据视频ID添加点击率
            boolean b = clickManage.update(videoId);
            //根据视频ID获取评论列表
            TCommentQuery queue = new TCommentQuery();
            queue.or().andVideoIdEqualTo(videoId);
            List<TComment> l = commentManage.getService().findBy(queue);
            return JSONArray.fromObject(l).toString();  //转换成JSON后返回
        }catch (Exception e){
            log.error("点击视频列表出现异常",e);
        }
        return null;
    }

    /**
     * 保存评论
     * @param model
     * @param videoId
     * @param content
     * @return
     */
    @RequestMapping(value = "/saveTComment",
            method = RequestMethod.GET)
    @ResponseBody
    String saveTComment (ModelMap model, @Null @ApiParam(value = "视频ID",required = false)
                                         @RequestParam(value = "videoId")Integer videoId,
                                         @Null @ApiParam(value = "评论",required = false)
                                         @RequestParam(value = "content")String content){
        log.info("保存评论");
        return commentManage.saveTComment(videoId,content);
    }

    //------------------------视频列表--------------后端代码---------------------------------------------------\\

    /**
     * 获取所有视频数据
     * @param model
     * @return
     */
    @RequestMapping(value = "/afterVideoList",
            method = RequestMethod.GET)
    String getafterVideoList (HttpServletRequest request, ModelMap model){
        log.info("视频-后端代码-获取所有视频数据");
        HttpSession session = request.getSession();
        TMember tMember = memberManage.getMemberDetail(session);
        TVideoQuery query = new TVideoQuery();
        if(tMember.getRole().equals("MEMBER")){
            query.or().andMemberIdEqualTo(tMember.getId());
        }

        List<TVideo> list = videoManage.getService().findBy(query);
        model.addAttribute("videoList",list);
        return "afterVideoList";
    }

    /**
     * 获取所有评论数据
     * @param model
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/afterTComment",
            method = RequestMethod.GET)
    String getAfterTComment(ModelMap model, @Null @ApiParam(value = "视频ID",required = false)
        @RequestParam(value = "videoId")Integer videoId){
        log.info("视频-后端代码-获取所有评论数据");
        TCommentQuery queue = new TCommentQuery();
        queue.or().andVideoIdEqualTo(videoId);
        List<TComment> l = commentManage.getService().findBy(queue);
        model.addAttribute("tComments",l);
        return "afterTCommentList";
    }

    @ApiOperation(value = "新增", notes = "新增 ")
    @RequestMapping(value = "/video/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseModel> save(@RequestBody VideoVo videoVo, HttpServletRequest request){
        HttpSession session = request.getSession();
        TMember tMember = (TMember) session.getAttribute("member");
        if(videoManage.save(videoVo, tMember)){
            return new ResponseEntity<ResponseModel>(successModel(), HttpStatus.OK);
        }else{
            return new ResponseEntity<ResponseModel>(badRequestModel(), HttpStatus.OK);
        }
    }
}
