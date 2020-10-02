import services.ReceiveService;
import services.SendService;
import services.TextService;

import java.util.Scanner;

public class QrFileTransfer {

    private final String GREETING = "what would you like to do?";
    private final String SEND_FILE = "[1] send a file to my phone";
    private final String RECEIVE_FILE = "[2] send a file to my computer";
    private final String EXCHANGE_TEXT = "[3] exchange text with my computer";
    private final String EXIT = "[4] to exit the program";
    private final String ENTER_OPTIONS = "Please select an option: ";


    public void displayOptions(){
        System.out.printf("%s\n%s\n%s\n%s\n%s\n%s", GREETING, SEND_FILE, RECEIVE_FILE, EXCHANGE_TEXT, EXIT, ENTER_OPTIONS);

        Scanner myInput = new Scanner( System.in );

        int a = myInput.nextInt();

        switch (a){
            case 1:
                new SendService().startSendingFile();
                break;
            case 2:
                new ReceiveService().receiveFile();
                break;
            case 3:
                new TextService().startTextExchange();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                displayOptions();
        }
    }


}
