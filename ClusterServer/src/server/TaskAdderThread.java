/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Игорь
 */
public class TaskAdderThread extends Thread {

    final String MY_NAME = "TaskAdderThread";
    
    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ;
    static Hashtable<Key, BlockInstance> HT;
    
    int size_rows_in_table = 0;

    JTable Table;
    JTextArea Logs;
    
    Object lockForRecvThread;
    Object lockForQueueHandlerThread;
    
    TaskAdderThread(PriorityBlockingQueue<ComparatorPriorityTask> _PBQ,
                    Hashtable<Key, BlockInstance> _HT,
                    JTable _Table,
                    Object _lockForRecvThread,
                    Object _lockForQueueHandlerThread,
                    JTextArea _Logs) {
        
        PBQ = _PBQ;
        HT = _HT;
        Table = _Table;
        Logs = _Logs;
        lockForRecvThread = _lockForRecvThread;
        lockForQueueHandlerThread = _lockForQueueHandlerThread;
    }

    public String ConvertPriorityToString(int priority) {
        switch (priority) {
            case 0:
                return "Low";
            case 1:
                return "Medium";
            case 2:
                return "High";
            default:
                return "N/A";
        }
    }

    public void AddTaskToQueue(BlockInstance BI, Key key) {
        BI.pos_in_table = size_rows_in_table;
        ComparatorPriorityTask CPT = new ComparatorPriorityTask(BI);
        PBQ.add(CPT);

        String namefile = key.name_file;
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        model.setValueAt(key.Login, size_rows_in_table, 0);
        model.setValueAt(namefile, size_rows_in_table, 1);
        model.setValueAt(ConvertPriorityToString(BI.priority), size_rows_in_table, 2);
        model.setValueAt("WAITING", size_rows_in_table, 3);
        Table.setModel(model);

        size_rows_in_table++;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lockForRecvThread) {
                for (Map.Entry<Key, BlockInstance> entrySet : HT.entrySet()) {
                    Key key = entrySet.getKey();
                    BlockInstance BI = entrySet.getValue();


                    AddTaskToQueue(BI, key);
                        
                   synchronized (lockForQueueHandlerThread) {
                        lockForQueueHandlerThread.notify();
                   }
                                    
                    HT.remove(key);
                }
                
                try {
                    lockForRecvThread.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TaskAdderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
