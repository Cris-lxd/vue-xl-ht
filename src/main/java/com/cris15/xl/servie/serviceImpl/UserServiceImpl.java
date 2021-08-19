package com.cris15.xl.servie.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cris15.xl.entity.User;
import com.cris15.xl.mapper.UserMapper;
import com.cris15.xl.servie.UserService;
import com.cris15.xl.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 15:09
 * Project: demotest
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getuserByNameAndPhone(String username, String phoneNumber,String oldPassword) {
        Map map = new HashMap<>();
        map.put("username",username);
        map.put("phone",phoneNumber);
        map.put("password",oldPassword);
        List<User> list = userMapper.selectByMap(map);
        return list;
    }

    @Override
    public int updateUserPwd(String username, String id, String password) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(MD5Utils.string2Md5(password));
        return userMapper.updateById(user);
    }
}