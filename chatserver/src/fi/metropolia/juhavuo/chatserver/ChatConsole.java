package fi.metropolia.juhavuo.chatserver;

/**
 * Chat console is printing messages to ide console.
 * @author Juha Vuokko
 * @version 1.0
 */
public class ChatConsole implements ObserverCI{

    public ChatConsole(){
        ChatHistory ch = ChatHistory.getInstance();
        ch.register(this);
    }

    @Override
    public void update(ChatMessage chatMessage) {
        System.out.println(chatMessage);
    }
}
