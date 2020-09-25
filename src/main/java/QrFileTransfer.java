import qr.QRCodeGenerator;
import server.PortScanner;
import server.Server;

import java.util.Scanner;

public class QrFileTransfer {

    private Server server;

    public void startApplication(){
        startServer();
        displayOptions();
    }

    public void displayOptions(){
        System.out.println("what would you like to do?");
        System.out.println("[1] send files to my pc");
        System.out.println("[2] send files to my phone");
        System.out.println("[3] send text to my pc");
        System.out.println("[4] to exit the program");
        System.out.print("please select an option: ");
        Scanner myInput = new Scanner( System.in );

        int a = myInput.nextInt();

        switch (a){

            case 1:
                sendToPc();
                break;
            case 2:
                sendToPhone();
                break;
            case 3:
                sendTextToPC();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Unrecognized option");
                displayOptions();
        }


    }

    public void startServer(){
        server = new Server();
    }

    public void sendToPc(){

        QRCodeGenerator code = new QRCodeGenerator("hello world");

        code.printCode();
        PortScanner scanner = new PortScanner();

    }

    public void sendToPhone(){

    }

    public void sendTextToPC(){

    }





}
