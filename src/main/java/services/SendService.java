package services;

import Util.FileChooser;
import Util.FileUtil;
import server.SendHandler;

import java.io.IOException;
import java.util.Scanner;

/**
 * handles the user options for sending files and configuring the right endpoint
 */
public class SendService extends Service {

    // define the user commands and options

    private final String OPENING = "Do want to send a single file or multiple as a zip?: ";
    private final String SINGLE = "[1] Send a single file";
    private final String MULTIPLE = "[2] Send multiple files as a zip";

    private final String TERMINAL_OR_FILECHOOSER = "Enter file path in the terminal or user a file chooser?";
    private final String TERMINAL = "[1] terminal";
    private final String FILECHOOSER = "[2] file chooser";

    private static final String PATH = "Enter the path of your file: ";
    private final String ENDPOINT = "/send";

    private final int SINGLE_ARGUMENT = 1;
    private final int MULTIPLE_ARGUMENT = 2;

    private final int TERMINAL_ARGUMENT = 1;
    private final int FILECHOOSER_ARGUMENT = 2;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * start the server and allow the user to choose an option for single or multiple files
     */
    public void startSendingFile() {
        startServer();
        System.out.println(OPENING);
        System.out.println(SINGLE);
        System.out.println(MULTIPLE);
        System.out.print(SELECT_OPTION);

        switch (scanner.nextInt()) {
            case SINGLE_ARGUMENT:
                sendSingleFile();
                break;
            case MULTIPLE_ARGUMENT:
                sendMultipleFiles();
                break;
            default:
                System.out.println(BAD_ARGUMENT);
                startSendingFile();
        }

        printCode(ENDPOINT);
    }

    /**
     * start sending a single file
     */
    public void sendSingleFile() {
        String filePath = selectFile();
        server.addHandler(ENDPOINT, new SendHandler(filePath, false));
    }

    /**
     * start sending multiple files by creating a zip of a number of different files
     */
    public void sendMultipleFiles() {
        String[] files = selectFiles();

        try {
            // create temporary zip file and delete is after sending
            String zipFilePath = FileUtil.loadFilesAsZip(files);
            server.addHandler(ENDPOINT, new SendHandler(zipFilePath, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * select a single file either with the commandline or with a file chooser
     */
    public String selectFile() {
        System.out.println(TERMINAL_OR_FILECHOOSER);
        System.out.println(TERMINAL);
        System.out.println(FILECHOOSER);
        System.out.print(SELECT_OPTION);

        String filename = "";
        switch (scanner.nextInt()) {
            case TERMINAL_ARGUMENT:
                System.out.print(PATH);
                //ignore the first next not consumer by the scanner.nextInt()
                scanner.nextLine();
                filename = scanner.nextLine();
                break;
            case FILECHOOSER_ARGUMENT:
                filename = new FileChooser().getFile();
                break;
            default:
                System.out.println(BAD_ARGUMENT);
                sendSingleFile();
        }
        return filename;
    }

    /**
     * select a multiple files either with the commandline or with a file chooser
     */
    public String[] selectFiles() {
        System.out.println(TERMINAL_OR_FILECHOOSER);
        System.out.println(TERMINAL);
        System.out.println(FILECHOOSER);
        System.out.print(SELECT_OPTION);

        switch (scanner.nextInt()) {
            case TERMINAL_ARGUMENT:
                System.out.print(PATH);
                //ignore the first next not consumer by the scanner.nextInt()
                scanner.nextLine();
                String files = scanner.nextLine();
                return files.split("\\s");
            case FILECHOOSER_ARGUMENT:
                return new FileChooser().getFiles();
            default:
                System.out.println(BAD_ARGUMENT);
                sendSingleFile();
        }
        return null;
    }


}
