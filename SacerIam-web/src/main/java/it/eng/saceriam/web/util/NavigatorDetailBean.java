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

import it.eng.spagoLite.db.base.BaseTableInterface;

/**
 *
 * @author gilioli_p
 */
public class NavigatorDetailBean {

    BigDecimal idObject;
    String sourceList;
    BaseTableInterface<?> sourceTable;
    int level = 1;

    public NavigatorDetailBean(BigDecimal idObject, String sourceList, BaseTableInterface<?> sourceTable, int level) {
        this.idObject = idObject;
        this.sourceTable = sourceTable;
        this.sourceList = sourceList;
        this.level = level;
    }

    public BigDecimal getIdObject() {
        return idObject;
    }

    public void setIdObject(BigDecimal idObject) {
        this.idObject = idObject;
    }

    public String getSourceList() {
        return sourceList;
    }

    public void setSourceList(String sourceList) {
        this.sourceList = sourceList;
    }

    public BaseTableInterface<?> getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(BaseTableInterface<?> sourceTable) {
        this.sourceTable = sourceTable;
    }

    public void addLevel() {
        this.level++;
    }

    public int getLevel() {
        return level;
    }

}
