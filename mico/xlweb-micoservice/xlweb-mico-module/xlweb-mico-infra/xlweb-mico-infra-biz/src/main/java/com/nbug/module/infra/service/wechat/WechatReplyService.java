package com.nbug.module.infra.service.wechat;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.wechat.WechatReply;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.WechatReplyRequest;
import com.nbug.mico.common.request.WechatReplySearchRequest;

import java.util.List;

/**
 *  WechatReplyService 接口

 */
public interface WechatReplyService extends IService<WechatReply> {

    /**
     * 列表
     * @param request 请求参数
     * @param pageParamRequest 分页类参数
     * @return List<WechatReply>
     */
    List<WechatReply> getList(WechatReplySearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增微信关键字回复表
     * @param wechatReply 新增参数
     */
    Boolean create(WechatReply wechatReply);

    /**
     * 根据关键字查询数据
     * @param keywords 新增参数
     * @return WechatReply
     */
    WechatReply getVoByKeywords(String keywords);

    /**
     * 查询微信关键字回复表信息
     * @param id Integer
     */
    WechatReply getInfo(Integer id);

    /**
     * 修改微信关键字回复表
     * @param wechatReplyRequest 修改参数
     */
    Boolean updateReply(WechatReplyRequest wechatReplyRequest);

    /**
     * 修改状态
     * @param id integer id
     * @param status boolean 状态
     */
    Boolean updateStatus(Integer id, Boolean status);
}
