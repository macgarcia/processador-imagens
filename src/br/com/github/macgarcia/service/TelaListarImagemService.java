package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.ModeloTabelaListarImagem;
import br.com.github.macgarcia.modelo.Imagem;
import br.com.github.macgarcia.qrcode.QrCodeCore;
import com.google.gson.Gson;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author macgarcia
 */
public class TelaListarImagemService {

    private final ModeloTabelaListarImagem modelo = new ModeloTabelaListarImagem();

    public TelaListarImagemService(final JInternalFrame tela) {
        tela.setTitle("Listar Imagens");
        tela.setResizable(false);
        acaoBotaoFecharJanela(tela);
    }

    public void iniciarTabela(final JTable tabela) {
        tabela.setModel(modelo);
        tabela.getTableHeader().setReorderingAllowed(false);
    }

    public void acaoDeCliqueNaTabela(final JTable tabela, final JLabel lblSelecionado, final JLabel lblQrCode) {
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Imagem imagem = modelo.getImagem(tabela.getSelectedRow());
                mostrarImagemNoPainel(imagem, lblSelecionado);
                criarQrCode(imagem, lblQrCode);
            }
        });

        tabela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    Imagem imagem = modelo.getImagem(tabela.getSelectedRow() - 1);
                    mostrarImagemNoPainel(imagem, lblSelecionado);
                    criarQrCode(imagem, lblQrCode);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Imagem imagem = modelo.getImagem(tabela.getSelectedRow() + 1);
                    mostrarImagemNoPainel(imagem, lblSelecionado);
                    criarQrCode(imagem, lblQrCode);
                }
            }
        });
    }

    private void mostrarImagemNoPainel(final Imagem imagem, final JLabel lblSelecionado) {
        lblSelecionado.setIcon(null);
        criarIconeParaOLabel(imagem.getImg(), lblSelecionado);
    }

    private void criarQrCode(final Imagem imagem, final JLabel lblQrCode) {
        Map<String, Object> map = new HashMap();
        map.put("id", imagem.getId().toString());
        map.put("nomeFoto", imagem.getNomeFoto());
        map.put("histograma", imagem.getHistograma().toString());
        
        Gson gson = new Gson();
        String imagemJson = gson.toJson(map);
        byte[] imagemByte = QrCodeCore.criarQrCode(imagemJson);
        
        criarIconeParaOLabel(imagemByte, lblQrCode);
    }

    private void criarIconeParaOLabel(final byte[] b, final JLabel lblSelecionado) {
        ImageIcon icon = new ImageIcon(b);
        Image image = icon.getImage();

        Image readyImage = image.getScaledInstance(lblSelecionado.getWidth(),
                lblSelecionado.getHeight(),
                Image.SCALE_SMOOTH);

        ImageIcon readyIcon = new ImageIcon(readyImage);
        lblSelecionado.setIcon(readyIcon);
    }

    // Quando fechar a janela
    // apagar os arquivos temporarios
    private void acaoBotaoFecharJanela(JInternalFrame tela) {
        tela.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                File file = new File(QrCodeCore.NOME_QR_CODE);
                if (file.exists()) {
                    file.delete();
                }
            }
        });
    }
}
