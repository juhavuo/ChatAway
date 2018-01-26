package fi.metropolia.juhavuo.chatserver;

/**
 * This for for messages. Format is: message sender@hh:mm Month xth
 * @author Juha Vuokko
 * @version 1.0
 */
public class ChatMessage {

    private String date;
    private String text;
    private String username;

    /**
     *
     * @param text Content of message
     * @param username Who is sendinng
     * @param date Sending time in format h,mm,d,MM (hours,minutes,day,month)
     */
    public ChatMessage(String text, String username, String date){
        this.text = text;
        this.username = username;
        this.date = date;

    }

    /**
     * Gives the message needed format
     * @return message in string
     */
    @Override
    public String toString(){
        String[] months = {"January","February","March","April","May","June","July","August","september","October","November","December"};
        String[] timeparts = date.split(",");

        String timestamp = timeparts[0] + ":" + timeparts[1] + " "  + months[Integer.parseInt(timeparts[3])-1] + " " + timeparts[2] + addEnding(Integer.parseInt(timeparts[2])) ;

        return text + " " + username +"@" + timestamp;
    }

    /**
     *
     * @param day The day, when message was sended
     * @return st, nd or th depending, what day it is
     */
    private String addEnding(int day){
        if(day%10==1){
            return "st";
        }else if(day%10==2){
            return "nd";
        }else{
            return "th";
        }
    }

}
