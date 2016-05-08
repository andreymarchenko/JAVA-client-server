/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.Socket;
import java.util.UUID;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;
import javax.swing.JTextArea;

/**
 *
 * @author Игорь
 */

public class ClientThread extends Thread {
        JTextArea Logs = null;
        Socket client_socket;
        UUID uuid_client;  // Unique id of client
        InputStream cis;   // for reading bytes in stream from client_socket
        OutputStream cos;  // for writing bytes to stream
        DataInputStream ctis;  // this using when server send the rezult of execution .class file which was sending by client
        
        public ClientThread(Socket _client_socket, String _path_to_java_byte_code, JTextArea _Logs) {
            Logs = _Logs;
            uuid_client = UUID.randomUUID();
            this.client_socket = _client_socket;
            
            AddToLog("Client thread connected to server!");
        }
        
        public void AddToLog(String info) {
            String curr_info = Logs.getText();
            curr_info += info + "\n";
            Logs.setText(curr_info);
        }
        
        public UUID GetUUIDOfClient() {
            return uuid_client;
        }
        
        @Override // Annotation. Check exist of redefined method in parent class 'Thread'
        public void run() {  // Connect to server
        
        }
        
        void SendJavaByteCodeToServer() {
        
        }
        
        void RecvTxtFileWithRezultsFromServer() {
            
        }
        
        void ReadTxtFile() throws IOException {
            
        }
}
