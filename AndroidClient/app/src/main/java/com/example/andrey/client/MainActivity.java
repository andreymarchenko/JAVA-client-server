package com.example.andrey.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    int port = 4445;
    String address = "85.143.1.208";
    InetAddress inetaddress;
    Socket socket;
    boolean IsConnected;


    ServerConnection serverConnection = new ServerConnection(this);

    public void SetConnection() {
        try {
            inetaddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverConnection.connect(inetaddress, port);
    }

    public void onConnect(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                socket = serverConnection.GetSocket();
                IsConnected = serverConnection.GetIsConnected();
            }
        });
    }

    public void getdata(View view){
        if (IsConnected) {
            new Thread() {
                public void run() {
                    try {
                        OutputStream outputstream = socket.getOutputStream();
                        InputStream inputstream = socket.getInputStream();
                        DataOutputStream dataoutputstream = new DataOutputStream(outputstream);
                        DataInputStream datainputstream = new DataInputStream(inputstream);

                        dataoutputstream.writeUTF("B");
                        String reply = datainputstream.readUTF();

                        final String[] mas = reply.split("\n");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                        android.R.layout.simple_list_item_1, mas);
                                ListView lv = (ListView) findViewById(R.id.listView);
                                lv.setAdapter(adapter);
                            }
                        });

                        Log.i("Работает!", reply);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        else System.out.println("Connection isn't established");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetConnection();
    }
}
