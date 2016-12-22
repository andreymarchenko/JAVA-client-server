package Presenter;

import View.IViewServer;
import java.net.Socket;

public interface IPresenter {
    // for ViewServer
    public void StartServer();
    public void StopServer();    
    // for ModelServer
    public IViewServer getViewServer();
    public void sendResult(Socket cs, String pathToRes);
}
