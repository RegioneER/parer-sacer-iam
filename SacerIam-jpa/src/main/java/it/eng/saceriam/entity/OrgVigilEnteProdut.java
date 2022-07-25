package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_VIGIL_ENTE_PRODUT database table.
 *
 */
@Entity
@Table(name = "ORG_VIGIL_ENTE_PRODUT")
@NamedQuery(name = "OrgVigilEnteProdut.findAll", query = "SELECT o FROM OrgVigilEnteProdut o")
public class OrgVigilEnteProdut implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idVigilEnteProdut;
    private Date dtFinVal;
    private Date dtIniVal;
    private OrgAccordoVigil orgAccordoVigil;
    private OrgEnteSiam orgEnteSiam;

    public OrgVigilEnteProdut() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_VIGIL_ENTE_PRODUT") // @SequenceGenerator(name =
                                                                            // "ORG_VIGIL_ENTE_PRODUT_IDVIGILENTEPRODUT_GENERATOR",
                                                                            // sequenceName = "SORG_VIGIL_ENTE_PRODUT",
                                                                            // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_VIGIL_ENTE_PRODUT_IDVIGILENTEPRODUT_GENERATOR")
    @Column(name = "ID_VIGIL_ENTE_PRODUT")
    public Long getIdVigilEnteProdut() {
        return this.idVigilEnteProdut;
    }

    public void setIdVigilEnteProdut(Long idVigilEnteProdut) {
        this.idVigilEnteProdut = idVigilEnteProdut;
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

    // bi-directional many-to-one association to OrgAccordoVigil
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCORDO_VIGIL")
    public OrgAccordoVigil getOrgAccordoVigil() {
        return this.orgAccordoVigil;
    }

    public void setOrgAccordoVigil(OrgAccordoVigil orgAccordoVigil) {
        this.orgAccordoVigil = orgAccordoVigil;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_PRODUT")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }
}
