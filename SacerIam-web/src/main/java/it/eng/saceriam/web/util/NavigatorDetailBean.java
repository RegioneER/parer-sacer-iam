package it.eng.saceriam.web.util;

import it.eng.spagoLite.db.base.BaseTableInterface;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
