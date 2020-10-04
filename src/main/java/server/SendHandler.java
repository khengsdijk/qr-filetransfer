package server;

import Util.FileUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

/**
 * handle the Http requests for sending a file to a users phone
 */
public class SendHandler implements HttpHandler {

    private String filePath;
    private boolean deleteAfterSending;

    public SendHandler(String filePath, boolean deleteAfterSending) {
        this.filePath = filePath;
        this.deleteAfterSending = deleteAfterSending;
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
        // write the file contents to the html response
        outputStream.write(fileResponse);
        outputStream.flush();
        outputStream.close();

        // in case of a temporary zip file delete it.
        if(deleteAfterSending)
            FileUtil.deleteFile(filePath);

        System.exit(0);
    }





}
