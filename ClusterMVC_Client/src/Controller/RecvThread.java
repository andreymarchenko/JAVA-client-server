/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ModelClient;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class RecvThread extends Thread {

    private ModelClient modelClient;
    final int CHUNK_BYTE_SIZE = 1024;
    final String MY_NAME = "RecvThread";

    //JTextArea Logs = null;
    Socket cs = null;
    InputStream cis = null;
    boolean IsConnect = true;
    String Login;

    public RecvThread(Socket _cs,
            //JTextArea _Logs,
            String _Login) {

        cs = _cs;
        //Logs = _Logs;
        Login = _Login;

        if (cs != null) {

            try {
                cis = cs.getInputStream(); // Get the output stream. Now we may receive the data from server
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }
        }
    }

    @Override
    public void run() {
        while (IsConnect) {
            File file;
            long size_file = 0;
            String name = null;

            DataInputStream cdis = new DataInputStream(cis);

            // Reading bytes of result from server
            try {
                size_file = cdis.readLong();
                name = cdis.readUTF();
            } catch (IOException ex) {
                IsConnect = false;
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (IsConnect) {
                String path_to_file = modelClient.getUserDirectoryPath() + Login + "/Results/" + name;
                file = new File(path_to_file);

                /* At the second step we must read bytes of Result file from server */
                byte[][] chunks_whole;
                byte[] chunk_rem;
                long num_of_chunks = size_file / CHUNK_BYTE_SIZE;
                long remainder_chunk_size = size_file % CHUNK_BYTE_SIZE;

                chunk_rem = new byte[(int) remainder_chunk_size];

                OutputStream cos = null;

                try {
                    cos = new FileOutputStream(path_to_file);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Recv the chunks with CHUNK_BYTE_SIZE bytes
                if (num_of_chunks != 0) {
                    chunks_whole = new byte[(int) num_of_chunks][CHUNK_BYTE_SIZE];
                    for (int i = 0; i < num_of_chunks; i++) {
                        try {
                            int bytes_read = cis.read(chunks_whole[i], 0, CHUNK_BYTE_SIZE);
                            cos.write(chunks_whole[i], 0, bytes_read);
                        } catch (IOException ex) {
                            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                // Recv the chunk with remainder bytes
                if (remainder_chunk_size != 0) {
                    num_of_chunks++;

                    try {
                        int bytes_read = cis.read(chunk_rem, 0, (int) remainder_chunk_size);
                        cos.write(chunk_rem, 0, bytes_read);
                    } catch (IOException ex) {
                        Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Log.AddToLog("Result has been successfully received!", Logs, MY_NAME);
                this.modelClient.updateTableModel(path_to_file, name);
                
            }
        }
    }
}
