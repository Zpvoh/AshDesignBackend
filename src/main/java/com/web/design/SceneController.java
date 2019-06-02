package com.web.design;

import com.web.design.mapper.SceneMapper;
import generator.SceneHistory;
import generator.SceneHistoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class SceneController {

    @Autowired
    private SceneMapper sceneMapper;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/getScene", method = RequestMethod.GET)
    public ArrayList<HashMap<String, Object> > getScene(@RequestParam(value = "userName") String username){
        return sceneMapper.getScenesByUser(username);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/saveScene", method = RequestMethod.POST)
    public HashMap<String, Object> saveScene(@RequestBody HashMap<String, Object> scene){
        sceneMapper.insertScene(scene);
        return scene;
    }
}
