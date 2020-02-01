package com.blaife.swagger2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 配置 Docket Bean
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.blaife.swagger2.controller")) // 配置映射路径和要扫描的接口的位置
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    /**
     * 配置一下Swagger2文档网站的信息，例如网站的title，网站的描述，联系人的信息，使用的协议等等。
     * @return
     */
    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("测试springboot2采用swagger2构建RESTFul APIs")// 标题
                .description("通过访问swagger-ui.htmnl,实现接口测试，文档生成。") // 说明
                .version("1.0") // 版本
                .contact(new Contact("blaife", "https://github.com/BigBlackKnife", "2767026270@qq.com")) // 联络方式
                .license("许可证书") // 许可证书
                .licenseUrl("http://www.baidu.com") // 证书地址
                .build();
    }

}
