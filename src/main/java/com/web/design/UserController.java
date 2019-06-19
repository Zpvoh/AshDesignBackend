package com.web.design;


import com.web.design.cipher.JWT;
import com.web.design.cipher.PasswordEncoder;
import com.web.design.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userCompletedInfo;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object> login(@RequestBody HashMap<String, Object> userInfo){
        String username=(String)userInfo.get("userName");
        String password=(String)userInfo.get("password");
        HashMap<String, Object> result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0) {
            String rightPass=(String)result.get("password");
            if(TokenUtil.passwordEncoder.isPasswordValid(rightPass, password)){
                //result=new HashMap<>();
                result.put("token",
                        JWT.createJWT(username, String.valueOf(result.get("uid")), password, 86400000));
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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> userInfo){
        String username=(String)userInfo.get("userName");
        String password=(String)userInfo.get("password");
        String originPass=new String(password);
        String email=(String)userInfo.get("email");
        password=TokenUtil.passwordEncoder.encode(password);
        HashMap<String, Object> result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0){
            userInfo.put("errorMsg", "注册失败");
            return userInfo;
        }
        userCompletedInfo.insertUserInfo(username, password, email);
        result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0) {
            //result=new HashMap<>();
            result.put("token",
                    JWT.createJWT(username, String.valueOf(result.get("uid")), originPass, 86400000));
            return result;
        }
        else {
            userInfo.put("errorMsg", "注册失败");
            return userInfo;
        }
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public ArrayList<HashMap<String, Object> > getUsers(@RequestParam(value = "token") String token){
        String username=JWT.parseJWT(token).getId();
        if(tokenValidate(token) && username.equals("root")) {
            ArrayList<HashMap<String, Object>> users = userCompletedInfo.getAllUsers();
            return users;
        }

        return new ArrayList<>();
    }

    public boolean tokenValidate(String token){
        Claims claims= JWT.parseJWT(token);
        if(claims==null)
            return false;

        String username=claims.getId();
        String password=claims.getSubject();
        HashMap<String, Object> result=userCompletedInfo.getUserInfo(username);
        if(result!=null && result.size()>0) {
            String rightPass=(String)result.get("password");
            return TokenUtil.passwordEncoder.isPasswordValid(rightPass, password);
        }

        return false;
    }
}
