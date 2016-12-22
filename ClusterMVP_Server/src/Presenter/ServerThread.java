package Presenter;

import Model.IModelServer;
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
import Presenter.RecvThread;

public class ServerThread extends Thread {

    final String MY_NAME = "ServerThread";

    //JTextArea Logs = null;
    private ServerSocket serverSocket;  // for establishing connection with clients
    private Socket clientSocket;
    private int port;
    private InetAddress ip = null;
    private boolean isStopped = true;
    private IModelServer modelServer;
    private IPresenter presenter;
    //QueueHandlerThread QHT;

    private final Object lock = new Object(); // lock for using by RecvThread and TaskAdderThread

    // PriorityBlockingQueue
    public ServerThread(IModelServer modelServer) {
        //Logs = _Logs;
        this.modelServer = modelServer;
        
        try {
            port = 4445;
            ip = InetAddress.getLocalHost();
            serverSocket = new ServerSocket(port, 0, ip);
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Error in getLocalHost() function", ex);
        }

        this.modelServer.createQueueHandlerThread(lock);
        this.modelServer.setPresenter(presenter);
        
        // Log.AddToLog("Creating of server thread complete!", Logs, MY_NAME);
    }

    public IModelServer getModelServer() {
        return modelServer;
    }

    public void setModelServer(IModelServer modelServer) {
        this.modelServer = modelServer;
    }

    public void StartServer() {
        isStopped = false;
        //Log.AddToLog("Server was started!", Logs, MY_NAME);
        start();
    }

    @Override
    public void run() {
        System.out.println("Start server OK!");
        while (!isStopped) {
            try {
                //  Log.AddToLog("Waiting of client...", Logs, MY_NAME);
                clientSocket = serverSocket.accept();
                // Log.AddToLog("Connection with client complete!", Logs, MY_NAME);
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "ServerThread: Can't accept", ex);
            }
            
            
            RecvThread receiveThread = new RecvThread(clientSocket/*, Logs,*/, lock);
            receiveThread.setModelServer(modelServer);
            receiveThread.start();
        }
        System.out.println("Start server end!");
    }

    public void StopServer() {
        System.out.println("Stop!");
        isStopped = true;
        //  Log.AddToLog("Server was stopped!", Logs, MY_NAME);
        stop();

        try {
            serverSocket.close();
            modelServer.stopQueueHandlerThread();

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
}
