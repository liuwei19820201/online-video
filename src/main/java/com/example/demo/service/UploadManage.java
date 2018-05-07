package com.example.demo.service;

import com.example.demo.exception.BusinessException;
import com.example.demo.util.FileUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class UploadManage {
    private final String filePath = "D:\\videoStore";
    private final String delimiter = "\\";
    /**
     * 文件上传
     *
     * @param multipartFile
     * @return
     */
    @Transactional
    public Map<String, Object> upload(MultipartFile multipartFile) {
        Map<String, Object> map = new HashedMap();
        try {
            String[] split = multipartFile.getOriginalFilename().split("[.]");
            String suffix = split[split.length - 1];
            map.put("suffix", suffix);
            map.put("realName", multipartFile.getOriginalFilename().replace("."+suffix, ""));
            FileUtil.mkdir(filePath);
            String md5Hex = DigestUtils.md5Hex(IOUtils.toByteArray(multipartFile.getInputStream()));
            map.put("md5", md5Hex);
            String fullPath = filePath + delimiter + md5Hex + "." + suffix;
            if(FileUtil.exists(fullPath)){
                return map;
            }
            File videoFile = new File(fullPath);
            multipartFile.transferTo(videoFile);
            return map;
        } catch (IOException e) {
            throw new BusinessException("上传视频异常：" + e.getMessage());
        }
    }

}
