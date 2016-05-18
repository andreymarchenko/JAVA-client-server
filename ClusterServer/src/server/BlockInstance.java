/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import javax.swing.JTextArea;
import server.SendThread;

/**
 *
 * @author Игорь
 */
public class BlockInstance {
    String path_to_jar_file = "";
    Socket cs;
    String path_to_result = "";
    String priority;
    JTextArea Logs;
    
    SendThread ST = null;
    
    
    BlockInstance( Socket _cs,  JTextArea _Logs) {    
        cs = _cs;
        Logs = _Logs;
        
        ST = new SendThread(cs, Logs);
    }
    
    public void setPathToResultFile(String _path_to_result) {
        path_to_result = _path_to_result;
    }
    
    public void setFileData(String _path_to_jar_file, String _priority) {
        path_to_jar_file = _path_to_jar_file;
        priority = _priority;
    }
    
    public void SendResults() {
        if(ST != null) {
            ST.SendResult(path_to_result);
        }
    }
    
}
