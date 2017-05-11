package com.tqmall.data.epc.biz.upload;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.DateUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.common.utils.upload.OSSClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by zxg on 16/9/22.
 * 10:58
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
@Slf4j
public class AliOssBizImpl implements AliOssBiz {

    @Autowired
    private OSSClientUtil ossClientUtil;

    @Value("${img.path}")
    private String imgPath;

    @Override
    public Result<String> uploadOneImg(CommonsMultipartFile multipartFile) {
        if(multipartFile==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        String fileName = multipartFile.getOriginalFilename().trim();
        String newFileName = imgPath+DateUtil.getTimestamp()+ fileName.replace("+","_").replaceAll(" +","").substring(0,fileName.lastIndexOf(".")).toLowerCase()+ ".jpg";

        //上传到 oss
        try {
            String aliFilePath = ossClientUtil.putObject(newFileName, multipartFile.getBytes());
            return ResultUtil.successResult(aliFilePath);
        } catch (Exception e) {
            log.error("toUploadImg error", e);
        }

        return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
    }

    @Override
    public boolean toDeleteImg(String fileName) {
        if(fileName==null){
            return false;
        }
        fileName = fileName.trim();
        if("".equals(fileName)){
            return false;
        }

        try{
            ossClientUtil.delObject(fileName);
            return true;
        }catch (Exception e){
            log.error("deleteImage error", e);
            return false;
        }
    }
}
