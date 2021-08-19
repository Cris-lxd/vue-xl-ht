package com.cris15.xl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cris15.xl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 15:10
 * Project: demotest
 **/
@Component
public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取用户
     * @param id
     * @return
     */
//    User selectById(@Param("id") String id);
}
