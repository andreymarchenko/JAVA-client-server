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
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */
public class RecvThread extends Thread {

    final String RELATIVE_PATH_FOR_JAVA_BYTE_FILES = "src/Files/JavaByteFilesFromClient/";
    final int CHUNK_BYTE_SIZE = 1024;
    JTextArea Logs = null;
    Socket cs = null;
    InputStream sis = null;
    Hashtable<String, Socket> HT;
    String Login = null;
    String Password = null;
    
    boolean IsAuthorized = false;
    boolean IsClientDisconnect = false;

    public RecvThread(Socket _cs, JTextArea _Logs, Hashtable<String, Socket> _HT) {
        cs = _cs;
        Logs = _Logs;
        HT = _HT;

        if (cs != null) {

            try {
                sis = cs.getInputStream(); // Get the output stream. Now we may receive the data from client
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }

        }
    }

    public void SendReplyToClient(String reply) {
        if(cs != null) {
            try {
                OutputStream sos = cs.getOutputStream();
                DataOutputStream dsos = new DataOutputStream(sos);
                
                dsos.writeUTF(reply);
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }

    public void Registration(String _Login, String _Password) {
        if (true) { // Если пользователя нет в БД
            String reply = "RecvThread:" + _Login + " was registrated!";
            SendReplyToClient("RO");
            AddToLog(reply);
        } else {
            String reply = "RecvThread:" + _Login + " was not registrated!";
            SendReplyToClient("RN"); // Пользователь с таким именем существует
            AddToLog(reply);
        }

        IsClientDisconnect = true;
        
        try {
            cs.close(); // Если пользователь авторизован, то Registration не будет вызвана, поэтому сокет не закроется
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Authorization(String _Login, String _Password) {
        if (!IsAuthorized) {

            // ПРОВЕРЯЕМ_ЗАРЕГАН_ЛИ_ПОЛЗЬОВАТЕЛЬ
            // IF_ZAREGAN: {
            Login = _Login;
            Password = _Password;

            HT.put(Login, cs);

            IsAuthorized = true;
            // }

            SendReplyToClient("AO");
            String reply = "RecvThread:" + Login + " is authorized";
            AddToLog(reply);
        } else {
            String reply = "RecvThread:" + "Authorization failed for " + Login;
            AddToLog(reply);
            SendReplyToClient("AN");
        }
    }

    public void Receive() {
        if (IsAuthorized) {

            File file;
            long size_file = 0;
            String name = null;
            String priority_file;

            DataInputStream sdis = new DataInputStream(sis);

            // Reading bytes of data from client
            try {
                size_file = sdis.readLong();
                name = sdis.readUTF();
                priority_file = sdis.readUTF();
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            String path_to_file = RELATIVE_PATH_FOR_JAVA_BYTE_FILES + name;
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
            /* try {
                sdis.close();
                sos.close();
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            SendReplyToClient("SO");
            AddToLog("RecvThread: File has been successfully received!");

            /*try {
                sis.close();

            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error in closing of Input and Output streams", ex);
            }*/
        } else {
            SendReplyToClient("SN");
        }

    }
    
    public void Disconnect() {
        if(IsAuthorized) {
            HT.remove(Login);
            IsAuthorized = false;
            
            try {
                cs.close();
                SendReplyToClient("DO");
            } catch (IOException ex) {
                SendReplyToClient("DN");
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void WrongCommand() {
        SendReplyToClient("CN");
    }

    /*
       1. Commands from client:
       
       1.1 "R" - Registration
       1.2 "A" - Authorization
       1.3 "S" - Send
       1.4 "D" - Disconnect
       1.5 "WRONG_COMMAND"
    
       2. Commands of server:
       
       2.1 "RO" - Registration is OK
       2.2 "RN" - Registration is not OK
       2.3 "AO" - Authorization is OK
       2.4 "AN" - Authorization is not OK
       2.5 "SO" - Receive file is OK
       2.6 "SN" - Receive file is not OK
       2.7 "DO" - Disconnect from server is OK
       2.8 "DN" - Disconnect from server is not OK
       2.9 "CN" - Wrong Command
     */
    
    public void HandlerOfClient() {
        DataInputStream sdis = new DataInputStream(sis);
        String login;
        String password;
        String command_from_client;

        try {
            command_from_client = sdis.readUTF();
            if (command_from_client.equalsIgnoreCase("R")) {
                login = sdis.readUTF();
                password = sdis.readUTF();

                Registration(login, password);
            } else if (command_from_client.equalsIgnoreCase("A")) {
                login = sdis.readUTF();
                password = sdis.readUTF();

                Authorization(login, password);
            } else if (command_from_client.equalsIgnoreCase("S")) {
                Receive();
            } else if (command_from_client.equalsIgnoreCase("D")) {
                Disconnect();
            } else {
                WrongCommand();
            }
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        while (true) {
            HandlerOfClient();
            
            if(IsClientDisconnect) {
                break;

                
            }
        }
    }
}
