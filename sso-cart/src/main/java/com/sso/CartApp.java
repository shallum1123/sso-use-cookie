package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 购物车子系统
 * @Author zyc
 * @Date 2020/12/30 16:15
 * @Version V1.0
 */
@SpringBootApplication
public class CartApp {
    public static void main(String[] args) {
        SpringApplication.run(CartApp.class,args);
        System.out.println("sso-cart服务启动成功！");
    }
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
