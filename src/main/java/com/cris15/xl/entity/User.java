package com.cris15.xl.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 14:26
 * Project: demotest
 **/
@TableName("sprint_user")
public class User {

    @TableId
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     * @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
     *     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
     */
    private Date createTime = new Date();

    public User() {
    }

    public User(String id, String username, String password, String phone, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}