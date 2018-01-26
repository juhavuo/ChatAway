package fi.juhavuometropolia.chatclient;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedTransferQueue;

/**
 * ServerConnector creates socket, creates instance of MessageSender and gives the stream
 * so that messages can be sent. Inputstream is given to ChatActivity so that coming messages
 * and server responces can be represented in EditText.
 */
public class ServerConnector{

    private Socket socket;
    private MessageSender messageSender;
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintStream printStream;
    private String message;
    /*
        In constructor socket is created and also streams.
        If socket creation doesn't succeed, exception will be thrown
     */
    public ServerConnector(int port, String ip_add) throws IOException{
        boolean isOkToStart = true;

        Thread sendThread = null;

            socket = new Socket(InetAddress.getByName(ip_add), port);
            if(!isConnected()){
                isOkToStart = false;
            }
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            printStream = new PrintStream(outputStream,true);
            messageSender = new MessageSender(printStream);
            sendThread = new Thread(messageSender);


        if(isOkToStart) {
            sendThread.start();

        }

    }

    public void sendToServer(String messageToSend){

        messageSender.sendToServer(messageToSend);
    }

    public void itIsTimeToQuit(){
        messageSender.itsTimeToQuit();
    }

    public InputStream getInputStream(){
        return inputStream;
    }

    public boolean isConnected(){
        return socket.isBound();
    }
}
