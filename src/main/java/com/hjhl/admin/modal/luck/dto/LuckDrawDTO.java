package com.hjhl.admin.modal.luck.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
@Data
public class LuckDrawDTO {

    private String id;

    /** 活动代码. */
    @NotBlank(message = "活动代码必传")
    private String actCode;

    /** 活动名称. */
    @NotBlank(message = "活动名称必传")
    private String actName;

    /** 抽奖次数 次/天. */
    @Min(value = 0,message = "抽奖次数数值有误")
    private Integer drawCount;

    /** 每人最多抽中次数. */
    @Min(value = 0,message = "每人最多抽中次数数值有误")
    private Integer maxCount;

    /** 每人每天最多抽中. */
    @Min(value = 0,message = "每人每天最多抽中次数数取值有误")
    private Integer dayMaxCount;

    /** 活动开始时间. */
    @NotBlank(message = "开始时间必传")
    private String startTime;

    /** 活动结束时间. */
    @NotBlank(message = "结束时间必传")
    private String endTime;

    /** 奖品json. */
    @NotBlank(message = "奖品必传")
    private String luckPrizes;

    /** 领奖信息. */
    private String receiveInfo;

}
