package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_V_SERV_TI_SERV_DA_EROG database table.
 *
 */
@Entity
@Table(name = "ORG_V_SERV_TI_SERV_DA_EROG")
@NamedQuery(name = "OrgVServTiServDaErog.findAll", query = "SELECT o FROM OrgVServTiServDaErog o")
public class OrgVServTiServDaErog implements Serializable {

    private static final long serialVersionUID = 1L;
    private String cdTipoServizio;
    private Date dtErog;
    // private Date dtRegAccordo;
    private BigDecimal idAccordoEnte;
    private BigDecimal idEnteConvenz;
    private BigDecimal idServizioErogato;
    private BigDecimal idTipoServizio;
    private String listStrut;
    private String nmEnteConvenz;
    private String nmServizioErogato;

    public OrgVServTiServDaErog() {
    }

    @Column(name = "CD_TIPO_SERVIZIO")
    public String getCdTipoServizio() {
        return this.cdTipoServizio;
    }

    public void setCdTipoServizio(String cdTipoServizio) {
        this.cdTipoServizio = cdTipoServizio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_EROG")
    public Date getDtErog() {
        return this.dtErog;
    }

    public void setDtErog(Date dtErog) {
        this.dtErog = dtErog;
    }

    /*
     * @Temporal(TemporalType.DATE)
     * 
     * @Column(name = "DT_REG_ACCORDO") public Date getDtRegAccordo() { return this.dtRegAccordo; }
     * 
     * public void setDtRegAccordo(Date dtRegAccordo) { this.dtRegAccordo = dtRegAccordo; }
     */

    @Column(name = "ID_ACCORDO_ENTE")
    public BigDecimal getIdAccordoEnte() {
        return this.idAccordoEnte;
    }

    public void setIdAccordoEnte(BigDecimal idAccordoEnte) {
        this.idAccordoEnte = idAccordoEnte;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Id
    @Column(name = "ID_SERVIZIO_EROGATO")
    public BigDecimal getIdServizioErogato() {
        return this.idServizioErogato;
    }

    public void setIdServizioErogato(BigDecimal idServizioErogato) {
        this.idServizioErogato = idServizioErogato;
    }

    @Column(name = "ID_TIPO_SERVIZIO")
    public BigDecimal getIdTipoServizio() {
        return this.idTipoServizio;
    }

    public void setIdTipoServizio(BigDecimal idTipoServizio) {
        this.idTipoServizio = idTipoServizio;
    }

    @Column(name = "LIST_STRUT")
    public String getListStrut() {
        return this.listStrut;
    }

    public void setListStrut(String listStrut) {
        this.listStrut = listStrut;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    @Column(name = "NM_SERVIZIO_EROGATO")
    public String getNmServizioErogato() {
        return this.nmServizioErogato;
    }

    public void setNmServizioErogato(String nmServizioErogato) {
        this.nmServizioErogato = nmServizioErogato;
    }

}
