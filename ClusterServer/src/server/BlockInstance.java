/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import server.SendThread;
import server.ExecutingThread;

/**
 *
 * @author Игорь
 */
public class BlockInstance {
    String path_to_jar_file;
    Socket cs;
    String path_to_result;
    int priority;
    boolean LookedByQueue = false;
    JTextArea Logs;
    JTable Table; // для отображения статуса задачи
    int pos_in_table = 0; // для отображения статуса задачи
    
    SendThread ST = null;
    ExecutingThread ET = null;
    
    BlockInstance() {
    
    }
    
    BlockInstance( Socket _cs,  String _path_to_jar_file, String _path_to_result, int _priority, JTextArea _Logs) {    
        path_to_jar_file = _path_to_jar_file;
        path_to_result = _path_to_result;
        priority = _priority;
        cs = _cs;
        Logs = _Logs;
        
        ST = new SendThread(cs, Logs);
        ET = new ExecutingThread(path_to_jar_file);
    }
    
    public void setPathToResultFile(String _path_to_result) {
        path_to_result = _path_to_result;
    }
    
    public void setLookedByQueue(boolean _LookedByQueue) {
        LookedByQueue = _LookedByQueue;
    }
    
    synchronized public void ExecuteTask() {
        if(ET != null) {
            ET.execute(ST, path_to_result, Table, pos_in_table);
        }
    }
    
    synchronized public void SendResults() {
        if(ST != null) {
            ST.SendResult(path_to_result);
        }
    }
    
    public void Implement(JTable _Table) {
        Table = _Table;
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        model.setValueAt("RUNNING", pos_in_table, 3);
        
        SendResults(); // Здесь поток SendThread застопается. Он пробудится только после того, как его разбудит ExecutingThread
        ExecuteTask();
    }
    
}
