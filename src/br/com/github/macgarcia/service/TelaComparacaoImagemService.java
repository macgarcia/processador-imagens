package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import br.com.github.macgarcia.processos.GeradorHistograma;
import com.gituhub.macgarcia.contrutor.tela.interna.core.FactoryMensagem;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public class TelaComparacaoImagemService extends RegraSelecaoImagem {

    private Mat mat1, mat2;
    private List<JTextField> campos;
    private double[] histogramaImagem01, histogramaImagem02;

    public TelaComparacaoImagemService(final JInternalFrame tela) {
        tela.setTitle("Comparação por histograma");
        tela.setResizable(false);
    }

    public void desabilitarCamposDeTexto(List<JTextField> campos) {
        campos.forEach(campo -> campo.setEditable(false));
        this.campos = campos;
    }

    public void acoesDosBotoes(List<JButton> botoes, JLabel... label) {

        // Selecionar foto 01
        botoes.get(0).addActionListener(ae -> {
            String caminhoFoto01 = abrirCaixaDeSelexao();
            if (!caminhoFoto01.isEmpty()) {
                mat1 = criarMatrizDaImagem(caminhoFoto01);
                setDadosImagem01(caminhoFoto01);
                montarMatrizImagemNoLabel(mat1, label[0]);
            } else {
                mat1 = null;
                histogramaImagem01 = null;
                label[0].setIcon(null);
                campos.get(0).setText(null);
                campos.get(1).setText(null);
            }
        });

        // Selecionar foto 02
        botoes.get(1).addActionListener(ae -> {
            String caminhoFoto02 = abrirCaixaDeSelexao();
            if (!caminhoFoto02.isEmpty()) {
                mat2 = criarMatrizDaImagem(caminhoFoto02);
                setDadosImagem02(caminhoFoto02);
                montarMatrizImagemNoLabel(mat2, label[1]);
            } else {
                mat2 = null;
                histogramaImagem02 = null;
                label[1].setIcon(null);
                campos.get(2).setText(null);
                campos.get(3).setText(null);
            }
        });

        // Executar processamento
        botoes.get(2).addActionListener(ae -> {
            validar();
        });
    }

    private Mat criarMatrizDaImagem(String caminhoImagem) {
        Mat mat = Imgcodecs.imread(caminhoImagem);
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
        return mat;
    }

    private void setDadosImagem01(String caminhoImagem) {
        File file = new File(caminhoImagem);
        campos.get(0).setText(file.getAbsolutePath());
        histogramaImagem01 = GeradorHistograma.criarHistogramaNormalizado(mat1);
        campos.get(1).setText(valorHistograma(histogramaImagem01));
    }

    private void setDadosImagem02(String caminhoImagem) {
        File file = new File(caminhoImagem);
        campos.get(2).setText(file.getAbsolutePath());
        histogramaImagem02 = GeradorHistograma.criarHistogramaNormalizado(mat2);
        campos.get(3).setText(valorHistograma(histogramaImagem02));
    }

    // Corrigir
    private String valorHistograma(double[] histograma) {
        int total = 0;
        for (double d : histograma) {
            total += d;
        }
        return new DecimalFormat(new String(new char[15]).replace('\0', '0')).format(total);
    }

    private void validar() {
        if (Objects.nonNull(mat1) && Objects.nonNull(mat1)) {
            if (histogramaImagem01.equals(histogramaImagem02)) {
                FactoryMensagem.mensagemOk("Imagens são iguais.");
            } else {
                FactoryMensagem.mensagemAlerta("Imagens são diferentes.");
            }
        } else {
            FactoryMensagem.mensagemAlerta("Selecione as imagens...");
        }
    }
}
