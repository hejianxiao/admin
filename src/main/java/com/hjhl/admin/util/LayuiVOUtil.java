package com.hjhl.admin.util;

import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.SelectVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
public abstract class LayuiVOUtil<T> {

    public ResultVO SelectInit(List<T> data) {
        List<SelectVO> list = new ArrayList<>();
        data.forEach(e -> list.add(Convert(e)));
        return ResultVOUtil.Success(list, ResultEnum.SELECT_SUCCESS);
    }

    public List<SelectVO> SelectTreeInit(List<T> data) {
        List<SelectVO> list = new ArrayList<>();
        data.forEach(e -> list.add(Convert(e)));
        return list;
    }

    public abstract SelectVO Convert(T t);
}
