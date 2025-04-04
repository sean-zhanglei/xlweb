package com.nbug.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.model.user.UserToken;
import com.nbug.module.user.dal.UserTokenDao;
import com.nbug.module.user.service.UserTokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserTokenServiceImpl 接口实现

 */
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserToken> implements UserTokenService {

    @Resource
    private UserTokenDao dao;

    /**
     * 检测token是否存在
     * @param token String openId
     * @param type int 类型
     * @return UserToken
     */
    @Override
    public UserToken getByOpenidAndType(String token, int type) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getType, type).eq(UserToken::getToken, token);
        return dao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 绑定token关系
     * @param token String token
     * @param type int 类型
     * @author Mr.Zhang
     * @since 2020-05-25
     */
    @Override
    public void bind(String token, int type, Integer userId) {
        UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setType(type);
        userToken.setUid(userId);
        save(userToken);
    }

    @Override
    public UserToken getTokenByUserId(Integer userId, int type) {
        LambdaQueryWrapper<UserToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserToken::getUid, userId).eq(UserToken::getType, type);
        return dao.selectOne(lambdaQueryWrapper);
    }
}

