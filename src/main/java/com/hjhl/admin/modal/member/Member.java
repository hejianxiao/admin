package com.hjhl.admin.modal.member;

import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class Member extends BaseEntity {
    
    /** openid. */
    private String openid;
    
    /** 创建时间. */
    private String createTime;

}
