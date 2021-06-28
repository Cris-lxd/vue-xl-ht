package com.cris15.xl.servie.serviceImpl;

import com.cris15.xl.entity.User;
import com.cris15.xl.mapper.UserMapper;
import com.cris15.xl.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}