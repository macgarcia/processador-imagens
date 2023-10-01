package br.com.github.macgarcia.repository;

import br.com.github.macgarcia.modelo.Imagem;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author macgarcia
 */
public class ImagemRepository extends GenericDao<Imagem> {
    
    private final String QUERY_BUSCAR_IMAGENS = "select i from Imagem i order by id asc";

    public List<Imagem> buscarImagens() {
        final EntityManager manager = getEntityManager();
        TypedQuery<Imagem> query = manager.createQuery(QUERY_BUSCAR_IMAGENS, Imagem.class);
        return query.getResultList();
    }
    
}
