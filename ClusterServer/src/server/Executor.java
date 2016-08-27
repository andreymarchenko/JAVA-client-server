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
import javax.swing.table.DefaultTableModel;
import server.Log;

/**
 *
 * @author Andrey
 */
public class Executor {
    final String MY_NAME = "Executor";
    
    String path_to_jar_file;
    String path_to_result;
    
    File result;

    public Executor(String _path_to_jar_file) {
        path_to_jar_file = _path_to_jar_file;
    }

    public void execute(String _path) {
        path_to_result = _path;

        File fl = new File(path_to_result); // Создаем текстовый файл с результатом
        String string_split[] = path_to_jar_file.split("/");
        String new_path_to_jar_file_for_process_builder = string_split[0];
        
        for (int i = 1; i < string_split.length; i++) {
            new_path_to_jar_file_for_process_builder += "\\" + string_split[i];
        }

        String absolute_path_to_directory = "C:\\JavaRep\\JAVA-client-server\\ClusterServer\\src";
        for (int i = 1; i < string_split.length - 1; i++) {
            absolute_path_to_directory += "\\" + string_split[i];
        }

        File dir = new File(absolute_path_to_directory);
        String str_path = "C:\\JavaRep\\JAVA-client-server\\ClusterServer\\" + new_path_to_jar_file_for_process_builder;
        ArrayList<String> command_for_builder = new ArrayList<>();

        /*
            PATH FOR ANDREY "C:\\Program Files\\Java\\jdk1.7.0_79\\bin\\java"
            PATH FOR IGOR   "C:\\Program Files\\Java\\jdk1.8.0_74\\bin\\java"
         */
        
        command_for_builder.add("C:\\Program Files\\Java\\jdk1.8.0_74\\bin\\java");
        command_for_builder.add("-jar");
        command_for_builder.add(str_path);
        
        ProcessBuilder procBuilder = new ProcessBuilder(command_for_builder);
        
        procBuilder.directory(dir);
        procBuilder.redirectErrorStream(true);

        try {
            Process process = procBuilder.start();
            InputStream stdout = process.getInputStream();
            FileOutputStream fos = new FileOutputStream(path_to_result);
            
            byte symbol;
            while ((symbol = (byte) stdout.read()) != -1) {
                fos.write(symbol);
            }

        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
