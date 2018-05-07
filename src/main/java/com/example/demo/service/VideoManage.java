package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.mybatis.domain.TMember;
import com.example.demo.mybatis.domain.TVideo;
import com.example.demo.mybatis.domain.TVideoQuery;
import com.example.demo.mybatis.service.TVideoService;
import com.example.demo.swagger.model.VideoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Service
public class VideoManage extends BaseManage<Integer>{
    @Autowired
    TVideoService<TVideo, TVideoQuery, Long> service;

    @Override
    public TVideoService<TVideo, TVideoQuery, Long> getService() {
        return service;
    }

    /**
     * 本地下载
     *
     * @param id
     * @param response
     */
    public void downloadLocal(Integer id, HttpServletResponse response) {
        try {
            String fileName = "测试视频.mp4";
            int fileSize = 401408;
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream("F:\\ve\\v1.mp4"));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setContentLength(fileSize);
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取视频列表
     * 包含点击率
     * @return
     */
    public List<Map<String,Object>> getVideoList()
    {
        List<Map<String,Object>> videos = getService().findBySQL("SELECT " +
                "v.id , " +
                "v.name, " +
                "v.address, " +
                "if(c.chick_rate,c.chick_rate,0) as chick_rate " +
                "FROM t_video v " +
                "LEFT JOIN t_click c ON v.id = c.video_id");
        return videos;
    }

    public boolean save(VideoVo videoVo, TMember tMember){
        if(videoVo == null){
            throw new BadRequestException("参数对象不能为空");
        }
        if(StringUtils.isBlank(videoVo.getRealName())){
            throw new BadRequestException("文件名不能为空");
        }
        if(StringUtils.isBlank(videoVo.getSuffix())){
            throw new BadRequestException("文件后缀不能为空");
        }
        if(StringUtils.isBlank(videoVo.getMd5())){
            throw new BadRequestException("MD5不能为空");
        }
        TVideo tVideo = new TVideo();
        tVideo.setName(videoVo.getRealName());
        tVideo.setType(videoVo.getSuffix());
        tVideo.setMemberId(tMember.getId());
        tVideo.setUploadTime(new Date());
        tVideo.setDel(0);
        tVideo.setSuffix(videoVo.getSuffix());
        tVideo.setAlias(videoVo.getMd5());
        tVideo.setAddress("http://localhost:7070/"+videoVo.getMd5()+"."+videoVo.getSuffix());
        int save = getService().save(tVideo);
        if(save == 1){
            return true;
        }
        return false;
    }

}
