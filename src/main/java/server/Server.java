package server;

import Util.NetworkingUtils;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.sql.SQLOutput;

public class Server {

    private String address;
    private boolean online;
    private HttpServer httpServer;

    public Server(){
        online = false;
    }

    /**
     * sends a single file to a users phone over wifi
     */
    public void send(){

    }


    /**
     * reveives a single file from a users phone over wifi
     */
    public void receive(){

    }


    /**
     *
     */
    public void text(){

    }

    public void startServer(){

        String networkInterface = NetworkingUtils.findWirelessInterface();
        int port = NetworkingUtils.findFreePort(networkInterface);

        try {

            httpServer = HttpServer.create(new InetSocketAddress(networkInterface, port), 0 );

        } catch (IOException e) {
            System.out.println("failed to start a server");
            e.printStackTrace();
            System.exit(1);
        }

        online = true;
    }

}
