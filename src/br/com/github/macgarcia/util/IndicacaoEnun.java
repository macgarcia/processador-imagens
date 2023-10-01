package br.com.github.macgarcia.util;

/**
 *
 * @author macgarcia
 */
public enum IndicacaoEnun {
    
    JANELA_ABERTA("Janela já esta aberta na area de trabalho."),
    REGISTRO_SALVO("Registro salvo com sucesso."),
    REGISTRO_NAO_SALVO("Erro ao tentar gravar o registro."),
    APAGAR_REGISTRO("Deseja apagar o registro selecionado?"),
    REGISTRO_EXCLUIDO("Registro excluído com sucesso.");
    
    private final String msg;

    IndicacaoEnun(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
}
