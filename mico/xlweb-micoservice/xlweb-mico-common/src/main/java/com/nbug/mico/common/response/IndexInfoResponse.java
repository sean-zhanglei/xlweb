package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 首页信息Response

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户登录返回数据")
public class IndexInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "首页banner滚动图")
    private List<HashMap<String, Object>> banner;

    @Schema(description = "导航模块")
    private List<HashMap<String, Object>> menus;

    @Schema(description = "新闻简报消息滚动")
    private List<HashMap<String, Object>> roll;

    @Schema(description = "企业logo")
    private String logoUrl;

    @Schema(description = "是否关注公众号")
    private boolean subscribe;

    @Schema(description = "首页超值爆款")
    private List<HashMap<String, Object>> explosiveMoney;

    @Schema(description = "首页精品推荐图片")
    private List<HashMap<String, Object>> bastBanner;

    @Schema(description = "云智服H5 url")
    private String yzfUrl;

    @Schema(description = "商品分类页配置")
    private String categoryPageConfig;

    @Schema(description = "是否隐藏一级分类")
    private String isShowCategory;

    @Schema(description = "客服电话")
    private String consumerHotline;

    @Schema(description = "客服电话服务开关")
    private String telephoneServiceSwitch;

    @Schema(description = "首页商品列表模板配置")
    private String homePageSaleListStyle;

    @Schema(description = "欢迎语标题")
    private String consumerWelcomeTitle;

    @Schema(description = "欢迎语")
    private String consumerWelcome;
}
