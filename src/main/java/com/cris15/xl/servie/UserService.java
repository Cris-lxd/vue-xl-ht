package com.cris15.xl.servie;

import com.cris15.xl.entity.User;

import java.util.List;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 15:08
 * Project: demotest
 **/
public interface UserService {

    /**
     * 获取用户
     * @param id
     * @return
     */
    User getUserById(String id);

    /**
     * 账号和手机号匹配用户
     * @param username
     * @param phoneNumber
     * @param oldpassword
     * @return
     */
    List<User> getuserByNameAndPhone(String username, String phoneNumber,String oldpassword);

    /**
     * 忘记密码---修改密码
     * @param username
     * @param id
     * @param password
     * @return
     */
    int updateUserPwd(String username,String id,String password);
}
