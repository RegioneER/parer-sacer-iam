package it.eng.saceriam.web.util;

import it.eng.spagoLite.db.base.BaseTableInterface;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gilioli_p
 */
public class NavigatorDetailBeanManager {

    static List<NavigatorDetailBean> navigatorDetailBeanStack;

    public NavigatorDetailBeanManager(List<NavigatorDetailBean> navigatorDetailBeanStack) {
        this.navigatorDetailBeanStack = navigatorDetailBeanStack;
    }

    public static List<NavigatorDetailBean> pushNavigatorDetailStack(BigDecimal idObjectDetail, String sourceList,
            BaseTableInterface<?> sourceTable, int level) {
        if (navigatorDetailBeanStack == null) {
            navigatorDetailBeanStack = new ArrayList<>();
        }
        navigatorDetailBeanStack.add(new NavigatorDetailBean(idObjectDetail, sourceList, sourceTable, level));

        return navigatorDetailBeanStack;
    }

    public static NavigatorDetailBean popNavigatorDetailStack() {
        NavigatorDetailBean last = null;
        if (navigatorDetailBeanStack != null && !navigatorDetailBeanStack.isEmpty()) {
            last = navigatorDetailBeanStack.remove(navigatorDetailBeanStack.size() - 1);
        }
        return last;
    }

    public static NavigatorDetailBean getLastNavigatorDetailStack() {
        NavigatorDetailBean last = null;
        if (navigatorDetailBeanStack != null && !navigatorDetailBeanStack.isEmpty()) {
            last = navigatorDetailBeanStack.get(navigatorDetailBeanStack.size() - 1);
        }
        return last;
    }

    public static void resetNavigatorDetailStack() {
        navigatorDetailBeanStack = null;
    }

    public static List<NavigatorDetailBean> getNavigatorDetailStack() {
        return navigatorDetailBeanStack;
    }
}
