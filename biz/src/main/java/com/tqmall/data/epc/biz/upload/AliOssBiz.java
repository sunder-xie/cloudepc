package com.tqmall.data.epc.biz.upload;

import com.tqmall.core.common.entity.Result;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by zxg on 16/9/22.
 * 10:57
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface AliOssBiz {
    /**
     * 上传一张图片
     * @param multipartFile
     * @return
     */
    Result<String> uploadOneImg(CommonsMultipartFile multipartFile);

    /**
     * 删除图片
     * @param fileName 文件名称，包含相对路径
     * @return
     */
    boolean toDeleteImg(String fileName);

}
