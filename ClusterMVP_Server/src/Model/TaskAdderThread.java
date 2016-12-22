package Model;

import Presenter.IPresenter;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class TaskAdderThread extends Thread {
    final String MY_NAME = "TaskAdderThread";
    
    static PriorityBlockingQueue<ComparatorPriorityTask> PBQ;
    static Hashtable<Key, BlockInstance> HT;
    
    int size_rows_in_table = 0;

    JTable Table;
    JTextArea Logs;
    
    Object lockForRecvThread;
    Object lockForQueueHandlerThread;
    
    IPresenter presenter;
    
    TaskAdderThread(PriorityBlockingQueue<ComparatorPriorityTask> _PBQ,
                    Hashtable<Key, BlockInstance> _HT,
                    Object _lockForRecvThread,
                    Object _lockForQueueHandlerThread) {
        
        PBQ = _PBQ;
        HT = _HT;
        lockForRecvThread = _lockForRecvThread;
        lockForQueueHandlerThread = _lockForQueueHandlerThread;
        
        System.out.println("TaskAdderThread created OK!");
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
        BI.setTablePosition(size_rows_in_table);
        BI.setPresenter(presenter);
        ComparatorPriorityTask CPT = new ComparatorPriorityTask(BI);
        PBQ.add(CPT);

        String namefile = key.name_file;
        
        DefaultTableModel model = (DefaultTableModel) presenter.getViewServer().getJTable().getModel();
        
        presenter.getViewServer().update(key.Login, size_rows_in_table, 0, model);
        presenter.getViewServer().update(namefile, size_rows_in_table, 1, model);
        presenter.getViewServer().update(ConvertPriorityToString(BI.getPriority()), size_rows_in_table, 2, model);
        presenter.getViewServer().update("WAITING", size_rows_in_table, 3, model);

        presenter.getViewServer().getJTable().setModel(model);

        size_rows_in_table++;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lockForRecvThread) {
                for (Map.Entry<Key, BlockInstance> entrySet : HT.entrySet()) {
                    Key key = entrySet.getKey();
                    BlockInstance BI = entrySet.getValue();

                    System.out.println("before add task queue OK!");
                    
                    AddTaskToQueue(BI, key);

                    synchronized (lockForQueueHandlerThread) {
                        lockForQueueHandlerThread.notify();
                    }

                    HT.remove(key);
                }

                try {
                    lockForRecvThread.wait();
                    System.out.println("TaskAdderThread notified OK!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(TaskAdderThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
}
