package com.cris15.xl.servie;

import com.cris15.xl.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:28
 * @Description:
 **/
public interface LoginService {
    /**
     *
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);

    int addUser(String id,String username,String password);

    User selectuserByUsername(String username);
}
