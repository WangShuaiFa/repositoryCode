package com.example.demo.storage;

import java.util.HashMap;

public class PicInfo {

    //forumList中图片下载成功位置
    Integer successLocation;
    //forumList中图片下载失败位置
    Integer failLocation;

    public HashMap<Integer, Integer> getFailmap() {
        return failmap;
    }

    public void setFailmap(HashMap<Integer, Integer> failmap) {
        this.failmap = failmap;
    }

    //记录下载成功数量
    HashMap<Integer,Integer> successmap;
    //记录下载失败数量
    HashMap<Integer,Integer> failmap;
    //判断是否下载成功
    boolean isdownloadsuccess;

    public Integer getSuccessLocation() {
        return successLocation;
    }

    public void setSuccessLocation(Integer successLocation) {
        this.successLocation = successLocation;
    }

    public Integer getFailLocation() {
        return failLocation;
    }

    public void setFailLocation(Integer failLocation) {
        this.failLocation = failLocation;
    }

    public HashMap<Integer, Integer> getSuccessmap() {
        return successmap;
    }

    public void setSuccessmap(HashMap<Integer, Integer> successmap) {
        this.successmap = successmap;
    }

    public boolean isIsdownloadsuccess() {
        return isdownloadsuccess;
    }

    public void setIsdownloadsuccess(boolean isdownloadsuccess) {
        this.isdownloadsuccess = isdownloadsuccess;
    }






}
