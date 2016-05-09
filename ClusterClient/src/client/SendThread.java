/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.DataOutputStream;
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
   1.Header (46 - 80 bytes)
     1.1.Name of file (32 bytes)
     1.2.Size of name file (1-32 bytes)
     1.3.Size of file (4 bytes)
     1.4.Priority (6 bytes)
     1.5.Size of priority (3-6 bytes)
   2.Data (> 0 bytes)
*/

public class SendThread extends Thread {
        final int CHUNK_BYTE_SIZE = 1024;
    
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
                char[] priority_file;
                long size_file = file.length();
                int size_name_of_file = file.getName().length();
                int size_of_priority = priority.length();
                
                name = new char[size_name_of_file];
                priority_file = new char[size_of_priority];
                
                name = file.getName().toCharArray();
                priority_file = priority.toCharArray();
                
                // Initialization of general info complete. Now we are sending
                // this info to server
                
                DataOutputStream dos = new DataOutputStream(cos);
                
                try {
                    dos.writeLong(size_file);
                    dos.write(size_name_of_file);
                    dos.write(size_of_priority);
                    dos.writeChars(name.toString());
                    dos.writeChars(priority_file.toString());
                    
                } catch (IOException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                /* At the second step we read bytes of JavaByteCode file */
                
                byte[][] chunks_whole;
                byte[] chunk_rem;
                long num_of_chunks = size_file / CHUNK_BYTE_SIZE;
                long remainder_chunk_size = size_file % CHUNK_BYTE_SIZE;
                
                chunk_rem = new byte[(int)remainder_chunk_size];

                try {
                    cis = new FileInputStream(path_to_file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, "Error in creating of InputStream", ex);
                }
                
                // Send the chunks with CHUNK_BYTE_SIZE bytes
                if (num_of_chunks != 0) {
                    chunks_whole = new byte[(int) num_of_chunks][CHUNK_BYTE_SIZE];
                    for (int i = 0; i < num_of_chunks; i++) {
                        try {
                            int bytes_read = cis.read(chunks_whole[i], 0, CHUNK_BYTE_SIZE);
                            cos.write(chunks_whole[i], 0, bytes_read);
                        } catch (IOException ex) {
                            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                // Send the chunk with remainder bytes
                if (remainder_chunk_size != 0) {
                    num_of_chunks++;
                    
                    try {
                        int bytes_read = cis.read(chunk_rem, 0, (int)remainder_chunk_size);
                        cos.write(chunk_rem, 0, bytes_read);
                    } catch (IOException ex) {
                        Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                try {
                    dos.close();
                } catch (IOException ex) {
                    Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
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
