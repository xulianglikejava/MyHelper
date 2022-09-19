package com.cat.services;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *@ClassName MessageController
 *@Description 本方法通过pushplus发送通知给用户
 *@Author xuliang
 *@Date 2022/9/19 11:35
 *@Version 1.0
 **/
public class MessageService {

    /**
     * 本方法通过pushplus发送通知给用户
     * @param title - 信件内容
     *
     * @throws Exception - 如果网络不佳或者余额不足则抛出异常
     */
    public static void sendMsg( String title) throws Exception {
        String token = "56aa839c329648579ec16e11d2b12123";
        // 创建一个HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建一个post对象
        HttpPost post = new HttpPost("http://www.pushplus.plus/send");
        JSONObject json = new JSONObject();
        json.put("token","56aa839c329648579ec16e11d2b12123");
        json.put("title",title);
        json.put("content",title);
        json.put("template","txt");

        // 设置请求的内容
        StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
        stringEntity.setContentType("application/json");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);


        // 执行post请求
        CloseableHttpResponse response = client.execute(post);
        // 获取响应码l
        int statusCode = response.getStatusLine().getStatusCode();
        // 响应处理
        if (statusCode == 200) {
            System.out.println("发送消息成功!");
        } else {
            System.out.println("发送消息失败!\n失败原因:" + statusCode);
        }

    }

}
