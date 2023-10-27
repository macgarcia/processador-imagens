package br.com.github.macgarcia.componente;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public abstract class RegraSelecaoImagem {

    private final String PNG = ".png";

    public String abrirCaixaDeSelexao() {
        JFileChooser seletor = new JFileChooser();
        seletor.setVisible(true);
        final int selecionado = seletor.showOpenDialog(null);
        seletor.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (JFileChooser.APPROVE_OPTION == selecionado) {
            try {
                return seletor.getSelectedFile().getCanonicalPath();
            } catch (IOException ex) {
                return "";
            }
        }
        return "";
    }

    public void mostrarImagemNoObjeto(final String caminhoDoArquivo, JLabel label) {
        ImageIcon icon = new ImageIcon(caminhoDoArquivo);
        Image image = icon.getImage();

        Image readyImage = image.getScaledInstance(label.getWidth(),
                label.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon readyIcon = new ImageIcon(readyImage);
        label.setIcon(readyIcon);
    }

    public void montarMatrizImagemNoLabel(Mat mat, JLabel label) {
       
        /* Redimensiona a imagem para o tamanho do label que irá apresentar */
        Mat matrizTamanho = new Mat();
        Imgproc.resize(mat, matrizTamanho, new Size(label.getWidth(), label.getHeight()));
        /*--*/
        
        /* Cria uma array de bytes a partir da matriz da imagem*/
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(PNG, matrizTamanho, mob);
        byte[] toArray = mob.toArray();
        /*--*/
        
        /* Carrega o array de bytes e transforma em um buffer para escrever no label como imagem*/
        try (ByteArrayInputStream b = new ByteArrayInputStream(toArray)) {
            BufferedImage read = ImageIO.read(b);
            ImageIcon imageIcon = new ImageIcon(read);
            label.setIcon(imageIcon);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

}
