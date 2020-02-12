package com.itheima.dao.weiXinlogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.itheima.domain.weiXinLogin.WeiXinLogin;

public interface WeiXinloginDao {
    // 查询微信绑定的用户
    WeiXinLogin findByOpenId(String openid);


    // 添加微信绑定信息
    void insert(WeiXinLogin weiXinLogin);
}
