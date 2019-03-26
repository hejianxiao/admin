package com.hjhl.admin.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 创建人: Hjx
 * Date: 2019/3/14
 * Description:
 */
@ConfigurationProperties("admin")
@Data
@Component
@PropertySource(value = "classpath:properties/admin.yml", encoding = "utf-8")
@EnableConfigurationProperties
public class AdminProperties {

    /** id. */
    @Value("${id}")
    private String id;

    /** 用户名. */
    @Value("${user-name}")
    private String userName;

    /** 密码. */
    @Value("${password}")
    private String password;

    /** 真实姓名. */
    @Value("${real-name}")
    private String realName;


}
