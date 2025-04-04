package com.nbug.module.infra.service.wechat.impl;

import com.alibaba.fastjson.JSONObject;
import com.nbug.mico.common.constants.WeChatConstants;
import com.nbug.mico.common.utils.redis.RedisUtil;
import com.nbug.module.infra.service.wechat.WechatNewService;
import com.nbug.module.infra.service.wechat.WechatPublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信公众号Service实现类

 */
@Service
public class WechatPublicServiceImpl implements WechatPublicService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private WechatNewService wechatNewService;

    /**
     * 获取公众号自定义菜单
     * @return Object
     */
    @Override
    public Object getCustomizeMenus() {
        Object list = redisUtil.get(WeChatConstants.REDIS_PUBLIC_MENU_KEY);
        if (list == null || "".equals(list)) {
            //如果没有， 去读取
            JSONObject tagsList = wechatNewService.getPublicCustomMenu();
            redisUtil.set(WeChatConstants.REDIS_PUBLIC_MENU_KEY, tagsList);
            list = tagsList;
        }
        return list;
    }

    /**
     * 保存自定义菜单
     * @param data 菜单json
     * @return Boolean
     */
    @Override
    public Boolean createMenus(String data) {
        Boolean create = wechatNewService.createPublicCustomMenu(data);
        if (!create) {
            return create;
        }
        // 清除历史缓存
        if (redisUtil.exists(WeChatConstants.REDIS_PUBLIC_MENU_KEY)) {
            redisUtil.delete(WeChatConstants.REDIS_PUBLIC_MENU_KEY);
        }
        return create;
    }

    /**
     * 删除自定义菜单
     * @return Boolean
     */
    @Override
    public Boolean deleteMenus() {
        Boolean delete = wechatNewService.deletePublicCustomMenu();
        if (!delete) {
            return delete;
        }
        // 清除历史缓存
        if (redisUtil.exists(WeChatConstants.REDIS_PUBLIC_MENU_KEY)) {
            redisUtil.delete(WeChatConstants.REDIS_PUBLIC_MENU_KEY);
        }
        return delete;
    }
}
