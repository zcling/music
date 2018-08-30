package com.qmx.controller;

import com.qmx.exception.BusinessException;
import com.qmx.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/skipLogin")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String index() {
        return "/common/index";
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, User user, Model model) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            logger.info("{}", "用户名或密码不能为空");
            //throw new BusinessException("用户名或密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            return "redirect:main";
        } catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    /**
     * 主页
     *
     * @return
     */
    @RequestMapping(value = {"/main", ""})
    public String main() {
        return "common/main";
    }

    /**
     * 用户管理
     *
     * @return
     */
    @RequestMapping(value = {"/usersPage"})
    public String usersPage() {
        return "user/users";
    }

    /**
     * 角色管理
     *
     * @return
     */
    @RequestMapping("/rolesPage")
    public String rolesPage() {
        return "role/roles";
    }

    /**
     * 资源管理
     *
     * @return
     */
    @RequestMapping("/resourcesPage")
    public String resourcesPage() {
        return "resources/resources";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }

    /**
     * 文件导入界面
     *
     * @return
     */
    @RequestMapping(value = "/readExcel", method = RequestMethod.GET)
    public String readExcel() {
        return "readExcel";
    }

}
