package qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;

public class QRCodeGenerator {

    /**
     * print a QR code to the terminal
     */
    public static void printCode(String input, int width, int height  ){
        String s = "Failed to generate QR code";

        // parameters for the QR writer are stored in the hashTable
        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<>();

        // Set lowest level of error correction for quality purposes
        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        qrParam.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            //generate a bit matrix based on the input and parameters
            BitMatrix bitMatrix = new MultiFormatWriter().encode(input, BarcodeFormat.QR_CODE, width, height, qrParam);
            //convert the bit matrix to an Ascii String
            s = toAscii(bitMatrix);
        } catch (WriterException e) {

            e.printStackTrace();
        }
        //print the Ascii result to the terminal
        System.out.println(s);
    }

    /**
     * convert bit matrix to ASCII characters
     */
    public static String toAscii(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        // build a string based on the bit matrix contents
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                // if a bit is true it means the bit is black
                // if a bit is black append a black terminal colour else append a white terminal colour
                if (x) {
                    //black  terminal colour
                    sb.append("\033[33m  \033[0m");
                } else {
                    //white terminal colour
                    sb.append("\033[47m  \033[0m");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
