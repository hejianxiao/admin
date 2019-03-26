package com.hjhl.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/14
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeVO {

    /** id. */
    private String id;

    /** 节点名称. */
    private String name;
    
    /** code. */
    private String code;

    /** pcode. */
    private String pcode;

    /** icon. */
    private String icon;
    
    /** uri. */
    private String uri;

    /** 排序. */
    private Integer num;

    /** 描述. */
    private String description;
    
    /** 子树. */
    private List<TreeVO> children;
    
    /** 是否最终子节点. */
    private boolean isFinal;

    private boolean spread;
    
}
