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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        javax.swing.JButton Authorization = new javax.swing.JButton();
        DisconnectFromServer = new javax.swing.JButton();
        Registration = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();

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
        setMaximumSize(new java.awt.Dimension(600, 440));
        setMinimumSize(new java.awt.Dimension(600, 440));
        setPreferredSize(new java.awt.Dimension(750, 440));
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

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setMaximumSize(new java.awt.Dimension(104, 64));
        jTextArea1.setMinimumSize(new java.awt.Dimension(104, 64));
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Priority:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low", "Medium", "High" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

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

        DisconnectFromServer.setText("Disconnect");
        DisconnectFromServer.setMaximumSize(new java.awt.Dimension(91, 23));
        DisconnectFromServer.setMinimumSize(new java.awt.Dimension(91, 23));
        DisconnectFromServer.setPreferredSize(new java.awt.Dimension(91, 23));
        DisconnectFromServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectFromServerActionPerformed(evt);
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
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setMaximumSize(new java.awt.Dimension(6, 20));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Login");

        jLabel4.setText("Password");

        jPasswordField1.setToolTipText("");
        jPasswordField1.setMaximumSize(new java.awt.Dimension(6, 20));

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Registration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(52, 52, 52))
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Authorization, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DisconnectFromServer, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel4)))
                .addContainerGap(53, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Authorization, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Registration, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addComponent(SendFile, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DisconnectFromServer, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            
    
    
    public void AddToLog(String info) {
        String curr_info = jTextArea3.getText();
        curr_info += info + "\n";
        jTextArea3.setText(curr_info);
    }

    private void AuthorizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AuthorizationActionPerformed
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
                        AddToLog("ClientMain: Authorization succesful!");
                        IsConnect = true;
                    } else {
                        AddToLog("ClientMain: Please, enter correct login and password!");
                        IsConnect = false;
                    }
                
                    } catch (IOException ex) {
                        Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else AddToLog("ClientMain: Please, input Login and Password!");
           } else {
                AddToLog("ClientMain: Client heve already connected and authorized!");
            }
    }//GEN-LAST:event_AuthorizationActionPerformed

    private void DisconnectFromServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectFromServerActionPerformed
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

                InputStream cis = cs.getInputStream();
                DataInputStream dcis = new DataInputStream(cis);
                String reply = dcis.readUTF();

                if (reply.equalsIgnoreCase("DO")) {
                    AddToLog("ClientMain: Disconnect is OK!");
                } else if (reply.equalsIgnoreCase("DN")){
                    AddToLog("ClientMain: Disconnect is not OK!");
                }
                
                IsConnect = false;

            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            AddToLog("ClientMain: Connection with server is not exist, please, connect to server!");
            IsConnect = false;
        }
    }//GEN-LAST:event_DisconnectFromServerActionPerformed

    private void RegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrationActionPerformed
        if (!IsConnect) {
            rform = new RegistrationForm();
            rform.setVisible(true);
        }
        else {
            AddToLog("ClientMain: You have already authorized, if you want to registration, you will need to click Disconnect!");
        }
    }//GEN-LAST:event_RegistrationActionPerformed

    private void SendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendFileActionPerformed
        // TODO add your handling code here:
        if (IsConnect) {
            if (!path_to_file.equalsIgnoreCase("")) {
                send_thread = new SendThread(cs, jTextArea3);
                String priority = (String) jComboBox1.getSelectedItem();
                send_thread.SendJavaByteFile(path_to_file, priority);
            } else {
                AddToLog("ClientMain: Jar file is not choosen!");
            }
        } else {
            AddToLog("ClientMain: You are not authorized on server");
        }
    }//GEN-LAST:event_SendFileActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            path_to_file = file.getAbsolutePath();
            String to_logs = "ClientMain: You have choosen " + path_to_file + " file";
            AddToLog(to_logs);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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

                InputStream cis = cs.getInputStream();
                DataInputStream dcis = new DataInputStream(cis);
                String reply = dcis.readUTF();

                if (reply.equalsIgnoreCase("DO")) {
                    AddToLog("ClientMain: Disconnect is OK!");
                    IsConnect = false;
                } else {
                    AddToLog("ClientMain: Disconnect is not OK!");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
    private javax.swing.JButton DisconnectFromServer;
    private javax.swing.JButton Registration;
    private javax.swing.JButton SendFile;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

}
