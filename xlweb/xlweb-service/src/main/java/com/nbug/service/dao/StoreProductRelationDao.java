package com.nbug.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbug.common.model.product.StoreProductRelation;
import com.nbug.common.response.UserRelationResponse;

import java.util.List;

/**
 * 商品点赞和收藏表 Mapper 接口
 */
public interface StoreProductRelationDao extends BaseMapper<StoreProductRelation> {

    /**
     * 用户收藏列表
     * @param uid 用户uid
     */
    List<UserRelationResponse> getUserList(Integer uid);
}
