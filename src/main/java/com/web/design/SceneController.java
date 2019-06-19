package com.web.design;

import com.web.design.cipher.JWT;
import com.web.design.mapper.SceneMapper;
import com.web.design.mapper.UserMapper;
import generator.SceneHistory;
import generator.SceneHistoryDao;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class SceneController {

    @Autowired
    private UserMapper userCompletedInfo;

    @Autowired
    private SceneMapper sceneMapper;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getScene", method = RequestMethod.GET)
    public ArrayList<HashMap<String, Object> > getScene
            (@RequestParam(value = "userName") String username,
             @RequestParam(value = "token") String token){
        if(tokenValidate(token)) {
            return sceneMapper.getScenesByUser(username);
        }
        return new ArrayList<>();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/saveScene", method = RequestMethod.POST)
    public HashMap<String, Object> saveScene(@RequestBody HashMap<String, Object> scene){
        if(tokenValidate((String)scene.get("token"))) {
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
