package br.com.github.macgarcia.util;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author macgarcia
 */
public class MatrizDeFiltro {

    public static Mat tonsDeCinza() {
        return new Mat(3, 3, CvType.CV_32F) {
            {
                put(0, 0, (float) 1 / 9);
                put(0, 1, (float) 1 / 9);
                put(0, 2, (float) 1 / 9);
                put(1, 0, (float) 1 / 9);
                put(1, 1, (float) 1 / 9);
                put(1, 2, (float) 1 / 9);
                put(2, 0, (float) 1 / 9);
                put(2, 1, (float) 1 / 9);
                put(2, 2, (float) 1 / 9);
            }
        };
    }

    public static Mat passaAlta() {
        return new Mat(3, 3, CvType.CV_32F) {
            {
                put(0, 0, 0);
                put(0, 1, -1);
                put(0, 2, 0);
                put(1, 0, -1);
                put(1, 1, 5);
                put(1, 2, -1);
                put(2, 0, 0);
                put(2, 1, -1);
                put(2, 2, 0);
            }
        };
    }
    
    

}
