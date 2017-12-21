/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author acruzb
 */
@Configuration
public class PreferenceConfig {

    private static final Logger LOGGER = Logger.getLogger(PreferenceConfig.class);
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String FILE_SEPERATOR = File.separator;
    @Value("${user.home.folder}")
    private String folderName;
    
    @Value("${user.home.file.type}")
    private String fileType;
    
    public static String RUTA_ARCHIVO;
    
    @PostConstruct
    public void init() throws URISyntaxException{
        RUTA_ARCHIVO = USER_HOME.concat(FILE_SEPERATOR).concat(folderName.toLowerCase()).concat(FILE_SEPERATOR).concat(folderName.toLowerCase()).concat(fileType);
        Path path = Paths.get(RUTA_ARCHIVO);
        LOGGER.info("se verifica la existencia del archivo de preferencias.");
        if(Files.notExists(path.getParent())){
            LOGGER.info("El archivo no existe.");
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                LOGGER.info(RUTA_ARCHIVO + " -- Creado correctamente.");
            } catch (IOException ex) {
                LOGGER.info("Ocurrio un error al crear el directorio.", ex);
            }
        }
        else{
            LOGGER.info("Archivo existente.");
        }
    }
}
