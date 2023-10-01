package br.com.github.macgarcia.repository;

import br.com.github.macgarcia.util.FactoryMensagem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author macgarcia
 * @param <T>
 */
public class GenericDao<T extends EntityBase> {
    
    private static final String UNIDADE_PERSISTENCIA = "processaImagemPU";

    public EntityManager getEntityManager() {
        try {
            final EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIDADE_PERSISTENCIA);
            return emf.createEntityManager();
        } catch (Exception e) {
            FactoryMensagem.mensagemAlerta("Banco de dados fora do ar...");
            throw new InternalError(e.getMessage());
        }
    }

    public boolean salvarEntidade(T t) {
        final EntityManager manager = this.getEntityManager();
        try {
            manager.getTransaction().begin();
            if (t.getId() != null) {
                manager.merge(t);
            } else {
                manager.persist(t);
            }
            manager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            manager.clear();
            manager.close();
        }
    }

    public boolean apagarEntidade(final Class<T> classe, final Long id) {
        final EntityManager manager = this.getEntityManager();
        final T t = manager.find(classe, id);
        try {
            manager.getTransaction().begin();
            manager.remove(t);
            manager.getTransaction().commit();
            return true;
        } finally {
            manager.clear();
            manager.close();
        }
    }

    public T consultarPorId(final Class<T> classe, final Long id) {
        final EntityManager manager = this.getEntityManager();
        T t = null;
        try {
            t = manager.find(classe, id);
        } finally {
            manager.clear();
            manager.close();
        }
        return t;
    }

}
