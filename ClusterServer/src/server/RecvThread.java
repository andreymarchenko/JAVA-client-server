/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Олег
 */
public class RecvThread extends Thread {
    
        final String RELATIVE_PATH_FOR_FILES = "/src/JavaByteFilesOnServer/"; 
        final int CHUNK_BYTE_SIZE = 1024;
        JTextArea Logs = null;
        Socket cs = null;
        InputStream sis = null;

    public RecvThread(Socket _cs, JTextArea _Logs) {
        cs = _cs;
        Logs = _Logs;

        if (cs != null) {

            try {
                sis = cs.getInputStream(); // Get the output stream. Now we may send the data to client
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }

        }
    }
    
    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }
    
    @Override
    public void run() {

            
System.out.println("1");
        File file;
        long size_file = 0;
        char[] name = null;
        char[] priority_file;

        int size_name_of_file;
        int size_of_priority;

        DataInputStream sdis = new DataInputStream(sis);

        // Reading bytes of data from client
        try {
            size_file = sdis.readLong();
            size_name_of_file = sdis.readInt();
            size_of_priority = sdis.readInt();

            name = new char[size_name_of_file];
            priority_file = new char[size_of_priority];

            for (int i = 0; i < size_name_of_file; i++) {
                name[i] = sdis.readChar();
            }

            for (int i = 0; i < size_of_priority; i++) {
                priority_file[i] = sdis.readChar();
            }
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }
System.out.println("2");
        String path_to_file = RELATIVE_PATH_FOR_FILES + name.toString();
        file = new File(path_to_file);

        /* At the second step we read bytes of JavaByteCode file from client */
        byte[][] chunks_whole;
        byte[] chunk_rem;
        long num_of_chunks = size_file / CHUNK_BYTE_SIZE;
        long remainder_chunk_size = size_file % CHUNK_BYTE_SIZE;

        chunk_rem = new byte[(int) remainder_chunk_size];

        OutputStream sos = null;

        try {
            sos = new FileOutputStream(path_to_file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }
System.out.println("3");
        // Recv the chunks with CHUNK_BYTE_SIZE bytes
        if (num_of_chunks != 0) {
            chunks_whole = new byte[(int) num_of_chunks][CHUNK_BYTE_SIZE];
            for (int i = 0; i < num_of_chunks; i++) {
                try {
                    int bytes_read = sis.read(chunks_whole[i], 0, CHUNK_BYTE_SIZE);
                    sos.write(chunks_whole[i], 0, bytes_read);
                } catch (IOException ex) {
                    Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Recv the chunk with remainder bytes
        if (remainder_chunk_size != 0) {
            num_of_chunks++;

            try {
                int bytes_read = sis.read(chunk_rem, 0, (int) remainder_chunk_size);
                sos.write(chunk_rem, 0, bytes_read);
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
System.out.println("4");
        try {
            sdis.close();
            sos.close();
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddToLog("RecvThread: File has been successfully received!");

        try {
            sis.close();

        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error in closing of Input and Output streams", ex);
        }
System.out.println("5");
    }
}
