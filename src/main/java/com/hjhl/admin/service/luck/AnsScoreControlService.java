package com.hjhl.admin.service.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.AnsScoreControl;
import com.hjhl.admin.service.BaseService;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
public interface AnsScoreControlService extends BaseService<AnsScoreControl> {

    TableVO view(Page<AnsScoreControl> page, AnsScoreControl scoreControl);

}
