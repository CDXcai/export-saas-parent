package com.itheima.domain.weiXinLogin;

import com.itheima.domain.BaseEntity;

import java.io.Serializable;

public class WeiXinLogin extends BaseEntity implements Serializable {
    private String  openId;// 微信用户唯一标示
    private String userId;// 用户id

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
