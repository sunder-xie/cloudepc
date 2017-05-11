package com.tqmall.data.epc.common.utils.upload;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by cloudgu on 16/2/26.
 */
public abstract class BaseOSSClient {

    @Value("${tqmall.oss.endpoint}")
    protected  String endpoint;

    @Value("${tqmall.oss.region}")
    protected  String region;

    @Value("${tqmall.oss.regionImg}")
    protected  String regionImg;

    @Value("${tqmall.oss.imgEndpoint}")
    protected  String imgEndpoint;

    @Value("${tqmall.oss.accesskeyId}")
    protected  String accessKeyId;

    @Value("${tqmall.oss.accessKeySecret}")
    protected  String accessKeySecret;

    @Value("${tqmall.oss.bucketName}")
    protected  String bucketName;


}
