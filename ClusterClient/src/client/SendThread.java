/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */


/*
   Format data for sending:
   1.Header (46 bytes)
     1.1.Name of file (32 bytes)
     1.2.Size of name file (4 bytes)
     1.3.Size of file (4 bytes)
     1.4.Priority (6 bytes)
   2.Data (> 0 bytes)
*/

public class SendThread extends Thread {
        JTextArea Logs = null;
        OutputStream cos;  // for writing bytes to stream
        Socket cs;
        String path_to_file = null;
        String priority;
        InputStream cis;
    
        public SendThread(Socket _cs, JTextArea _Logs) {
            cs = _cs;
            Logs = _Logs;
            
            if(cs != null) {
                
                try {
                    cos = cs.getOutputStream(); // Get the output stream. Now we may send the data to client
                } catch (IOException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error of getting output stream", ex);
                }
                
            }
        }
        
        public void AddToLog(String info) {
            String curr_info = Logs.getText();
            curr_info += info + "\n";
            Logs.setText(curr_info);
        }
                
        public void SendJavaByteFile(String _path_to_file, String _priority) {
            path_to_file = _path_to_file;
            priority = _priority;
            start();
        }
        
        @Override
        public void run() {
            
            // At the first step we will send the general info about file
            // such as: name, size, priority
            File file = new File(path_to_file);
            
            if (file.exists()) {
                char[] name;
                int size;
                
                name = new char[32];
                
                name = file.getName().toCharArray();
                System.out.print(name.length);

                for(int i = 0; i < 12; i++) {
                    System.out.print(name[i]);
                }
                
                // At the first step we read bytes of JavaByteCode file
                int byte_in_file = -1;
                char byte_as_symbol;

                try {
                    cis = new FileInputStream(path_to_file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in creating of InputStream", ex);
                }

                try {
                    byte_in_file = cis.read();
                    byte_as_symbol = (char) byte_in_file;
                    cos.write(byte_as_symbol);
                } catch (IOException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in reading and writing byte", ex);
                }

                while (byte_in_file != -1) {
                    byte_as_symbol = (char) byte_in_file;

                    try {
                        cos.write(byte_as_symbol);
                        byte_in_file = cis.read();
                    } catch (IOException ex) {
                        Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                AddToLog("SendThread: File has been successfully sent!");
            } else {
                AddToLog("SendThread: File is not exist!");
            }
            
            try {
                cis.close();
                cos.close();
            } catch (IOException ex) {
                Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in closing of Input and Output streams", ex);
            }
        
        }
    
}
