package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.model.product.StoreProductReply;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductReplyAddRequest;
import com.nbug.mico.common.request.StoreProductReplyCommentRequest;
import com.nbug.mico.common.request.StoreProductReplySearchRequest;
import com.nbug.mico.common.response.StoreProductReplyResponse;
import com.nbug.module.store.service.StoreProductReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 评论表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/product/reply")
@Tag(name = "商品 -- 评论") //配合swagger使用
public class StoreProductReplyController {

    @Autowired
    private StoreProductReplyService storeProductReplyService;

    /**
     * 分页显示评论表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:product:reply:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreProductReplyResponse>>  getList(@Validated StoreProductReplySearchRequest request,
                                                                        @Validated PageParamRequest pageParamRequest) {
        CommonPage<StoreProductReplyResponse> storeProductReplyCommonPage =
                CommonPage.restPage(storeProductReplyService.getList(request, pageParamRequest));
        return CommonResult.success(storeProductReplyCommonPage);
    }

    /**
     * 新增评论表
     * @param request 新增参数
     */
    @PreAuthorize("hasAuthority('admin:product:reply:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated StoreProductReplyAddRequest request) {
        if (storeProductReplyService.virtualCreate(request)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除评论表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:product:reply:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public CommonResult<String> delete(@PathVariable Integer id) {
        if (storeProductReplyService.delete(id)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询评论表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:product:reply:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductReply> info(@PathVariable Integer id) {
        StoreProductReply storeProductReply = storeProductReplyService.getById(id);
        return CommonResult.success(storeProductReply);
   }

    /**
     * 回复商品评论
     * @param request  StoreProductReplyCommentRequest 回复参数
     */
    @PreAuthorize("hasAuthority('admin:product:reply:comment')")
   @Operation(summary = "回复")
   @RequestMapping(value = "/comment", method = RequestMethod.POST)
   public CommonResult<String> comment(@RequestBody StoreProductReplyCommentRequest request) {
       if (storeProductReplyService.comment(request)) {
           return CommonResult.success("success");
       }
       return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
   }
}



