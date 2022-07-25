package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ORG_SOLLECITO_FATTURA_ENTE database table.
 *
 */
@Entity
@Table(name = "ORG_SOLLECITO_FATTURA_ENTE")
@NamedQuery(name = "OrgSollecitoFatturaEnte.findAll", query = "SELECT o FROM OrgSollecitoFatturaEnte o")
public class OrgSollecitoFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSollecitoFatturaEnte;
    private BigDecimal aaVarSollecito;
    private String cdKeyVarSollecito;
    private String cdRegistroSollecito;
    private String dlSollecito;
    private Date dtSollecito;
    private OrgFatturaEnte orgFatturaEnte;

    public OrgSollecitoFatturaEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_SOLLECITO_FATTURA_ENTE") // @SequenceGenerator(name =
                                                                                 // "ORG_SOLLECITO_FATTURA_ENTE_IDSOLLECITOFATTURAENTE_GENERATOR",
                                                                                 // sequenceName =
                                                                                 // "SORG_SOLLECITO_FATTURA_ENTE",
                                                                                 // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_SOLLECITO_FATTURA_ENTE_IDSOLLECITOFATTURAENTE_GENERATOR")
    @Column(name = "ID_SOLLECITO_FATTURA_ENTE")
    public Long getIdSollecitoFatturaEnte() {
        return this.idSollecitoFatturaEnte;
    }

    public void setIdSollecitoFatturaEnte(Long idSollecitoFatturaEnte) {
        this.idSollecitoFatturaEnte = idSollecitoFatturaEnte;
    }

    @Column(name = "AA_VAR_SOLLECITO")
    public BigDecimal getAaVarSollecito() {
        return this.aaVarSollecito;
    }

    public void setAaVarSollecito(BigDecimal aaVarSollecito) {
        this.aaVarSollecito = aaVarSollecito;
    }

    @Column(name = "CD_KEY_VAR_SOLLECITO")
    public String getCdKeyVarSollecito() {
        return this.cdKeyVarSollecito;
    }

    public void setCdKeyVarSollecito(String cdKeyVarSollecito) {
        this.cdKeyVarSollecito = cdKeyVarSollecito;
    }

    @Column(name = "CD_REGISTRO_SOLLECITO")
    public String getCdRegistroSollecito() {
        return this.cdRegistroSollecito;
    }

    public void setCdRegistroSollecito(String cdRegistroSollecito) {
        this.cdRegistroSollecito = cdRegistroSollecito;
    }

    @Column(name = "DL_SOLLECITO")
    public String getDlSollecito() {
        return this.dlSollecito;
    }

    public void setDlSollecito(String dlSollecito) {
        this.dlSollecito = dlSollecito;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SOLLECITO")
    public Date getDtSollecito() {
        return this.dtSollecito;
    }

    public void setDtSollecito(Date dtSollecito) {
        this.dtSollecito = dtSollecito;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FATTURA_ENTE")
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }

}
