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
@ConfigurationProperties("cpy")
@Data
@Component
@PropertySource(value = "classpath:properties/company.yml", encoding = "utf-8")
@EnableConfigurationProperties
public class CompanyProperties {

    /** 公司全称. */
    @Value("${full-name}")
    private String fullName;

    /** 公司名称. */
    @Value("${name}")
    private String name;

    /** 软件名称. */
    @Value("${software}")
    private String software;

    /** 著作权. */
    @Value("${copyright}")
    private String copyright;

}
