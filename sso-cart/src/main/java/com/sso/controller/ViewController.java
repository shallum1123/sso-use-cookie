package com.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Description
 * @Author zyc
 * @Date 2020/12/30 17:17
 * @Version V1.0
 */
@Controller
@RequestMapping("view")
public class ViewController {
    @Autowired
    RestTemplate restTemplate;

    //携带token跨服务请求登录服务接口获取已经登陆用户信息--若解决session共享则可以省略该步骤
    private final String LOGIN_INFO_ADDRESS="http://login.sso.com:9001/login/info?token=";
    @GetMapping("/index")
    public String toIndex(@CookieValue(required = false,value = "TOKEN")Cookie cookie, HttpSession session){
        if(null!=cookie){
            String token = cookie.getValue();
            if(StringUtils.isNotEmpty(token)){
                Map result = restTemplate.getForObject(LOGIN_INFO_ADDRESS + token, Map.class);
                session.setAttribute("loginUser",result);
            }
        }else {
            session.removeAttribute("loginUser");
        }
        return "index";
    }

    @GetMapping("/exit")
    public String toExit(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0){
            for (Cookie cookie : cookies) {
                if("TOKEN".equals(cookie.getName())){
                    cookie.setDomain("sso.com");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/view/index";
    }

}
