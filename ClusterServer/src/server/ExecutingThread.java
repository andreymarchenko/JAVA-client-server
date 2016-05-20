/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrey
 */
public class ExecutingThread extends Thread {
    
   String path_to_jar_file;
   String path_to_result;
   SendThread ST;
   boolean IsFinished = false;
   File result;
   
   public ExecutingThread(String _path_to_jar_file) {
       path_to_jar_file = _path_to_jar_file;
    }

    public void execute(SendThread _ST, String _path) {
        path_to_result=_path;
        ST = _ST;
        start();
    }

    @Override
    public void run() {
          
          
          String ss [] = path_to_jar_file.split("/");
          String sss = ss[0];
          for (int i = 1; i < ss.length; i++)
              sss += "\\" + ss[i];
          
          String sss2 = "C:\\JAVA-client-server\\ClusterServer\\";
          for (int i = 1; i < ss.length-1; i++)
              sss2 += "\\" + ss[i];
        String str_path = "C:\\JAVA-client-server\\ClusterServer\\" + sss;
        File fl = new File(sss2);
        ArrayList<String> l = new ArrayList<>();
        l.add("\"C:\\Program Files\\Java\\jre7\\bin\\java.exe\"");
        l.add("-jar");
        l.add(str_path);
        //path_to_jar_file.replaceAll("\\", "/")
        ProcessBuilder procBuilder = new ProcessBuilder(l);
     //   procBuilder.directory(fl);
     //   procBuilder.redirectErrorStream(true);
        System.out.println(path_to_jar_file);
        try {
            Process process = procBuilder.start();
            InputStream stdout = process.getInputStream();
            InputStreamReader isrStdout = new InputStreamReader(stdout);
            //InputStreamReader q = new InputStreamWriter(stdout);
            BufferedReader brStdout = new BufferedReader(isrStdout);
            FileOutputStream fos = new FileOutputStream(path_to_result);
//BufferedWriter bwStdout = new BufferedWriter(isrStdout);
            String line = null;
            byte[] buffer;
            while (brStdout.readLine() != null) {
                line=brStdout.readLine();
                buffer = line.getBytes();
                fos.write(buffer,0,buffer.length);
                System.out.println(line);
            }
            //result=new File(path_to_result,brStdout.);
            
            
            IsFinished = true;
            ST.notify();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
