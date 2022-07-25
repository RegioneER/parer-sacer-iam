package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.PrfRuolo;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUsoRuoloDich;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Usr_Uso_Ruolo_Dich
 *
 */
public class UsrUsoRuoloDichRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Thursday, 26 April 2018 14:18" )
     */

    public static UsrUsoRuoloDichTableDescriptor TABLE_DESCRIPTOR = new UsrUsoRuoloDichTableDescriptor();

    public UsrUsoRuoloDichRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdUsoRuoloDich() {
        return getBigDecimal("id_uso_ruolo_dich");
    }

    public void setIdUsoRuoloDich(BigDecimal idUsoRuoloDich) {
        setObject("id_uso_ruolo_dich", idUsoRuoloDich);
    }

    public BigDecimal getIdDichAbilOrganiz() {
        return getBigDecimal("id_dich_abil_organiz");
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        setObject("id_dich_abil_organiz", idDichAbilOrganiz);
    }

    public BigDecimal getIdRuolo() {
        return getBigDecimal("id_ruolo");
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        setObject("id_ruolo", idRuolo);
    }

    public String getTiScopoRuolo() {
        return getString("ti_scopo_ruolo");
    }

    public void setTiScopoRuolo(String tiScopoRuolo) {
        setObject("ti_scopo_ruolo", tiScopoRuolo);
    }

    public BigDecimal getIdOrganizIamRuolo() {
        return getBigDecimal("id_organiz_iam_ruolo");
    }

    public void setIdOrganizIamRuolo(BigDecimal idOrganizIamRuolo) {
        setObject("id_organiz_iam_ruolo", idOrganizIamRuolo);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrUsoRuoloDich entity = (UsrUsoRuoloDich) obj;
        this.setIdUsoRuoloDich(new BigDecimal(entity.getIdUsoRuoloDich()));
        if (entity.getUsrDichAbilOrganiz() != null) {
            this.setIdDichAbilOrganiz(new BigDecimal(entity.getUsrDichAbilOrganiz().getIdDichAbilOrganiz()));

        }
        if (entity.getPrfRuolo() != null) {
            this.setIdRuolo(new BigDecimal(entity.getPrfRuolo().getIdRuolo()));

        }
        this.setTiScopoRuolo(entity.getTiScopoRuolo());
        if (entity.getUsrOrganizIam() != null) {
            this.setIdOrganizIamRuolo(new BigDecimal(entity.getUsrOrganizIam().getIdOrganizIam()));

        }
    }

    @Override
    public UsrUsoRuoloDich rowBeanToEntity() {
        UsrUsoRuoloDich entity = new UsrUsoRuoloDich();
        if (this.getIdUsoRuoloDich() != null) {
            entity.setIdUsoRuoloDich(this.getIdUsoRuoloDich().longValue());
        }
        if (this.getIdDichAbilOrganiz() != null) {
            if (entity.getUsrDichAbilOrganiz() == null) {
                entity.setUsrDichAbilOrganiz(new UsrDichAbilOrganiz());
            }
            entity.getUsrDichAbilOrganiz().setIdDichAbilOrganiz(this.getIdDichAbilOrganiz().longValue());
        }
        if (this.getIdRuolo() != null) {
            if (entity.getPrfRuolo() == null) {
                entity.setPrfRuolo(new PrfRuolo());
            }
            entity.getPrfRuolo().setIdRuolo(this.getIdRuolo().longValue());
        }
        entity.setTiScopoRuolo(this.getTiScopoRuolo());
        if (this.getIdOrganizIamRuolo() != null) {
            if (entity.getUsrOrganizIam() == null) {
                entity.setUsrOrganizIam(new UsrOrganizIam());
            }
            entity.getUsrOrganizIam().setIdOrganizIam(this.getIdOrganizIamRuolo().longValue());
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
