package br.com.github.macgarcia.qrcode;

import net.glxn.qrgen.QRCode;

/**
 *
 * @author macgarcia
 */
public final class QrCodeCore {

    private QrCodeCore(){}
    
    private static final int TAMANHO = 125;
    public static final String NOME_QR_CODE = "Arq.png";
    
    public static byte [] criarQrCode(final String json) {
        return QRCode.from(json).withSize(TAMANHO, TAMANHO)
                .stream().toByteArray();
    }
}
