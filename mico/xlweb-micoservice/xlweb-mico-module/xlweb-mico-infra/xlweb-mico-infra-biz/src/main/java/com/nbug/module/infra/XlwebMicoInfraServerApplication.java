package com.nbug.module.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 项目的启动类
 * <p>
 *
 * @author NBUG
 */
@ComponentScan(basePackages = {"com.nbug"}) // 需要扫描通过maven引入的jar包中的类
@SpringBootApplication
public class XlwebMicoInfraServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(XlwebMicoInfraServerApplication.class, args);
    }

}
