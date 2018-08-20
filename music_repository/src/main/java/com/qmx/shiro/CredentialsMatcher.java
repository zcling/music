package com.qmx.shiro;

import com.qmx.model.User;
import com.qmx.util.PasswordHelper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();

        User user = (User) info.getPrincipals().getPrimaryPrincipal();
        user.setPassword(inPassword);//还原用户输入的密码
        PasswordHelper passwordHelper = new PasswordHelper();
        passwordHelper.encryptPassword(user);

        //进行密码的比对
        return this.equals(user.getPassword(), dbPassword);
    }

}