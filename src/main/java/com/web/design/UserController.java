package com.web.design;


import com.web.design.cipher.PasswordEncoder;
import com.web.design.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    private final String salt = "be5e0323a9195ade5f56695ed9f2eb6b036f3e6417115d0cbe2fb9d74d8740406838dc84f78f7d978efe989a98c90883486ae7c4838b";
    private PasswordEncoder passwordEncoder=new PasswordEncoder(salt, "SHA");

    @Autowired
    private UserMapper userCompletedInfo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object> login(@RequestBody HashMap<String, Object> userInfo){
        String username=(String)userInfo.get("userName");
        String password=(String)userInfo.get("password");
        HashMap<String, Object> result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0) {
            String rightPass=(String)result.get("password");
            if(passwordEncoder.isPasswordValid(rightPass, password)){
                return result;
            }else {
                userInfo.put("errorMsg", "密码不正确");
                return userInfo;
            }
        }
        else {
            userInfo.put("errorMsg", "用户名不存在");
            return userInfo;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> userInfo){
        String username=(String)userInfo.get("userName");
        String password=(String)userInfo.get("password");
        String email=(String)userInfo.get("email");
        password=passwordEncoder.encode(password);
        userCompletedInfo.insertUserInfo(username, password, email);
        HashMap<String, Object> result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0) {
            return result;
        }
        else {
            userInfo.put("errorMsg", "注册失败");
            return userInfo;
        }
    }
}
