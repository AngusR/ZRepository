package com.test.controller;


import com.test.entity.User;
import com.test.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 登录方法
     */
    @PostMapping("/login")
    public String login(User user, HttpSession session){
        User userDB = userService.login(user);
       if(userDB!=null){
            session.setAttribute("user",userDB);
            return "redirect:/file/showAll";
       }else {
           //防止表单重复提交
           return "redirect:/index";
       }
    }
}
