package br.com.github.macgarcia.componente;

import br.com.github.macgarcia.modelo.Imagem;
import br.com.github.macgarcia.repository.ImagemRepository;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author macgarcia
 */
public class ModeloTabelaListarImagem extends AbstractTableModel {

    private static final int NUMERO_DE_COLUNAS = 4;

    private final ImagemRepository dao;

    private List<Imagem> imagens;

    public ModeloTabelaListarImagem() {
        this.dao = new ImagemRepository();
        buscarImagens();
    }

    @Override
    public int getRowCount() {
        return imagens.size();
    }

    @Override
    public int getColumnCount() {
        return NUMERO_DE_COLUNAS;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Nome da foto";
            case 2:
                return "Histograma";
            case 3:
                return "Conteudo";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Imagem img = imagens.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return img.getId();
            case 1:
                return img.getNomeFoto();
            case 2:
                return img.getHistograma();
            case 3:
                return "Imagem";
            default:
                return "";
        }
    }
    
    public Imagem getImagem(final int linha) {
        return imagens.get(linha);
    }

    private void buscarImagens() {
        imagens = dao.buscarImagens();
    }
    
}
