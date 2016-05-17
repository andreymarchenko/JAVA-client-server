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
   public void execute() {
       ProcessBuilder procBuilder = new ProcessBuilder("java", "-jar", "sqlite-jdbc-3.8.11.2.jar");       
       try {
           Process process = procBuilder.start();          
           procBuilder.redirectErrorStream(true);
           InputStream stdout = process.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
           System.out.println("huuuu");
           while(brStdout.readLine()!=null) {
               System.out.println(line);
           }
           try {
               int exitVal = process.waitFor();
               System.out.println(exitVal);
           } catch (InterruptedException ex) {
               Logger.getLogger(ExecutingThread.class.getName()).log(Level.SEVERE, null, ex);
           }
       } catch (IOException ex) {
           Logger.getLogger(ExecutingThread.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
    
    public void run() {
        while(true) {

}
    }
}
