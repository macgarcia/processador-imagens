
package br.com.github.macgarcia.mainteste;

import br.com.github.macgarcia.qrcode.ProcessarQrCode;

/**
 *
 * @author macgarcia
 */
public class MainTeste {
    
    public static void main(String[] args) {
        ProcessarQrCode a = new ProcessarQrCode();
        a.criarQrCode("001 002 003");
    }
    
}
