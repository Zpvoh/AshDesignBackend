package com.web.design.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface SceneMapper {
    ArrayList<HashMap<String, Object> > getScenesByUser(String username);
    void insertScene(HashMap<String, Object> scene);

}
