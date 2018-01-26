package fi.metropolia.juhavuo.chatserver;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Chat history uses singleton design pattern. One can get instance by using static getInstance-method.
 * Chat history also uses observer design pattern.
 *
 * @author Juha Vuokko
 * @version 1.0
 */
public class ChatHistory implements  ObservarbleHistory{

    private static ChatHistory ourInstance = new ChatHistory();
    private ArrayList<ChatMessage> history;
    private ArrayList<ObserverCI> observers;

    private ChatHistory() {
        history = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static ChatHistory getInstance() {
        return ourInstance;
    }

    public synchronized void insert(ChatMessage message){
        history.add(message);
    }

    /**
     * ToString method output: all messages on own lines.
     * @return output String
     */
    @Override
    public String toString(){
        StringBuilder messages = new StringBuilder();

        for (int i = 0; i < history.size();++i){
            messages.append(history.get(i).toString());
            if(i<history.size()-1){
                messages.append("\r\n");
            }
        }
        return messages.toString();
    }

    @Override
    public synchronized void register(ObserverCI oci) {
        observers.add(oci);
    }

    @Override
    public synchronized void deregister(ObserverCI oci) {
        observers.remove(oci);
    }

    @Override
    public void messageToAllObservers(ChatMessage message) {
        for(int i = 0; i < observers.size();++i){
            observers.get(i).update(message);
        }
    }

    public int getAmountOfMessages(){
        return history.size();
    }
}
