package com.hjhl.admin.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oss")
@PropertySource("classpath:/properties/oss.properties")//@PropertySource来指定自定义的资源目录
@Data
public class OssConstant {

    /**
     *创建的云oss服务其地址.
     */
    private String endpoint;

    /**
     * accessKeyId.
     */
    private String accessKeyId;

    /**
     * accessKeySecret.
     */
    private String accessKeySecret;
    /**
     * 空间名
     */
    private String bucketName;
    /**
     * 文件名
     */
    private String filedir;

}