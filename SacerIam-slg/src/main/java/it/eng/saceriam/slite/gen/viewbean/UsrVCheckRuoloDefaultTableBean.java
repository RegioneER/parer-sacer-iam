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

package it.eng.saceriam.slite.gen.viewbean;

import java.util.Iterator;

/**
 * ViewBean per la vista Usr_V_Check_Ruolo_Default
 *
 */
import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * TableBean per la tabella Usr_V_Check_Ruolo_Default
 *
 */
public class UsrVCheckRuoloDefaultTableBean
	extends AbstractBaseTable<UsrVCheckRuoloDefaultRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$ViewBeanWriter",
     * comments = "This class was generated by OraTool", date = "Thursday, 3 October 2013 15:05" )
     */

    public static UsrVCheckRuoloDefaultTableDescriptor TABLE_DESCRIPTOR = new UsrVCheckRuoloDefaultTableDescriptor();

    public UsrVCheckRuoloDefaultTableBean() {
	super();
    }

    protected UsrVCheckRuoloDefaultRowBean createRow() {
	return new UsrVCheckRuoloDefaultRowBean();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<UsrVCheckRuoloDefaultRowBean> getRowsIterator() {
	return iterator();
    }
}
