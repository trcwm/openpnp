package org.openpnp.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class OpenCvUtils {
    /*
     * Notes
     * cvtColor(src,src,CV_8UC1); may come in handy later
     * 
     *                 BufferedImage resultImage = new BufferedImage(result.width(),
                        result.height(), BufferedImage.TYPE_USHORT_GRAY);
     */
    
    public static BufferedImage toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster()
                .getDataBuffer()).getData();
        m.get(0, 0, targetPixels);
        return image;
    }
    
    public static Mat toMat(BufferedImage img) {
        img = convertBufferedImage(img, BufferedImage.TYPE_3BYTE_BGR);
        Mat mat = new Mat(img.getWidth(), img.getHeight(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    /**
     * Convert a BufferedImage from it's current type to a new, specified type
     * by creating a new BufferedImage and drawing the source image onto it. If
     * the image is already of the specified type it is returned unchanged.
     * 
     * @param src
     * @param type
     * @return
     */
    public static BufferedImage convertBufferedImage(BufferedImage src, int type) {
        if (src.getType() == type) {
            return src;
        }
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(),
                type);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return img;
    }
}
