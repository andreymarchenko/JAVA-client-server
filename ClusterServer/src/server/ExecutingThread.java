/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrey
 */
public class ExecutingThread extends Thread{
   public ExecutingThread() {   
   }   
   
   public void execute(String str) {        
       File fl = new File(str);
       ProcessBuilder procBuilder = new ProcessBuilder("java", "-jar", "C:\\JAVA-client-server\\ClusterServer\\easy.jar");       
       procBuilder.directory(fl);
       procBuilder.redirectErrorStream(true);
       try {
           Process process = procBuilder.start();
           InputStream stdout = process.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
           while((line = brStdout.readLine()) != null) {
               System.out.println(line);
           }
       } catch (IOException ex) {
           Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
       }             
   }
    
    public void run() {

    }
}
