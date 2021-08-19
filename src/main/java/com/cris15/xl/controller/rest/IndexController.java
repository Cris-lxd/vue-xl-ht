package com.cris15.xl.controller.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cris15.xl.controller.BaseController;
import com.cris15.xl.entity.User;
import com.cris15.xl.mapper.UserMapper;
import com.cris15.xl.util.CurrentUser;
import com.cris15.xl.util.NotFoundException;
import com.cris15.xl.util.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Cris_liuxd
 * @Date: 2021/06/21/10:17
 * @Description:
 **/
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private UserMapper userMapper;

    @UserLoginToken
    @RequestMapping("/test")
    public List test(@CurrentUser User user){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("id","username","password");
        List list = userMapper.selectList(queryWrapper);
        return list;
    }
}