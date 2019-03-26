package com.hjhl.admin.mapper.luck;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjhl.admin.modal.luck.AnsScoreControl;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
public interface AnsScoreControlMapper extends BaseMapper<AnsScoreControl> {

    List<AnsScoreControl> selectTable();

}
