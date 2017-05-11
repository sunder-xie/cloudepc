package com.tqmall.data.epc.web.controller.common;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.biz.upload.AliOssBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by lyj on 16/8/1.
 * 开开心心写代码-lyj!
 */
@Controller
@RequestMapping("upload")
@Slf4j
public class UploadController {
    @Autowired
    private AliOssBiz aliOssBiz;


    /*
    * 上传图片到阿里oss上
    * */
    @RequestMapping("uploadImage")
    @ResponseBody
    public Result<String> uploadImage(@RequestParam("file") CommonsMultipartFile multipartFile) {
//        String uploadInfo = String.format("上传信息: 文件名(%s), 大小(%s), 类型(%s)",
//                multipartFile.getOriginalFilename(), multipartFile.getSize(), multipartFile.getContentType());
//
//        log.info(uploadInfo);

        return aliOssBiz.uploadOneImg(multipartFile);
    }

    /**
     * 删除上传的文件
     *
     * @param url
     */
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(String url) {
        log.info("delete img, url:{}", url);
        return aliOssBiz.toDeleteImg(url);
    }

}
