package com.aicanvas.aicanvas.service;


import com.aicanvas.aicanvas.VO.LoginForm;
import com.aicanvas.aicanvas.utils.R;

public interface LoginService {

    R login(LoginForm loginForm);

    R logout();
}
