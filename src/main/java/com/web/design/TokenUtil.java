package com.web.design;

import com.web.design.cipher.JWT;
import com.web.design.cipher.PasswordEncoder;
import com.web.design.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class TokenUtil {
    private static final String salt = "be5e0323a9195ade5f56695ed9f2eb6b036f3e6417115d0cbe2fb9d74d8740406838dc84f78f7d978efe989a98c90883486ae7c4838b";
    public static PasswordEncoder passwordEncoder=new PasswordEncoder(salt, "SHA");
}
