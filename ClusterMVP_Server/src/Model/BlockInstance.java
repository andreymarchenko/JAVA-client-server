package Model;

import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import Model.Executor;
import Presenter.IPresenter;
import static java.lang.Thread.sleep;

public class BlockInstance {

    private String path_to_jar_file;
    private Socket cs;
    private String path_to_result;
    private int priority;
    
    private int tablePosition = 0;

    Executor EX = null;
    
    IPresenter presenter;

    BlockInstance(Socket _cs,
            String _path_to_jar_file,
            String _path_to_result,
            int _priority) {

        path_to_jar_file = _path_to_jar_file;
        path_to_result = _path_to_result;
        priority = _priority;
        cs = _cs;
        
        EX = new Executor(path_to_jar_file);
    }

    public void setPathToResultFile(String _path_to_result) {
        path_to_result = _path_to_result;
    }

    synchronized public void ExecuteTask() {
        if (EX != null) {
            EX.execute(path_to_result);
        }
    }

    // lockForQHT mutex using by QueueHandlerThread
    public void Implement(Object lockForQHT) {
        synchronized (lockForQHT) {
            DefaultTableModel model = (DefaultTableModel) presenter.getViewServer().getJTable().getModel();

            presenter.getViewServer().update("RUNNING", tablePosition, 3, model);
            ExecuteTask();

            presenter.getViewServer().update("SENDING", tablePosition, 3, model);
            presenter.sendResult(cs, path_to_result);

            presenter.getViewServer().update("FINISHED", tablePosition, 3, model);
        }
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }
}
