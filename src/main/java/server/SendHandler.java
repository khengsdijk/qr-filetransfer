package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class SendHandler implements HttpHandler {

    private String filePath;

    public SendHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        File file = new File(filePath);

        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream is = new FileInputStream(file);

        byte[] fileResponse = is.readAllBytes();

        // set the headers and content of the html response
        httpExchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=" + file.getName());
        httpExchange.sendResponseHeaders(200, fileResponse.length);
        outputStream.write(fileResponse);
        outputStream.flush();
        outputStream.close();
        System.exit(0);
    }





}
