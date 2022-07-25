package it.eng.saceriam.helper;

import it.eng.paginator.util.HibernateUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Consumer;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bonora_L
 */
@Stateless
@LocalBean
public class GenericHelper implements HelperInterface {

    private static final Logger logger = LoggerFactory.getLogger(GenericHelper.class);

    @PersistenceContext(unitName = "SacerIamPU")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> void detachEntity(T entity) {
        if (entity != null) {
            entityManager.detach(entity);
        }
    }

    public <T> T mergeEntity(T entity) {
        if (entity != null) {
            logger.debug("Persisting instance of class {}", entity.getClass().getSimpleName());
            return entityManager.merge(HibernateUtils.unproxyEntity(entity));
        }
        return entity;
    }

    @Override
    public <T> void insertEntity(T entity, boolean forceFlush) {
        if (entity != null) {
            logger.debug("Persisting instance of class {}", entity.getClass().getSimpleName());
            entityManager.persist(entity);
            if (forceFlush) {
                entityManager.flush();
            }
        }
    }

    @Override
    public <T> void removeEntity(T entity, boolean forceFlush) {
        if (entity != null) {
            logger.debug("Removing instance of class {}", entity.getClass().getSimpleName());
            entityManager.remove(entity);
            if (forceFlush) {
                entityManager.flush();
            }
        }
    }

    public <T> T findById(Class<T> entityClass, BigDecimal id) {
        return findById(entityClass, id.longValue());
    }

    @Override
    public <T> T findById(Class<T> entityClass, Serializable id) {
        logger.debug("Getting instance of class {} with id: {}", entityClass.getSimpleName(), id);
        try {
            T instance = entityManager.find(entityClass, id);
            logger.debug("Get successful");
            return instance;
        } catch (RuntimeException re) {
            logger.error("Get failed", re);
            throw re;
        }
    }

    public <T> T findByIdForceLazyField(Class<T> entityClass, Serializable id, Consumer<T> lazyLoaderFunction) {
        logger.debug("Getting instance of class {} with id: {}", entityClass.getSimpleName(), id);
        try {
            T instance = entityManager.find(entityClass, id);
            logger.debug("Get successful");
            lazyLoaderFunction.accept(instance);
            logger.debug("Loaded lazy fields");
            return instance;
        } catch (RuntimeException re) {
            logger.error("Get failed", re);
            throw re;
        }
    }

    public <T> T findByIdWithLock(Class<T> entityClass, BigDecimal id) {
        return findByIdWithLock(entityClass, id.longValue());
    }

    @Override
    public <T> T findByIdWithLock(Class<T> entityClass, Serializable id) {
        logger.debug("Getting instance of class {} with id: {}, with exclusive lock", entityClass.getSimpleName(), id);
        T instance = null;
        try {
            instance = entityManager.find(entityClass, id, LockModeType.PESSIMISTIC_WRITE);
            logger.debug("Get successful");
        } catch (LockTimeoutException lte) {
            logger.error(GenericHelper.class.getSimpleName() + " --- Impossibile acquisire il lock", lte);
        }
        return instance;
    }

    @Override
    public <T> T findViewById(Class<T> entityViewClass, Serializable id) {
        logger.debug("Getting instance of class {} with id: {}", entityViewClass.getSimpleName(), id);
        try {
            T instance = entityManager.find(entityViewClass, id);
            logger.debug("Get successful");
            return instance;
        } catch (RuntimeException re) {
            logger.error("Get failed", re);
            throw re;
        }
    }

    public void removeById(Class clazz, Long id) {
        removeEntity(findById(clazz, id), true);
    }
}
