package com.nbug.mico.common.token;

import cn.hutool.core.util.StrUtil;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.utils.RequestUtil;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.mico.common.vo.LoginUserVo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理

 */
@Component
public class FrontTokenComponent {

    @Resource
    private RedisUtil redisUtil;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    private static final Long MILLIS_MINUTE = 60 * 1000L;

    // 令牌有效期（默认30分钟） todo 调试期改为5小时
//    private static final int expireTime = 30;
    private static final int expireTime = 5 * 60;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            return redisUtil.get(userKey);
        }
        return null;
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StrUtil.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisUtil.delete(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param user 用户信息
     * @return 令牌
     */
    public String createToken(User user) {
        LoginUserVo loginUserVo = new LoginUserVo();
        String token = UUID.randomUUID().toString().replace("-", "");
        loginUserVo.setToken(token);
        loginUserVo.setLoginTime(System.currentTimeMillis());
        loginUserVo.setExpireTime(loginUserVo.getLoginTime() + expireTime * MILLIS_MINUTE);
        loginUserVo.setFrontUser(user);
        redisUtil.set(getTokenKey(token), loginUserVo, Constants.TOKEN_EXPRESS_MINUTES, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser LoginUserVo
     */
    public void verifyToken(LoginUserVo loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUserVo loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisUtil.set(userKey, loginUser, (long) expireTime, TimeUnit.MINUTES);
    }

    /**
     * 获取请求token
     *
     * @param request HttpServletRequest
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_AUTHORIZATION_KEY);
        if (StrUtil.isNotEmpty(token) && token.startsWith(Constants.USER_TOKEN_REDIS_KEY_PREFIX)) {
            token = token.replace(Constants.USER_TOKEN_REDIS_KEY_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.USER_TOKEN_REDIS_KEY_PREFIX + uuid;
    }

    /**
     * 推出登录
     * @param request HttpServletRequest
     */
    public void logout(HttpServletRequest request) {
        String token = getToken(request);
        delLoginUser(token);
    }

    /**
     * 获取当前登录用户id
     */
    public Integer getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = getToken(request);
        if (StrUtil.isEmpty(token)) {
            return null;
//            throw new XlwebException("登录信息已过期，请重新登录！");
        }
        LoginUserVo loginUserVo  = redisUtil.get(getTokenKey(token));
        return loginUserVo.getFrontUser().getUid();
    }

    //路由在此处，则返回true，无论用户是否登录都可以访问
    public boolean checkRouter(String uri) {
        String[] routerList = {
                "api/front/store/product/detail",
                "api/front/store/coupons",
                "api/front/store/index",
                "api/front/store/bargain/list",
                "api/front/store/combination/list",
                "api/front/store/index/product",
                "api/front/store/combination/index",
                "api/front/store/bargain/index",
                "api/front/store/product/list",
                "api/front/store/product/sku/detail",
                "api/front/store/product/leaderboard",
                "api/front/user/index/color/config",

                "api/front/index/get/version",
                "api/front/image/domain",
                "api/front/user/index/info"
        };

        return ArrayUtils.contains(routerList, uri);
    }

    public Boolean check(String token, HttpServletRequest request){

        try {
            boolean exists = redisUtil.exists(getTokenKey(token));
            if(exists){
                Integer uid = redisUtil.get(getTokenKey(token));
                redisUtil.set(getTokenKey(token), uid, Constants.TOKEN_EXPRESS_MINUTES, TimeUnit.MINUTES);
            }else{
                //判断路由，部分路由不管用户是否登录/token过期都可以访问
                exists = checkRouter(RequestUtil.getUri(request));
            }
            return exists;
        }catch (Exception e){
            return false;
        }
    }
}
