package com.hjhl.admin.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.modal.BaseEntity;
import com.hjhl.admin.util.CopyUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import org.springframework.util.StringUtils;

/**
 * 创建人: Hjx
 * Date: 2019/1/11
 * Description:
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public ResultVO addOrUpd(T t, String... args) {
        if (StringUtils.isEmpty(t.getId())) {
            boolean r = this.insert(t);
            if (!r) {
                throw new GlobalException(ResultEnum.HANDLE_ERROR);
            }
        } else {
            T persisEntity = this.selectById(t.getId());
            CopyUtil.copyProperties(t, persisEntity);
            boolean r = this.updateById(persisEntity);
            if (!r) {
                throw new GlobalException(ResultEnum.HANDLE_ERROR);
            }
        }
        return ResultVOUtil.Success();
    }

    @Override
    public ResultVO delById(String id) {
        boolean r = this.deleteById(id);
        if (r) {
            return ResultVOUtil.Success();
        } else {
            throw new GlobalException(ResultEnum.HANDLE_ERROR);
        }
    }


    @Override
    public ResultVO findById(String id) {
        T t = this.selectById(id);
        if (t == null) {
            throw new GlobalException(ResultEnum.OBJECT_EMPTY);
        }
        return ResultVOUtil.Success(t);
    }
}
