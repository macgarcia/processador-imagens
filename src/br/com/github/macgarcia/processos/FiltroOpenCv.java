package br.com.github.macgarcia.processos;

import java.io.File;
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
public class FiltroOpenCv {
    
    private final String DIRETORIO = "img";
    private final String IMAGEM_TONS_DE_CINZA = "tonsDeCinza.png";
    private final String IMAGEM_PASSA_ALTA = "passaAlta.png";
    private final String IMAGEM_BLUR = "blur.png";
    private final String IMAGEM_SUAVIZADA = "suavizada.png";
    private final String IMAGEM_EROSAO = "erosao.png";
    private final String IMAGEM_SATURADA = "saturada.png";
    private final String IMAGEM_SOBEL = "sobel.png";
    private final String IMAGEM_ESPELHADA = "espelhada.png";

    private Mat matriz;

    public FiltroOpenCv(Mat matriz) {
        this.matriz = matriz;
    }

    public void tonsDeCinza() {
        Mat matrizImagenTonsDeCinza = new Mat();
        Imgproc.cvtColor(matriz, matrizImagenTonsDeCinza, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_TONS_DE_CINZA, matrizImagenTonsDeCinza);
    }
    
    public void passaAlta() {
        Mat matrizImagemPassaAlta = new Mat();
        Imgproc.Laplacian(matriz, matrizImagemPassaAlta, CvType.CV_16S);
        Core.convertScaleAbs(matrizImagemPassaAlta, matrizImagemPassaAlta);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_PASSA_ALTA, matrizImagemPassaAlta);
    }
    
    public void blur() {
        Mat matrizImagemBlur = new Mat();
        Size kernelSize = new Size(15, 15);
        Imgproc.GaussianBlur(matriz, matrizImagemBlur, kernelSize, 0);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_BLUR, matrizImagemBlur);
    }
    
    public void suavizar() {
        Mat suavizacao = new Mat();
        Imgproc.bilateralFilter(matriz, suavizacao, 9, 75, 75);
        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_SUAVIZADA, suavizacao);
    }
    
    public void erosao() {
      Mat erosao =  new Mat();
      Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
      Imgproc.erode(matriz, erosao, kernel);
      Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_EROSAO, erosao);
    }
    
    public void saturar() {
        Mat hsv = new Mat();
        Imgproc.cvtColor(matriz, hsv, Imgproc.COLOR_BGR2HSV);
        
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
    
    public void sobel() {
        //Mat imagem = Imgcodecs.imread(caminhoImnagemSelecionada, Imgcodecs.IMREAD_GRAYSCALE);
        Mat imagem = matriz.clone();
        
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
    
//    private void espelhamento() {
//        Mat espelho = new Mat();
//        Core.flip(matriz, espelho, 1);
//        matriz = espelho.clone();
//        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_ESPELHADA, espelho);
//    }

}
