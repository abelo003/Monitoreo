/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
public class PropertiesTest {

    private static final String USER_HOME = System.getProperty("user.home");
    @Value("user.home.folder")
    private String folderName;
    
    @Value("user.home.file.type")
    private String fileType;
    
    @Value("application.name")
    private String arti;
    
    @Value("${application.version}")
    private String version;
    
    @Value("${ip.servidor.monitoreo}")
    private String ipMonitoreo;
    
    @PostConstruct
    public void init(){
        System.out.println(folderName);
        System.out.println(fileType);
        System.out.println(USER_HOME);
        System.out.println(arti);
        System.out.println(version);
        System.out.println(ipMonitoreo);
    }
}
