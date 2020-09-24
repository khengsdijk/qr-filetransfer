import qr.QRCodeGenerator;
import server.PortScanner;

import java.net.SocketException;

public class Main {

    public static void main(String[] args) {
        QRCodeGenerator code = new QRCodeGenerator("hello world");

        code.printCode();
        PortScanner scanner = new PortScanner();
        try {
            scanner.findInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

}
