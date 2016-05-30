package com.example.andrey.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Andrey on 22.05.2016.
 */

public class ServerConnection {

    private Socket socket;
    private boolean IsConnected;
    MainActivity mainactivity;

    public ServerConnection(MainActivity mainactivity) {
        this.mainactivity = mainactivity;
    }

    public Socket GetSocket() {
        return this.socket;
    }

    public boolean GetIsConnected() {
        return this.IsConnected;
    }

    public void connect(final InetAddress ip, final int port) {
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(ip, port);
                    IsConnected = true;
                    mainactivity.onConnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        }
    }
