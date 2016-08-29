/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import server.Sender;
import server.Executor;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;
import static java.lang.Thread.sleep;

/**
 *
 * @author Игорь
 */
public class BlockInstance {

    String path_to_jar_file;
    Socket cs;
    String path_to_result;
    int priority;

    JTextArea Logs;

    JTable Table; // для отображения статуса задачи
    int pos_in_table = 0; // для отображения статуса задачи

    Sender SR = null;
    Executor EX = null;
    

    BlockInstance(Socket _cs,
            String _path_to_jar_file,
            String _path_to_result,
            int _priority,
            JTextArea _Logs) {

        path_to_jar_file = _path_to_jar_file;
        path_to_result = _path_to_result;
        priority = _priority;
        cs = _cs;
        Logs = _Logs;

        SR = new Sender(cs, Logs);
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

    synchronized public void SendResultToClient() {
        if (SR != null) {
            SR.SendResult(path_to_result);
        }
    }

    // lockForQHT mutex using by QueueHandlerThread
    public void Implement(Object lockForQHT, JTable _Table) {
        synchronized (lockForQHT) {
            Table = _Table;
            DefaultTableModel model = (DefaultTableModel) Table.getModel();

            model.setValueAt("RUNNING", pos_in_table, 3);
            ExecuteTask();

            model.setValueAt("SENDING", pos_in_table, 3);
            SendResultToClient();

            model.setValueAt("FINISHED", pos_in_table, 3);
        }
    }

}
