package com.cris15.xl.mapper;

import com.cris15.xl.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/22/10:30
 * @Description:
 **/
@Component
public interface LoginMapper {
    /**
     * user
     */
    User checkUser(@Param("username") String username, @Param("password")String password);

    /**
     * 创建用户
     */
    int addUser(@Param("id")String id,@Param("username") String username, @Param("password")String password);

    /**
     * 根绝用户名查找
     */
    @Select("select * from sprint_user where username = #{username}")
    User selectByUsername(String username);
}
