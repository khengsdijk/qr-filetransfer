package Util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    /**
     * create a zip file of the provided files and return the path of the zip
     */
    public static String loadFilesAsZip(String[] files) throws IOException {

        File f = new File("temp.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));


        for(String file: files){
            File entry = new File(file);

            FileInputStream inputStream = new FileInputStream(entry);
            ZipEntry zipEntry = new ZipEntry(entry.getName());
            out.putNextEntry(zipEntry);
            byte[] bytes = inputStream.readAllBytes();
            out.write(bytes, 0,bytes.length);
            out.closeEntry();
        }
        out.close();

        return f.getAbsolutePath();
    }

    /**
     * Deletes a file
     */
    public static void deleteFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }

    /**
     * Saves a file
     */
    public static void saveFile(String fileName, byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(new File(fileName), data);
        System.out.println("wassup");
    }

    /**
     * Checks if a directory actually exists and is a valid directory string
     */
    public static boolean validateDirectory(String directory){
        if(directory.isEmpty())
            return true;
        try {
            Paths.get(directory);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
}
