package qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeGenerator {

    private String input;
    private final char WHITE_SQUARE = 0x25A0;
    private final char WHITE_SPACE = 0x25A1;

    private int width = 40;
    private int height = 40;

    public QRCodeGenerator(String input) {
        this.input = input;
    }

    public ByteArrayOutputStream getByteStream(){
        return QRCode.from(input).stream();
    }

    public void printCode(){
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

    public static String toAscii(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                if (!x) {
                    // white
                    sb.append("\033[47m  \033[0m");
                } else {
                    sb.append("\033[40m  \033[0m");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
