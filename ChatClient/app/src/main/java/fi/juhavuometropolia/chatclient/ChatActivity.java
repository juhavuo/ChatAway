package fi.juhavuometropolia.chatclient;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/*
    Chat activity is where the chatting happens.
    Server connection is created by ServerConnector class.
    From serverconnector one get inputstream and with scanner
    one reads messages coming from the server side.
 */
public class ChatActivity extends AppCompatActivity{

    private EditText output;
    private EditText input;
    private Button sendButton;
    private ServerConnector sConnector;
    private Scanner scanner;
    private boolean timeToQuit;
    private String ip_add;

    /*
        ChatActivity get ip address from main activity in Extras
        ServerConnector is created. If there is exception in creating of socket,
        error message will be printed to screen. If connection can't be formed, it will
        take a lot of time, before that message get printed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ip_add = bundle.getString("CHAT_IP");

        timeToQuit = false;
        output = (EditText) findViewById(R.id.textOutput);
        input = (EditText) findViewById(R.id.inputView);
        sendButton = (Button)findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processInput();
            }
        });


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sConnector = new ServerConnector(52000, ip_add); //port of server is set fixed value
                    if(!sConnector.isConnected()){
                        timeToQuit = true;
                        printException("No connection.");
                    }
                    InputStream inputStream = sConnector.getInputStream();
                    scanner = new Scanner(inputStream);
                    while (!timeToQuit) {
                        final String text = scanner.nextLine();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                output.append(text + "\n");
                            }
                        });
                    }

                }catch (IOException ioe){
                    printException(ioe.getMessage());
                }
            }
        });
        t.start();
    }

    /*
        Sending what is written to the server. First text goes to
        ServerConnector and from that to Message
     */
    public void processInput(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                sConnector.sendToServer(input.getText().toString());
                input.setText("");
                if(input.getText().toString().equals(":quit")){
                    sConnector.itIsTimeToQuit();
                    timeToQuit = true;
                }
            }
        });
    }

    /*
        To printing out, if there is connection to server could not formed.
     */
    public void printException(String message){
        final String mssg = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                output.append(mssg);
            }
        });
    }
}
