package com.example.demo.controller;

import com.example.demo.entity.Dev_setup_pic;
import com.example.demo.service.DownloadPicTest;
import com.example.demo.service.HuaweiObsClientUtil;
import com.example.demo.storage.OssInfo;
import com.example.demo.storage.PicInfo;
import com.obs.services.ObsClient;
import com.obs.services.model.AuthTypeEnum;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownloadUpload {
    private static AuthTypeEnum authType = AuthTypeEnum.OBS;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        CloseableHttpClient client = null;


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //本地迁移源库
//            String url = "jdbc:mysql://127.0.0.1:3306/cbs?serverTimezone=GMT%2B8";
//            Connection con = DriverManager.getConnection(url, "root", "root");
//            Statement statement = con.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * from dev_setup_pic ");
            //正式迁移源库
            String url = "jdbc:mysql://114.116.55.251:3306/cbs?serverTimezone=GMT%2B8";
            Connection con = DriverManager.getConnection(url, "root", "!Q@W#E4r5t");
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from dev_setup_pic ");

            List<Dev_setup_pic> forumList = new ArrayList<Dev_setup_pic>();
            while (resultSet.next()) {
                Dev_setup_pic dev_setup_pic = new Dev_setup_pic();
                dev_setup_pic.setId(resultSet.getString(1));
                dev_setup_pic.setSetup_log_id(resultSet.getString(2));
                dev_setup_pic.setPic_uri(resultSet.getString(3));
                dev_setup_pic.setPic_index(resultSet.getInt(4));
                forumList.add(dev_setup_pic);

            }

            client = HttpClients.createDefault();


            DownloadPicTest pic = new DownloadPicTest();

            //创建huweiobs对象
            HuaweiObsClientUtil huweiobs = new HuaweiObsClientUtil();

            //初始化OSSClient
            ObsClient obsClient = HuaweiObsClientUtil.getObSClient();
            // ObsClient obsClient=huweiobs.getObSClient();
            //用来记录下载成功数量
            HashMap<Integer, Integer> successMap = new HashMap<>();
            for (int i = 0; i < forumList.size(); i++) {
                //下载图片
                String uri = "http://49.4.11.20:81" + forumList.get(i).getPic_uri();
                String id = forumList.get(i).getId();
                String setup_log_id = forumList.get(i).getSetup_log_id();
                Integer pic_index = forumList.get(i).getPic_index();

                String path = "d:/image/optimize";
                PicInfo picInfo = pic.httpGetImg(client, uri, path + "/" + i + ".jpg", i);
                /* if(picInfo.getSuccessmap().size()==1){
                 *//*      successMap.put(i,i);
               }*/
                if (picInfo.isIsdownloadsuccess()) {

                    successMap.put(i, i);
                } else if (picInfo.isIsdownloadsuccess() == false) {
                    System.out.println("不放进successMap");
                }
                //上传图片
                if (picInfo.getSuccessLocation() != null) {
                    String file = "d:/image/optimize" + "/" + picInfo.getSuccessLocation() + ".jpg";
                    InputStream fileInputStream = new FileInputStream(file);
                    OssInfo ossinfo = huweiobs.upload(obsClient, fileInputStream, file);
                    ossinfo.setId(id);
                    ossinfo.setSetup_log_id(setup_log_id);
                    ossinfo.setUrl(ossinfo.getUrl());
                    ossinfo.setPic_index(pic_index);
                    System.out.println("id为：" + ossinfo.getId());
                    System.out.println("setup_log_id为：" + ossinfo.getSetup_log_id());
                    System.out.println("url为：" + ossinfo.getUrl());
                    System.out.println("pic_index为：" + ossinfo.getPic_index());
                    //保存到新的数据库
                    Connection conn;
                    PreparedStatement stmt;
                    //String driver = "com.mysql.jdbc.Driver";
                    //本地迁移目的库
//                    String urii = "jdbc:mysql://localhost:3306/test001?serverTimezone=GMT%2B8";
//                    String user = "root";
//                    String password = "root";
//                    String sql = "replace into dev_setup_pic values (?,?,?,?)";
                    //正式迁移目的库
                    String urii = "jdbc:mysql://114.116.55.251:3306/sjz_cbs?serverTimezone=GMT%2B8";
                    String user = "root";
                    String password = "!Q@W#E4r5t";
                    String sql = "replace into dev_setup_pic values (?,?,?,?)";

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        conn = DriverManager.getConnection(urii, user, password);
                        stmt = (PreparedStatement) conn.prepareStatement(sql);
                        stmt.setString(1, ossinfo.getId());
                        stmt.setString(2, ossinfo.getSetup_log_id());
                        stmt.setString(3, ossinfo.getUrl());
                        stmt.setInt(4, ossinfo.getPic_index());
                        stmt.executeUpdate();

                    } catch (ClassNotFoundException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    } catch (SQLException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }


                }
            }
            System.out.println("图片下载成功数量：" + successMap.size());
            Integer failnum = forumList.size() - successMap.size();
            System.out.println("图片下载失败数量：" + failnum);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
