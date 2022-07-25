package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_SUPT_ESTERNO_ENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "ORG_SUPT_ESTERNO_ENTE_CONVENZ")
@NamedQuery(name = "OrgSuptEsternoEnteConvenz.findAll", query = "SELECT o FROM OrgSuptEsternoEnteConvenz o")
public class OrgSuptEsternoEnteConvenz implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSuptEstEnteConvenz;
    private Date dtFinVal;
    private Date dtIniVal;
    private OrgEnteSiam orgEnteSiamByIdEnteFornitEst;
    private OrgEnteSiam orgEnteSiamByIdEnteProdut;

    public OrgSuptEsternoEnteConvenz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_SUPT_ESTERNO_ENTE_CONVENZ") // @SequenceGenerator(name =
                                                                                    // "ORG_SUPT_ESTERNO_ENTE_CONVENZ_IDSUPTESTENTECONVENZ_GENERATOR",
                                                                                    // sequenceName =
                                                                                    // "SORG_SUPT_ESTERNO_ENTE_CONVENZ",
                                                                                    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_SUPT_ESTERNO_ENTE_CONVENZ_IDSUPTESTENTECONVENZ_GENERATOR")
    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public Long getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(Long idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_FORNIT_EST")
    public OrgEnteSiam getOrgEnteSiamByIdEnteFornitEst() {
        return this.orgEnteSiamByIdEnteFornitEst;
    }

    public void setOrgEnteSiamByIdEnteFornitEst(OrgEnteSiam orgEnteSiamByIdEnteFornitEst) {
        this.orgEnteSiamByIdEnteFornitEst = orgEnteSiamByIdEnteFornitEst;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_PRODUT")
    public OrgEnteSiam getOrgEnteSiamByIdEnteProdut() {
        return this.orgEnteSiamByIdEnteProdut;
    }

    public void setOrgEnteSiamByIdEnteProdut(OrgEnteSiam orgEnteSiamByIdEnteProdut) {
        this.orgEnteSiamByIdEnteProdut = orgEnteSiamByIdEnteProdut;
    }
}
