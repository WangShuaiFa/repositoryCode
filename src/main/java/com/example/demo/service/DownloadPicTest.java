package com.example.demo.service;

import com.example.demo.storage.PicInfo;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//import com.dao.LogPicVo;

@Service
public class DownloadPicTest {






    /**
     * 发送get请求,  下载图片
     *
     * @param
     *
     * @return
     */
    public static PicInfo httpGetImg(CloseableHttpClient client, String imgUrl, String savePath,int loc) {


        PicInfo picInfo=new PicInfo();
        // 发送get请求
        HttpGet request = new HttpGet(imgUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(50000).setConnectTimeout(50000).build();

        //设置请求头
        request.setHeader( "User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.79 Safari/537.1" );

        request.setConfig(requestConfig);
        try {
        //    List<Map<String,String>> num=new ArrayList<Map<String,String>>();

            CloseableHttpResponse response = client.execute(request);
            HashMap<Integer,Integer> successMap=new HashMap<>();
            HashMap<Integer,Integer> failMap=new HashMap<>();
            //failMap.put(0,0);*/
            //请求成功才下载
            if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();

                InputStream in = entity.getContent();

                FileUtils.copyInputStreamToFile(in, new File(savePath));
                System.out.println("下载图片成功:"+imgUrl);
                picInfo.setSuccessLocation(loc);

                successMap.put(loc,loc);
                picInfo.setSuccessmap(successMap);
                picInfo.setIsdownloadsuccess(true);
            }

            else if (HttpStatus.SC_NOT_FOUND==response.getStatusLine().getStatusCode()){
                System.out.println("下载图片失败:"+imgUrl);
                picInfo.setFailLocation(loc);

                failMap.put(loc,loc);
                picInfo.setFailmap(failMap);



           /*     failMap.put(loc,loc);
                picInfo.setFailmap(successMap);*/
            }
            else {
                System.out.println("下载异常");
            }
            return picInfo;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            request.releaseConnection();

        }
    }


    /*public static void main(String[] args) {

     *//*   CloseableHttpClient client =null;

        try {
            client =   HttpClients.createDefault();
     *//**//*       String  url ="https://picsum.photos/300/150/?image=";*//**//*
            //从cbs数据库中查询到
         String url="http://49.4.11.20:81/group1/M00/03/FC/wKgLZF2MN9SAIYlIAAC0NIsc5YE215.jpg" ;
            String  path="d:/pic";
            httpGetImg(client,url, path+"/"+1+".jpg");
           *//**//* for(int i=0;i<10;i++){ //下载100张图片
             *//**//**//**//*   httpGetImg(client,url+i, path+"/"+i+".jpg");*//**//**//**//*
                System.out.println("ok");
            }*//**//*
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*//*

    }*/
}