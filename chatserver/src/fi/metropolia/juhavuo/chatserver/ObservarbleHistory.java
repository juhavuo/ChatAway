package fi.metropolia.juhavuo.chatserver;

/**
 * For observer pattern
 * History is observable
 * @author Juha Vuokko
 * @version 1.0
 */
public interface ObservarbleHistory {

    public void register(ObserverCI oci);

    public void deregister(ObserverCI oci);

    public void messageToAllObservers(ChatMessage message);
}
