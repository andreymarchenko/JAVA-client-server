/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.swing.JTextArea;
import server.RecvThread;

/**
 *
 * @author Игорь
 */
public class ServerThread extends Thread {
    JTextArea Logs = null;
    ServerSocket server_socket;  // for establishing connection with clients
    Socket socket_client;
    int port = 4445;
    InetAddress ip = null;
    boolean IsStopped = false;
    InputStream sis;
    OutputStream sos;
    Hashtable<String, Socket> allClient =
             new Hashtable<String, Socket>(); // Login of client <-> SocketClient
    
    public ServerThread(JTextArea _Logs) {
        Logs = _Logs;
        
        try {
            ip = InetAddress.getLocalHost();
        }
        catch(IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in getLocalHost() function", ex);
        }
        
        try {
            server_socket = new ServerSocket(port,5, ip);
        }
        catch(IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in create server_socket", ex);
        }
        
        AddToLog("ServerThread: Creating of server thread complete!");
    }
    
    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }
    
    @Override
    public void run() {
        while(!IsStopped) {
            try {
                AddToLog("ServerThread: Waiting of client...");
                socket_client = server_socket.accept();
                AddToLog("ServerThread: Connection with client complete!");
            }
            catch(IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Can't accept", ex);
            }
            
            RecvThread RT = new RecvThread(socket_client, Logs, allClient);
            RT.start();
            
            /*SendThread ST = new SendThread(socket_client, Logs);
            ST.SendResult(_path_to_file);*/
            
        }
    }
    
    synchronized void StopServer() {
        IsStopped = true;
        AddToLog("ServerThread: Server was stopped!");
        stop();
    }
    
    synchronized void StartServer() {
        IsStopped = false;
        AddToLog("ServerThread: Server was started!");
        start(); // Call the run method of client
    }
    
}