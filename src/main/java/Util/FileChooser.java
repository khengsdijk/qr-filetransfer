package Util;

import javax.swing.*;
import java.io.File;

public class FileChooser {
        private JFrame frame;

        public FileChooser() {
            frame = new JFrame();

            frame.setVisible(true);
            BringToFront();
        }

        public String getFile() {
            JFileChooser fc = new JFileChooser();
            if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
                frame.setVisible(false);
                return fc.getSelectedFile().getAbsolutePath();
            }

            else {
                System.out.println("Please select a file next time");
                System.exit(1);
            }
            return null;
        }

    public String[] getFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);

        File[] files = fc.getSelectedFiles();
        String[] filePaths = new String[files.length];
        for (int i = 0; i < files.length ; i++) {
            filePaths[i] = files[i].getAbsolutePath();
        }

        return filePaths;
    }

        private void BringToFront() {
            frame.setExtendedState(JFrame.ICONIFIED);
            frame.setExtendedState(JFrame.NORMAL);

        }

}
