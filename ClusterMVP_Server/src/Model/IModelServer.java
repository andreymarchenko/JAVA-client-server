package Model;

import Presenter.IPresenter;
import java.net.Socket;

public interface IModelServer {
    public void setPresenter(IPresenter _presenter);
    public void addElementToHashTable(String login, String nameFile, String pathToJarFiles, String pathToResultFiles, int priorityTask, Socket cs, Object lock);
    
    public void createQueueHandlerThread(Object lock);
    public void stopQueueHandlerThread();
    
    public boolean registrateUser(String login, String password);
    public boolean authorizateUser(String login, String password);
}
