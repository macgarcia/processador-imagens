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
    private final String IMAGEM_SUAVIZADA = "suavizada.png";
    private final String IMAGEM_EROSAO = "erosao.png";
    private final String IMAGEM_SATURADA = "saturada.png";
    private final String IMAGEM_SOBEL = "sobel.png";
    private final String IMAGEM_ESPELHADA = "espelhada.png";
    
    private String caminhoImnagemSelecionada;
    private Mat matrizImagemSelecionada;

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
            if (Objects.nonNull(caminhoImnagemSelecionada)) {
                criarDiretorioImg();
                construirMatrizDaImagemSelecionada();
                mostrarImagemNoObjeto(caminhoImnagemSelecionada, lblImagem);
                ativarBotoes(botoes);
            }
        });
        
        // Original
        botoes.get(indice++).addActionListener(ae -> {
            construirMatrizDaImagemSelecionada();
            mostrarImagemNoObjeto(caminhoImnagemSelecionada, lblImagem);
        });
        
        // Tons de cinza
        botoes.get(indice++).addActionListener(ae -> {
            tonsDeCinza();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, lblImagem);
        });
        
        // Passa alta
        botoes.get(indice++).addActionListener(ae -> {
            passaAlta();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, lblImagem);
        });
        
        // Blur
        botoes.get(indice++).addActionListener(ae -> {
            blur();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_BLUR, lblImagem);
        });
        
        // Suavizar
        botoes.get(indice++).addActionListener(ae -> {
            suavizar();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SUAVIZADA, lblImagem);
        });
        
        // Erosao
        botoes.get(indice++).addActionListener(ae -> {
            erosao();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_EROSAO, lblImagem);
        });
        
        // Saturada
        botoes.get(indice++).addActionListener(ae -> {
            saturar();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SATURADA, lblImagem);
        });
        
        // Sobel
        botoes.get(indice++).addActionListener(ae -> {
            sobel();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_SOBEL, lblImagem);
        });
        
        // Espelhar
        botoes.get(indice++).addActionListener(ae -> {
            espelhamento();
            mostrarImagemNoObjeto(DIRETORIO + File.separator + IMAGEM_ESPELHADA, lblImagem);
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
    }
    
    private void criarDiretorioImg() {
        File file = new File(DIRETORIO);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    private void tonsDeCinza() {
        Mat matrizImagenTonsDeCinza = new Mat();
        Imgproc.cvtColor(matrizImagemSelecionada, matrizImagenTonsDeCinza, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, matrizImagenTonsDeCinza);
    }
    
    private void passaAlta() {
        Mat matrizImagemPassaAlta = new Mat();
        Imgproc.Laplacian(matrizImagemSelecionada, matrizImagemPassaAlta, CvType.CV_16S);
        Core.convertScaleAbs(matrizImagemPassaAlta, matrizImagemPassaAlta);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, matrizImagemPassaAlta);
    }
    
    private void blur() {
        Mat matrizImagemBlur = new Mat();
        Size kernelSize = new Size(15, 15);
        Imgproc.GaussianBlur(matrizImagemSelecionada, matrizImagemBlur, kernelSize, 0);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_BLUR, matrizImagemBlur);
    }
    
    private void suavizar() {
        Mat suavizacao = new Mat();
        Imgproc.bilateralFilter(matrizImagemSelecionada, suavizacao, 9, 75, 75);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_SUAVIZADA, suavizacao);
    }
    
    private void erosao() {
      Mat erosao =  new Mat();
      Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
      Imgproc.erode(matrizImagemSelecionada, erosao, kernel);
      Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_EROSAO, erosao);
    }
    
    private void saturar() {
        Mat hsv = new Mat();
        Imgproc.cvtColor(matrizImagemSelecionada, hsv, Imgproc.COLOR_BGR2HSV);
        
        for (int r = 0; r < hsv.rows(); r++) {
            for (int c = 0; c < hsv.cols(); c++) {
                double[] pixel = hsv.get(r, c);
                pixel[1] = 50;
                hsv.put(r, c, pixel);
            }
        }
        
        Mat saturada = new Mat();
        Imgproc.cvtColor(hsv, saturada, Imgproc.COLOR_BGR2HSV);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_SATURADA, saturada);
    }
    
    private void sobel() {
        //Mat imagem = Imgcodecs.imread(caminhoImnagemSelecionada, Imgcodecs.IMREAD_GRAYSCALE);
        Mat imagem = matrizImagemSelecionada.clone();
        
        Mat gradienteX = new Mat();
        Mat gradienteY = new Mat();
        Mat imagemBordas = new Mat();

        Imgproc.Sobel(imagem, gradienteX, CvType.CV_16S, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);
        Imgproc.Sobel(imagem, gradienteY, CvType.CV_16S, 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);

        // Calcular a magnitude do gradiente
        Core.convertScaleAbs(gradienteX, gradienteX);
        Core.convertScaleAbs(gradienteY, gradienteY);
        Core.addWeighted(gradienteX, 0.5, gradienteY, 0.5, 0, imagemBordas);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_SOBEL, imagemBordas);
    }
    
    private void espelhamento() {
        Mat espelho = new Mat();
        Core.flip(matrizImagemSelecionada, espelho, 1);
        matrizImagemSelecionada = espelho.clone();
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_ESPELHADA, espelho);
    }

}
