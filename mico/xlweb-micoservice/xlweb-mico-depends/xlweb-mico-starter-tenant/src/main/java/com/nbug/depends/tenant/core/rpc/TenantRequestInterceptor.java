package com.nbug.depends.tenant.core.rpc;

import com.nbug.depends.tenant.core.context.TenantContextHolder;
import com.nbug.depends.web.web.core.util.WebFrameworkUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Tenant 的 RequestInterceptor 实现类：Feign 请求时，将 {@link TenantContextHolder} 设置到 header 中，继续透传给被调用的服务
 *
 * @author NBUG
 */
public class TenantRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            requestTemplate.header(WebFrameworkUtils.HEADER_TENANT_ID, String.valueOf(tenantId));
        }
    }

}
