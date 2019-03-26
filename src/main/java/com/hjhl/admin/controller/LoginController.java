package com.hjhl.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hjhl.admin.constant.RedisConstant;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.service.RedisService;
import com.hjhl.admin.util.IpUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 * 登录控制器
 */
@RestController
public class LoginController {

    private DefaultKaptcha producer;

    private RedisService redisService;

    @Autowired
    public LoginController(DefaultKaptcha producer, RedisService redisService) {
        this.producer = producer;
        this.redisService = redisService;
    }

    @PostMapping("/kaptcha")
    public Object kaptcha(HttpServletRequest request) throws IOException {
        String ip = IpUtil.getIPAddress(request);
        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        redisService.setValue(String.format(RedisConstant.LOGIN_IP_PREFIX, ip),
                text, RedisConstant.LOGIN_CODE_EXPIRE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return Base64.getEncoder().encode(outputStream.toByteArray());
    }

    @PostMapping("/login")
    public ResultVO login(HttpServletRequest request, SysUser user, String picText) {
        String ip = IpUtil.getIPAddress(request);
        String redisPic = redisService.getValue(String.format(RedisConstant.LOGIN_IP_PREFIX, ip));

        if (StringUtils.isEmpty(redisPic)) {
            throw new GlobalException(ResultEnum.LOGIN_PIC_EXPIRE_ERROR);
        }

        if (!redisPic.equals(picText)) {
            throw new GlobalException(ResultEnum.LOGIN_PIC_ERROR);
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getUsername(),
                DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()));
        try {
            subject.login(token);
            return ResultVOUtil.Success(ResultEnum.HANDLE_SUCCESS);
        } catch (Exception e) {
            return ResultVOUtil.Error(ResultEnum.LOGIN_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResultVO logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return ResultVOUtil.Success(ResultEnum.HANDLE_SUCCESS);
        } catch (Exception e) {
            return ResultVOUtil.Error(ResultEnum.LOGOUT_ERROR);
        }
    }

}
