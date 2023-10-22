package br.com.github.macgarcia.componente;

import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author macgarcia
 */
public abstract class RegraSelecaoImagem {

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

}
