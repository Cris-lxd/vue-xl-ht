package com.cris15.xl.servie.serviceImpl;

import com.cris15.xl.entity.User;
import com.cris15.xl.mapper.LoginMapper;
import com.cris15.xl.servie.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int addUser(String id,String username, String password,String phoneNumber) {
        return loginMapper.addUser(id,username,password,phoneNumber);
    }

    @Override
    public User selectuserByUsername(String username) {
        return loginMapper.selectByUsername(username);
    }
}