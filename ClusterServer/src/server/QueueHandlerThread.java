/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.Hashtable;
import java.util.concurrent.PriorityBlockingQueue;
import javax.swing.JTable;
import javax.swing.JTextArea;
import server.SendThread;
import server.ExecutingThread;
import server.BlockInstance;

/**
 *
 * @author Игорь
 */
public class QueueHandlerThread extends Thread {
    static PriorityBlockingQueue PBQ;
    static Hashtable<String, BlockInstance> HT;
    JTable Table;
    JTextArea Logs;

    QueueHandlerThread(JTextArea _Logs, JTable _Table, Hashtable<String, BlockInstance> _HT) {
        Table = _Table;
        Logs = _Logs;
        HT = _HT;
    }

    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }

    @Override
    public void run() {
        while(true) {
            
        }

    }

}
