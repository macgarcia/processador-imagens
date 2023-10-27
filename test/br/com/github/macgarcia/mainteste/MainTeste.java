package br.com.github.macgarcia.mainteste;

import br.com.github.macgarcia.qrcode.QrCodeCore;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author macgarcia
 */
public class MainTeste {

    public static void main(String[] args) throws IOException {
        //testeQrCode();
        testeMatriz();
    }

    public static void testeQrCode() {
        byte[] criarQrCode2 = QrCodeCore.criarQrCode("https://uol.com.br");

        ImageIcon imageIcon = new ImageIcon(criarQrCode2);

        // Crie um JLabel com o ImageIcon
        JLabel jLabel = new JLabel(imageIcon);

        // Exiba o JLabel em um JOptionPane
        JOptionPane.showMessageDialog(null, jLabel, "Imagem", JOptionPane.PLAIN_MESSAGE);
    }

    public static void testeMatriz() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat m1 = Imgcodecs.imread("C:\\Users\\macgarcia\\Pictures\\Screenshots\\Captura de tela 2023-04-21 225157.png");

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".png", m1, mob);

        byte[] toArray = mob.toArray();

        try (ByteArrayInputStream b = new ByteArrayInputStream(toArray)) {
            
            BufferedImage read = ImageIO.read(b);
            ImageIcon imageIcon = new ImageIcon(read);

            // Crie um JLabel com o ImageIcon
            JLabel jLabel = new JLabel(imageIcon);

            // Exiba o JLabel em um JOptionPane
            JOptionPane.showMessageDialog(null, jLabel, "Imagem", JOptionPane.PLAIN_MESSAGE);
        }

    }

}
