package com.hjhl.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.hjhl.admin.vo.ResultVO;

/**
 * 创建人: Hjx
 * Date: 2019/1/11
 * Description:
 */
public interface BaseService<T> extends IService<T> {

    ResultVO addOrUpd(T t, String... args);

    ResultVO delById(String id);

    ResultVO findById(String id);
}
