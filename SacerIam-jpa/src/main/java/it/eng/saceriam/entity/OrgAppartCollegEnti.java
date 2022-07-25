package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ORG_APPART_COLLEG_ENTI database table.
 *
 */
@Entity
@Table(name = "ORG_APPART_COLLEG_ENTI")
@NamedQuery(name = "OrgAppartCollegEnti.findAll", query = "SELECT o FROM OrgAppartCollegEnti o")
public class OrgAppartCollegEnti implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAppartCollegEnti;
    private Date dtIniVal;
    private Date dtFinVal;
    private OrgEnteSiam orgEnteSiam;
    private OrgCollegEntiConvenz orgCollegEntiConvenz;

    public OrgAppartCollegEnti() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_APPART_COLLEG_ENTI") // @SequenceGenerator(name =
                                                                             // "ORG_APPART_COLLEG_ENTI_IDAPPARTCOLLEGENTI_GENERATOR",
                                                                             // sequenceName =
                                                                             // "SORG_APPART_COLLEG_ENTI",
                                                                             // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_APPART_COLLEG_ENTI_IDAPPARTCOLLEGENTI_GENERATOR")
    @Column(name = "ID_APPART_COLLEG_ENTI")
    public Long getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(Long idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL")
    public Date getDtFinVal() {
        return this.dtFinVal;
    }

    public void setDtFinVal(Date dtFinVal) {
        this.dtFinVal = dtFinVal;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to OrgCollegEntiConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COLLEG_ENTI_CONVENZ")
    public OrgCollegEntiConvenz getOrgCollegEntiConvenz() {
        return this.orgCollegEntiConvenz;
    }

    public void setOrgCollegEntiConvenz(OrgCollegEntiConvenz orgCollegEntiConvenz) {
        this.orgCollegEntiConvenz = orgCollegEntiConvenz;
    }

}
