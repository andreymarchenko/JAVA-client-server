package Model;

import View.ViewClient;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

public class ModelClient {

    private File file;
    private ViewClient viewClient;
    private String userDirectoryPath;
    private String filePath;
    private DefaultTableModel tableModel;
    private int tableSize;

    public ModelClient() {
        this.userDirectoryPath = "C:/JavaRep/JAVA-client-server/ClusterClient/AllResults/";
        this.tableSize = 0;
    }

    public void createDirectory(String userName) {
        //Log.AddToLog("Registration is OK!", jTextArea1, MY_NAME);
        String clientDirectory = userDirectoryPath + userName;

        File specialClientDirectory = new File(clientDirectory);
        specialClientDirectory.mkdir();

        String clientDirectoryPath = clientDirectory + "/";

        String resultsClientDirectory = clientDirectoryPath + "Results";

        File clientResultsDirectory = new File(resultsClientDirectory);
        clientResultsDirectory.mkdir();

        String clientBinaryDirectory = clientDirectoryPath + "JavaByteFiles";

        File clientBinariesDirectory = new File(clientBinaryDirectory);
        clientBinariesDirectory.mkdir();
    }

    public void setViewClient(ViewClient viewClient) {
        this.viewClient = viewClient;
    }

    public void updateTableModel(String filePath, String fileName) {
        tableModel.setValueAt(fileName, tableSize, 0);
        tableModel.setValueAt(filePath, tableSize, 1);
        viewClient.getTable().setModel(tableModel);
        tableSize++;
    }

    public String getUserDirectoryPath() {
        return userDirectoryPath;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
