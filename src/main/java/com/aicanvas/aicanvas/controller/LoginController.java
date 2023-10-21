package com.aicanvas.aicanvas.controller;

import com.aicanvas.aicanvas.VO.LoginForm;
import com.aicanvas.aicanvas.domain.LoginUser;
import com.aicanvas.aicanvas.domain.User;
import com.aicanvas.aicanvas.service.CaptchaService;
import com.aicanvas.aicanvas.service.LoginService;
import com.aicanvas.aicanvas.utils.JwtUtil;
import com.aicanvas.aicanvas.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/account")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private CaptchaService captchaService;
    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;


    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    @CrossOrigin
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = captchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/user/login")
    @CrossOrigin
    public R login(@RequestBody LoginForm loginForm) {
        boolean captcha = captchaService.validate(loginForm.getUuid(), loginForm.getCaptcha());
        System.out.println(captcha);
        if (!captcha) {
            return R.error("验证码不正确");
        }
        return loginService.login(loginForm);
    }

    /**
     * 登出
     */
    @RequestMapping("/user/logout")
    public R logout() {
        return loginService.logout();
    }

    @RequestMapping("/user/info/{token}")
    public R info(@RequestParam("token") String token) {
        try {
            String userId = JwtUtil.parseJWT(token).getSubject();
            LoginUser loginUser = redisTemplate.opsForValue().get("login:" + userId);
            User user = loginUser.getUser();
            return R.ok().put("info", user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
