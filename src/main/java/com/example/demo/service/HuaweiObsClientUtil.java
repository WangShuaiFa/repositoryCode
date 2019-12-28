package com.example.demo.service;

import com.example.demo.storage.OssInfo;
import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.exception.ObsException;
import com.obs.services.model.AuthTypeEnum;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class HuaweiObsClientUtil {
    //log日志
    private Logger logger = LoggerFactory.getLogger(getClass());
    //华为云API的内或外网域名
    private static String hwyunEndPoint;
    //华为云API的密钥Access Key ID
    private static String hwyunAccessKeyId;
    //华为云API的密钥Access Key Secret
    private static String hwyunAccessKeySecret;
    //华为云API的bucket名称
    private static String hwyunBucketName;

    private static AuthTypeEnum authType = AuthTypeEnum.OBS;
    private static ObsClient obsClient;


    static {

        hwyunEndPoint = ObsClientConstants.hwyunEndPoint;
        hwyunAccessKeyId = ObsClientConstants.hwyunAccessKeyId;
        hwyunAccessKeySecret = ObsClientConstants.hwyunAccessKeySecret;
        hwyunBucketName = ObsClientConstants.hwyunBucketName;
    }

    //获取Obsclient对象
    public static ObsClient getObSClient() {
        ObsConfiguration obsConfiguration = new ObsConfiguration();
        obsConfiguration.setEndPoint(hwyunEndPoint);
        obsConfiguration.setAuthType(authType);
        return new ObsClient(hwyunAccessKeyId, hwyunAccessKeySecret, obsConfiguration);
    }

    /*
     *传入参数为obsClient,fileInputStream,path
     * */
    public OssInfo upload(ObsClient obsClient, InputStream fileInputStream, String file) throws FileNotFoundException {

        // 创建日期文件夹
        final String keySuffixWithSlash = DateUtil.format(new Date(), DateUtil.DATE_PATTERN.YYYYMMDD);
        try {
            //上传图片
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("jpg");

            String uuidStr = GenUUIdUtil.uuid();
            String fileType = file.substring(file.lastIndexOf("."));

            String path = uuidStr + "_" + DateUtil.getDateTime(DateUtil.DATE_PATTERN.HHMMSS) + fileType;
            String uri = keySuffixWithSlash + "/" + path;
            PutObjectResult putObjectResult = obsClient.putObject(hwyunBucketName,
                    uri, fileInputStream, metadata);

            if (logger.isInfoEnabled()) {
                logger.info("info: huawei cloud oss upload file result={}.",
                        putObjectResult);
            }

            //String url = putObjectResult.getObjectUrl();
            //System.out.println("可访问url:" + url);
            //测试截取长度
            //String saveurl=url.substring(64);
            //正式迁移截取长度
            //String saveurl=url.substring(59);
            String saurl = "http://file.cbs.comtom.cn/" + uri;
            OssInfo ossInfo = new OssInfo();
            ossInfo.setUrl(saurl);
            ossInfo.setKey(file);
            return ossInfo;
        } catch (ObsException e) {
            logger.error("error: upload file failed.", e);
            return null;

        }
    }


}
