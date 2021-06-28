package com.cris15.xl.servie;

import com.cris15.xl.entity.User;

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
}
