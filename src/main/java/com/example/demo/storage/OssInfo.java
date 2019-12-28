package com.example.demo.storage;


public class OssInfo {
    /**
     * dev_setup_pic表中 pic_uri
     */
    private String  url;
    /**
     * dev_setup_pic表中id
     */
    private String  id;
    /**
     *  dev_setup_pic表中setup_log_id
     */
    private String setup_log_id;
    /**
     *  dev_setup_pic表中pic_index
     */
    private Integer pic_index;

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

    public Integer getPic_index() {
        return pic_index;
    }

    public void setPic_index(Integer pic_index) {
        this.pic_index = pic_index;
    }





    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 文件路径URI
     */
    private String key;

    /**
     * URL过期时间
     */
    private Long expireTime;
}
