package com.cris15.xl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * Author: Cris
 * Date: 2021/08/19
 * Time: 17:04
 * Project: demotest
 * Description：定时删除错误日志
 **/
@Component
public class ScheduleTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * “?”字符：表示不确定的值
     * “,”字符：指定数个值
     * “-”字符：指定一个值的范围
     * “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
     * “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
     * “W”字符：指定离给定日期最近的工作日(周一到周五)
     * “#”字符：表示该月第几个周X。6#3表示该月第3个周五
     *
     * 每隔5秒执行一次：/5 * * * ?
     * 每隔1分钟执行一次：0 /1 * * ?
     * 每天23点执行一次：0 0 23 * * ?
     * 每天凌晨1点执行一次：0 0 1 * * ?
     * 每月1号凌晨1点执行一次：0 0 1 1 * ?
     * 每月最后一天23点执行一次：0 0 23 L * ?
     * 每周星期天凌晨1点实行一次：0 0 1 ? * L
     * 在26分、29分、33分执行一次：0 26,29,33 * * * ?
     * 每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
     * 每天的8点开始，2小时执行一次：0 0 8/2 * * ?
     */
    @Scheduled(cron = "0 0 1 ? * L")
    public void myScheduleTask(){
        File file = new File("D:\\project\\ECSR_NJ\\ecology\\logs");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName();
            if(files[i].getName().length() > 11){
                files[i].delete();
            }
        }
        logger.info("删除成功,删除日期：" + StringUtils.dateFormat(new Date()));
    }
}