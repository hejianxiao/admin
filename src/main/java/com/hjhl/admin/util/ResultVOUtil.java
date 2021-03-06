package com.hjhl.admin.util;


import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;

import java.util.List;

/**
 * Created by hjx
 * 2017/12/25 0025.
 */
public class ResultVOUtil {

    /**
     * 返回对象
     */
    public static ResultVO Success(Object obj, ResultEnum resultEnum) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setData(obj);
        resultVO.setMsg(resultEnum.getMsg());
        resultVO.setCode(resultEnum.getCode());
        return resultVO;
    }

    /**
     * 返回对象
     */
    public static ResultVO Success(Object obj) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setData(obj);
        resultVO.setMsg(ResultEnum.HANDLE_SUCCESS.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_SUCCESS.getCode());
        return resultVO;
    }

    /**
     * 返回成功信息
     */
    public static ResultVO Success(ResultEnum resultEnum) {
        return Success(null, resultEnum);
    }

    /**
     * 返回成功信息
     */
    public static ResultVO Success() {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setMsg(ResultEnum.HANDLE_SUCCESS.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_SUCCESS.getCode());
        return resultVO;
    }

    /**
     * 返回失败信息
     */
    public static ResultVO Error() {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setMsg(ResultEnum.HANDLE_ERROR.getMsg());
        resultVO.setCode(ResultEnum.HANDLE_ERROR.getCode());
        return resultVO;
    }

    /**
     * 返回错误信息
     */
    public static ResultVO Error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }

    public static ResultVO Error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    /**
     * 返回分页对象
     */
    public static TableVO TableSuccess(Page page) {
        TableVO<List> tableVO = new TableVO<>();
        tableVO.setCode(0);
        tableVO.setCount((int) page.getTotal());
        tableVO.setData(page.getRecords());
        return tableVO;
    }

    /**
     * 返回错误信息
     */
    public static ResultVO HandlerError(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
