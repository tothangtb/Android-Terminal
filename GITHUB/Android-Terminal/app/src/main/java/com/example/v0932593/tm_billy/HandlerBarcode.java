package com.example.v0932593.tm_billy;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HandlerBarcode {

    private static String strSMOResult = "BB";
    private  String strBarcodeResult;

    ScanActivity scan_activity;
    ServerSocket serverSocket;
    String message = "";
    static final int serverPort = 55962;

    public String getStrSMOResult() {
        return strSMOResult;
    }

    public void setStrSMOResult(String strSMOResult) {
        HandlerBarcode.strSMOResult = strSMOResult;
    }

    public HandlerBarcode(String scan_activity){
        //this.scan_activity = (ScanActivity) scan_activity;
        strBarcodeResult = scan_activity;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }


    public int getPort(){
        return serverPort;
    }

    private class SocketServerThread extends Thread {

        int count =1;
        public void run(){
            try{
                serverSocket = new ServerSocket(serverPort);
                while (count !=11){
                    Socket socket = serverSocket.accept();
                    count++;
                    message = "Data send : -->"+strBarcodeResult+"\n";
                    setStrSMOResult(message);

                SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket,count);
                socketServerReplyThread.run();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private class SocketServerReplyThread extends Thread {
        private  Socket hostThreadSocket;
        int ctn;

        SocketServerReplyThread(Socket socket,int c){
            hostThreadSocket = socket;
            ctn = c;
        }
        public void run() {
            OutputStream outputStream;
            String contentSend = scan_activity.contentSend.toString();

            try{
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(contentSend);
                printStream.close();

                message +="Replay : "+contentSend+"Pass\n";

                scan_activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scan_activity.contentRespond.setText(message);
                    }
                });
            }catch(IOException e){
                e.printStackTrace();
                message += "Something wrong !"+e.toString()+"\n";
            }

            scan_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    scan_activity.contentRespond.setText(message);
                }
            });
        }
    }

    /*public String getIpAddress() {
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
                        ip += "Server running at : "
                                + inetAddress.getHostAddress();
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
*/

}
