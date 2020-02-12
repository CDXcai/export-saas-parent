package com.itheima.service.weinXinLogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.itheima.domain.weiXinLogin.WeiXinLogin;

public interface WeiXinLoginService {
    // 查询微信是否绑定了用户
    WeiXinLogin findByOpenId(String openid);

    // 添加微信绑定信息
    void insert(WeiXinLogin weiXinLogin);

}
