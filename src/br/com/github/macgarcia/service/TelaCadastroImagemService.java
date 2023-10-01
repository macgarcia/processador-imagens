package br.com.github.macgarcia.service;

import br.com.github.macgarcia.componente.RegraSelecaoImagem;
import br.com.github.macgarcia.modelo.Imagem;
import br.com.github.macgarcia.qrcode.ProcessarQrCode;
import br.com.github.macgarcia.repository.ImagemRepository;
import br.com.github.macgarcia.util.FactoryMensagem;
import br.com.github.macgarcia.util.GeradorHistograma;
import br.com.github.macgarcia.util.IndicacaoEnun;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author macgarcia
 */
public class TelaCadastroImagemService extends RegraSelecaoImagem {

    private final ImagemRepository dao;

    public TelaCadastroImagemService(final JInternalFrame frame) {
        frame.setTitle("Cadastro de imagem");
        frame.setResizable(false);
        dao = new ImagemRepository();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void limparCampos(final List<JTextField> camposTextos) {
        camposTextos.forEach(e -> e.setText(""));
    }

    public void acaoDosBotoes(final List<JButton> botoes, final List<JTextField> camposTextos,
            final JLabel lblImagemSelecionada) {

        //Selecionar
        botoes.get(0).addActionListener(ae -> {
            acaoSelecionar(camposTextos.get(1));
            carregarImagemNoPanel(camposTextos.get(1), lblImagemSelecionada);
        });

        //Salvar
        botoes.get(1).addActionListener(ae -> {

            final String nome = camposTextos.get(0).getText();
            final String caminho = camposTextos.get(1).getText();

            boolean validou = validarCampos(nome, caminho);
            if (!validou) {
                return;
            }

            GeradorHistograma gh = new GeradorHistograma();

            File file = new File(caminho);

            try {
                final byte[] imagemEmBytes = Files.readAllBytes(file.toPath());

                Mat mat = Highgui.imread(caminho);
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
                Long histogramaSomado = gh.criarHistograma(mat);             

                Imagem novaImagem = new Imagem(nome, histogramaSomado, imagemEmBytes);

                final boolean salvou = salvarImagem(novaImagem);
                if (salvou) {
                    FactoryMensagem.mensagemOk(IndicacaoEnun.REGISTRO_SALVO);
                    limparTela(camposTextos, lblImagemSelecionada);
                } else {
                    FactoryMensagem.mensagemOk(IndicacaoEnun.REGISTRO_NAO_SALVO);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        });
    }

    private void acaoSelecionar(final JTextField txtLocal) {
        final String caminhoDoArquivo = abrirCaixaDeSelexao();
        txtLocal.setText(caminhoDoArquivo);
    }

    private void carregarImagemNoPanel(final JTextField txtLocal,
            final JLabel lblImagemSelecionado) {
        mostrarImagemNoObjeto(txtLocal.getText(), lblImagemSelecionado);
    }

    private boolean salvarImagem(final Imagem imagem) {
        return dao.salvarEntidade(imagem);
    }

    private void limparTela(final List<JTextField> camposTextos,
            final JLabel lblImagemSelecionada) {
        this.limparCampos(camposTextos);
        lblImagemSelecionada.setIcon(null);
    }

    private boolean validarCampos(String nome, String caminho) {
        if (nome.isEmpty()) {
            FactoryMensagem.mensagemAlerta("Preencha o nome do arquivo");
            return false;
        }

        if (caminho.isEmpty()) {
            FactoryMensagem.mensagemAlerta("Selecione uma foto");
            return false;
        }
        return true;
    }
}
