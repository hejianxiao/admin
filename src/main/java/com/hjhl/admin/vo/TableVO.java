package com.hjhl.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建人: Hjx
 * Date: 2019/3/9
 * Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableVO<T> implements Serializable {

    /** code. */
    private Integer code;
    
    /** msg. */
    private String msg;

    /** count. */
    private Integer count;

    /** data. */
    private T data;

}
