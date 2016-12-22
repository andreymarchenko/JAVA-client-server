package Presenter;

import Model.BModelServer;
import Model.IModelServer;
import View.IViewServer;

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
            serverThread = new ServerThread();
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

    public IViewServer getViewServer() {
        return viewServer;
    }

    public IModelServer getModelServer() {
        return modelServer;
    }
}
