package br.com.github.macgarcia.service;

import br.com.github.macgarcia.util.Configuracao;
import br.com.github.macgarcia.util.FactoryMensagem;
import br.com.github.macgarcia.util.IndicacaoEnun;
import br.com.github.macgarcia.view.TelaCadastroImagem;
import br.com.github.macgarcia.view.TelaCapturarImagem;
import br.com.github.macgarcia.view.TelaComparacaoImagem;
import br.com.github.macgarcia.view.TelaListarImagem;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author macgarcia
 */
public class TelaInicialService {

    public TelaInicialService(final JFrame telaInicial) {
        telaInicial.setTitle("Processador de imagens");
        telaInicial.setResizable(false);
        telaInicial.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void configurarAcoesDosBotoesDaBarraDeMenu(final JMenuBar barraMenu,
            final JDesktopPane desktop) {

        menuArquivo(barraMenu.getMenu(0), desktop);
        menuProcessamentoImagem(barraMenu.getMenu(1), desktop);
    }

    private void menuArquivo(final JMenu menu, final JDesktopPane desktop) {
        //Cadastro de imagens
        //Arquivo -> Cadastro de imagem
        menu.getItem(0).addActionListener(ae -> {
            abrirTelaCadastroImagem(desktop);
        });

        //Arquivo -> Listar imagens
        menu.getItem(1).addActionListener(ae -> {
            abrirTelaListarImagem(desktop);
        });

        //Arquivo -> capturar Imagem
        menu.getItem(2).addActionListener(ae -> {
            abrirTelaCapturaImagem(desktop);
        });
    }

    private void menuProcessamentoImagem(final JMenu menu, final JDesktopPane desktop) {

        //Processamento de imagem -> Comparação por histograma
        menu.getItem(0).addActionListener(ae -> {
            abrirTelaComparacaoPorHistograma(desktop);
        });
    }

    private void abrirTelaCadastroImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaCadastroImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(IndicacaoEnun.JANELA_ABERTA);
            return;
        }
        TelaCadastroImagem tela = new TelaCadastroImagem();
        desktop.add(tela);
        Configuracao.setPosicaoInternalFrame(desktop, tela);
        tela.setVisible(true);
    }

    private void abrirTelaListarImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaListarImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(IndicacaoEnun.JANELA_ABERTA);
            return;
        }
        TelaListarImagem tela = new TelaListarImagem();
        desktop.add(tela);
        Configuracao.setPosicaoInternalFrame(desktop, tela);
        tela.setVisible(true);
    }

    private void abrirTelaCapturaImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaCapturarImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(IndicacaoEnun.JANELA_ABERTA);
            return;
        }
        TelaCapturarImagem tela = new TelaCapturarImagem();
        desktop.add(tela);
        Configuracao.setPosicaoInternalFrame(desktop, tela);
        tela.setVisible(true);

    }

    private void abrirTelaComparacaoPorHistograma(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaComparacaoImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(IndicacaoEnun.JANELA_ABERTA);
            return;
        }
        TelaComparacaoImagem tela = new TelaComparacaoImagem();
        desktop.add(tela);
        Configuracao.setPosicaoInternalFrame(desktop, tela);
        tela.setVisible(true);
    }

}
