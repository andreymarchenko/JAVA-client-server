/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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
 * @author Олег
 */
public class SendThread extends Thread {
        JTextArea Logs = null;
        OutputStream cos;  // for writing bytes to stream
        Socket cs;
        String path_to_file = null;
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
        
        public void SendJavaByteFile(String _path_to_file) {
            path_to_file = _path_to_file;
            start();
        }
        
        @Override
        public void run() {
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
                byte_as_symbol = (char)byte_in_file;
                cos.write(byte_as_symbol);
            } catch (IOException ex) {
                Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in reading and writing byte", ex);
            }
            
            while(byte_in_file != -1) {
                byte_as_symbol = (char)byte_in_file;
                
                try {
                    cos.write(byte_as_symbol);
                } catch (IOException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in writing byte to OutputStream", ex);
                }
            }
            
            try {
                cis.close();
                cos.close();
            } catch (IOException ex) {
                Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in closing of Input and Output streams", ex);
            }
        
        }
    
}
