package com.hjhl.admin.modal.luck;

import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckReceive extends BaseEntity<LuckReceive> {

    /** 订单id. */
    private String luckOrderId;
    
    /** 领取信息标题. */
    private String receiveName;
    
    /** 领取信息详情. */
    private String receiveValue;
    
}
