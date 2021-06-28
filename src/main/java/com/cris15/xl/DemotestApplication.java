package com.cris15.xl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.cris15.xl.mapper"})
public class DemotestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemotestApplication.class, args);
    }

}
