package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 登录系统
 * @Author zyc
 * @Date 2020/12/30 16:13
 * @Version V1.0
 */
@SpringBootApplication
public class LoginApp {
    public static void main(String[] args) {
        SpringApplication.run(LoginApp.class,args);
        System.out.println("sso-login服务启动成功！");
    }
}
