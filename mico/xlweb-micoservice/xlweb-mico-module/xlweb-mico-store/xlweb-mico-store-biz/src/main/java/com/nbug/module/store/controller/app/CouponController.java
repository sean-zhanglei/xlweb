package com.nbug.module.store.controller.app;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.StoreCouponFrontResponse;
import com.nbug.mico.common.response.StoreCouponUserOrder;
import com.nbug.module.store.service.StoreCouponService;
import com.nbug.module.store.service.StoreCouponUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 优惠券表 前端控制器
 
 */
@Slf4j
@RestController("CouponFrontController")
@RequestMapping("api/front/store/coupons")
@Tag(name = "应用后台 - 优惠券")
public class CouponController {

    @Autowired
    private StoreCouponService storeCouponService;

    @Autowired
    private StoreCouponUserService storeCouponUserService;


    /**
     * 分页显示优惠券表
     * @param type 类型，1-通用，2-商品，3-品类
     * @param productId 产品id，搜索产品指定优惠券
     * @param pageParamRequest 分页参数
     */
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name="type", description="类型，1-通用，2-商品，3-品类", required = true),
            @Parameter(name="productId", description="产品id"),
            @Parameter(name="page", description="页码", required = true),
            @Parameter(name="limit", description="每页数量", required = true)
    })
    public CommonResult<List<StoreCouponFrontResponse>>  getList(@RequestParam(value = "type", defaultValue = "0") int type,
                                                                 @RequestParam(value = "productId", defaultValue = "0") int productId, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(storeCouponService.getH5List(type, productId, pageParamRequest));
    }

    /**
     * 根据购物车id获取可用优惠券
     */
    @Operation(summary = "当前订单可用优惠券")
    @RequestMapping(value = "/order/{preOrderNo}", method = RequestMethod.GET)
    public CommonResult<List<StoreCouponUserOrder>> getCouponsListByPreOrderNo(@PathVariable String preOrderNo) {
        return CommonResult.success(storeCouponUserService.getListByPreOrderNo(preOrderNo));
    }
}



