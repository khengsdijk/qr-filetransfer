package server;

import Util.NetworkingUtils;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private String address;
    private int port;
    private HttpServer httpServer;

    public Server(){
        //start server in constructor to avoid nullPointer errors when adding handlers
        startServer();
    }

    public void addHandler(String endpoint, HttpHandler handler){
        httpServer.createContext(endpoint, handler);
    }

    /**
     * adds handler to the server for editing text
     */
    public void text() {
        httpServer.createContext("/text", new TextHandler());
    }

    /**
     * adds handler to the server for sending a file
     */
    public void send(String filePath){
        httpServer.createContext("/send", new SendHandler(filePath));
    }

    /**
     * adds handler to the server for receiving a file
     */
    public void receive(String storageDirectory) {
        httpServer.createContext("/receive", new ReceiveHandler( storageDirectory));
    }

    /**
     * start the server by finding network
     */
    private void startServer(){

        String networkInterface = NetworkingUtils.getPossibleAddresses().get(0);
        int usedPort = NetworkingUtils.findFreePort(networkInterface);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            httpServer = HttpServer.create(new InetSocketAddress(networkInterface, usedPort), 0 );
            httpServer.setExecutor(threadPoolExecutor);
            httpServer.start();
            address = httpServer.getAddress().getHostName();
            port = httpServer.getAddress().getPort();

        } catch (IOException e) {
            System.out.println("failed to start a server");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public int getPort(){
        return port;
    }

    public String getAddress(){
        return address;
    }
}
