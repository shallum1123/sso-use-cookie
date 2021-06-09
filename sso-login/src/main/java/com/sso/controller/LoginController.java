package com.sso.controller;

import com.sso.pojo.User;
import com.sso.utils.LoginCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @Description
 * @Author zyc
 * @Date 2020/12/30 16:27
 * @Version V1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    // 模拟数据库用户
    private static Set<User> dbUser;
    static {
        dbUser = new HashSet<>();
        dbUser.add(new User(0, "zhangsan", "1234"));
        dbUser.add(new User(1, "lisi", "1234"));
        dbUser.add(new User(2, "中国魂", "123456"));
    }
    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        String target = (String) session.getAttribute("target");
        // 模拟从数据库中通过登录的用户名和密码查找用户，
        Optional<User> first = dbUser.stream().filter(dbUser -> dbUser.getUsername().equals(user.getUsername()) &&
                dbUser.getPassword().equals(user.getPassword())).findFirst();
        // 判断用户是否登录d
        if (first.isPresent()) {
            // 保存用户登录信息
            String token = UUID.randomUUID().toString();
            LoginCacheUtil.loginUser.put(token, first.get());
            session.removeAttribute("msg");
            //设置cookie
            Cookie cookie = new Cookie("TOKEN", token);
            //cookie在子系统间访问域要相同
            //cookie不能跨域
            cookie.setDomain("sso.com");
            /*
            cookie过期时间设置方式：
            cookie.setMaxAge(0);//不记录cookie
            cookie.setMaxAge(-1);//会话级cookie，关闭浏览器失效
            cookie.setMaxAge(60*60);//过期时间为1小时
             */
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            //通过HttpServletResponse将cookie响应到子系统
            response.addCookie(cookie);
        } else {
            //登录失败
            session.setAttribute("msg", "用户名或密码错误");
            return "login";
        }
        // 重定向到target地址
        return "redirect:" + target;
    }

    /**
     * 通过token获取用户登录信息
     * 跨系统交互
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token) {
        if (StringUtils.isNotEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
