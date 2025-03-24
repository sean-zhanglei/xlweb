package com.nbug.module.store.api.storeProductRelation;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCollectAllRequest;
import com.nbug.mico.common.request.UserCollectRequest;
import com.nbug.mico.common.response.UserRelationResponse;
import com.nbug.module.store.service.StoreProductRelationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreProductRelationApiImpl implements StoreProductRelationApi {

    @Resource
    private StoreProductRelationService storeProductRelationService;

    /**
     * 获取用户的收藏数量
     * @param userId 用户uid
     * @return 收藏数量
     */
    @Override
    public CommonResult<Integer> getCollectCountByUid(Integer userId) {
        return success(storeProductRelationService.getCollectCountByUid(userId));
    }

    /**
     * 添加收藏
     * @param request
     * @return
     */
    @Override
    public CommonResult<Boolean> add(UserCollectRequest request) {
        return success(storeProductRelationService.add(request));
    }

    /**
     * 取消收藏
     * @param requestJson
     * @return
     */
    @Override
    public CommonResult<Boolean> delete(String requestJson) {
        return success(storeProductRelationService.delete(requestJson));
    }

    /**
     * 根据商品Id取消收藏
     * @param proId
     * @return
     */
    @Override
    public CommonResult<Boolean> deleteByProId(Integer proId) {
        return success(storeProductRelationService.deleteByProId(proId));
    }

    /**
     * 根据商品Id取消收藏
     * @param proId
     * @return
     */
    @Override
    public CommonResult<Boolean> deleteByProIdAndUid(Integer proId) {
        return success(storeProductRelationService.deleteByProIdAndUid(proId));
    }

    /**
     * 批量收藏
     * @param request
     * @return
     */
    @Override
    public CommonResult<Boolean> all(UserCollectAllRequest request) {
        return success(storeProductRelationService.all(request));
    }

    /**
     * 获取用户收藏列表
     * @param pageParamRequest 分页参数
     * @return List<UserRelationResponse>
     */
    @Override
    public CommonResult<List<UserRelationResponse>> getUserList(PageParamRequest pageParamRequest) {
        return success(storeProductRelationService.getUserList(pageParamRequest));
    }
}
