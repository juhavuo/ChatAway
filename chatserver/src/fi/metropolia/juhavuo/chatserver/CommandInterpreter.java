package fi.metropolia.juhavuo.chatserver;


import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Command Interpreter ia interpreting commands get from input stream and gives responces to output stream.
 *
 * @author Juha Vuokko
 * @version 1.0
 */
public class CommandInterpreter implements ObserverCI, Runnable{

    private InputStream input;
    private PrintStream output;
    private DateFormat dateFormat;
    private Date date;
    private String username;
    private Scanner scanner;
    private ChatHistory history;
    private UserNameList nameList;
    private boolean timeToQuit;

    /**
     * Command interpreter constructor.
     * @param inputP Input stream is for reading input
     * @param outputP Output stream is for sending responces to users
     */
    public CommandInterpreter(InputStream inputP, PrintStream outputP){
        input = inputP;
        scanner = new Scanner(input);
        output = outputP;
        username = null;
        dateFormat = new SimpleDateFormat("H,mm,d,MM");
        history = ChatHistory.getInstance();
        history.register(this);
        nameList = UserNameList.getInstance();

        timeToQuit = false;
    }

    /**
     * This method is not supposed to call directly. One needs to use threads start method to start this.
     */
    public void run(){
        output.println("Hello!");

        while(!timeToQuit) {
            String text = scanner.nextLine();
            if (text.length() > 0) {
                if (text.charAt(0) == ':') {
                    if (text.length() > 1) {
                        text = text.substring(1);
                        String[] parts = text.split(" ");
                        if (parts[0].equalsIgnoreCase("user")) { //setting the user
                            if (parts.length > 1) {
                                String testName = parts[1];
                                if (!nameList.contains(testName)) {
                                    nameList.insert(testName);
                                    if (nameList.contains(username)) {
                                        nameList.remove(username);
                                    }
                                    this.username = testName;
                                    output.println("Username is " + this.username);
                                } else {
                                    if (!testName.equals(this.username)) {
                                        output.println("Username " + testName + " is already in use.");
                                    }
                                }
                            }
                        } else if (parts[0].equalsIgnoreCase("quit")) { //quitting
                            output.println("Goodbye!");
                            nameList.remove(username);
                            history.deregister(this);
                            timeToQuit = true;

                        } else if (parts[0].equalsIgnoreCase("messages")) { //getting message history
                            if(history.getAmountOfMessages()>0) {
                                output.println("History\n" + history.toString());
                            }else{
                                output.println("No messages in history");
                            }

                        } else if (parts[0].equalsIgnoreCase("users")) { //getting the user list

                            if(nameList.getSize()>0) {
                                output.println("Users\n" + nameList);
                            }else{
                                output.println("No registered users.");
                            }

                        } else {
                            output.println("Did not get it.");
                        }

                    }
                } else {
                    if (username != null) { //sending message
                        date = new Date();
                        ChatMessage message = new ChatMessage(text, username, dateFormat.format(date));
                        history.insert(message);
                        history.messageToAllObservers(message);

                    } else {
                        output.println("Username not set.");
                    }
                }
            }
        }
    }

    /**
     * To print the message
     * @param chatMessage message to be printed
     */
    @Override
    public void update(ChatMessage chatMessage) {
        output.println(chatMessage);
    }


}
