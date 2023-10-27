package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import br.com.github.macgarcia.processos.GeradorHistograma;
import com.gituhub.macgarcia.contrutor.tela.interna.core.FactoryMensagem;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public class TelaComparacaoImagemService extends RegraSelecaoImagem {

    private String caminhoFoto01, caminhoFoto02;

    public TelaComparacaoImagemService(final JInternalFrame tela) {
        tela.setTitle("Comparação por histograma");
        tela.setResizable(false);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void acoesDosBotoes(List<JButton> botoes, JLabel... label) {

        //Selecionar foto 01
        botoes.get(0).addActionListener(ae -> {
            caminhoFoto01 = abrirCaixaDeSelexao();
            if (!caminhoFoto01.isEmpty()) {
                mostrarImagemNoObjeto(caminhoFoto01, label[0]);
            } else {
                label[0].setIcon(null);
            }
        });

        //Selecionar foto 02
        botoes.get(1).addActionListener(ae -> {
            caminhoFoto02 = abrirCaixaDeSelexao();
            if (!caminhoFoto02.isEmpty()) {
                mostrarImagemNoObjeto(caminhoFoto02, label[1]);
            } else {
                label[1].setIcon(null);
            }
        });

        //Executar processamento
        botoes.get(2).addActionListener(ae -> {
            validarCampos();
            executarComparacaoPorHistograma();
        });
    }

    private void validarCampos() {
        if (caminhoFoto01 == null || caminhoFoto01.isEmpty()) {
            FactoryMensagem.mensagemAlerta("Selecione a foto no campo 01");
            return;
        }

        if (caminhoFoto02 == null || caminhoFoto02.isEmpty()) {
            FactoryMensagem.mensagemAlerta("Selecione a foto no campo 02");
            return;
        }
    }

    private void executarComparacaoPorHistograma() {
        Mat mat01 = Imgcodecs.imread(caminhoFoto01);
        Imgproc.cvtColor(mat01, mat01, Imgproc.COLOR_RGB2GRAY);

        Mat mat02 = Imgcodecs.imread(caminhoFoto02);
        Imgproc.cvtColor(mat02, mat02, Imgproc.COLOR_RGB2GRAY);
        
        GeradorHistograma geradorHistograma = new GeradorHistograma();
        boolean valido = geradorHistograma.compararHistogramas(mat01, mat02);
        if (valido) {
            FactoryMensagem.mensagemOk("Imagens iguais");
        } else {
            FactoryMensagem.mensagemAlerta("Imagens diferentes");
        }
    }
}
