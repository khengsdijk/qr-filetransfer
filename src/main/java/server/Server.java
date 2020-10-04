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
     * start the server by finding network
     */
    private void startServer(){

        //find suitable network interface and free port
        String networkInterface = NetworkingUtils.getPossibleAddresses().get(0);
        int usedPort = NetworkingUtils.findFreePort(networkInterface);
        //set the thread executor for the server
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            //attempt to start the server with the discovered network interface address and port
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
