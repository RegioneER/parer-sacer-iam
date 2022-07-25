package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the ORG_STATO_FATTURA_ENTE database table.
 *
 */
@Entity
@Table(name = "ORG_STATO_FATTURA_ENTE")
@NamedQuery(name = "OrgStatoFatturaEnte.findAll", query = "SELECT o FROM OrgStatoFatturaEnte o")
public class OrgStatoFatturaEnte implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idStatoFatturaEnte;
    private Date dtRegStatoFatturaEnte;
    private String tiStatoFatturaEnte;
    private OrgFatturaEnte orgFatturaEnte;

    public OrgStatoFatturaEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_STATO_FATTURA_ENTE") // @SequenceGenerator(name =
                                                                             // "ORG_STATO_FATTURA_ENTE_IDSTATOFATTURAENTE_GENERATOR",
                                                                             // sequenceName =
                                                                             // "SORG_STATO_FATTURA_ENTE",
                                                                             // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_STATO_FATTURA_ENTE_IDSTATOFATTURAENTE_GENERATOR")
    @Column(name = "ID_STATO_FATTURA_ENTE")
    public Long getIdStatoFatturaEnte() {
        return this.idStatoFatturaEnte;
    }

    public void setIdStatoFatturaEnte(Long idStatoFatturaEnte) {
        this.idStatoFatturaEnte = idStatoFatturaEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_STATO_FATTURA_ENTE")
    public Date getDtRegStatoFatturaEnte() {
        return this.dtRegStatoFatturaEnte;
    }

    public void setDtRegStatoFatturaEnte(Date dtRegStatoFatturaEnte) {
        this.dtRegStatoFatturaEnte = dtRegStatoFatturaEnte;
    }

    @Column(name = "TI_STATO_FATTURA_ENTE")
    public String getTiStatoFatturaEnte() {
        return this.tiStatoFatturaEnte;
    }

    public void setTiStatoFatturaEnte(String tiStatoFatturaEnte) {
        this.tiStatoFatturaEnte = tiStatoFatturaEnte;
    }

    // bi-directional many-to-one association to OrgFatturaEnte
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "ID_FATTURA_ENTE", nullable = false)
    public OrgFatturaEnte getOrgFatturaEnte() {
        return this.orgFatturaEnte;
    }

    public void setOrgFatturaEnte(OrgFatturaEnte orgFatturaEnte) {
        this.orgFatturaEnte = orgFatturaEnte;
    }

}
