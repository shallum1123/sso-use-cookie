package com.sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author zyc
 * @Date 2020/12/30 16:22
 * @Version V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)//支持链式调用
public class User {
    private Integer id;
    private String username;
    private String password;
}
