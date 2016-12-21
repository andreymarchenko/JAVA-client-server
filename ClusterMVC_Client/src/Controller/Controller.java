package Controller;

import Model.BModelClient;
import Model.ModelClient;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private ModelClient modelClient;
    private Socket socket;
    private int port;
    private InetAddress ip;
    private SendThread sendThread;
    private RecvThread receiveThread;
    private boolean isConnect = false;
    private String login = "";
    private String password = "";
    private int priority;
    private String jarFilePath;

    public Controller() {
        modelClient = BModelClient.createModelClient();
    }

    public ModelClient getModel() {
        return this.modelClient;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public SendThread getSendThread() {
        return sendThread;
    }

    public void setSendThread(SendThread sendThread) {
        this.sendThread = sendThread;
    }

    public RecvThread getReceiveThread() {
        return receiveThread;
    }

    public void setReceiveThread(RecvThread receiveThread) {
        this.receiveThread = receiveThread;
    }

    public boolean getIsConnect() {
        return isConnect;
    }

    public void setIsConnect(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }      

    public String getJarFilePath() {
        return jarFilePath;
    }

    public void setJarFilePath(String jarFilePath) {
        this.jarFilePath = jarFilePath;
    }

    public void HandlerRequestOfClient(String request) {
        if (request.equalsIgnoreCase("R")) {
            Registration();
        } else if (request.equalsIgnoreCase("A")) {
            Authorization();
        } else if (request.equalsIgnoreCase("S")) {
            Send();
        } else if (request.equalsIgnoreCase("D")) {
            Disconnect();
        } else {
            WrongCommand();
        }
    }

    public void Registration() {
        //Не забыть засетить лог и пасс и isConnect
        if (!this.login.isEmpty() && !this.password.isEmpty()) {
            try {
                ip = InetAddress.getLocalHost();
                socket = new Socket(ip, port);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, "Error in process of creating socket", ex);
            }
            try {
                OutputStream cos = socket.getOutputStream();
                DataOutputStream dcos = new DataOutputStream(cos);

                dcos.writeUTF("R");
                dcos.writeUTF(this.login);
                dcos.writeUTF(this.password);

                InputStream clientInputStream = socket.getInputStream();
                DataInputStream dcis = new DataInputStream(clientInputStream);
                String reply = dcis.readUTF();

                if (reply.equalsIgnoreCase("RO")) {
                    modelClient.createDirectory(login);
                } else {
                    //Log.AddToLog("Registration is not OK!", jTextArea1, MY_NAME);
                    socket = null;
                }
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void Authorization() {
        if (!isConnect) {
            if (!this.login.isEmpty() && !this.password.isEmpty()) {
                try {
                    ip = InetAddress.getLocalHost();
                    socket = new Socket(ip, port);

                    OutputStream cos = socket.getOutputStream();
                    DataOutputStream dcos = new DataOutputStream(cos);

                    dcos.writeUTF("A");
                    dcos.writeUTF(this.login);
                    dcos.writeUTF(this.password);

                    InputStream cis = socket.getInputStream();
                    DataInputStream dcis = new DataInputStream(cis);

                    String reply;

                    reply = dcis.readUTF();

                    if (reply.equalsIgnoreCase("AO")) {
                        //Log.AddToLog("Authorization succesful!", jTextArea3, MY_NAME);

                        isConnect = true;

                        receiveThread = new RecvThread(socket, login);
                        /*jTextArea3 сюда*/
                        receiveThread.start();

                    } else {
                        //Log.AddToLog("Please, enter correct login and password!", jTextArea3, MY_NAME);
                        isConnect = false;
                    }

                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Log.AddToLog("Please, input Login and Password!", jTextArea3, MY_NAME);
            }
        } else {
            //Log.AddToLog("Client have already connected and authorized!", jTextArea3, MY_NAME);
        }
    }

    public void Send() {
        if (isConnect) {
            if (!jarFilePath.equalsIgnoreCase("")) {
                sendThread = new SendThread(socket); //jTextArea3 добавить
                
                sendThread.SendJavaByteFile(jarFilePath, "1");
            } else {
                //Log.AddToLog("Jar file is not choosen!", jTextArea3, MY_NAME);
            }
        } else {
            //Log.AddToLog("You are not authorized on server", jTextArea3, MY_NAME);
        }
    }

    public void Disconnect() {
        if (isConnect) {
            try {
                if (sendThread != null) {
                    sendThread.stop();
                }

                if (receiveThread != null) {
                    receiveThread.stop();
                }

                OutputStream cos = socket.getOutputStream();
                DataOutputStream dcos = new DataOutputStream(cos);

                dcos.writeUTF("D");

                isConnect = false;
                //Log.AddToLog("Client disconnected from server!", jTextArea3, MY_NAME);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //Log.AddToLog("Client is not authorized to server!", jTextArea3, MY_NAME);
        }
    }

    public void WrongCommand() {
        //Log.AddToLog("Wrong command!", jTextArea3, MY_NAME);
    }

}
