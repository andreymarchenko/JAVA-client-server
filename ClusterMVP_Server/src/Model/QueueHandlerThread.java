package Model;

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
import Model.BlockInstance;
import Model.ComparatorPriorityTask;
import Presenter.IPresenter;
//import Model.Log;

public class QueueHandlerThread extends Thread {
    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ = new PriorityBlockingQueue<ComparatorPriorityTask>();
    static Hashtable<Key, BlockInstance> HT;

    //JTextArea Logs;

    Object lockForRecvThread;
    Object lockForBI = new Object();

    TaskAdderThread TAT;
    Object lockForTaskAdderThread = new Object();
    
    IPresenter presenter;

    
    QueueHandlerThread(Object _lockForRecvThread, Hashtable<Key, BlockInstance> _HT, IPresenter presenter) {

        lockForRecvThread = _lockForRecvThread;
        
        HT = _HT;
        TAT = new TaskAdderThread(PBQ,
                                  HT,
                                  lockForRecvThread,
                                  lockForTaskAdderThread);
        TAT.setPresenter(presenter);
        
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
                                    PBQ.poll().BI.Implement(lockForBI);
                                }
                                }
                            }
                        }.start();
                    }
                }
            }
        }
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
}
