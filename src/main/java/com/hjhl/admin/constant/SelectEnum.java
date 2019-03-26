package com.hjhl.admin.constant;

import lombok.Getter;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@Getter
public enum SelectEnum {

    SELECTED("selected"),
    DISABLED("disabled");

    /** tag. */
    private String tag;

    SelectEnum(String tag) {
        this.tag = tag;
    }
}
