package model;

public class TextUpdate {

    private int initialMessage;
    private int hasUpdate;
    private String clientID;
    private String content;

    public TextUpdate() {

    }

    public TextUpdate(int initialMessage, int hasUpdate, String clientID, String content) {
        this.initialMessage = initialMessage;
        this.hasUpdate = hasUpdate;
        this.clientID = clientID;
        this.content = content;
    }

    public int getHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(int hasUpdate) {
        this.hasUpdate = hasUpdate;
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
