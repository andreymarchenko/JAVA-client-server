/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Игорь и Андрей
 */
import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.SendThread;
import client.RecvThread;
import client.RegistrationForm;
import java.awt.Desktop;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import client.Log;

public class client extends javax.swing.JFrame {
    /**
     * Creates new form client
     */
    public client() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        SendFile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        javax.swing.JButton Authorization = new javax.swing.JButton();
        Registration = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        SendFile1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 440));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        SendFile.setText("Send file");
        SendFile.setMaximumSize(new java.awt.Dimension(91, 23));
        SendFile.setMinimumSize(new java.awt.Dimension(91, 23));
        SendFile.setPreferredSize(new java.awt.Dimension(91, 23));
        SendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendFileActionPerformed(evt);
            }
        });

        jLabel2.setText("Priority:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High" }));

        Authorization.setLabel("Sign in");
        Authorization.setMaximumSize(new java.awt.Dimension(91, 23));
        Authorization.setMinimumSize(new java.awt.Dimension(91, 23));
        Authorization.setName(""); // NOI18N
        Authorization.setPreferredSize(new java.awt.Dimension(91, 23));
        Authorization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AuthorizationActionPerformed(evt);
            }
        });

        Registration.setText("Registration");
        Registration.setToolTipText("");
        Registration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrationActionPerformed(evt);
            }
        });

        jButton1.setText("Choose file");
        jButton1.setMaximumSize(new java.awt.Dimension(91, 23));
        jButton1.setMinimumSize(new java.awt.Dimension(91, 23));
        jButton1.setPreferredSize(new java.awt.Dimension(91, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseActionPerformed(evt);
            }
        });

        jTextField1.setMaximumSize(new java.awt.Dimension(6, 20));

        jLabel3.setText("Login");

        jLabel4.setText("Password");

        jPasswordField1.setToolTipText("");
        jPasswordField1.setMaximumSize(new java.awt.Dimension(6, 20));

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setMaximumSize(new java.awt.Dimension(104, 64));
        jTextArea3.setMinimumSize(new java.awt.Dimension(104, 64));
        jScrollPane3.setViewportView(jTextArea3);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Result", "Path to File"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setMaximumSize(new java.awt.Dimension(75, 480));
        jTable1.setMinimumSize(new java.awt.Dimension(75, 480));
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        SendFile1.setText("View result");
        SendFile1.setMaximumSize(new java.awt.Dimension(91, 23));
        SendFile1.setMinimumSize(new java.awt.Dimension(91, 23));
        SendFile1.setPreferredSize(new java.awt.Dimension(91, 23));
        SendFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });

        jButton2.setText("Disconnect");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Registration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SendFile1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(52, 52, 52))
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Authorization, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButton2)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Authorization, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(Registration, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addGap(18, 18, 18)
                            .addComponent(SendFile, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(SendFile1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(38, 38, 38))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    final String MY_NAME = "ClientThread";
    
    Socket cs;
    int port = 4445;
    InetAddress ip = null;
    
    String path_to_file = "";
    
    SendThread send_thread = null;
    RecvThread recv_thread = null;
    
    RegistrationForm rform;
    
    String Login = "";
    String Password = "";
    
    boolean IsConnect = false;
    
    /*
      1. Commands from client:
       
       1.1 "R" - Registration
       1.2 "A" - Authorization
       1.3 "S" - Send
       1.4 "D" - Disconnect
       1.5 "WRONG_COMMAND"
     */
    
    private void HandlerRequestOfClient(String req) {
        if(req.equalsIgnoreCase("R")) {
            Registration();
        } else if(req.equalsIgnoreCase("A")) {
            Authorization();
        } else if(req.equalsIgnoreCase("S")) {
            Send();
        } else if(req.equalsIgnoreCase("D")) {
            Disconnect();
        } else {
            WrongCommand();
        }
    }
    
    private void Registration() {
        if (!IsConnect) {
            rform = new RegistrationForm();
            rform.setVisible(true);
        }
        else {
            Log.AddToLog("You have already authorized, if you want to registratation, you will need to click Disconnect!", jTextArea3, MY_NAME);
        }
    }
    
    private void Authorization() {
        if (!IsConnect ) {
               if (!jTextField1.getText().equalsIgnoreCase("") && !jPasswordField1.getText().equalsIgnoreCase("")) {
                try {
                    ip = InetAddress.getLocalHost();
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, "getLocalHost fail", ex);
                }

                try {
                    cs = new Socket(ip, port);
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, "Error in process of creating socket", ex);
                }
                
                Login = jTextField1.getText();
                Password = jPasswordField1.getText();  
                
                try {
                    OutputStream cos = cs.getOutputStream();
                    DataOutputStream dcos = new DataOutputStream(cos);
                    
                    dcos.writeUTF("A");
                    dcos.writeUTF(Login);
                    dcos.writeUTF(Password);
                    
                    InputStream cis = cs.getInputStream();
                    DataInputStream dcis = new DataInputStream(cis);
                    
                    String reply;
                    
                    reply = dcis.readUTF();
                    
                    if (reply.equalsIgnoreCase("AO")) {
                        Log.AddToLog("Authorization succesful!", jTextArea3, MY_NAME);
                        
                        IsConnect = true;
                        
                        recv_thread = new RecvThread(cs,
                                                     jTextArea3,
                                                     jTable1,
                                                     Login);
                        
                        recv_thread.start();
                    } else {
                        Log.AddToLog("Please, enter correct login and password!", jTextArea3, MY_NAME);
                        IsConnect = false;
                    }
                
                }
                catch (IOException ex) {
                        Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else 
                   Log.AddToLog("Please, input Login and Password!", jTextArea3, MY_NAME);
           } else {
                Log.AddToLog("Client have already connected and authorized!", jTextArea3, MY_NAME);
            }
    }
        
    private void Send() {
        if (IsConnect) {
            if (!path_to_file.equalsIgnoreCase("")) {
                String priority = (String) jComboBox1.getSelectedItem();
                
                send_thread = new SendThread(cs, jTextArea3);         
                send_thread.SendJavaByteFile(path_to_file, priority);
            } else {
                Log.AddToLog("Jar file is not choosen!", jTextArea3, MY_NAME);
            }
        } else {
            Log.AddToLog("You are not authorized on server", jTextArea3, MY_NAME);
        }
    }
            
    private void Disconnect() {
        if (IsConnect) {
            try {
                if (send_thread != null) {
                    send_thread.stop();
                }

                if (recv_thread != null) {
                    recv_thread.stop();
                }
                
                OutputStream cos = cs.getOutputStream();
                DataOutputStream dcos = new DataOutputStream(cos);

                dcos.writeUTF("D");
                
                IsConnect = false;
                Log.AddToLog("Client disconnected from server!", jTextArea3, MY_NAME);
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Log.AddToLog("Client is not authorized to server!", jTextArea3, MY_NAME);
        }
    }
                
    private void WrongCommand() {
        Log.AddToLog("Wrong command!", jTextArea3, MY_NAME);
    }

    private void AuthorizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AuthorizationActionPerformed
        String request = "A";
        HandlerRequestOfClient(request);
    }//GEN-LAST:event_AuthorizationActionPerformed

    private void RegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrationActionPerformed
        String request = "R";
        HandlerRequestOfClient(request);
    }//GEN-LAST:event_RegistrationActionPerformed

    private void SendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendFileActionPerformed
        String request = "S";
        HandlerRequestOfClient(request);
    }//GEN-LAST:event_SendFileActionPerformed

    private void ChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseActionPerformed
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            path_to_file = file.getAbsolutePath();
            String to_logs = "You have chosen " + path_to_file + " file";
            Log.AddToLog(to_logs, jTextArea3, MY_NAME);
        }
    }//GEN-LAST:event_ChooseActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (IsConnect) {
            try {
                if (send_thread != null) {
                    send_thread.stop();
                }

                if (recv_thread != null) {
                    recv_thread.stop();
                }
                
                OutputStream cos = cs.getOutputStream();
                DataOutputStream dcos = new DataOutputStream(cos);

                dcos.writeUTF("D");
                
                IsConnect = false;
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewActionPerformed
    File Folder = new File("C:/JavaRep/JAVA-client-server/ClusterClient/AllResults");
    Desktop desktop = null; 
    
    if (Desktop.isDesktopSupported()) { 
        desktop = Desktop.getDesktop(); 
        
        try { 
            desktop.open(Folder);
        } catch (IOException ex) {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    }//GEN-LAST:event_ViewActionPerformed

    private void DisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectActionPerformed
        String request = "D";
        HandlerRequestOfClient(request);
    }//GEN-LAST:event_DisconnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>     
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new client().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Registration;
    private javax.swing.JButton SendFile;
    private javax.swing.JButton SendFile1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
