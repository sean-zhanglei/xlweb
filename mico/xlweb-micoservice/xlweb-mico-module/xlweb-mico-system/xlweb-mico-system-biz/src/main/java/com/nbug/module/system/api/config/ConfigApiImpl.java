package com.nbug.module.system.api.config;

import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.ExpressSheetVo;
import com.nbug.module.system.service.SystemConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ConfigApiImpl implements ConfigApi {

    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 根据 name 获取 value 找不到抛异常
     * @param name menu name
     * @return String
     */
    @Override
    public CommonResult<String> getValueByKeyException(String name) {
        String value = systemConfigService.getValueByKeyException(name);
        if (null == value) {
            throw new XlwebException("没有找到"+ name +"数据");
        }

        return CommonResult.success(value);
    }

    /**
     * 根据menu name 获取 value
     * @param key menu name
     * @return String
     */
    @Override
    public CommonResult<String> getValueByKey(String key) {
        String value = systemConfigService.getValueByKey(key);
        if (null == value) {
            throw new XlwebException("没有找到"+ key +"数据");
        }

        return CommonResult.success(value);
    }

    /**
     * 获取面单默认配置信息
     * @return ExpressSheetVo
     */
    @Override
    public CommonResult<ExpressSheetVo> getDeliveryInfo() {
        return CommonResult.success(systemConfigService.getDeliveryInfo());
    }

    /**
     * 同时获取多个配置
     * @param keys 多个配置key
     * @return 查询到的多个结果
     */
    @Override
    public CommonResult<List<String>> getValuesByKes(List<String> keys) {
        List<String> value = systemConfigService.getValuesByKes(keys);
        if (null == value) {
            throw new XlwebException("没有找到"+ keys +"数据");
        }

        return CommonResult.success(value);
    }

    /**
     * 根据formId查询数据
     * @param formId Integer id
     * @return HashMap<String, String>
     */
    @Override
    public CommonResult<HashMap<String, String>> info(Integer formId) {
        return CommonResult.success(systemConfigService.info(formId));
    }

    /**
     * 获取颜色配置
     * @return
     */
    @Override
    public CommonResult<SystemConfig> getColorConfig() {
        return CommonResult.success(systemConfigService.getColorConfig());
    }
}
