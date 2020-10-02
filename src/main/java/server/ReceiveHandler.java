package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ReceiveHandler implements HttpHandler {

    private String outputDirectory;

    public ReceiveHandler(String outputDirectory){
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if(httpExchange.getRequestMethod().equals("POST")){
            saveFile(httpExchange);
            return;
        }

        loadPage(httpExchange);
    }

    /**
     *
     * Save the file by wrapping the httpExchange in Apache commons file upload
     * and save it to the users pc
     *
     */
    public void saveFile(final HttpExchange httpExchange) throws IOException {

        DiskFileItemFactory d = new DiskFileItemFactory();

        try {
            ServletFileUpload up = new ServletFileUpload(d);
            // implement the RequestContext with HttpExchange
            List<FileItem> result = up.parseRequest(new RequestContext() {

                @Override
                public String getCharacterEncoding() {
                    //Set a standard Character encoding which wont be used when dealing with binary data
                    return "UTF-8";
                }

                @Override
                public int getContentLength() {
                    // content will be 0 since nothing is send back in the response
                    return 0;
                }

                @Override
                public String getContentType() {
                    //set content type from the request header
                    return httpExchange.getRequestHeaders().getFirst("Content-type");
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    // set inputstream as data for the request
                    return httpExchange.getRequestBody();
                }

            });

            OutputStream outputStream = httpExchange.getResponseBody();
            // only write files to the users pc and skip HTML form elements that are also parsed by FileUpload
            for(FileItem fi : result) {
                if (fi.isFormField())
                    continue;

                // only append directory seperator if the output directory is not empty
                if (outputDirectory.isEmpty())
                    outputDirectory += "/";

                fi.write(new File(fi.getName()));
                System.out.println("Created file: " + outputDirectory + fi.getName());
            }
            InputStream is = getClass().getClassLoader().getResourceAsStream("pages/result.html");
            byte[] htmlResponse = is.readAllBytes();

            httpExchange.sendResponseHeaders(200, 0);
            outputStream.write(htmlResponse);
            outputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads the HTML page for the user
     */
    public void loadPage(HttpExchange httpExchange)  throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream is = getClass().getClassLoader().getResourceAsStream("pages/receive.html");
        byte[] htmlResponse = is.readAllBytes();

        // set the headers and content of the html response
        httpExchange.sendResponseHeaders(200, htmlResponse.length);
        outputStream.write(htmlResponse);
        outputStream.flush();
        outputStream.close();
    }



}
