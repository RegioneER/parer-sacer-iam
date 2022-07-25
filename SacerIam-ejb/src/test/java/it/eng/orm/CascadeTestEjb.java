package it.eng.orm;

import it.eng.orm.cascade.nessuno.LogAgenteForTest;
import it.eng.orm.cascade.nessuno.UsrUserForTest;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.function.Consumer;

@Stateless
public class CascadeTestEjb {

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    public void persist(Object entity) {
        System.out.println("INSERTING " + entity.getClass().getSimpleName());
        entityManager.persist(entity);
    }

    public void removeById(Class clazz, Long id) {
        System.out.println("DELETING " + clazz.getSimpleName() + " ID " + id);
        final Object entity = entityManager.find(clazz, id);
        if (entity != null) {
            entityManager.remove(entity);
            System.out.println("DELETING ... ");
        }
    }

    public <T> T findById(Long id, Class<T> clazz, Consumer<T> lazyLoaderFunction) {
        final T instance = entityManager.find(clazz, id);
        if (lazyLoaderFunction != null && instance != null) {
            lazyLoaderFunction.accept(instance);
        }
        return instance;
    }

    public <T> T findById(Long id, Class<T> clazz) {
        return findById(id, clazz, null);
    }

    public <T> T merge(T entity) {
        System.out.println("MERGING " + entity.getClass().getSimpleName());
        return entityManager.merge(entity);
    }
}
