package com.qmx.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.qmx.exception.BaseException;
import com.qmx.model.User;
import com.qmx.service.UserService;
import com.qmx.util.InstanceUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by zcl on 2018/5/22.
 */
@Controller
@RequestMapping("/")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(User user, Model model) {
        Map params = InstanceUtil.transBean2Map(user);
        Page<User> userPage = userService.query(params);
        model.addAttribute("page", userPage);
        return "/user/add";
    }

    @RequestMapping("/add")
    public String add() {
        return "/user/add";
    }

    @RequestMapping("/save")
    public String save(User user, HttpServletRequest request) {
        Assert.notNull(user.getPassword(), "密码不能为空");
        Assert.notNull(user.getUsername(), "用户名不能为空");
        Boolean flag = userService.saveUser(user);
        if (!flag) {
            throw new BaseException("保存用户失败！");
        }
        return "redirect:list";
    }
}
