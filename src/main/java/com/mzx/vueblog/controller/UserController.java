package com.mzx.vueblog.controller;


import com.mzx.vueblog.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mzx
 * @since 2020-10-07
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/save")
    public Object testUser(@Validated @RequestBody User user){
        return user.toString();
    }
}
