package View;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public interface IViewServer {
    public void StartServer();
    public void StopServer();
    
    public JTable getJTable();
    public void setJTable(JTable table);
    
    public void update(String status, int posRow, int posCol, DefaultTableModel model);
}
