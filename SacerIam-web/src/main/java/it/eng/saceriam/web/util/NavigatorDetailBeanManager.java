/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.web.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.eng.spagoLite.db.base.BaseTableInterface;

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
