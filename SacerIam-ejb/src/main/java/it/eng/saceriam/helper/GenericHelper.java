/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.helper;

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

import it.eng.paginator.util.HibernateUtils;

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

    public <T> T findByIdForceLazyField(Class<T> entityClass, Serializable id,
	    Consumer<T> lazyLoaderFunction) {
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
	logger.debug("Getting instance of class {} with id: {}, with exclusive lock",
		entityClass.getSimpleName(), id);
	T instance = null;
	try {
	    instance = entityManager.find(entityClass, id, LockModeType.PESSIMISTIC_WRITE);
	    logger.debug("Get successful");
	} catch (LockTimeoutException lte) {
	    logger.error(GenericHelper.class.getSimpleName() + " --- Impossibile acquisire il lock",
		    lte);
	}
	return instance;
    }

    @Override
    public <T> T findViewById(Class<T> entityViewClass, Serializable id) {
	logger.debug("Getting instance of class {} with id: {}", entityViewClass.getSimpleName(),
		id);
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
