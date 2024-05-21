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

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import it.eng.spagoLite.db.oracle.bean.column.ColumnDescriptor;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * @author Sloth
 *
 *         Bean per la tabella Prf_Ruolo_Categoria
 *
 */
public class PrfRuoloCategoriaTableDescriptor extends TableDescriptor {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Wednesday, 1 April 2020 17:58" )
     */

    public static final String SELECT = "Select * from Prf_Ruolo_Categoria /**/";
    public static final String TABLE_NAME = "Prf_Ruolo_Categoria";
    public static final String COL_ID_RUOLO_CATEGORIA = "id_ruolo_categoria";
    public static final String COL_ID_RUOLO = "id_ruolo";
    public static final String COL_TI_CATEG_RUOLO = "ti_categ_ruolo";

    private static Map<String, ColumnDescriptor> map = new LinkedHashMap<String, ColumnDescriptor>();

    static {
        map.put(COL_ID_RUOLO_CATEGORIA, new ColumnDescriptor(COL_ID_RUOLO_CATEGORIA, Types.DECIMAL, 22, true));
        map.put(COL_ID_RUOLO, new ColumnDescriptor(COL_ID_RUOLO, Types.DECIMAL, 22, false));
        map.put(COL_TI_CATEG_RUOLO, new ColumnDescriptor(COL_TI_CATEG_RUOLO, Types.VARCHAR, 20, false));
    }

    public Map<String, ColumnDescriptor> getColumnMap() {
        return map;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getStatement() {
        return SELECT;
    }

}
