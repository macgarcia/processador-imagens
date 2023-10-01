package br.com.github.macgarcia.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author macgarcia
 */
public class ProcessarQrCode {

    private final int TAMANHO = 125;
    public final String NOME_QR_CODE = "Arq.png";

    public void criarQrCode(final String json) {
        try {
            FileOutputStream fos = new FileOutputStream(NOME_QR_CODE);
            ByteArrayOutputStream baos = QRCode
                    .from(json)
                    .to(ImageType.PNG)
                    .withSize(TAMANHO, TAMANHO)
                    .stream();
            fos.write(baos.toByteArray());
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProcessarQrCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessarQrCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
