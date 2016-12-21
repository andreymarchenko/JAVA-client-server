package View;

import Controller.BController;
import Controller.Controller;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class ViewClient {

    private Controller controller;
    private JTable table;
    private JComboBox jComboBox;

    public ViewClient() {
        controller = BController.createController();
    }

    public void HandlerRequestOfClient(String request) {

        if (request.equalsIgnoreCase("R")) {
            controller.Registration();
        } else if (request.equalsIgnoreCase("A")) {
            controller.Authorization();
        } else if (request.equalsIgnoreCase("S")) {
            controller.Send();
        } else if (request.equalsIgnoreCase("D")) {
            controller.Disconnect();
        } else {
            controller.WrongCommand();
        }
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTable getTable() {
        return this.table;
    }

    public JComboBox getjComboBox() {
        return jComboBox;
    }

    public void setjComboBox(JComboBox jComboBox) {
        this.jComboBox = jComboBox;
    }       

    public Controller getController() {
        return controller;
    }

}
