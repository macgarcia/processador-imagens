package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import br.com.github.macgarcia.processos.FiltroOpenCv;
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
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author macgarcia
 */
public class TelaEdicaoImagemService extends RegraSelecaoImagem {
    
    private String caminhoImnagemSelecionada;
    private Mat matrizImagemSelecionada;
    
    private FiltroOpenCv filtro;

    public TelaEdicaoImagemService(final JInternalFrame frame) {
        frame.setTitle("Edição de imagem");
        frame.setResizable(false);
    }
    
    public void acaoDosBotoes(final List<JButton> botoes, final JLabel lblImagem) {
        
        cancelarBotoes(botoes);
        
        int indice = 0;
        
        //Procurar
        botoes.get(indice++).addActionListener(ae -> {
            caminhoImnagemSelecionada = abrirCaixaDeSelexao();
            if (Objects.nonNull(caminhoImnagemSelecionada) && !caminhoImnagemSelecionada.isEmpty()) {
                construirMatrizDaImagemSelecionada();
                montarMatrizImagemNoLabel(matrizImagemSelecionada, lblImagem);
                ativarBotoes(botoes);
            } else {
                cancelarBotoes(botoes);
                lblImagem.setIcon(null);
            }
        });
        
        // Original
        botoes.get(indice++).addActionListener(ae -> {
            construirMatrizDaImagemSelecionada();
            montarMatrizImagemNoLabel(matrizImagemSelecionada, lblImagem);
        });
        
        // Tons de cinza
        botoes.get(indice++).addActionListener(ae -> {
            Mat tonsDeCinza = filtro.tonsDeCinza();
            montarMatrizImagemNoLabel(tonsDeCinza, lblImagem);
        });
        
        // Passa alta
        botoes.get(indice++).addActionListener(ae -> {
            Mat passaAlta = filtro.passaAlta();
            montarMatrizImagemNoLabel(passaAlta, lblImagem);
        });
        
        // Blur
        botoes.get(indice++).addActionListener(ae -> {
            Mat blur = filtro.blur();
            montarMatrizImagemNoLabel(blur, lblImagem);
        });
        
        // Suavizar
        botoes.get(indice++).addActionListener(ae -> {
            Mat suavizar = filtro.suavizar();
            montarMatrizImagemNoLabel(suavizar, lblImagem);
        });
        
        // Erosao
        botoes.get(indice++).addActionListener(ae -> {
            Mat erosao = filtro.erosao();
            montarMatrizImagemNoLabel(erosao, lblImagem);
        });
        
        // Saturada
        botoes.get(indice++).addActionListener(ae -> {
            Mat saturar = filtro.saturar();
            montarMatrizImagemNoLabel(saturar, lblImagem);
        });
        
        // Sobel
        botoes.get(indice++).addActionListener(ae -> {
            Mat sobel = filtro.sobel();
            montarMatrizImagemNoLabel(sobel, lblImagem);
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
        filtro = new FiltroOpenCv(matrizImagemSelecionada);
    }
}
