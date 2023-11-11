package com.aicanvas.aicanvas.service.impl;


import com.aicanvas.aicanvas.VO.LoginForm;
import com.aicanvas.aicanvas.domain.LoginUser;
import com.aicanvas.aicanvas.domain.User;
import com.aicanvas.aicanvas.service.LoginService;
import com.aicanvas.aicanvas.utils.JwtUtil;
import com.aicanvas.aicanvas.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager ProviderManager;
    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;

    @Override
    public R login(LoginForm LoginForm) {
        // UsernamePasswordAuthenticationToken是Authentication类型的
        // 通过将用户名密码封装在Authentication类型中传给ProviderManager(接口是AuthenticationManager)进行认证
        // 第一个参数是对象主体principal，第二个参数是对象凭据credentials
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(LoginForm.getUsername(), LoginForm.getPassword());
        // 调用authentication方法进行认证后，返回了认证信息authenticate(子类型也是UsernamePasswordAuthenticationToken)
        // 里面包含的对象主体principal为LoginUser(抽象类型为UserDetails),这个LoginUser由UserDetailsServiceImpl里返回的
        Authentication authenticate = ProviderManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();
        String userType = user.getUserType();
        //status为1时表示账号停用
        if (user.getStatus().equals("1")) {
            throw new RuntimeException("账号已被锁定,请联系管理员");
        }
        String userId = user.getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // authenticate存入redis,将userId存入是因为上面的jwt也是用userId生成的，你等等可以通过jwt获得登录用户的id，然后去redis获取loginUser
        redisTemplate.opsForValue().set("login:" + userId, loginUser);
        //把token响应给前端
        return R.ok().put("code", 200).put("msg","登录成功").put("token", jwt).put("userType", userType);
    }

    @Override
    public R logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisTemplate.delete("login:" + userId);
        return R.ok().put("code", 200).put("msg","退出成功");
    }
}