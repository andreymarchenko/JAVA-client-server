package Model;

import Presenter.IPresenter;
import java.io.File;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModelServer implements IModelServer {

    final String RELATIVE_PATH_FOR_ALL_DIRECTORIES = "src/Files/";

    private IPresenter presenter;
    private QueueHandlerThread queueHandlerThread;
    Hashtable<Key, BlockInstance> allClient; // Login of client <-> SocketClient

    public ModelServer() {
        allClient = new Hashtable<Key, BlockInstance>();
    }
        
    public IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public synchronized void addElementToHashTable(String login, String nameFile, String pathToJarFiles, String pathToResultFiles, int priorityTask, Socket cs, Object lock) {
        Key key = new Key(login, nameFile);

        BlockInstance BI = new BlockInstance(cs,
                pathToJarFiles,
                pathToResultFiles,
                priorityTask);
        BI.setPresenter(presenter);

        allClient.put(key, BI);
        
        synchronized(lock) {
            lock.notify();
        }
    }

    public void createDirectories(String login) {
        String directory_client = RELATIVE_PATH_FOR_ALL_DIRECTORIES + login;
        File specially_directory_for_client = new File(directory_client);
        specially_directory_for_client.mkdir();
        String path_to_directory_client = directory_client + "/";

        String directory_client_results = path_to_directory_client + "Results";
        File directory_with_results_for_client = new File(directory_client_results);
        directory_with_results_for_client.mkdir();

        String directory_client_binaries = path_to_directory_client + "JavaByteFiles";
        File directory_with_binaries_from_client = new File(directory_client_binaries);
        directory_with_binaries_from_client.mkdir();
    }

    @Override
    public boolean registrateUser(String login, String password) {
        ResultSet checkedlogin = null;
        ResultSet checkedpair = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c1 = null;
        try {
            c1 = DriverManager.getConnection("jdbc:sqlite:BASE.db");
            System.out.println("Opened database successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement checkuser;
        String str = "";

        try {
            checkuser = c1.prepareStatement("SELECT login FROM CLIENTS WHERE login = ?; ");
            checkuser.setString(1, login);
            checkedlogin = checkuser.executeQuery();

            while (checkedlogin.next()) {
                str = checkedlogin.getString(1);
                break;
            }

            checkuser.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!str.equalsIgnoreCase(login)) { // Если пользователя нет в БД
            PreparedStatement adduser;
            try {
                adduser = c1.prepareStatement("INSERT INTO CLIENTS (login, password)"
                        + " VALUES (?, ?); ");
                adduser.setString(1, login);
                adduser.setString(2, password);
                adduser.executeUpdate();
                adduser.close();
                c1.close();

                // Creating directories for each client
                createDirectories(login);

            } catch (SQLException ex) {
                Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
            }

            return true;
        } else {
            return false; // Пользователь с таким именем существует
        }
    }

    @Override
    public boolean authorizateUser(String login, String password) {
        ResultSet checkedlogin = null;
        ResultSet checkedpair = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:BASE.db");
            System.out.println("Opened database successfully");
        } catch (SQLException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement checkpair;
        String str1 = "";
        String str2 = "";

        try {
            checkpair = c.prepareStatement("SELECT login,password FROM CLIENTS WHERE login = ?; ");
            checkpair.setString(1, login);
            checkedpair = checkpair.executeQuery();

            while (checkedpair.next()) {
                str1 = checkedpair.getString(1);
                str2 = checkedpair.getString(2);
                break;
            }

            checkpair.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModelServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (str1.equalsIgnoreCase(login) && str2.equalsIgnoreCase(password)) {         
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createQueueHandlerThread(Object lock) {
        queueHandlerThread = new QueueHandlerThread(lock, allClient, presenter);      
        queueHandlerThread.start();
    }

    @Override
    public void stopQueueHandlerThread() {
        queueHandlerThread.stop();
    }
}
