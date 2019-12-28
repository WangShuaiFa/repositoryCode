package com.example.demo.entity;

public class Dev_setup_pic {
    private String id;
    private String setup_log_id;
    private String pic_uri;
    private Integer pic_index;

    public Integer getPic_index() {
        return pic_index;
    }

    public void setPic_index(Integer pic_index) {
        this.pic_index = pic_index;
    }

    public void setPic_index(int pic_index) {
        this.pic_index = pic_index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetup_log_id() {
        return setup_log_id;
    }

    public void setSetup_log_id(String setup_log_id) {
        this.setup_log_id = setup_log_id;
    }

    public String getPic_uri() {
        return pic_uri;
    }

    public void setPic_uri(String pic_uri) {
        this.pic_uri = pic_uri;
    }




}
