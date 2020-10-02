package services;

import qr.QRCodeGenerator;
import server.Server;

public abstract class Service {

    protected Server server;
    protected final String SELECT_OPTION = "Please select an option: ";
    protected final String BAD_ARGUMENT = "Unrecognized argument!";


    public void startServer(){
        server = new Server();
    }

    public void printCode(String endpoint){
        QRCodeGenerator.printCode( "http://" + server.getAddress() + ":" + server.getPort() + endpoint, 30, 20);
        System.out.println("Visit: http://" + server.getAddress() + ":" + server.getPort() + endpoint);
    }

}
