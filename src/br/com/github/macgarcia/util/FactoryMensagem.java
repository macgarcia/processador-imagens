package br.com.github.macgarcia.util;

import javax.swing.JOptionPane;

/**
 *
 * @author macgarcia
 */
public class FactoryMensagem {

    private static final String ALERTA = "Alerta";
    private static final String ERRO = "Erro";
    private static final String INFORMACAO = "Informação";

    public static void mensagemOk(IndicacaoEnun indicacaoEnun) {
        exibirMensagemInformacao(indicacaoEnun.getMsg());
    }

    public static void mensagemOk(String informacao) {
        exibirMensagemInformacao(informacao);
    }

    public static void mensagemAlerta(IndicacaoEnun indicacaoEnun) {
        exibirMensagemAlerta(indicacaoEnun.getMsg());
    }

    public static void mensagemAlerta(String conteudo) {
        exibirMensagemAlerta(conteudo);
    }

    public static void mensagemErro(IndicacaoEnun indicacaoEnun) {
        exibirMensagemErro(indicacaoEnun.getMsg());
    }

    public static void mensagemErro(String erro) {
        exibirMensagemErro(erro);
    }

    public static int mensagemDeConfirmar(IndicacaoEnun indicacaoEnun) {
        return JOptionPane
                .showConfirmDialog(null, indicacaoEnun.getMsg(), "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION);
    }

    // Metodos core
    private static void exibirMensagemAlerta(final String alerta) {
        JOptionPane.showMessageDialog(null, alerta, ALERTA, JOptionPane.WARNING_MESSAGE);
    }

    private static void exibirMensagemErro(final String erro) {
        JOptionPane.showMessageDialog(null, erro, ERRO, JOptionPane.ERROR_MESSAGE);
    }

    private static void exibirMensagemInformacao(final String informacao) {
        JOptionPane.showMessageDialog(null, informacao, INFORMACAO, JOptionPane.INFORMATION_MESSAGE);

    }

}
