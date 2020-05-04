package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.config.UploadProperties;
import com.leyou.upload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;


    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpg","image/jpeg","image/bmp","image/png");



    @Override
    public String uploadImage(MultipartFile file) {

        //校验文件
        String contentType = file.getContentType();
        if(!prop.getAllowTypes().contains(contentType)){
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }

        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image==null){
                log.error("【文件上传】非法的文件内容");
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
        }catch (IOException e){
            log.error("【文件上传】非法的文件内容");
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        //保存图片
        try {
            String extensionName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            log.info("extensionName==========================="+extensionName);
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extensionName, null);
            //返回保存图片的完整url
            log.info("FullPath==========================="+"http://image.leyou.com/" + storePath.getFullPath());
//            return "http://image.leyou.com/" + storePath.getFullPath();
            return prop.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            throw new LyException(ExceptionEnum.UPLOAD_IMAGE_EXCEPTION);
        }
//        //准备目标路径
//        File dest = new File("D:\\黑马JavaEE 57期",file.getOriginalFilename());
//        //保存到本地
//        try {
//            file.transferTo(dest);
//            //返回路径
//            return "http://image.leyou.com/"+file.getOriginalFilename();
//        } catch (IOException e) {
//            log.error("上传文件失败",e);
//            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
//        }

    }
}
