package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.gituhub.macgarcia.core.FactoryMensagem;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author macgarcia
 */
public class TelaCapturarImagemService extends RegraSelecaoImagem {

    private final String EXTENCAO_ARQUIVO = "jpg";

    private Webcam webCam;
    private boolean cameraLigada;
    private JLabel lblImagemCapturada;
    private BufferedImage fotografia;

    public TelaCapturarImagemService(final JInternalFrame tela) {
        tela.setTitle("Capturar imagem");
        tela.setResizable(false);
        configurarCamera();
        acaoDoFecharJanea(tela);
    }

    private void configurarCamera() {
        Dimension size = WebcamResolution.VGA.getSize();
        webCam = Webcam.getDefault();
        webCam.setViewSize(size);
    }

    public void acaoDosBotoes(final List<JButton> botoes,
            final JLabel lblImagemCapturada) {

        this.lblImagemCapturada = lblImagemCapturada;
        JButton ligar = botoes.get(0);
        JButton capturar = botoes.get(1);
        capturar.setEnabled(false);

        //Ligar
        ligar.addActionListener(ae -> {
            ligarCamera();
            ligar.setEnabled(false);
            capturar.setEnabled(true);
        });

        //Capturar
        capturar.addActionListener(ae -> {
            acaoBotaoCaputrar();
        });
    }

    private void iniciarThreadDeCaptura() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (cameraLigada) {
                        BufferedImage image = webCam.getImage();
                        ImageIcon icon = new ImageIcon(image);
                        icon.setImage(image.getScaledInstance(lblImagemCapturada.getWidth(),
                                lblImagemCapturada.getHeight(),
                                100));
                        lblImagemCapturada.setIcon(icon);
                    }
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                }
            }
        }.start();
    }

    private void ligarCamera() {
        new Thread() {
            @Override
            public void run() {
                webCam.open();
                cameraLigada = true;
                iniciarThreadDeCaptura();
            }
        }.start();
    }

    private String caixaDeSalvamento() {
        JFileChooser seletor = new JFileChooser();
        seletor.setVisible(true);
        seletor.setDialogTitle("Salvar em...");
        seletor.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        desabilitaCampoTexto(seletor);
        final int selecionado = seletor.showSaveDialog(null);
        if (JFileChooser.APPROVE_OPTION == selecionado) {
            try {
                return seletor.getSelectedFile().getCanonicalPath();
            } catch (IOException ex) {
                return "";
            }
        }
        return "";
    }

    public boolean desabilitaCampoTexto(Container container) {
        Component[] comps = container.getComponents();

        for (Component comp : comps) {

            if (comp instanceof JTextField) {
                ((JTextField) comp).setEnabled(false);
                return true;
            }

            if (comp instanceof Container) {
                if (desabilitaCampoTexto((Container) comp)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void acaoBotaoCaputrar() {
        fotografia = webCam.getImage();
        cameraLigada = false;
        final String caminhoParaSalvar = caixaDeSalvamento();
        //se cancelou a ação de salvar a foto
        if (!caminhoParaSalvar.isEmpty()) {
            salvarCaptura(caminhoParaSalvar);
        }
        ligarCamera();
    }

    private void acaoDoFecharJanea(JInternalFrame tela) {
        tela.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                cameraLigada = false;
                if (webCam.isOpen()) {
                    webCam.close();
                }
            }
        });
    }

    private void salvarCaptura(final String caminho) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(fotografia, EXTENCAO_ARQUIVO, baos);
            byte[] bytes = baos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(bais);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            final String nomeArquivo = LocalDateTime.now().format(formatter) + "." + EXTENCAO_ARQUIVO;

            ImageIO.write(bi,
                    EXTENCAO_ARQUIVO,
                    new File(caminho + File.separator + nomeArquivo));

            webCam.close();
            FactoryMensagem.mensagemOk("Imagem salva com sucesso.");
        } catch (IOException e) {
            FactoryMensagem.mensagemErro("Diretório não encontrado");
        }
    }
}
