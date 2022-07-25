package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_MODIF_FATTURA_ENTE database table.
 * 
 */
@Entity
@Table(name = "ORG_MODIF_FATTURA_ENTE")
@NamedQuery(name = "OrgModifFatturaEnte.findAll", query = "SELECT o FROM OrgModifFatturaEnte o")
public class OrgModifFatturaEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idModifFatturaEnte;
    private Date dtModifFatturaEnte;
    private String ntModifFatturaEnte;
    private OrgFatturaEnte orgFatturaEnte;

    public OrgModifFatturaEnte() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_MODIF_FATTURA_ENTE") // @SequenceGenerator(name =
                                                                             // "ORG_MODIF_FATTURA_ENTE_IDMODIFFATTURAENTE_GENERATOR",
                                                                             // sequenceName =
                                                                             // "SORG_MODIF_FATTURA_ENTE",
                                                                             // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_MODIF_FATTURA_ENTE_IDMODIFFATTURAENTE_GENERATOR")
    @Column(name = "ID_MODIF_FATTURA_ENTE")
    public Long getIdModifFatturaEnte() {
        return this.idModifFatturaEnte;
    }

    public void setIdModifFatturaEnte(Long idModifFatturaEnte) {
        this.idModifFatturaEnte = idModifFatturaEnte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_MODIF_FATTURA_ENTE")
    public Date getDtModifFatturaEnte() {
        return this.dtModifFatturaEnte;
    }

    public void setDtModifFatturaEnte(Date dtModifFatturaEnte) {
        this.dtModifFatturaEnte = dtModifFatturaEnte;
    }

    @Column(name = "NT_MODIF_FATTURA_ENTE")
    public String getNtModifFatturaEnte() {
        return this.ntModifFatturaEnte;
    }

    public void setNtModifFatturaEnte(String ntModifFatturaEnte) {
        this.ntModifFatturaEnte = ntModifFatturaEnte;
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