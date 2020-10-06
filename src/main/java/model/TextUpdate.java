package model;

public class TextUpdate {

    private int initialMessage;
    private String clientID;
    private String content;

    public TextUpdate() {

    }

    public TextUpdate(int initialMessage, String clientID, String content) {
        this.initialMessage = initialMessage;
        this.clientID = clientID;
        this.content = content;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public int getInitialMessage() {
        return initialMessage;
    }

    public void setInitialMessage(int initialMessage) {
        this.initialMessage = initialMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
