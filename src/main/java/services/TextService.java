package services;

import server.TextHandler;

public class TextService extends Service {

    private final String ENDPOINT = "/text";

    /**
     * start the server and add the endpoint for adding text
     */
    public void startTextExchange() {
        startServer();
        server.addHandler(ENDPOINT, new TextHandler());
        printCode(ENDPOINT);
    }

}
