package com.web.design;

import com.web.design.cipher.JWT;
import com.web.design.mapper.SceneMapper;
import com.web.design.mapper.UserMapper;
import com.web.design.test.CodeEvaluation;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CompilerController {

    @Autowired
    private UserMapper userCompletedInfo;

    @Autowired
    private SceneMapper sceneMapper;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/compile", method = RequestMethod.POST)
    public HashMap<String, Object> check(@RequestBody HashMap<String, Object> scene){
        String token=(String)scene.get("token");
        if(tokenValidate(token)) {
            CodeEvaluation.evaluate(scene);
            Claims claims= JWT.parseJWT(token);
            scene.put("uid", claims.getIssuer());
            scene.remove("token");
            sceneMapper.insertScene(scene);
            return scene;
        }

        HashMap<String, Object> result=new HashMap<>();
        result.put("errorMsg", "invalid token");
        return result;
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
