package com.nbug.front.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.nbug.common.constants.SmsConstants;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.model.user.User;
import com.nbug.common.request.LoginMobileRequest;
import com.nbug.common.request.LoginRequest;
import com.nbug.common.response.LoginResponse;
import com.nbug.common.token.FrontTokenComponent;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.common.utils.DateUtil;
import com.nbug.common.utils.RedisUtil;
import com.nbug.front.service.LoginService;
import com.nbug.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 移动端登录服务类

 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FrontTokenComponent tokenComponent;

    /**
     * 账号密码登录
     *
     * @return LoginResponse
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userService.getByPhone(loginRequest.getPhone());
        if (ObjectUtil.isNull(user)) {
            throw new XlwebException("此账号未注册");
        }
        if (!user.getStatus()) {
            throw new XlwebException("此账号被禁用");
        }

        // 校验密码
        String password = XlwebUtil.encryptPassword(loginRequest.getPassword(), loginRequest.getPhone());
        if (!user.getPwd().equals(password)) {
            throw new XlwebException("密码错误");
        }

        LoginResponse loginResponse = new LoginResponse();
        String token = tokenComponent.createToken(user);
        loginResponse.setToken(token);

        //绑定推广关系
        if (loginRequest.getSpreadPid() > 0) {
            bindSpread(user, loginRequest.getSpreadPid());
        }

        // 记录最后一次登录时间
        user.setLastLoginTime(DateUtil.nowDateTime());
        userService.updateById(user);

        loginResponse.setUid(user.getUid());
        loginResponse.setNikeName(user.getNickname());
        loginResponse.setPhone(user.getPhone());
        return loginResponse;
    }

    /**
     * 手机号验证码登录
     *
     * @param loginRequest 登录请求信息
     * @return LoginResponse
     */
    @Override
    public LoginResponse phoneLogin(LoginMobileRequest loginRequest) {
        //检测验证码
        checkValidateCode(loginRequest.getPhone(), loginRequest.getCaptcha());
        Integer spreadPid = Optional.ofNullable(loginRequest.getSpreadPid()).orElse(0);
        //查询手机号信息
        User user = userService.getByPhone(loginRequest.getPhone());
        if (ObjectUtil.isNull(user)) {// 此用户不存在，走新用户注册流程
            user = userService.registerPhone(loginRequest.getPhone(), spreadPid);
        } else {
            if (!user.getStatus()) {
                throw new XlwebException("当前账户已禁用，请联系管理员！");
            }
            if (user.getSpreadUid().equals(0) && spreadPid > 0) {
                // 绑定推广关系
                bindSpread(user, spreadPid);
            }
            // 记录最后一次登录时间
            user.setLastLoginTime(DateUtil.nowDateTime());
            boolean b = userService.updateById(user);
            if (!b) {
                logger.error("用户登录时，记录最后一次登录时间出错,uid = " + user.getUid());
            }
        }

        //生成token
        LoginResponse loginResponse = new LoginResponse();
        String token = tokenComponent.createToken(user);
        loginResponse.setToken(token);
        loginResponse.setUid(user.getUid());
        loginResponse.setNikeName(user.getNickname());
        loginResponse.setPhone(user.getPhone());
        return loginResponse;
    }

    /**
     * 检测手机验证码
     *
     * @param phone 手机号
     * @param code  验证码
     */
    private void checkValidateCode(String phone, String code) {
        Object validateCode = redisUtil.get(SmsConstants.SMS_VALIDATE_PHONE + phone);
        if (ObjectUtil.isNull(validateCode)) {
            throw new XlwebException("验证码已过期");
        }
        if (!validateCode.toString().equals(code)) {
            throw new XlwebException("验证码错误");
        }
        //删除验证码
        redisUtil.delete(SmsConstants.SMS_VALIDATE_PHONE + phone);
    }

    /**
     * 绑定分销关系
     *
     * @param user      User 用户user类
     * @param spreadUid Integer 推广人id
     * @return Boolean
     * 1.判断分销功能是否启用
     * 2.判断分销模式
     * 3.根据不同的分销模式校验
     * 4.指定分销，只有分销员才可以分销，需要spreadUid是推广员才可以绑定
     * 5.满额分销，同上
     * 6.人人分销，可以直接绑定
     */
    @Override
    public Boolean bindSpread(User user, Integer spreadUid) {
        Boolean checkBingSpread = userService.checkBingSpread(user, spreadUid, "old");
        if (!checkBingSpread) return false;

        user.setSpreadUid(spreadUid);
        user.setSpreadTime(DateUtil.nowDateTime());

        Boolean execute = transactionTemplate.execute(e -> {
            userService.updateById(user);
            userService.updateSpreadCountByUid(spreadUid, "add");
            return Boolean.TRUE;
        });
        if (!execute) {
            logger.error(StrUtil.format("绑定推广人时出错，userUid = {}, spreadUid = {}", user.getUid(), spreadUid));
        }
        return execute;
    }

    /**
     * 推出登录
     * @param request HttpServletRequest
     */
    @Override
    public void loginOut(HttpServletRequest request) {
        tokenComponent.logout(request);
    }
}
