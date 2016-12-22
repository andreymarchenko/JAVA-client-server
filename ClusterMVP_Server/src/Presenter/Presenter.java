/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presenter;

import Model.BModelServer;
import Model.IModelServer;
import View.IViewServer;

/**
 *
 * @author Игорь
 */
public class Presenter implements IPresenter {
    IViewServer viewServer;
    IModelServer modelServer;
    ServerThread st = null;
    
    public Presenter(IViewServer _viewServer) {
        modelServer = BModelServer.createModelServer();
        viewServer = _viewServer;
        
        modelServer.setPresenter(this);
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

    @Override
    public void StartServer() {
        if(st == null)
        {
            st = new ServerThread();
            st.start();
        }
    }

    @Override
    public void StopServer() {
        if(st != null)
        {
            st = null;
            st.StopServer();
        }
    }
}
