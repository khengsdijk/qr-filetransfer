package services;

import server.TextHandler;

public class TextService extends Service {

        private final String ENDPOINT = "/text";

        public void startTextExchange(){
            startServer();
            server.addHandler(ENDPOINT, new TextHandler());
            printCode(ENDPOINT);
        }

}
