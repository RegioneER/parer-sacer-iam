package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.IamParamApplic;
import it.eng.saceriam.entity.IamValoreParamApplic;
import it.eng.saceriam.entity.OrgAmbienteEnteConvenz;
import it.eng.saceriam.entity.OrgEnteSiam;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Iam_Valore_Param_Applic
 *
 */
public class IamValoreParamApplicRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 13 December 2018 10:37" )
     */

    public static IamValoreParamApplicTableDescriptor TABLE_DESCRIPTOR = new IamValoreParamApplicTableDescriptor();

    public IamValoreParamApplicRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdValoreParamApplic() {
        return getBigDecimal("id_valore_param_applic");
    }

    public void setIdValoreParamApplic(BigDecimal idValoreParamApplic) {
        setObject("id_valore_param_applic", idValoreParamApplic);
    }

    public BigDecimal getIdParamApplic() {
        return getBigDecimal("id_param_applic");
    }

    public void setIdParamApplic(BigDecimal idParamApplic) {
        setObject("id_param_applic", idParamApplic);
    }

    public String getTiAppart() {
        return getString("ti_appart");
    }

    public void setTiAppart(String tiAppart) {
        setObject("ti_appart", tiAppart);
    }

    public String getDsValoreParamApplic() {
        return getString("ds_valore_param_applic");
    }

    public void setDsValoreParamApplic(String dsValoreParamApplic) {
        setObject("ds_valore_param_applic", dsValoreParamApplic);
    }

    public BigDecimal getIdAmbienteEnteConvenz() {
        return getBigDecimal("id_ambiente_ente_convenz");
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        setObject("id_ambiente_ente_convenz", idAmbienteEnteConvenz);
    }

    public BigDecimal getIdEnteConvenz() {
        return getBigDecimal("id_ente_convenz");
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        setObject("id_ente_convenz", idEnteConvenz);
    }

    @Override
    public void entityToRowBean(Object obj) {
        IamValoreParamApplic entity = (IamValoreParamApplic) obj;
        this.setIdValoreParamApplic(new BigDecimal(entity.getIdValoreParamApplic()));
        if (entity.getIamParamApplic() != null) {
            this.setIdParamApplic(new BigDecimal(entity.getIamParamApplic().getIdParamApplic()));
        }
        this.setTiAppart(entity.getTiAppart());
        this.setDsValoreParamApplic(entity.getDsValoreParamApplic());
        if (entity.getOrgAmbienteEnteConvenz() != null) {
            this.setIdAmbienteEnteConvenz(
                    new BigDecimal(entity.getOrgAmbienteEnteConvenz().getIdAmbienteEnteConvenz()));
        }
        if (entity.getOrgEnteSiam() != null) {
            this.setIdEnteConvenz(new BigDecimal(entity.getOrgEnteSiam().getIdEnteSiam()));
        }
    }

    @Override
    public IamValoreParamApplic rowBeanToEntity() {
        IamValoreParamApplic entity = new IamValoreParamApplic();
        if (this.getIdValoreParamApplic() != null) {
            entity.setIdValoreParamApplic(this.getIdValoreParamApplic().longValue());
        }
        if (this.getIdParamApplic() != null) {
            if (entity.getIamParamApplic() == null) {
                entity.setIamParamApplic(new IamParamApplic());
            }
            entity.getIamParamApplic().setIdParamApplic(this.getIdParamApplic().longValue());
        }
        entity.setTiAppart(this.getTiAppart());
        entity.setDsValoreParamApplic(this.getDsValoreParamApplic());
        if (this.getIdAmbienteEnteConvenz() != null) {
            if (entity.getOrgAmbienteEnteConvenz() == null) {
                entity.setOrgAmbienteEnteConvenz(new OrgAmbienteEnteConvenz());
            }
            entity.getOrgAmbienteEnteConvenz().setIdAmbienteEnteConvenz(this.getIdAmbienteEnteConvenz().longValue());
        }
        if (this.getIdEnteConvenz() != null) {
            if (entity.getOrgEnteSiam() == null) {
                entity.setOrgEnteSiam(new OrgEnteSiam());
            }
            entity.getOrgEnteSiam().setIdEnteSiam(this.getIdEnteConvenz().longValue());
        }
        return entity;
    }

    // gestione della paginazione
    public void setRownum(Integer rownum) {
        setObject("rownum", rownum);
    }

    public Integer getRownum() {
        return Integer.parseInt(getObject("rownum").toString());
    }

    public void setRnum(Integer rnum) {
        setObject("rnum", rnum);
    }

    public Integer getRnum() {
        return Integer.parseInt(getObject("rnum").toString());
    }

    public void setNumrecords(Integer numRecords) {
        setObject("numrecords", numRecords);
    }

    public Integer getNumrecords() {
        return Integer.parseInt(getObject("numrecords").toString());
    }

}
