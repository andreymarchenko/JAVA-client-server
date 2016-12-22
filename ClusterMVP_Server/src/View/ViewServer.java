package View;

import Presenter.BPresenter;
import Presenter.IPresenter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewServer implements IViewServer {
    private IPresenter presenter;
    private JTable table;
    
    public ViewServer() {
        presenter = BPresenter.createPresenter(this);
    }    

    @Override
    public void StartServer() {
        presenter.StartServer();
    }

    @Override
    public void StopServer() {
        presenter.StopServer();
    }

    @Override
    public JTable getJTable() {
        return this.table;
    }

    @Override
    public void setJTable(JTable table) {
        this.table = table;
    }

    @Override
    public void update(String status, int posRow, int posCol, DefaultTableModel model) {
        model.setValueAt(status, posRow, posCol);
    }
}
