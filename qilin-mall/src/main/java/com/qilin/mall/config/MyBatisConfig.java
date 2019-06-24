package com.qilin.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 的配置类
 * （用于配置动态生成的 mapper 接口的路径）
 */
@Configuration
@MapperScan("com.qilin.mall.mbg.mapper")
public class MyBatisConfig {
}
