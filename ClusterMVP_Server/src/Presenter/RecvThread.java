package Presenter;

import Model.IModelServer;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class RecvThread extends Thread {

    private IModelServer modelServer;
    private final String RELATIVE_PATH_FOR_ALL_DIRECTORIES = "src/Files/";
    private final String MY_NAME = "RecvThread";
    private final int CHUNK_BYTE_SIZE = 1024;
        
    private Socket cs = null;
    private InputStream sis = null;

    private String Login = null;
    private String Password = null;
    
   // JTextArea Logs = null;
    
    Object lock;

    boolean IsAuthorized = false;
    boolean IsClientDisconnect = false;

    
    public RecvThread(Socket _cs,
                      //JTextArea _Logs,
                      Object _lock) {
        
        lock = _lock;
        cs = _cs;
        //Logs = _Logs;

        if (cs != null) {

            try {
                sis = cs.getInputStream(); // Get the output stream. Now we may receive the data from client
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, "Error of getting intput stream", ex);
            }

        }
    }

    public void SendReplyToClient(String reply) {
        if (cs != null) {
            try {
                OutputStream sos = cs.getOutputStream();
                DataOutputStream dsos = new DataOutputStream(sos);
                dsos.writeUTF(reply);
                
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Registration(String _Login, String _Password) {
        if(modelServer.registrateUser(_Login, _Password)) {
            String reply = _Login + " was registrated!";
            SendReplyToClient("RO");
        } else {
            String reply = _Login + " was not registrated!";
            SendReplyToClient("RN"); // Пользователь с таким именем существует
        }

        IsClientDisconnect = true;

        try {
            cs.close(); // Если пользователь авторизован, то Registration не будет вызвана, поэтому сокет не закроется
        } catch (IOException ex) {
            Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Authorization(String _Login, String _Password) {
        if (modelServer.authorizateUser(_Login, _Login)) {
            Login = _Login;
            Password = _Password;

            IsAuthorized = true;
            SendReplyToClient("AO");
            String reply = Login + " is authorized";
            //  Log.AddToLog(reply, Logs, MY_NAME);
        } else {
            String reply = "Authorization failed for " + Login;
            // Log.AddToLog(reply, Logs, MY_NAME);
            SendReplyToClient("AN");
            IsClientDisconnect = true;
        }
    }

    public void Receive() {
        String path_to_java_byte_file = "";
        String path_to_result_file = "";

        if (IsAuthorized) {
            File file;
            long size_file = 0;
            String name = null;
            String priority_file = null;

            DataInputStream sdis = new DataInputStream(sis);

            // Reading bytes of data from client
            try {
                size_file = sdis.readLong();
                name = sdis.readUTF();
                priority_file = sdis.readUTF();
            } catch (IOException ex) {
                Logger.getLogger(RecvThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            String path_to_java_byte_files = RELATIVE_PATH_FOR_ALL_DIRECTORIES + Login + "/JavaByteFiles/" + name;
            file = new File(path_to_java_byte_files);

            /* At the second step we read bytes of JavaByteCode file from client */
            byte[][] chunks_whole;
            byte[] chunk_rem;
            long num_of_chunks = size_file / CHUNK_BYTE_SIZE;
            long remainder_chunk_size = size_file % CHUNK_BYTE_SIZE;

            chunk_rem = new byte[(int) remainder_chunk_size];

            OutputStream sos = null;

            try {
                sos = new FileOutputStream(path_to_java_byte_files);
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

            String name_of_result_file = name.replaceAll("jar", "txt");
            
            path_to_result_file = RELATIVE_PATH_FOR_ALL_DIRECTORIES +
                                  Login + "/" + "Results/" +
                                  name_of_result_file;
            
            modelServer.addElementToHashTable(Login, name, path_to_java_byte_files, path_to_result_file, ConvertStringPriorityToInt(priority_file), cs, lock);

           // Log.AddToLog("File has been successfully received!", Logs, MY_NAME);
        }
    }

    public void Disconnect() {
        if (IsAuthorized) {
            IsAuthorized = false;
            String reply = Login + " disconnect from server";

            IsClientDisconnect = true;

           // Log.AddToLog(reply, Logs, MY_NAME);
        } else {
            //Log.AddToLog(Login + " don't disconnect from server", Logs, MY_NAME);
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
       1.5 "B" - Get all logins from database (Android client only)
       1.6 "WRONG_COMMAND"
    
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
    
    public IModelServer getModelServer() {
        return modelServer;
    }

    public void setModelServer(IModelServer modelServer) {
        this.modelServer = modelServer;
    }
    
    public int ConvertStringPriorityToInt(String str) {
        if (str.equalsIgnoreCase("Low")) {
            return 0;
        } else if (str.equalsIgnoreCase("Medium")) {
            return 1;
        } else if (str.equalsIgnoreCase("High")) {
            return 2;
        } else {
            return -1;
        }
    }

    @Override
    public void run() {
        while (true) {
            HandlerOfClient();
            if (IsClientDisconnect) {
                break;
            }
        }
    }
}
