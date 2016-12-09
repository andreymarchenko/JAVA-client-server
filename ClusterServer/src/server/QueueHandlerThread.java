/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import server.BlockInstance;
import server.ComparatorPriorityTask;
import server.Log;

/**
 *
 * @author Игорь
 */
public class QueueHandlerThread extends Thread {

    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ = new PriorityBlockingQueue<ComparatorPriorityTask>();
    static Hashtable<Key, BlockInstance> HT;

    JTextArea Logs;

    Object lockForRecvThread;
    Object lockForBI = new Object();

    JTable Table;

    TaskAdderThread TAT;
    Object lockForTaskAdderThread = new Object();

    
    QueueHandlerThread(JTextArea _Logs,
            JTable _Table,
            Hashtable<Key, BlockInstance> _HT,
            Object _lockForRecvThread) {

        lockForRecvThread = _lockForRecvThread;
        Table = _Table;
        Logs = _Logs;
        HT = _HT;
        
        TAT = new TaskAdderThread(PBQ,
                                  HT,
                                  Table,
                                  lockForRecvThread,
                                  lockForTaskAdderThread,
                                  Logs);
        
        TAT.start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lockForTaskAdderThread) {
                try {
                    lockForTaskAdderThread.wait(); // QueueHandlerThread was norified by TaskAdderThread
                } catch (InterruptedException ex) {
                    Logger.getLogger(QueueHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (!PBQ.isEmpty()) {
                    synchronized (lockForBI) {
                        new Thread() {
                            @Override
                            public void run() {
                                if (!(PBQ.peek() == null)) {
                                {
                                    PBQ.poll().BI.Implement(lockForBI, Table);
                                }
                                }
                            }
                        }.start();
                    }
                }
            }
        }
    }
}
