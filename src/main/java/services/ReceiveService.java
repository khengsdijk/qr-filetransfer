package services;

import Util.FileUtil;
import server.ReceiveHandler;

import java.util.Scanner;

public class ReceiveService extends Service {

    // define user options and endpoint
    private final String DIRECTORY_OPTION = "please enter the directory to save the files or leave empty for current directory";
    private final String ENDPOINT = "/receive";

    private final Scanner scanner = new Scanner(System.in);

    /**
     * start the server and add the endpoint for receiving a file to the server
     */
    public void receiveFile() {
        startServer();
        String directory = configureDirectory();
        server.addHandler(ENDPOINT, new ReceiveHandler(directory));
        printCode(ENDPOINT);
    }

    /**
     * let a user choose a directory or use the current directory
     */
    public String configureDirectory() {

        System.out.println(DIRECTORY_OPTION);
        System.out.print("enter directory: ");
        String directory = scanner.nextLine();

        if (!FileUtil.validateDirectory(directory)) {
            System.out.println("Invalid directory!");
            configureDirectory();
        }
        return directory;
    }


}
