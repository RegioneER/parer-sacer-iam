package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.OrgAppartCollegEnti;
import it.eng.saceriam.entity.OrgSuptEsternoEnteConvenz;
import it.eng.saceriam.entity.OrgVigilEnteProdut;
import it.eng.saceriam.entity.UsrDichAbilOrganiz;
import it.eng.saceriam.entity.UsrOrganizIam;
import it.eng.saceriam.entity.UsrUsoUserApplic;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;

/**
 * RowBean per la tabella Usr_Dich_Abil_Organiz
 *
 */
public class UsrDichAbilOrganizRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 15 April 2019 15:50" )
     */

    public static UsrDichAbilOrganizTableDescriptor TABLE_DESCRIPTOR = new UsrDichAbilOrganizTableDescriptor();

    public UsrDichAbilOrganizRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdDichAbilOrganiz() {
        return getBigDecimal("id_dich_abil_organiz");
    }

    public void setIdDichAbilOrganiz(BigDecimal idDichAbilOrganiz) {
        setObject("id_dich_abil_organiz", idDichAbilOrganiz);
    }

    public BigDecimal getIdUsoUserApplic() {
        return getBigDecimal("id_uso_user_applic");
    }

    public void setIdUsoUserApplic(BigDecimal idUsoUserApplic) {
        setObject("id_uso_user_applic", idUsoUserApplic);
    }

    public String getTiScopoDichAbilOrganiz() {
        return getString("ti_scopo_dich_abil_organiz");
    }

    public void setTiScopoDichAbilOrganiz(String tiScopoDichAbilOrganiz) {
        setObject("ti_scopo_dich_abil_organiz", tiScopoDichAbilOrganiz);
    }

    public BigDecimal getIdOrganizIam() {
        return getBigDecimal("id_organiz_iam");
    }

    public void setIdOrganizIam(BigDecimal idOrganizIam) {
        setObject("id_organiz_iam", idOrganizIam);
    }

    public BigDecimal getIdAppartCollegEnti() {
        return getBigDecimal("id_appart_colleg_enti");
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        setObject("id_appart_colleg_enti", idAppartCollegEnti);
    }

    public BigDecimal getIdSuptEstEnteConvenz() {
        return getBigDecimal("id_supt_est_ente_convenz");
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        setObject("id_supt_est_ente_convenz", idSuptEstEnteConvenz);
    }

    public BigDecimal getIdVigilEnteProdut() {
        return getBigDecimal("id_vigil_ente_produt");
    }

    public void setIdVigilEnteProdut(BigDecimal idVigilEnteProdut) {
        setObject("id_vigil_ente_produt", idVigilEnteProdut);
    }

    public String getDsCausaleDich() {
        return getString("ds_causale_dich");
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        setObject("ds_causale_dich", dsCausaleDich);
    }

    @Override
    public void entityToRowBean(Object obj) {
        UsrDichAbilOrganiz entity = (UsrDichAbilOrganiz) obj;
        this.setIdDichAbilOrganiz(new BigDecimal(entity.getIdDichAbilOrganiz()));
        if (entity.getUsrUsoUserApplic() != null) {
            this.setIdUsoUserApplic(new BigDecimal(entity.getUsrUsoUserApplic().getIdUsoUserApplic()));

        }
        this.setTiScopoDichAbilOrganiz(entity.getTiScopoDichAbilOrganiz());
        if (entity.getUsrOrganizIam() != null) {
            this.setIdOrganizIam(new BigDecimal(entity.getUsrOrganizIam().getIdOrganizIam()));

        }
        if (entity.getOrgAppartCollegEnti() != null) {
            this.setIdAppartCollegEnti(new BigDecimal(entity.getOrgAppartCollegEnti().getIdAppartCollegEnti()));

        }
        if (entity.getOrgSuptEsternoEnteConvenz() != null) {
            this.setIdSuptEstEnteConvenz(
                    new BigDecimal(entity.getOrgSuptEsternoEnteConvenz().getIdSuptEstEnteConvenz()));

        }
        if (entity.getOrgVigilEnteProdut() != null) {
            this.setIdVigilEnteProdut(new BigDecimal(entity.getOrgVigilEnteProdut().getIdVigilEnteProdut()));

        }
        this.setDsCausaleDich(entity.getDsCausaleDich());
    }

    @Override
    public UsrDichAbilOrganiz rowBeanToEntity() {
        UsrDichAbilOrganiz entity = new UsrDichAbilOrganiz();
        if (this.getIdDichAbilOrganiz() != null) {
            entity.setIdDichAbilOrganiz(this.getIdDichAbilOrganiz().longValue());
        }
        if (this.getIdUsoUserApplic() != null) {
            if (entity.getUsrUsoUserApplic() == null) {
                entity.setUsrUsoUserApplic(new UsrUsoUserApplic());
            }
            entity.getUsrUsoUserApplic().setIdUsoUserApplic(this.getIdUsoUserApplic().longValue());
        }
        entity.setTiScopoDichAbilOrganiz(this.getTiScopoDichAbilOrganiz());
        if (this.getIdOrganizIam() != null) {
            if (entity.getUsrOrganizIam() == null) {
                entity.setUsrOrganizIam(new UsrOrganizIam());
            }
            entity.getUsrOrganizIam().setIdOrganizIam(this.getIdOrganizIam().longValue());
        }
        if (this.getIdAppartCollegEnti() != null) {
            if (entity.getOrgAppartCollegEnti() == null) {
                entity.setOrgAppartCollegEnti(new OrgAppartCollegEnti());
            }
            entity.getOrgAppartCollegEnti().setIdAppartCollegEnti(this.getIdAppartCollegEnti().longValue());
        }
        if (this.getIdSuptEstEnteConvenz() != null) {
            if (entity.getOrgSuptEsternoEnteConvenz() == null) {
                entity.setOrgSuptEsternoEnteConvenz(new OrgSuptEsternoEnteConvenz());
            }
            entity.getOrgSuptEsternoEnteConvenz().setIdSuptEstEnteConvenz(this.getIdSuptEstEnteConvenz().longValue());
        }
        if (this.getIdVigilEnteProdut() != null) {
            if (entity.getOrgVigilEnteProdut() == null) {
                entity.setOrgVigilEnteProdut(new OrgVigilEnteProdut());
            }
            entity.getOrgVigilEnteProdut().setIdVigilEnteProdut(this.getIdVigilEnteProdut().longValue());
        }
        entity.setDsCausaleDich(this.getDsCausaleDich());
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
