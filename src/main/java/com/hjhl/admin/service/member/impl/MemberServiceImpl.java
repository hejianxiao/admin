package com.hjhl.admin.service.member.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjhl.admin.mapper.member.MemberMapper;
import com.hjhl.admin.modal.member.Member;
import com.hjhl.admin.service.member.MemberService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/25
 * Description:
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public TableVO view(Page<Member> page, Member member) {
        List<Member> list = baseMapper.selectPage(page, new EntityWrapper<>(new Member())
                .where(!StringUtils.isEmpty(member.getId()), "id={0}", member.getId())
                .orderBy("create_time", false));
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }
}
