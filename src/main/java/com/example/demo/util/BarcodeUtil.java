package com.example.demo.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class BarcodeUtil {
    public static ByteArrayInputStream generateBarcodeStream(String content, String fileName, int width, int height) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB());
            Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.MARGIN, 0);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height, hintMap);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream, matrixToImageConfig);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (WriterException we) {
            return null;
        } catch (IOException io) {
            return null;
        }
    }

    public static String generateBarcodeFile(String content, String fileName, int width, int height) {
        try {
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB());
            Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.MARGIN, 0);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height, hintMap);
            MatrixToImageWriter.writeToPath(bitMatrix, "png", Paths.get(String.format("%s.png", fileName)), matrixToImageConfig);
//            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; ++y)
//                    bufferedImage.setRGB(x, y, bitMatrix.get(x, 0) ? Byte.MAX_VALUE : Byte.MIN_VALUE);
//            }
//
//            ImageIO.write(bufferedImage, "png", new File("test1.png"));
            return null;
        } catch (WriterException we) {
            return null;
        } catch (IOException io) {
            return null;
        }
    }
}
