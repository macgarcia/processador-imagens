package br.com.github.macgarcia.processos;

import org.opencv.core.Mat;

/**
 *
 * @author macgarcia
 */
public class GeradorHistograma {


    public Long criarHistograma(final Mat mat) {
        
        int[] vetor = new int[256];
        int count = 0;
        Integer histograma = 0;
        
        for (int i = 0; i < mat.rows(); i++) {
            for (int j = 0; j < mat.cols(); j++) {
                count = (int) mat.get(i, j)[0];
                vetor[count]++;
            }
        }
       
        for(int i = 0; i < vetor.length; i++) {
            histograma += vetor[i];
        }
        
        return Long.valueOf(histograma.toString());
    }

    public boolean compararHistogramas(Mat m1, Mat m2) {
        int[] vet1 = new int[256];
        int[] vet2 = new int[256];
        int hist1, hist2, count = 0;

        for (int i = 0; i < m1.rows(); i++) {
            for (int j = 0; j < m1.cols(); j++) {
                hist1 = (int) m1.get(i, j)[0];
                vet1[hist1]++;
            }
        }

        for (int i = 0; i < m2.rows(); i++) {
            for (int j = 0; j < m2.cols(); j++) {
                hist2 = (int) m2.get(i, j)[0];
                vet2[hist2]++;
            }
        }
        
        for (int c = 0; c < 256; c++) {
            if (vet1[c] == vet2[c]) {
                count = count + 1;
            }
        }
        
        return count == 256;
    }

}
