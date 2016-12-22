package Presenter;

import Model.BModelServer;
import Model.IModelServer;
import View.IViewServer;
import java.net.Socket;

public class Presenter implements IPresenter {
    IViewServer viewServer;
    IModelServer modelServer;
    ServerThread serverThread = null;
    Sender sender;
    
    public Presenter(IViewServer viewServer) {
        this.modelServer = BModelServer.createModelServer();
        this.modelServer.setPresenter(this);
        this.viewServer = viewServer;          
    }

        @Override
    public void StartServer() {
        if(serverThread == null)
        {
            serverThread = new ServerThread(modelServer);
            serverThread.start();
        }
    }

    @Override
    public void StopServer() {
        if(serverThread != null)
        {
            serverThread = null;
            serverThread.StopServer();
        }
    }
    
    public void setViewServer(IViewServer viewServer) {
        this.viewServer = viewServer;
    }

    public void setModelServer(IModelServer modelServer) {
        this.modelServer = modelServer;
    }
    
    

    public IModelServer getModelServer() {
        return modelServer;
    }

    @Override
    public IViewServer getViewServer() {
        return this.viewServer;
    }

    @Override
    public void sendResult(Socket cs, String pathToRes) {
        Sender SR = new Sender(cs);
        
        SR.SendResult(pathToRes);
    }
}