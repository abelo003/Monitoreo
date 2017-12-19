/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import com.cruz.mx.monitoreo.view.Principal;
import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author acruzb
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.cruz.mx.monitoreo.business", "com.cruz.mx.monitoreo"})
public class SpringBootConsoleApplication implements CommandLineRunner {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Value("${application.version}")
    private String version;
    
    public static void main(String[] args) throws Exception {
        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setHeadless(false);
        app.setWebEnvironment(false);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal.applicationContext = applicationContext;
        Principal.version = version;
        Principal.iconoSistema = new ImageIcon(this.getClass().getClassLoader().getResource("images/monitoreo.png"));
        Principal.main(args);
    }
    
}
