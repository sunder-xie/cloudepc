package com.tqmall.data.epc.common.utils.upload;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * Created by zxg on 16/9/22.
 * 10:04
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Slf4j
@Service
public class OSSClientUtil extends BaseOSSClient {


    private static OSSClient client;

    private static OSSClient clientImg;

    /**
     * 初始化链接
     */
    public void open(){
        ClientConfiguration conf = new ClientConfiguration();
        if (client==null){
            client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
        }
        if (clientImg == null){
            clientImg = new OSSClient(imgEndpoint, accessKeyId, accessKeySecret, conf);
        }
    }

    /**
     * bucket是否存在
     * @param bucketName bucketName
     * @return boolean
     */
    public boolean isExistsBucket(String bucketName){
        if (StringUtils.isBlank(bucketName)) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        return client.doesBucketExist(bucketName);
    }

    /**
     * 返回bucketName
     * @return bucketName
     */
    public String getBucketName(){
        return this.bucketName;
    }

    /**
     * 获取endpoint
     * @return endpoint
     */
    public String getEndpoint(){
        return this.endpoint;
    }

    /**
     * 获取region
     * @return 获取region
     */
    public String getRegion(){
        return this.region;
    }

    /**
     * 上传对象(使用OSSClientUtil中的bucketName)
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param inputStream inputStream
     * @return url字符串
     */
    public String putObject(String objectKey, InputStream inputStream){
        return this.putObject(this.bucketName, objectKey, inputStream);
    }

