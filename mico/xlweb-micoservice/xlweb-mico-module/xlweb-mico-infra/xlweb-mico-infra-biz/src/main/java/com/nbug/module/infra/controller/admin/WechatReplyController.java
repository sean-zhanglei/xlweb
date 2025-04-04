package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.model.wechat.WechatReply;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.WechatReplyRequest;
import com.nbug.mico.common.request.WechatReplySearchRequest;
import com.nbug.module.infra.service.wechat.WechatReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 微信关键字回复表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("infra/wechat/keywords/reply")
@Tag(name = "管理后台 - 微信开放平台 -- 微信关键字回复")
public class WechatReplyController {

    @Autowired
    private WechatReplyService wechatReplyService;

    /**
     * 分页显示微信关键字回复表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<WechatReply>>  getList(@Validated WechatReplySearchRequest request, @Validated PageParamRequest pageParamRequest) {
        CommonPage<WechatReply> wechatReplyCommonPage = CommonPage.restPage(wechatReplyService.getList(request, pageParamRequest));
        return CommonResult.success(wechatReplyCommonPage);
    }

    /**
     * 新增微信关键字回复表
     * @param wechatReplyRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated WechatReplyRequest wechatReplyRequest) {
        WechatReply wechatReply = new WechatReply();
        BeanUtils.copyProperties(wechatReplyRequest, wechatReply);
        wechatReplyService.create(wechatReply);
        return CommonResult.success("success");
    }

    /**
     * 删除微信关键字回复表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        wechatReplyService.removeById(id);
        return CommonResult.success("success");
    }

    /**
     * 修改微信关键字回复表
     * @param wechatReplyRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestBody @Validated WechatReplyRequest wechatReplyRequest) {
        wechatReplyService.updateReply(wechatReplyRequest);
        return CommonResult.success("success");
    }

    /**
     * 修改状态
     * @param id integer id
     * @param status boolean 状态
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:status')")
    @Operation(summary = "状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam(value = "id") Integer id, @RequestParam(value = "status") Boolean status) {
        wechatReplyService.updateStatus(id, status);
        return CommonResult.success("success");
    }

    /**
     * 查询微信关键字回复表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<WechatReply> info(@RequestParam(value = "id") Integer id) {
        WechatReply wechatReply = wechatReplyService.getInfo(id);
        return CommonResult.success(wechatReply);
   }

    /**
     * 根据关键字查询数据
     * @param keywords String 关键字
     */
    @PreAuthorize("hasAuthority('admin:wechat:keywords:reply:info:keywords')")
    @Operation(summary = "根据关键字查询数据")
    @RequestMapping(value = "/info/keywords", method = RequestMethod.GET)
    public CommonResult<WechatReply> info(@RequestParam(value = "keywords") String keywords) {
        WechatReply wechatReply = wechatReplyService.getVoByKeywords(keywords);
        return CommonResult.success(wechatReply);
    }
}



