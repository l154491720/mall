package com.qilin.mall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author: qilin.liu
 * @Date: 2019/6/24 10:42
 * @Description:
 * 注意: Swagger对生成API文档的范围有三种不同的选择
 * 1.生成指定包下面的类的API文档
 * 2.生成有指定注解的类的API文档
 * 3.生成有指定注解的方法的API文档
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包下 controller 生成 API 文档
                .apis(RequestHandlerSelectors.basePackage("com.qilin.mall.controller"))
                // 为 有@Api 注解的 Controller 生成 API 文档
                // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //为有 @ApiOperation 注解的方法生成 API 文档
                // .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        return  new ApiInfoBuilder().title("SwaggerUI 演示")
                .description("qilin_mall")
                .contact("qilin")
                .version("1.0")
                .build();
    }


}