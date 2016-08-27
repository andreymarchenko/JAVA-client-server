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
import javax.swing.JTable;
import javax.swing.JTextArea;
import server.RecvThread;
import server.QueueHandlerThread;
import server.BlockInstance;
import server.Key;
import server.Log;

/**
 *
 * @author Игорь
 */
public class ServerThread extends Thread {
    final String MY_NAME = "ServerThread";
    
    JTextArea Logs = null;
    JTable Table = null;
    ServerSocket server_socket;  // for establishing connection with clients
    Socket socket_client;
    int port = 4445;
    InetAddress ip = null;
    boolean IsStopped = true;
    InputStream sis;
    OutputStream sos;
    QueueHandlerThread QHT;
    Hashtable<Key, BlockInstance> allClient =
             new Hashtable<Key, BlockInstance>(); // Login of client <-> SocketClient
    
    private final Object lock = new Object();
    
    // PriorityBlockingQueue
    
    public ServerThread(JTextArea _Logs, JTable _Table) {
        Logs = _Logs;
        Table = _Table;
        
        try {
            ip = InetAddress.getLocalHost();
        }
        catch(IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in getLocalHost() function", ex);
        }
        
        try {
            server_socket = new ServerSocket(port, 0, ip);
        }
        catch(IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in create server_socket", ex);
        }
        
        QHT = new QueueHandlerThread(Logs,
                                     Table,
                                     allClient,
                                     lock);
        
        QHT.start();
        Log.AddToLog("Creating of server thread complete!", Logs, MY_NAME);
    }
    
    @Override
    public void run() {
        while(!IsStopped) {
            try {
                Log.AddToLog("Waiting of client...", Logs, MY_NAME);
                socket_client = server_socket.accept();
                Log.AddToLog("Connection with client complete!", Logs, MY_NAME);
            }
            catch(IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Can't accept", ex);
            }
            
            RecvThread RT = new RecvThread(socket_client, Logs, allClient, lock);
            RT.start();
        }
    }

    synchronized void StopServer() {

        IsStopped = true;
        Log.AddToLog("Server was stopped!", Logs, MY_NAME);
        stop();
        
        try {
            server_socket.close();
            QHT.stop();
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    synchronized void StartServer() {
        IsStopped = false;
        Log.AddToLog("Server was started!", Logs, MY_NAME);
        start(); // Call the run method of client
    }
}
