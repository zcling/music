package com.qmx.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.qmx.exception.BaseException;
import com.qmx.exception.BusinessException;
import com.qmx.model.User;
import com.qmx.model.common.UseCommon;
import com.qmx.service.UserService;
import com.qmx.util.InstanceUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zcl on 2018/5/22.
 */
@Controller
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(User user, Model model) {
        return "/user/list";
    }

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public UseCommon getData(User user) {
        Map prams = new HashMap();
        prams = InstanceUtil.transBean2Map(user);
        List<User> users = userService.queryList(prams);
        UseCommon useCommon = new UseCommon();
        useCommon.setRows(users);
        useCommon.setTotal(users.size() + "");
        return useCommon;
    }

    @RequestMapping("/add")
    public String add() {
        return "/user/add";
    }

    @ResponseBody
    @RequestMapping("/save")
    public JSONObject save(User user, HttpServletRequest request) {
        Assert.notNull(user.getPassword(), "密码不能为空");
        Assert.notNull(user.getUsername(), "用户名不能为空");
        JSONObject jsonObject = new JSONObject();
        try {
            Boolean flag = userService.saveUser(user);
            if (!flag) {
                throw new BusinessException("保存失败");
            }
            jsonObject.put("data", "success");
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("data", "error");
        }
        return jsonObject;
    }
}
