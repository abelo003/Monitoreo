/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author acruzb
 */
@Component
public class ExecuteShellComand {

    public void excecuteJarCommand() throws URISyntaxException, UnsupportedEncodingException {
        String path = new File("").getAbsolutePath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        String osName = System.getProperty("os.name");
        String[] cmd = new String[4];
        if (osName.contains("Windows")) {
            cmd[0] = "cmd";
            cmd[1] = "/C";
            cmd[2] = "cd " + decodedPath;
            cmd[3] = "&& jar -uf Monitoreo-1.0-SNAPSHOT-jar-with-dependencies.jar archivo.txt";
        }
//        String command = "jar -uvf Monitoreo-1.0-SNAPSHOT-jar-with-dependencies.jar archivo.txt";
        String command1 = "cd " + decodedPath;
        System.out.println(command1);
        System.out.println(executeCommand(cmd));
    }

    private String executeCommand(String[] command) {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
