package com.nbug.front.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.nbug.common.config.XlwebConfig;
import com.nbug.common.constants.Constants;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Swagger配置组件

 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConfigurationProperties(prefix = "api.doc")
public class SwaggerConfig{

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    Boolean swaggerEnabled = true;

    @Autowired
    XlwebConfig xlwebConfig;

    @Bean("front")
    public Docket create1RestApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("front")
                .host(xlwebConfig.getDomain())
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.nbug.front"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(frontPathsAnt()) //只监听
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
//                .globalOperationParameters(pars) // 针对单个url的验证 如果需要的话
                .pathMapping("/");
    }

    @Bean("public")
    public Docket create2RestApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public")
                .host(xlwebConfig.getDomain())
                .apiInfo(apiInfo())
                // 是否开启
                .enable(swaggerEnabled)
                .select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.nbug.front"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(publicPathsAnt()) //只监听
                .build()
                .securitySchemes(security())
                .securityContexts(securityContexts())
//                .globalOperationParameters(pars) // 针对单个url的验证 如果需要的话
                .pathMapping("/");
    }

    private Predicate<String> frontPathsAnt() {
        return PathSelectors.ant("/api/front/**");
    }

    private Predicate<String> publicPathsAnt() {
        return PathSelectors.ant("/api/public/**");
    }

    private List<ApiKey> security() {
        return newArrayList(
                new ApiKey(Constants.HEADER_AUTHORIZATION_KEY, Constants.HEADER_AUTHORIZATION_KEY, "header")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Xlweb Java")
                .description("Xlweb")
//                .termsOfServiceUrl("http://host:port")
                .version("1.0.0").build();
    }


    private List<SecurityContext> securityContexts() {
        List<SecurityContext> res = new ArrayList<>();
        res.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        return res;
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> res = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", Constants.HEADER_AUTHORIZATION_KEY);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        res.add(new SecurityReference(Constants.HEADER_AUTHORIZATION_KEY, authorizationScopes));
        return res;
    }
}
