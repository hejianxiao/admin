package com.hjhl.admin.modal.luck;

import com.baomidou.mybatisplus.annotations.TableField;
import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnsScoreControl extends BaseEntity<AnsScoreControl> {

    /** 控制分数. */
    private Integer score;
    
    /** 活动 id. */
    private String luckDrawId;

    /** 创建时间. */
    private Date createTime;

    /** 活动名称. */
    @TableField(exist = false)
    private String drawName;

}
