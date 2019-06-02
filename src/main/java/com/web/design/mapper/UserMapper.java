package com.web.design.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface UserMapper {

    HashMap<String, Object> getUserInfo(String username);
    ArrayList<HashMap<String, Object> > getAllUsers();

    void insertUserInfo(String username, String password, String email);
}
