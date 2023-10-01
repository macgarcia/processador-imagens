package br.com.github.macgarcia.processador;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JPanel;
import org.opencv.core.Mat;

public class Util {

    /**
     * FUNÇÃO..: toBufferedImage OBJETIVO: Converter uma imagem do tipo Mat
     * (OpenCV) em um BufferedImage para exibição no JPanel
     *
     * Créditos:
     * https://github.com/master-atul/ImShow-Java-OpenCV/blob/master/ImShow_JCV/src/com/atul/JavaOpenCV/Imshow.java
     *
     * @param mat
     * @return
     */
    public static BufferedImage toBufferedImage(Mat mat) {

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] b = new byte[bufferSize];
        mat.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster()
                .getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public static void exibirImagem(Graphics g, Mat mat) {
        BufferedImage buff = Util.toBufferedImage(mat);
        g.drawImage(buff, 0, 0, null);
    }

    public static void exibirImagem(JPanel p, Mat mat) {
        if (mat != null) {
            ((OpenPanel) p).setImage(mat);
        }
    }

}
