package com.nbug.module.store.api.storePink;

import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StorePinkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StorePinkApiImpl implements StorePinkApi {

    @Resource
    private StorePinkService storePinkService;

    @Override
    public CommonResult<StorePink> getById(Integer id) {
        return success(storePinkService.getById(id));
    }

    /**
     * 查询拼团列表
     * @param cid
     * @param kid
     */
    @Override
    public CommonResult<List<StorePink>> getListByCidAndKid(Integer cid, Integer kid) {
        return success(storePinkService.getListByCidAndKid(cid, kid));
    }

    @Override
    public CommonResult<Boolean> updateBatchById(List<StorePink> storePinks) {
        return success(storePinkService.updateBatchById(storePinks));
    }

    /**
     * 根据团长拼团id获取拼团人数
     * @param pinkId
     * @return
     */
    @Override
    public CommonResult<Integer> getCountByKid(Integer pinkId) {
        return success(storePinkService.getCountByKid(pinkId));
    }

    @Override
    public CommonResult<Boolean> save(StorePink storePink) {
        return success(storePinkService.save(storePink));
    }
}
