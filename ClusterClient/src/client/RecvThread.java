/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

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

/**
 *
 * @author Игорь
 */
public class RecvThread extends Thread {

    final String RELATIVE_PATH_FOR_FILES = "C:/JavaRep/JAVA-client-server/ClusterClient/AllResults/";
    final int CHUNK_BYTE_SIZE = 1024;
    JTextArea Logs = null;
    Socket cs = null;
    InputStream cis = null;
    boolean IsConnect = true;
    JTable table;
    int table_size = 0;
    String Login;

    public RecvThread(Socket _cs, JTextArea _Logs, JTable _table, String _Login) {
        cs = _cs;
        Logs = _Logs;
        table = _table;
        Login = _Login;
        
        if (cs != null) {

            try {
                cis = cs.getInputStream(); // Get the output stream. Now we may receive the data from server
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

        while (IsConnect) {
            File file;
            long size_file = 0;
            String name = null;

            DataInputStream cdis = new DataInputStream(cis);

            // Reading bytes of result from server
            try {
                size_file = cdis.readLong();
                System.out.println(size_file);
                name = cdis.readUTF();
            } catch (IOException ex) {
                IsConnect = false;
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (!IsConnect) {
                try {
                    cs.close();
                } catch (IOException ex) {
                    Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            String path_to_file = RELATIVE_PATH_FOR_FILES + Login +"/Results/"+ name;
            System.out.println(name);
            System.out.println(path_to_file);
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
            /* try {
                sdis.close();
                sos.close();
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            AddToLog("RecvThread: Result has been successfully received!");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setValueAt(name, table_size, 0);
            model.setValueAt(path_to_file, table_size, 1);
            table.setModel(model);
            table_size++;

            /* try {
                sis.close();

            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error in closing of Input and Output streams", ex);
            }*/
        }
    }

}
