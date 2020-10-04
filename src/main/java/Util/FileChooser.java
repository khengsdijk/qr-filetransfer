package Util;

import javax.swing.*;
import java.io.File;

/**
 * A swing file chooser to provide the user with a convenient way of picking a file
 */
public class FileChooser {
    private JFrame frame;

    public FileChooser() {
        frame = new JFrame();

        frame.setVisible(true);
        BringToFront();
    }

    /**
     * allow the user to pick a single file
     */
    public String getFile() {
        JFileChooser fc = new JFileChooser();

        // keep the file chooser open until the user confirms their choice
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
            frame.setVisible(false);
            return fc.getSelectedFile().getAbsolutePath();
        } else {
            System.out.println("Please select a file next time");
            System.exit(1);
        }
        return null;
    }

    /**
     * allow the user to pick multiple files
     */
    public String[] getFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        // keep the file chooser open until the user confirms their choice
        if (JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)) {
            File[] files = fc.getSelectedFiles();
            String[] filePaths = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                filePaths[i] = files[i].getAbsolutePath();
            }

            return filePaths;
        } else {
            System.out.println("Please select a file next time");
            System.exit(1);
        }
        return null;
    }

    private void BringToFront() {
        frame.setExtendedState(JFrame.ICONIFIED);
        frame.setExtendedState(JFrame.NORMAL);

    }

}
