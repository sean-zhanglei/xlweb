package com.nbug.module.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCombinationRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.request.StorePinkRequest;
import com.nbug.mico.common.response.CombinationDetailResponse;
import com.nbug.mico.common.response.CombinationHeaderResponse;
import com.nbug.mico.common.response.CombinationIndexResponse;
import com.nbug.mico.common.response.GoPinkResponse;
import com.nbug.mico.common.response.StoreCombinationH5Response;
import com.nbug.mico.common.response.StoreCombinationResponse;
import com.nbug.mico.common.response.StoreProductInfoResponse;

import java.util.List;
import java.util.Map;

/**
 * StorePinkService

 */
public interface StoreCombinationService extends IService<StoreCombination> {

    /**
     * 分页显示拼团商品表
     * @param request   搜索条件
     * @param pageParamRequest  分页参数
     */
    PageInfo<StoreCombinationResponse> getList(StoreCombinationSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增拼团商品
     */
    Boolean saveCombination(StoreCombinationRequest request);

    /**
     * 删除拼团商品
     */
    Boolean deleteById(Integer id);

    /**
     * 编辑拼团商品
     */
    Boolean updateCombination(StoreCombinationRequest request);

    /**
     * 查询拼团商品详情
     * @return StoreProductInfoResponse
     */
    StoreProductInfoResponse getAdminDetail(Integer id);

    /**
     * 修改拼团商品状态
     */
    Boolean updateCombinationShow(Integer id, Boolean isShow);

    /**
     * admin拼团统计
     */
    Map<String, Object> getAdminStatistics();

    /**
     * H5拼团商品列表
     */
    List<StoreCombinationH5Response> getH5List(PageParamRequest pageParamRequest);

    /**
     * H5拼团商品详情
     * @param id    拼团商品编号
     */
    CombinationDetailResponse getH5Detail(Integer id);

    /**
     * 去拼团
     * @param pinkId 拼团团长单ID
     */
    GoPinkResponse goPink(Integer pinkId);

    /**
     * 更多拼团信息
     */
    PageInfo<StoreCombination> getMore(PageParamRequest pageParamRequest, Integer comId);

    /**
     * 取消拼团
     */
    Boolean removePink(StorePinkRequest storePinkRequest);

    /**
     * 后台任务批量操作库存
     */
    void consumeProductStock();

    /**
     * 获取当前时间的拼团商品
     */
    List<StoreCombination> getCurrentBargainByProductId(Integer productId);

    /**
     * 商品是否存在拼团活动
     * @param productId 商品编号
     */
    Boolean isExistActivity(Integer productId);

    /**
     * 查询带异常
     * @param combinationId 拼团商品id
     * @return StoreCombination
     */
    StoreCombination getByIdException(Integer combinationId);

    /**
     * 添加/扣减库存
     * @param id 秒杀商品id
     * @param num 数量
     * @param type 类型：add—添加，sub—扣减
     */
    Boolean operationStock(Integer id, Integer num, String type);

    /**
     * 拼团首页数据
     * @return CombinationIndexResponse
     */
    CombinationIndexResponse getIndexInfo();

    /**
     * 拼团列表header
     * @return CombinationHeaderResponse
     */
    CombinationHeaderResponse getHeader();
}
