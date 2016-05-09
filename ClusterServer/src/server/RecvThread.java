/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.InputStream;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author Олег
 */
public class RecvThread extends Thread {
    
        final int CHUNK_BYTE_SIZE = 1024;
        JTextArea Logs = null;
        Socket cs;
        InputStream cis;
        
        @Override 
        public void run() {
            
        
        }
}
