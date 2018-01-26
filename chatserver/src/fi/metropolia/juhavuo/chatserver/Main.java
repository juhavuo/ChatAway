package fi.metropolia.juhavuo.chatserver;

public class Main {

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.serve();
    }
}
