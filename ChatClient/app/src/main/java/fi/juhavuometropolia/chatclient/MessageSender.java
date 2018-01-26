package fi.juhavuometropolia.chatclient;

import java.io.PrintStream;


/**
 * MessageSender has printstream from where messages get sent to server.
 */

public class MessageSender implements Runnable{

    private boolean isTimeToQuit;
    private PrintStream printStream;
    private boolean timeToSend, connectionEstablished;
    private String messageToSend;

    public MessageSender(PrintStream outputStream){

        isTimeToQuit = false;
        this.printStream = new PrintStream(outputStream);
        timeToSend = false;
        connectionEstablished = false;
        messageToSend = "";
    }

    //when run method started to execute, connectionEstablished is set to true
    //and messages can be sent
    @Override
    public void run() {

        while (!isTimeToQuit) {
            connectionEstablished = true;

            if(timeToSend){
                if(messageToSend.length()>0){
                    printStream.print(messageToSend);
                }
                timeToSend = false;
            }
        }
    }


    //this is used to send messages to server
    //nothing happends, if connection is not yet established
    public void sendToServer(String message){
        if(connectionEstablished) {
            messageToSend = message +"\n";

            timeToSend = true;
        }
    }

    public void itsTimeToQuit(){
        isTimeToQuit = true;
    }

}
