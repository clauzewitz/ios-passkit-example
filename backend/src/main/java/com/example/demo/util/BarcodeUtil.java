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
    public static ByteArrayInputStream generateBarcodeStream(final BarcodeFormat barcodeFormat, final String content, final int width, final int height) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB());
            Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hintMap.put(EncodeHintType.MARGIN, 20);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, barcodeFormat, width, height, hintMap);
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream, matrixToImageConfig);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (WriterException we) {
            return null;
        } catch (IOException io) {
            return null;
        }
    }
}
