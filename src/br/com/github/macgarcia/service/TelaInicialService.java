package br.com.github.macgarcia.service;

import br.com.github.macgarcia.view.TelaCadastroImagem;
import br.com.github.macgarcia.view.TelaCapturarImagem;
import br.com.github.macgarcia.view.TelaComparacaoImagem;
import br.com.github.macgarcia.view.TelaEdicaoImagem;
import br.com.github.macgarcia.view.TelaListarImagem;
import com.gituhub.macgarcia.contrutor.tela.interna.core.Configuracao;
import com.gituhub.macgarcia.contrutor.tela.interna.core.FactoryMensagem;
import com.gituhub.macgarcia.contrutor.tela.interna.core.FactoryTela;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import org.opencv.core.Core;

/**
 *
 * @author macgarcia
 */
public class TelaInicialService {

    private final String JANELA_ABERTA = "Janela já esta aberta na area de trabalho.";

    public TelaInicialService(final JFrame telaInicial) {
        telaInicial.setTitle("Processador de imagens");
        telaInicial.setResizable(false);
        telaInicial.setExtendedState(JFrame.MAXIMIZED_BOTH);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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

        //Processamento de imagem -> Edição de imagem
        menu.getItem(1).addActionListener(ae -> {
            abrirTelaDeEdicaoDeImagem(desktop);
        });
    }

    private void abrirTelaCadastroImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaCadastroImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(JANELA_ABERTA);
            return;
        }
        FactoryTela.criarTela(TelaCadastroImagem.class, desktop);
    }

    private void abrirTelaListarImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaListarImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(JANELA_ABERTA);
            return;
        }
        FactoryTela.criarTela(TelaListarImagem.class, desktop);
    }

    private void abrirTelaCapturaImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaCapturarImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(JANELA_ABERTA);
            return;
        }
        FactoryTela.criarTela(TelaCapturarImagem.class, desktop);
    }

    private void abrirTelaComparacaoPorHistograma(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaComparacaoImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(JANELA_ABERTA);
            return;
        }
        FactoryTela.criarTela(TelaComparacaoImagem.class, desktop);
    }

    private void abrirTelaDeEdicaoDeImagem(final JDesktopPane desktop) {
        boolean aberta = Configuracao.verificarJanelaAberta(desktop, TelaEdicaoImagem.class);
        if (aberta) {
            FactoryMensagem.mensagemAlerta(JANELA_ABERTA);
            return;
        }
        FactoryTela.criarTela(TelaEdicaoImagem.class, desktop);
    }

}
