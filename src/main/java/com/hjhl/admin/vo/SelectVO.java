package com.hjhl.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectVO implements Serializable {

    /** text. */
    private String name;
    
    /** value. */
    private String value;

    /** selected. */
    private String selected;
    
    /** disabled. */
    private String disabled;

    private List<SelectVO> children;

    public SelectVO() {
    }

    public SelectVO(String name, String value) {
        this.name = name;
        this.value = value;
        this.selected = "";
        this.disabled = "";
    }

    public SelectVO(String name, String value, String selected, String disabled) {
        this.name = name;
        this.value = value;
        this.selected = selected;
        this.disabled = disabled;
    }
}
