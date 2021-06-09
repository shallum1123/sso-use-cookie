package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description vip子系统
 * @Author zyc
 * @Date 2020/12/30 16:16
 * @Version V1.0
 */
@SpringBootApplication
public class VipApp {
    public static void main(String[] args) {
        SpringApplication.run(VipApp.class,args);
        System.out.println("sso-vip服务启动成功！");
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
