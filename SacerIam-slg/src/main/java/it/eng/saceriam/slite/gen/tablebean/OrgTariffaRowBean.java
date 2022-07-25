package it.eng.saceriam.slite.gen.tablebean;

import it.eng.saceriam.entity.OrgClasseEnteConvenz;
import it.eng.saceriam.entity.OrgTariffa;
import it.eng.saceriam.entity.OrgTariffario;
import it.eng.saceriam.entity.OrgTipoServizio;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;
import java.math.BigDecimal;
import org.eclipse.persistence.internal.sessions.AbstractRecord;

/**
 * RowBean per la tabella Org_Tariffa
 *
 */
public class OrgTariffaRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter", comments =
     * "This class was generated by OraTool", date = "Monday, 11 July 2016 11:04" )
     */

    public static OrgTariffaTableDescriptor TABLE_DESCRIPTOR = new OrgTariffaTableDescriptor();

    public OrgTariffaRowBean() {
        super();
    }

    public TableDescriptor getTableDescriptor() {
        return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdTariffa() {
        return getBigDecimal("id_tariffa");
    }

    public void setIdTariffa(BigDecimal idTariffa) {
        setObject("id_tariffa", idTariffa);
    }

    public BigDecimal getIdTariffario() {
        return getBigDecimal("id_tariffario");
    }

    public void setIdTariffario(BigDecimal idTariffario) {
        setObject("id_tariffario", idTariffario);
    }

    public String getNmTariffa() {
        return getString("nm_tariffa");
    }

    public void setNmTariffa(String nmTariffa) {
        setObject("nm_tariffa", nmTariffa);
    }

    public String getDsTariffa() {
        return getString("ds_tariffa");
    }

    public void setDsTariffa(String dsTariffa) {
        setObject("ds_tariffa", dsTariffa);
    }

    public BigDecimal getIdTipoServizio() {
        return getBigDecimal("id_tipo_servizio");
    }

    public void setIdTipoServizio(BigDecimal idTipoServizio) {
        setObject("id_tipo_servizio", idTipoServizio);
    }

    public BigDecimal getIdClasseEnteConvenz() {
        return getBigDecimal("id_classe_ente_convenz");
    }

    public void setIdClasseEnteConvenz(BigDecimal idClasseEnteConvenz) {
        setObject("id_classe_ente_convenz", idClasseEnteConvenz);
    }

    public String getTipoTariffa() {
        return getString("tipo_tariffa");
    }

    public void setTipoTariffa(String tipoTariffa) {
        setObject("tipo_tariffa", tipoTariffa);
    }

    public BigDecimal getImValoreFissoTariffa() {
        return getBigDecimal("im_valore_fisso_tariffa");
    }

    public void setImValoreFissoTariffa(BigDecimal imValoreFissoTariffa) {
        setObject("im_valore_fisso_tariffa", imValoreFissoTariffa);
    }

    public BigDecimal getNiQuantitaUnit() {
        return getBigDecimal("ni_quantita_unit");
    }

    public void setNiQuantitaUnit(BigDecimal niQuantitaUnit) {
        setObject("ni_quantita_unit", niQuantitaUnit);
    }

    @Override
    public void entityToRowBean(Object obj) {
        OrgTariffa entity = (OrgTariffa) obj;
        AbstractRecord ar;
        this.setIdTariffa(new BigDecimal(entity.getIdTariffa()));
        if (entity.getOrgTariffario() != null) {
            this.setIdTariffario(new BigDecimal(entity.getOrgTariffario().getIdTariffario()));
        }

        this.setNmTariffa(entity.getNmTariffa());
        this.setDsTariffa(entity.getDsTariffa());
        if (entity.getOrgTipoServizio() != null) {
            this.setIdTipoServizio(new BigDecimal(entity.getOrgTipoServizio().getIdTipoServizio()));

        }
        if (entity.getOrgClasseEnteConvenz() != null) {
            this.setIdClasseEnteConvenz(new BigDecimal(entity.getOrgClasseEnteConvenz().getIdClasseEnteConvenz()));

        }
        this.setTipoTariffa(entity.getTipoTariffa());
        this.setImValoreFissoTariffa(entity.getImValoreFissoTariffa());
        this.setNiQuantitaUnit(entity.getNiQuantitaUnit());
    }

    @Override
    public OrgTariffa rowBeanToEntity() {
        OrgTariffa entity = new OrgTariffa();
        if (this.getIdTariffa() != null) {
            entity.setIdTariffa(this.getIdTariffa().longValue());
        }
        if (this.getIdTariffario() != null) {
            if (entity.getOrgTariffario() == null) {
                entity.setOrgTariffario(new OrgTariffario());
            }
            entity.getOrgTariffario().setIdTariffario(this.getIdTariffario().longValue());
        }
        entity.setNmTariffa(this.getNmTariffa());
        entity.setDsTariffa(this.getDsTariffa());
        if (this.getIdTipoServizio() != null) {
            if (entity.getOrgTipoServizio() == null) {
                entity.setOrgTipoServizio(new OrgTipoServizio());
            }
            entity.getOrgTipoServizio().setIdTipoServizio(this.getIdTipoServizio().longValue());
        }
        if (this.getIdClasseEnteConvenz() != null) {
            if (entity.getOrgClasseEnteConvenz() == null) {
                entity.setOrgClasseEnteConvenz(new OrgClasseEnteConvenz());
            }
            entity.getOrgClasseEnteConvenz().setIdClasseEnteConvenz(this.getIdClasseEnteConvenz().longValue());
        }
        entity.setTipoTariffa(this.getTipoTariffa());
        entity.setImValoreFissoTariffa(this.getImValoreFissoTariffa());
        entity.setNiQuantitaUnit(this.getNiQuantitaUnit());
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
