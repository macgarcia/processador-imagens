package br.com.github.macgarcia.processos;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public class FiltroOpenCv {

    private final Mat matriz;

    public FiltroOpenCv(Mat matriz) {
        this.matriz = matriz;
    }

    public Mat tonsDeCinza() {
        Mat matrizImagenTonsDeCinza = new Mat();
        Imgproc.cvtColor(matriz, matrizImagenTonsDeCinza, Imgproc.COLOR_RGB2GRAY);
        return matrizImagenTonsDeCinza;
    }

    public Mat passaAlta() {
        Mat matrizImagemPassaAlta = new Mat();
        Imgproc.Laplacian(matriz, matrizImagemPassaAlta, CvType.CV_16S);
        Core.convertScaleAbs(matrizImagemPassaAlta, matrizImagemPassaAlta);
        return matrizImagemPassaAlta;
    }

    public Mat blur() {
        Mat matrizImagemBlur = new Mat();
        Size kernelSize = new Size(15, 15);
        Imgproc.GaussianBlur(matriz, matrizImagemBlur, kernelSize, 0);
        return matrizImagemBlur;
    }

    public Mat suavizar() {
        Mat matrizSuavizacao = new Mat();
        Imgproc.bilateralFilter(matriz, matrizSuavizacao, 9, 75, 75);
        return matrizSuavizacao;
    }

    public Mat erosao() {
        Mat matrizErosao = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.erode(matriz, matrizErosao, kernel);
        return matrizErosao;

    }

    public Mat saturar() {
        Mat hsv = new Mat();
        Imgproc.cvtColor(matriz, hsv, Imgproc.COLOR_BGR2HSV);

        for (int r = 0; r < hsv.rows(); r++) {
            for (int c = 0; c < hsv.cols(); c++) {
                double[] pixel = hsv.get(r, c);
                pixel[1] = 50;
                hsv.put(r, c, pixel);
            }
        }

        Mat matrizSaturada = new Mat();
        Imgproc.cvtColor(hsv, matrizSaturada, Imgproc.COLOR_BGR2HSV);
        return matrizSaturada;
    }

    public Mat sobel() {
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
        return imagemBordas;
    }

//    private void espelhamento() {
//        Mat espelho = new Mat();
//        Core.flip(matriz, espelho, 1);
//        matriz = espelho.clone();
//        Imgcodecs.imwrite(DIRETORIO + File.separator + IMAGEM_ESPELHADA, espelho);
//    }
}
