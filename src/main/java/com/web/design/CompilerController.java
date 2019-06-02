package com.web.design;

import com.web.design.mapper.SceneMapper;
import com.web.design.test.CodeEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CompilerController {

    @Autowired
    private SceneMapper sceneMapper;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/compile", method = RequestMethod.POST)
    public HashMap<String, Object> check(@RequestBody HashMap<String, Object> scene){
        CodeEvaluation.evaluate(scene);
        sceneMapper.insertScene(scene);
        return scene;
    }
}
