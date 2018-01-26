package fi.metropolia.juhavuo.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ChatServer waits for requests from clients and creates a new thread for each client.
 * ChatServer also creates chat console, that prints messages to ide console.
 * @author Juha Vuokko
 * @version 1.0
 */
public class ChatServer {

    /**
     * Sets the server running
     */
    public void serve(){
        ServerSocket serverSocket;
        ChatConsole cc = new ChatConsole();
        try {
            serverSocket = new ServerSocket(52000,2);
            System.out.println(serverSocket.getLocalPort());

            while(true) {

                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                PrintStream output = new PrintStream(socket.getOutputStream());
                CommandInterpreter ci = new CommandInterpreter(inputStream,output);
                Thread t = new Thread(ci);
                t.start();

            }
        }catch (IOException ioe){
            System.out.println(ioe);
        }

    }
}
