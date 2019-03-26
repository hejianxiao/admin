package com.hjhl.admin.controller.member;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.LuckDraw;
import com.hjhl.admin.modal.member.Member;
import com.hjhl.admin.service.member.MemberService;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人: Hjx
 * Date: 2019/3/25
 * Description:
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @RequiresPermissions("member:view")
    public TableVO view(Member member, Integer page, Integer limit) {
        Page<Member> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return memberService.view(pages, member);
    }


}
