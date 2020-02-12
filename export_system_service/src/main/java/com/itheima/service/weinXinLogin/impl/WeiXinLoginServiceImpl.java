package com.itheima.service.weinXinLogin.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.itheima.dao.weiXinlogin.WeiXinloginDao;
import com.itheima.domain.weiXinLogin.WeiXinLogin;
import com.itheima.service.weinXinLogin.WeiXinLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiXinLoginServiceImpl implements WeiXinLoginService {
    @Autowired
    private WeiXinloginDao weiXinloginDao;
    @Override
    public WeiXinLogin findByOpenId(String openid) {
        // 查询微信绑定的用户
       return weiXinloginDao.findByOpenId(openid);
    }

    @Override
    public void insert(WeiXinLogin weiXinLogin) {
        // 添加微信绑定信息
        weiXinloginDao.insert(weiXinLogin);
    }
}
