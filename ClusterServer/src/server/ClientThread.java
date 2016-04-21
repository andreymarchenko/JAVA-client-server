/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.UUID;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;

/**
 *
 * @author Игорь
 */

public class ClientThread extends Thread {
        Socket client_socket;
        UUID uuid_client;  // Unique id of client
        InputStream tis;   // for reading bytes in stream from client_socket
        OutputStream tos;  // for writing bytes to stream
        DataInputStream dtis;  // this using when server send the rezult of execution .class file which was sending by client
        String path_to_java_byte_code;
        
        public ClientThread(Socket _client_socket, String _path_to_java_byte_code) {
            uuid_client = UUID.randomUUID();
            this.client_socket = _client_socket;
            path_to_java_byte_code = _path_to_java_byte_code;
            
            try {
            tis = client_socket.getInputStream();
            tos = client_socket.getOutputStream();
            
            dtis = new DataInputStream(tis);
            }
            catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, "Error when write/read bytes", ex);  // This message will always be printed because this level is higher
            }
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
