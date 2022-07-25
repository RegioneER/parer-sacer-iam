package it.eng.saceriam.web.util;

import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.BaseTableInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Proxy;

/*
 * @author Quaranta_M
 * 
 * La trasformazione funziona purché siano soddisatte le seguenti condizioni:
 *  - nomeRowBean = nome Entity + "RowBean" (NB: Dali tronca una eventuale 's' posta alla fine del nome della entity)
 *  - nomeTableBean = nome Entity + "TableBean"
 *  - i nomi delle variabili degli oggetti referenziati dalle entity (e le PK di tali oggetti) hanno la parte finale del nome uguale alle FK dei rowBean
 *  - le PK Embeddable nelle entity devono avere il nome "id"
 * 
 */
public class Transform {

    private static final Logger log = LoggerFactory.getLogger(Transform.class);

    static final String[] excludedProps = new String[] { "class", "tableDescriptor", "iteratorColumnContained",
            "numrecords", "rnum", "rownum" };

    public static List tableBean2Entities(BaseTableInterface tableBean, Class c)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        List entities = new ArrayList();
        for (Object rowBean : tableBean) {
            entities.add(Transform.rowBean2Entity(rowBean));
        }
        return entities;
    }

    /**
     * Metodo di trasformazione da lista di entity a tableBean
     *
     * @param entities
     *            la lista di entity da trasformare
     * 
     * @return il rowBean trasformato
     * 
     * @throws ClassNotFoundException
     *             errore generico
     * @throws NoSuchMethodException
     *             errore generico
     * @throws InstantiationException
     *             errore generico
     * @throws IllegalAccessException
     *             errore generico
     * @throws IllegalArgumentException
     *             errore generico
     * @throws InvocationTargetException
     *             errore generico
     */
    public static AbstractBaseTable entities2TableBean(List entities)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String entityClassName = entities.get(0).getClass().getName()
                .substring(entities.get(0).getClass().getName().lastIndexOf("."));
        String tbClassName = null;
        if (entities.get(0).getClass().getName().startsWith(Constants.ENTITY_PACKAGE_NAME)
                || entities.get(0).getClass().getName().startsWith(Constants.GRANTED_ENTITY_PACKAGE_NAME)) {
            tbClassName = Constants.ROWBEAN_PACKAGE_NAME + entityClassName + "TableBean";
        } else {
            tbClassName = Constants.VIEWROWBEAN_PACKAGE_NAME + entityClassName + "TableBean";
        }
        Class clazz = Class.forName(tbClassName);
        Constructor constructor = clazz.getConstructor();
        AbstractBaseTable tableBean = (AbstractBaseTable) constructor.newInstance();
        for (Object entity : entities) {
            tableBean.add(Transform.entity2RowBean(entity));
        }
        return tableBean;
    }

    /**
     * Metodo di trasformazione da entity a rowBean
     *
     * @param entity
     *            oggetto da trasformare
     * 
     * @return il rowBean trasformato
     * 
     * @throws ClassNotFoundException
     *             errore generico
     * @throws NoSuchMethodException
     *             errore generico
     * @throws InstantiationException
     *             errore generico
     * @throws IllegalAccessException
     *             errore generico
     * @throws IllegalArgumentException
     *             errore generico
     * @throws InvocationTargetException
     *             errore generico
     */
    public static BaseRowInterface entity2RowBean(Object entity) throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String entityClassName = getClassName(entity);
        String rbClassName = null;
        if (entity.getClass().getName().startsWith(Constants.ENTITY_PACKAGE_NAME)
                || entity.getClass().getName().startsWith(Constants.GRANTED_ENTITY_PACKAGE_NAME)) {
            rbClassName = Constants.ROWBEAN_PACKAGE_NAME + "." + entityClassName + "RowBean";
        } else {
            rbClassName = Constants.VIEWROWBEAN_PACKAGE_NAME + "." + entityClassName + "RowBean";
        }
        Class clazz = Class.forName(rbClassName);
        Constructor constructor = clazz.getConstructor();
        Object rowBean = constructor.newInstance();
        ((JEEBaseRowInterface) rowBean).entityToRowBean(entity);
        return (BaseRowInterface) rowBean;
    }

    /**
     * Metodo di trasformazione da rowBean a entity.
     *
     * <strong>UTILIZZARE ESCLUSIVAMENTE IN CASO DI NUOVI INSERIMENTI (PERSIST). NON UTILIZZARE IN CASO DI
     * UPDATE</strong>
     *
     * @param rowBean
     *            bean da trasformare
     * 
     * @return l'entity trasformata
     */
    public static Object rowBean2Entity(Object rowBean) {
        return ((JEEBaseRowInterface) rowBean).rowBeanToEntity();
    }

    private static String getClassName(Object obj) {
        final String proxySuffix = "_$$";
        String className = obj.getClass().getSimpleName();
        if (Proxy.isProxyClass(obj.getClass()) || className.contains(proxySuffix)) {
            className = className.substring(0, className.indexOf(proxySuffix));
        }
        return className;
    }
}
