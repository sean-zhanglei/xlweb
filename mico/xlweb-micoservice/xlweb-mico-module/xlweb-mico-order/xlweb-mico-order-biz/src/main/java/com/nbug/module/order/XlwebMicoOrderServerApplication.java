package com.nbug.module.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.nbug"}) // 需要扫描通过maven引入的jar包中的类
@SpringBootApplication
public class XlwebMicoOrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XlwebMicoOrderServerApplication.class, args);
    }

}
