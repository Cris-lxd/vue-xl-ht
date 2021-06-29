package com.cris15.xl.servie.serviceImpl;

import com.cris15.xl.entity.User;
import com.cris15.xl.mapper.LoginMapper;
import com.cris15.xl.servie.LoginService;
import com.cris15.xl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:29
 * @Description:
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User checkUser(String username, String password) {
        return loginMapper.checkUser(username,password);
    }

    @Override
    public int addUser(String id,String username, String password) {
        return loginMapper.addUser(id,username,password);
    }

    @Override
    public User selectuserByUsername(String username) {
        return loginMapper.selectByUsername(username);
    }
}