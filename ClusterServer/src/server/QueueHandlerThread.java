/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;
import server.BlockInstance;
import server.ComparatorPriorityTask;

/**
 *
 * @author Игорь
 */

enum STATUSES {
    PENDING,
    WAITING,
    PROCESSING,
    FINISH
};

public class QueueHandlerThread extends Thread {

    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ;
    static Hashtable<Key, BlockInstance> HT;
    
    JTextArea Logs;
    
    JTable Table;
    String[] columnNames = {"Login_client",
        "Name_task",
        "Priority",
        "Status"};

    QueueHandlerThread(JTextArea _Logs, JTable _Table, Hashtable<Key, BlockInstance> _HT) {
        Table = _Table;
        Logs = _Logs;
        HT = _HT;
    }

    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }
    
    public String ConvertSTATUSESToString(STATUSES S) {
        switch(S) {
            case PENDING: return "PENDING";
            case WAITING: return "WAITING";
            case PROCESSING: return "PROCESSING";
            case FINISH: return "FINISH";
            default: return "N/A";             
        }
    }

    public void AddTaskToQueue(BlockInstance BI) {
        ComparatorPriorityTask CPT = new ComparatorPriorityTask(BI);
        PBQ.add(CPT);
    }
    
    @Override
    public void run() {
        while (true) {
            for (Map.Entry<Key, BlockInstance> entrySet : HT.entrySet()) {
                Key key = entrySet.getKey();
                BlockInstance BI = entrySet.getValue();
                if(BI.LookedByQueue == false) {
                    BI.LookedByQueue = true;
                    AddTaskToQueue(BI);
                    
                }
            }
            if(!HT.isEmpty()) {
                PBQ.poll().BI.Implement();
            }
        }

    }

}
