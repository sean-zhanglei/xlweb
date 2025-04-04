package com.nbug.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.nbug.common.constants.Constants;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.common.utils.RedisUtil;
import com.nbug.common.utils.ValidateCodeUtil;
import com.nbug.admin.service.ValidateCodeService;
import com.nbug.admin.vo.ValidateCode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * ValidateCodeService 实现类

 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private RedisUtil redisAdminUtil;

    /**
     * 获取验证码信息
     */
    @Override
    public ValidateCode get() {
        ValidateCodeUtil.Validate randomCode = ValidateCodeUtil.getRandomCode();//直接调用静态方法，返回验证码对象
        if (ObjectUtil.isNull(randomCode)) {
            return null;
        }

        String value = randomCode.getValue().toLowerCase();
        String md5Key = DigestUtils.md5Hex(value);
        String redisKey = getRedisKey(md5Key);
        redisAdminUtil.set(redisKey, value, 5L, TimeUnit.MINUTES);   //5分钟过期
        String base64Str = randomCode.getBase64Str();
        return new ValidateCode(md5Key, XlwebUtil.getBase64Image(base64Str));
    }

    /**
     * 获取redis key
     * @param md5Key value的md5加密值
     */
    public String getRedisKey(String md5Key) {
        return Constants.VALIDATE_REDIS_KEY_PREFIX + md5Key;
    }

    /**
     * 验证
     */
    public Boolean check(String key, String code) {
        if (!redisAdminUtil.exists(getRedisKey(key))) {
            throw new XlwebException("验证码错误");
        }
        Object redisValue = redisAdminUtil.get(getRedisKey(key));
        if (ObjectUtil.isNull(redisValue)) {
            return false;
        }
        return redisValue.equals(code.toLowerCase());
    }
}

