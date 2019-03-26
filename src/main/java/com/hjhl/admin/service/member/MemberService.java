package com.hjhl.admin.service.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.member.Member;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/25
 * Description:
 */
public interface MemberService {

    TableVO view(Page<Member> page, Member member);

}
