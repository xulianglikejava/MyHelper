package com.cat.bean;/**
 * @ClassName MessageBean
 * @Description TODO
 * @Author 无境科技-许良
 * @Date 2022/9/19 15:09
 * @Version 1.0
 **/

/**
 *@ClassName MessageBean
 *@Description TODO
 *@Author xuliang
 *@Date 2022/9/19 15:09
 *@Version 1.0
 **/

public class MessageBean {

    private String token = "56aa839c329648579ec16e11d2b12123";
    private String title = "56aa839c329648579ec16e11d2b12123";
    private String template = "txt";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "token='" + token + '\'' +
                ", title='" + title + '\'' +
                ", template='" + template + '\'' +
                '}';
    }
}