    /**
     * 上传对象
     * @param bucketName bucketName
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param inputStream inputStream
     * @return url字符串
     */
    public String putObject(String bucketName, String objectKey, InputStream inputStream){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || inputStream == null) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        PutObjectResult putObjectResult = client.putObject(bucketName, objectKey, inputStream);
        return "http://"+bucketName+"."+region+"/"+objectKey;
    }



    /**
     * 上传对象
     * @param bucketName bucketName
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param file file文件
     * @return 字符串
     */
    public String putObject(String bucketName, String objectKey, File file){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || file == null) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        PutObjectResult putObjectResult = client.putObject(bucketName, objectKey, file);
        return "http://"+bucketName+"."+region+"/"+objectKey;
    }

    /**
     * 上传对象(使用OSSClientUtil中的bucketName)
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param file file文件
     * @return 字符串
     */
    public String putObject(String objectKey, File file){
        return this.putObject(this.bucketName,objectKey,file);
    }


    /**
     * 下载文件(5个线程下载)
     * @param filePath 本地路径
     * @param bucketName bucketName
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @return boolean
     */
    public boolean downloadFile(String filePath, String bucketName, String objectKey){
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) ) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        boolean ret = false;
        DownloadFileResult downloadFileResult = null;
        try {
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName,objectKey);
            downloadFileRequest.setDownloadFile(filePath);
            downloadFileRequest.setTaskNum(5);
            downloadFileRequest.setEnableCheckpoint(true);
            downloadFileResult = client.downloadFile(downloadFileRequest);

            ret = true;
        } catch (Throwable throwable) {
            log.error("oss客户端下载文件",throwable);
        }
        return ret;
    }

    /**
     * 下载文件(5个线程下载)(使用OSSClientUtil中的bucketName)
     * @param filePath 本地路径
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @return boolean
     */
    public boolean downloadFile(String filePath,  String objectKey){
        return downloadFile(filePath,this.bucketName,objectKey);
    }



    /**
     * 上传对象
     * @param bucketName bucketName
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param bs 对象
     * @return 字符串
     */
    public String putObject(String bucketName, String objectKey, byte[] bs){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || bs == null) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        PutObjectResult putObjectResult = client.putObject(bucketName, objectKey, new ByteArrayInputStream(bs));
        return objectKey;
    }

    /**
     * 上传对象
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     * @param bs 对象
     * @return 字符串
     */
    public String putObject(String objectKey, byte[] bs){
        return this.putObject(this.bucketName, objectKey, bs);
    }

    /**
     * 删除对象
     * @param bucketName bucketName
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     */
    public void delObject(String bucketName, String objectKey){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) ) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        client.deleteObject(bucketName, objectKey);
    }

    /**
     * 删除对象
     * @param objectKey 格式:1.使用 UTF-8 编码; 2.长度必须在 1-1023 字节之间; 3.不能以“/”或者“\”字符开头
     */
    public void delObject(String objectKey){
        if ( StringUtils.isBlank(objectKey) ) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        client.deleteObject(this.bucketName, objectKey);
    }

    /**
     * 拼接URL
     * @param bucketName bucketName
     * @param objectKey objectKey
     * @return 字符串
     */
    public String appendUrl(String bucketName, String objectKey){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) ) {
            throw new IllegalArgumentException();
        }
        return "http://"+bucketName+"."+region+"/"+objectKey;
    }

    /**
     * 拼接img域名的URL
     * @param bucketName bucketName
     * @param objectKey objectKey
     * @return 字符串
     */
    public String appendImgUrl(String bucketName, String objectKey){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) ) {
            throw new IllegalArgumentException();
        }
        return "http://"+bucketName+"."+regionImg+"/"+objectKey;
    }

    /**
     * 上传图片
     * @param bucketName bucketName
     * @param objectKey objectKey
     * @param file 对象文件
     * @return 字符串
     */
    public String uploadImg(String bucketName, String objectKey, File file){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || file == null) {
            throw new IllegalArgumentException();
        }
        PutObjectResult putObjectResult = client.putObject(bucketName, objectKey, file);
        return "http://"+bucketName+"."+regionImg+"/"+objectKey;
    }

    /**
     * 上传图片
     * @param objectKey objectKey
     * @param file 对象文件
     * @return 字符串
     */
    public String uploadImg(String objectKey, File file){
        if (StringUtils.isBlank(objectKey) || file == null) {
            throw new IllegalArgumentException();
        }
        PutObjectResult putObjectResult = client.putObject(this.bucketName, objectKey, file);
        return "http://"+this.bucketName+"."+this.regionImg+"/"+objectKey;
    }

    /**
     * 生成签名URL
     * @param bucketName bucketName
     * @param objectKey objectKey
     * @param date 过期时间
     * @return URL对象
     */
    public URL generatePresignedUrl(String bucketName, String objectKey, Date date){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || date == null) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        return client.generatePresignedUrl(bucketName, objectKey, date);
    }

    /**
     * 生成签名URL(图片域名)
     * @param bucketName bucketName
     * @param objectKey objectKey
     * @param date 过期实践
     * @return URL对象
     */
    public URL generatePresignedUrlImg(String bucketName, String objectKey, Date date){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(objectKey) || date == null) {
            throw new IllegalArgumentException();
        }
        if (clientImg==null){
            this.open();
        }
        return clientImg.generatePresignedUrl(bucketName, objectKey, date);
    }

    /**
     * 获取ACL
     * @param bucketName bucketName
     * @return AccessControlList
     */
    public AccessControlList getBucketAcl(String bucketName){
        if (StringUtils.isBlank(bucketName)) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        return client.getBucketAcl(bucketName);
    }

    /**
     * 修改ACL权限
     * @param bucketName bucketName
     */
    private void setBucketAclPublicRead(String bucketName){
        if (StringUtils.isBlank(bucketName)) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 拷贝对象(同一个bucket中拷贝)
     * @param orgKey 源objectKey
     * @param targetKey 目标objectKey
     */
    public void copyObject(String orgKey, String targetKey){
        if (StringUtils.isBlank(orgKey) || StringUtils.isBlank(targetKey) ) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        client.copyObject(this.bucketName, orgKey, this.bucketName, targetKey);
    }

    /**
     * 拷贝对象(从源bucket拷贝到目标bucket)
     * @param orgBucketName bucketName
     * @param orgKey 源objectKey
     * @param targetBucketName bucketName
     * @param targetKey 目标objectKey
     */
    public void copyObject(String orgBucketName, String orgKey, String targetBucketName, String targetKey){
        if (StringUtils.isBlank(orgBucketName) || StringUtils.isBlank(orgKey) || StringUtils.isBlank(targetBucketName) || StringUtils.isBlank(targetKey) ) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        client.copyObject(orgBucketName, orgKey, targetBucketName, targetKey);
    }

    /**
     * 获取对象
     * @param bucketName bucketName
     * @param key objectKey
     * @return InputStream
     */
    public InputStream getObject(String bucketName, String key){
        if (StringUtils.isBlank(bucketName) || StringUtils.isBlank(key)) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        OSSObject object = client.getObject(bucketName, key);
        return object.getObjectContent();
    }

    /**
     * 获取对象
     * @param key objectKey
     * @return InputStream
     */
    public InputStream getObject( String key){
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException();
        }
        if (client==null){
            this.open();
        }
        OSSObject object = client.getObject(this.bucketName, key);
        return object.getObjectContent();
    }
}
