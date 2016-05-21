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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import server.BlockInstance;
import server.ComparatorPriorityTask;

/**
 *
 * @author Игорь
 */


public class QueueHandlerThread extends Thread {
    
    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ = new PriorityBlockingQueue<>();
    static Hashtable<Key, BlockInstance> HT;
    
    JTextArea Logs;
    
    int size_rows_in_table = 0;
    int old_row = 0;
    Object lock;
    
    JTable Table;
    String[] columnNames = {"Login_client",
        "Name_task",
        "Priority",
        "Status"};

    QueueHandlerThread(JTextArea _Logs, JTable _Table, Hashtable<Key, BlockInstance> _HT, Object _lock) {
        lock = _lock;
        Table = _Table;
        Logs = _Logs;
        HT = _HT;
    }

    public void AddToLog(String info) {
        String curr_info = Logs.getText();
        curr_info += info + "\n";
        Logs.setText(curr_info);
    }
    
    public String ConvertPriorityToString(int priority) {
        switch(priority) {
            case 0: return "Low";
            case 1: return "Medium";
            case 2: return "High";
            default: return "N/A";
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
            synchronized (lock) {
                for (Map.Entry<Key, BlockInstance> entrySet : HT.entrySet()) {
                    Key key = entrySet.getKey();
                    BlockInstance BI = entrySet.getValue();
                    if (BI.LookedByQueue == false) {
                        BI.LookedByQueue = true;
                        AddTaskToQueue(BI, key);
                    }
                    if (!HT.isEmpty() && (((DefaultTableModel) Table.getModel()).getValueAt(old_row, 3).equals("FINISHED") || size_rows_in_table == 1)) {  // МЕГА КОСТЫЛЬ
                        old_row = PBQ.peek().BI.pos_in_table;
                        PBQ.poll().BI.Implement(Table);
                    }
                     
                }
            }

        }

    }

}
