package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import br.com.github.macgarcia.processos.FiltroOpenCv;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.commons.io.FileUtils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public class TelaEdicaoImagemService extends RegraSelecaoImagem {
    
    private final String DIRETORIO = "img";
    private final String IMAGEM_TONS_DE_CINZA = "tonsDeCinza.png";
    private final String IMAGEM_PASSA_ALTA = "passaAlta.png";
    private final String IMAGEM_BLUR = "blur.png";
    private final String IMAGEM_SUAVIZADA = "suavizada.png";
    private final String IMAGEM_EROSAO = "erosao.png";
    private final String IMAGEM_SATURADA = "saturada.png";
    private final String IMAGEM_SOBEL = "sobel.png";
    private final String IMAGEM_ESPELHADA = "espelhada.png";
    
    private String caminhoImnagemSelecionada;
    private Mat matrizImagemSelecionada;
    
    private FiltroOpenCv filtro;

    public TelaEdicaoImagemService(final JInternalFrame frame) {
        frame.setTitle("Edição de imagem");
        frame.setResizable(false);
        executarTarefaQuandoFecharATela(frame);
    }
    
    /* Ação para apagar os arquivos quando fechar a janela de edição */
    private void executarTarefaQuandoFecharATela(final JInternalFrame frame) {
        frame.addInternalFrameListener(new InternalFrameAdapter(){
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                try {
                    FileUtils.deleteDirectory(new File(DIRETORIO));
                } catch (IOException ex) {
                    ex.getMessage();
                }
            }
        });
    }
    
    public void acaoDosBotoes(final List<JButton> botoes, final JLabel lblImagem) {
        
        cancelarBotoes(botoes);
        
        int indice = 0;
        
        //Procurar
        botoes.get(indice++).addActionListener(ae -> {
            caminhoImnagemSelecionada = abrirCaixaDeSelexao();
            if (Objects.nonNull(caminhoImnagemSelecionada) && !caminhoImnagemSelecionada.isEmpty()) {
                criarDiretorioImg();
                construirMatrizDaImagemSelecionada();
                mostrarImagemNoObjeto(caminhoImnagemSelecionada, lblImagem);
                ativarBotoes(botoes);
            } else {
                cancelarBotoes(botoes);
                lblImagem.setIcon(null);
            }
        });
        
        // Original
        botoes.get(indice++).addActionListener(ae -> {
            construirMatrizDaImagemSelecionada();
            mostrarImagemNoObjeto(caminhoImnagemSelecionada, lblImagem);
        });
        
        // Tons de cinza
        botoes.get(indice++).addActionListener(ae -> {
            filtro.tonsDeCinza();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, lblImagem);
        });
        
        // Passa alta
        botoes.get(indice++).addActionListener(ae -> {
            filtro.passaAlta();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, lblImagem);
        });
        
        // Blur
        botoes.get(indice++).addActionListener(ae -> {
            filtro.blur();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_BLUR, lblImagem);
        });
        
        // Suavizar
        botoes.get(indice++).addActionListener(ae -> {
            filtro.suavizar();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SUAVIZADA, lblImagem);
        });
        
        // Erosao
        botoes.get(indice++).addActionListener(ae -> {
            filtro.erosao();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_EROSAO, lblImagem);
        });
        
        // Saturada
        botoes.get(indice++).addActionListener(ae -> {
            filtro.saturar();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SATURADA, lblImagem);
        });
        
        // Sobel
        botoes.get(indice++).addActionListener(ae -> {
            filtro.sobel();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SOBEL, lblImagem);
        });
    }
    
    private void cancelarBotoes(final List<JButton> botoes) {
        int index = 1;
        while(index < botoes.size()) {
            botoes.get(index).setEnabled(false);
            index++;
        }
    }
    
    private void ativarBotoes(final List<JButton> botoes) {
        int index = 1;
        while(index < botoes.size()) {
            botoes.get(index).setEnabled(true);
            index++;
        }
    }
    
    private void construirMatrizDaImagemSelecionada() {
        matrizImagemSelecionada = Imgcodecs.imread(caminhoImnagemSelecionada);
        filtro = new FiltroOpenCv(matrizImagemSelecionada);
    }
    
    private void criarDiretorioImg() {
        File file = new File(DIRETORIO);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
