package server;

import Util.FileUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * handle the Http requests for exchanging text
 */
public class TextHandler implements HttpHandler {

    private final String EXIT_PAGE = "pages/exit.html";
    private final String SAVE_PAGE = "pages/save.html";
    private final String TEXT_PAGE = "pages/text.html";

    private final String SAVE_PARAMETER = "save";
    private final String UPDATE_PARAMETER = "update";
    private final String EXIT_PARAMETER = "update";

    private String textAreaContent;

    public TextHandler(){
        textAreaContent = "";
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if(httpExchange.getRequestMethod().equals("PUT")){
            handleTextChanges(httpExchange);
            return;
        }

        if(httpExchange.getRequestMethod().equals("POST")){
            saveFile(httpExchange);
            return;
        }

        if(SAVE_PARAMETER.equals(httpExchange.getRequestURI().getQuery())) {
            handlePageLoading(httpExchange, SAVE_PAGE);
            System.exit(0);
        }

        if(UPDATE_PARAMETER.equals(httpExchange.getRequestURI().getQuery()))
            updateTextArea(httpExchange);


        if(EXIT_PARAMETER.equals(httpExchange.getRequestURI().getQuery())) {
            handlePageLoading(httpExchange, EXIT_PAGE);
            System.exit(0);
        }
        handlePageLoading(httpExchange, TEXT_PAGE);
    }

    /**
     * send the latest version of the text to the client
     */
    private void updateTextArea(HttpExchange httpExchange) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        String jsonResult = new JSONObject().put("content", textAreaContent).toString();

        // set the headers and content of the html response
        httpExchange.sendResponseHeaders(200, jsonResult.getBytes().length);
        httpExchange.getResponseHeaders().set("Content-Type", "appication/json");
        outputStream.write(jsonResult.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * Loads the HTML page for the user
     */
    private void handlePageLoading(HttpExchange httpExchange, String page)  throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        InputStream is = getClass().getClassLoader().getResourceAsStream(page);

        byte[] htmlResponse = is.readAllBytes();

        // set the headers and content of the html response
        httpExchange.sendResponseHeaders(200, htmlResponse.length);
        outputStream.write(htmlResponse);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * updates current text area content
     */
    private void handleTextChanges(HttpExchange httpExchange) throws IOException {

        InputStream body = httpExchange.getRequestBody();
        StringWriter writer = new StringWriter();
        IOUtils.copy(body, writer, StandardCharsets.UTF_8);
        String jsonBody = writer.toString();

        // close the request
        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.close();

        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            String result = jsonObject.get("content").toString();

            if(!result.equals(textAreaContent))
                textAreaContent = jsonObject.get("content").toString();

        } catch (JSONException err){
            System.out.println(err);
        }
    }

    private void saveFile(HttpExchange httpExchange) throws IOException {

        InputStream body = httpExchange.getRequestBody();
        StringWriter writer = new StringWriter();
        IOUtils.copy(body, writer, StandardCharsets.UTF_8);
        String jsonBody = writer.toString();

        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            String result = jsonObject.get("content").toString();

            FileUtil.saveFile("result.txt", result.getBytes());

        } catch (JSONException err){
            System.out.println(err);
        }

        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.close();

    }

}
