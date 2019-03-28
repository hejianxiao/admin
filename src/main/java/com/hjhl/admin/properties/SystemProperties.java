package com.hjhl.admin.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 创建人: Hjx
 * Date: 2019/3/28
 * Description:
 */
@ConfigurationProperties("system")
@Data
@Component
@PropertySource(value = "classpath:properties/system.yml", encoding = "utf-8")
@EnableConfigurationProperties
public class SystemProperties {

    /** 版本. */
    @Value("${version}")
    private String version;

    /** 作者. */
    @Value("${author}")
    private String author;

    /** 首页. */
    @Value("${index-page}")
    private String indexPage;

    /** 服务器配置. */
    @Value("${server-config}")
    private String serverConfig;

    /** 数据库配置. */
    @Value("${data-base}")
    private String dataBase;

    /** tomcat上传限制. */
    @Value("${upload-limit}")
    private String uploadLimit;

}
