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

package it.eng.saceriam.slite.gen.tablebean;

import java.util.Iterator;

import it.eng.spagoLite.db.base.table.AbstractBaseTable;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * TableBean per la tabella Org_Pagam_Fattura_Ente
 *
 */
public class OrgPagamFatturaEnteTableBean extends AbstractBaseTable<OrgPagamFatturaEnteRowBean> {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 29 June 2017 14:45" )
     */

    public static OrgPagamFatturaEnteTableDescriptor TABLE_DESCRIPTOR = new OrgPagamFatturaEnteTableDescriptor();

    public OrgPagamFatturaEnteTableBean() {
        super();
    }

    protected OrgPagamFatturaEnteRowBean createRow() {
        return new OrgPagamFatturaEnteRowBean();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    @Deprecated
    public Iterator<OrgPagamFatturaEnteRowBean> getRowsIterator() {
        return iterator();
    }
}
