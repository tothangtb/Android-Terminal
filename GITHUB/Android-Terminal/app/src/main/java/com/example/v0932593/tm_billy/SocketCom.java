package com.example.v0932593.tm_billy;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class SocketCom extends IOException {
    private ScanActivity activity;
    private String message = "";
    private static final int socketServerPORT = 55962;
    private String data = "";
    public static  int temp = 0;

    private ServerSocket serverSocket;

    public SocketCom(){
        try {
            serverSocket = new ServerSocket(socketServerPORT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendRequest(ScanActivity activity, String input){
        this.activity = activity;
        this.data = input;
        SocketSend();
    }


    public void SocketSend(){
          Thread socketServerThread = new Thread(new SocketServerThread());
          socketServerThread.start();
    }


    public int getPort() {
        return socketServerPORT;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        int count = 0; 
        String tmp = message,scan;
        @Override
        public void run() {
            try {
                    // 1. instance ServerSocket

              //  while (true) {
                    // 2. Ket noi den Server
                    final Socket socket = serverSocket.accept();

                    if(socket.isConnected()) {
//                        message = "Connected : "
//                                + socket.getInetAddress() +" : "+ socket.getPort();
                        message = "SMO IP : "+socket.getInetAddress();
                    //    activity.contentRespond.setBackgroundColor(activity.getApplicationContext().getResources().getColor(R.color.yello));
                    }
                    // gui du lieu ra UI

                    activity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            activity.tvStatus.setText("OK");
                            activity.IPServer.setText(message);
                            temp = 1;
                            getIpAddress();
                        }
                    });

                // gui du lieu len sv
                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                        socket, count);
                socketServerReplyThread.run();



              //  }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.d("EXECEPTION",e.getMessage());
                e.printStackTrace();
            }
        }

    }

    public class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        int cnt;

        SocketServerReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }


        @Override
        public void run() {
            OutputStream outputStream;
            InputStream response;
            byte[] responseByte = null;
            try {
                outputStream = hostThreadSocket.getOutputStream();

                // Gui
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(data);
                //printStream.close();

                // Nhan
                response = hostThreadSocket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(response);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String tmp = br.readLine();

                //Log.d("RESPONSE", tmp);

                message = tmp + "\n";

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        activity.contentRespond.append(message);
                       // activity.writeToSDFile();
                      //  activity.ReadFile();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            }

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    activity.contentRespond.setText(message);
                }
            });
        }

    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
//                        ip += "Server running at : "
//                                + inetAddress.getHostAddress();
                        ip = inetAddress.getHostAddress();
                        activity.IPClinet.setText("Terminal IP : "+ip);
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Errrrrrr",e.toString());
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}