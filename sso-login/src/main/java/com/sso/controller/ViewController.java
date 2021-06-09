package com.sso.controller;

import com.sso.pojo.User;
import com.sso.utils.LoginCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Author zyc
 * @Date 2020/12/30 16:28
 * @Version V1.0
 */

/**
 * 页面跳转逻辑
 */
@Controller
@RequestMapping("view")
public class ViewController {
    /**
     * 跳转到登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false, defaultValue = "") String target, HttpSession session,
                          @CookieValue(required = false, value = "TOKEN") Cookie cookie) {
        if (StringUtils.isEmpty(target)) {
            //未携带url设置默认页面
            target = "http://www.sso.com:9000";
        }
        if (cookie != null) {
            //如果是已经登录的用户再次访问登录系统时，就重定向到回页面
            String token = cookie.getValue();
            User user = LoginCacheUtil.loginUser.get(token);
            if (user != null) {
                return "redirect:" + target;
            }
        }
        //未登录，跳转登录controller
        session.setAttribute("target", target);
        return "login";
    }
}
