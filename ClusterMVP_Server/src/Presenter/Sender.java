/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter;

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
public class Sender {

    final String RELATIVE_PATH_FOR_RESULTS = "src/Results/";
    final int CHUNK_BYTE_SIZE = 1024;
    final String MY_NAME = "Sender";

    JTextArea Logs = null;
    Socket cs = null;
    OutputStream sos = null;
    String path_to_file = null;
    InputStream sis = null;

    public Sender(Socket _cs, JTextArea _Logs) {

        cs = _cs;
        Logs = _Logs;

        if (cs != null) {

            try {
                sos = cs.getOutputStream(); // Get the output stream. Now we may send the result to client
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, "Error of getting output stream", ex);
            }

        }
    }

    public void SendResult(final String _path_to_file) {
        path_to_file = _path_to_file;
        File file = new File(path_to_file);

        if (file.exists()) {

            String name;
            long size_file = file.length();

            name = file.getName();

            // Initialization of general info complete. Now we are sending
            // this info to client
            DataOutputStream sdos = new DataOutputStream(sos);
            try {
                sdos.writeLong(size_file);
                sdos.writeUTF(name);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* At the second step we read bytes of file with result */
            byte[][] chunks_whole;
            byte[] chunk_rem;
            long num_of_chunks = size_file / CHUNK_BYTE_SIZE;
            long remainder_chunk_size = size_file % CHUNK_BYTE_SIZE;

            chunk_rem = new byte[(int) remainder_chunk_size];

            try {
                sis = new FileInputStream(path_to_file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, "Error in creating of InputStream", ex);
            }

            // Send the chunks with CHUNK_BYTE_SIZE bytes
            if (num_of_chunks != 0) {
                chunks_whole = new byte[(int) num_of_chunks][CHUNK_BYTE_SIZE];
                for (int i = 0; i < num_of_chunks; i++) {
                    try {
                        int bytes_read = sis.read(chunks_whole[i], 0, CHUNK_BYTE_SIZE);
                        sos.write(chunks_whole[i], 0, bytes_read);
                    } catch (IOException ex) {
                        Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            // Send the chunk with remainder bytes
            if (remainder_chunk_size != 0) {
                num_of_chunks++;

                try {
                    int bytes_read = sis.read(chunk_rem, 0, (int) remainder_chunk_size);
                    sos.write(chunk_rem, 0, bytes_read);
                } catch (IOException ex) {
                    Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            //Log.AddToLog("Result has been successfully sent!", Logs, MY_NAME);
        } else {
            //Log.AddToLog("File is not exist!", Logs, MY_NAME);
        }

    }
}
