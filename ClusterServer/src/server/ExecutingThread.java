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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.Log;

/**
 *
 * @author Andrey
 */
public class ExecutingThread extends Thread {

    Object semophore;
    String path_to_jar_file;
    String path_to_result;
    SendThread ST;
    boolean IsFinished = false;
    File result;
    JTable Table;
    int pos_in_table = 0;

    public ExecutingThread(String _path_to_jar_file) {
        path_to_jar_file = _path_to_jar_file;
    }

    public void execute(SendThread _ST, String _path, JTable _Table, int _pos_in_table, Object obj) {
        semophore = obj;
        Table = _Table;
        pos_in_table = _pos_in_table;
        path_to_result = _path;
        ST = _ST;
        start();
    }

    @Override
    public void run() {
        synchronized (semophore) {
            File fl = new File(path_to_result); // Создаем текстовый файл с результатом
            System.out.println(path_to_result);
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
            /*
            */
            command_for_builder.add("-jar");
            command_for_builder.add(str_path);
            ProcessBuilder procBuilder = new ProcessBuilder(command_for_builder);
            procBuilder.directory(dir);
            procBuilder.redirectErrorStream(true);

            System.out.println(path_to_jar_file);
            System.out.println(absolute_path_to_directory);
            System.out.println(str_path);
            System.out.println(new_path_to_jar_file_for_process_builder);

            try {
                Process process = procBuilder.start();
                InputStream stdout = process.getInputStream();

                FileOutputStream fos = new FileOutputStream(path_to_result);
                byte symbol;
                while ((symbol = (byte) stdout.read()) != -1) {

                    fos.write(symbol);
                }

                IsFinished = true;
                DefaultTableModel model = (DefaultTableModel) Table.getModel();
                model.setValueAt("FINISHED", pos_in_table, 3);

            } catch (IOException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
