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

        // Used to set QR code parameters
        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<>();

        // Set the error correction level of the QR code-choose the lowest L level here
        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        qrParam.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(input, BarcodeFormat.QR_CODE, width, height, qrParam);
            s = toAscii(bitMatrix);
        } catch (WriterException e) {

            e.printStackTrace();
        }
        System.out.println(s);
    }

    /**
     * convert bitmatrix to ASCII
     */
    public static String toAscii(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                if (!x) {
                    // black terminal colour
                    sb.append("\033[47m  \033[0m");
                } else {
                    //white terminal colour
                    sb.append("\033[33m  \033[0m");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
