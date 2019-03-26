package com.hjhl.admin.modal.luck;

import com.baomidou.mybatisplus.annotations.TableField;
import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckOrder extends BaseEntity<LuckOrder> {

    /** 活动id. */
    private String luckDrawId;
    
    /** 会员id. */
    private String memberId;
    
    /** 奖品id. */
    private String luckPrizeId;
    
    /** 订单时间. */
    private Date createTime;
    
    /** 是否奖品. */
    private String orPrize;


    @TableField(exist = false)
    private String actName;

    @TableField(exist = false)
    private String prizeName;

    @TableField(exist = false)
    private String prizeType;

}
