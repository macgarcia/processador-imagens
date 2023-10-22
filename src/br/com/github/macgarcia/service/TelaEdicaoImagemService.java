package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
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
    
    private String caminhoImnagemSelecionada;
    private Mat matrizImagemSelecionada, matrizImagenTonsDeCinza, matrizImagemPassaAlta, matrizImagemBlur, blur2;

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
        
        int indice = 0;
        
        //Procurar
        botoes.get(indice++).addActionListener(ae -> {
            caminhoImnagemSelecionada = abrirCaixaDeSelexao();
            
            if (Objects.nonNull(caminhoImnagemSelecionada)) {
                construirMatrizesFiltros(caminhoImnagemSelecionada);
                
                mostrarImagemNoObjeto(caminhoImnagemSelecionada, lblImagem);
            }
        });
        
        // Tons de cinza
        botoes.get(indice++).addActionListener(ae -> {
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, lblImagem);
        });
        
        // Passa alta
        botoes.get(indice++).addActionListener(ae -> {
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, lblImagem);
        });
        
        //Blur
        botoes.get(indice++).addActionListener(ae -> {
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_BLUR, lblImagem);
        });
    }
    
    private void construirMatrizesFiltros(final String caminho) {
        matrizImagemSelecionada = Imgcodecs.imread(caminho);
        criarDiretorioImg();
        tonsDeCinza();
        passaAlta();
        blur();
    }
    
    private void criarDiretorioImg() {
        File file = new File(DIRETORIO);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    private void tonsDeCinza() {
        matrizImagenTonsDeCinza = new Mat();
        Imgproc.cvtColor(matrizImagemSelecionada, matrizImagenTonsDeCinza, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, matrizImagenTonsDeCinza);
    }
    
    private void passaAlta() {
        matrizImagemPassaAlta = new Mat();
        Imgproc.Laplacian(matrizImagemSelecionada, matrizImagemPassaAlta, CvType.CV_16S);
        Core.convertScaleAbs(matrizImagemPassaAlta, matrizImagemPassaAlta);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, matrizImagemPassaAlta);
    }
    
    private void blur() {
        matrizImagemBlur = new Mat();
        Size kernelSize = new Size(15, 15);
        Imgproc.GaussianBlur(matrizImagemSelecionada, matrizImagemBlur, kernelSize, 0);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_BLUR, matrizImagemBlur);
    }

}
