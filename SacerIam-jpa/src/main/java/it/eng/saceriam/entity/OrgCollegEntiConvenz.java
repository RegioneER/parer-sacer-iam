package it.eng.saceriam.entity;

import it.eng.saceriam.entity.constraint.ConstOrgCollegEntiConvenz.TiColleg;
import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_COLLEG_ENTI_CONVENZ database table.
 * 
 */
@Entity
@Table(name = "ORG_COLLEG_ENTI_CONVENZ")
@NamedQuery(name = "OrgCollegEntiConvenz.findAll", query = "SELECT o FROM OrgCollegEntiConvenz o")
public class OrgCollegEntiConvenz implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCollegEntiConvenz;
    private String dsColleg;
    private String nmColleg;
    private TiColleg tiColleg;
    private Date dtIniVal;
    private Date dtFinVal;
    private OrgEnteSiam orgEnteSiam;
    private List<OrgAppartCollegEnti> orgAppartCollegEntis = new ArrayList<>();

    public OrgCollegEntiConvenz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_COLLEG_ENTI_CONVENZ") // @SequenceGenerator(name =
                                                                              // "ORG_COLLEG_ENTI_CONVENZ_IDCOLLEGENTICONVENZ_GENERATOR",
                                                                              // sequenceName =
                                                                              // "SORG_COLLEG_ENTI_CONVENZ",
                                                                              // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_COLLEG_ENTI_CONVENZ_IDCOLLEGENTICONVENZ_GENERATOR")
    @Column(name = "ID_COLLEG_ENTI_CONVENZ")
    public Long getIdCollegEntiConvenz() {
        return this.idCollegEntiConvenz;
    }

    public void setIdCollegEntiConvenz(Long idCollegEntiConvenz) {
        this.idCollegEntiConvenz = idCollegEntiConvenz;
    }

    @Column(name = "DS_COLLEG")
    public String getDsColleg() {
        return this.dsColleg;
    }

    public void setDsColleg(String dsColleg) {
        this.dsColleg = dsColleg;
    }

    @Column(name = "NM_COLLEG")
    public String getNmColleg() {
        return this.nmColleg;
    }

    public void setNmColleg(String nmColleg) {
        this.nmColleg = nmColleg;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TI_COLLEG")
    public TiColleg getTiColleg() {
        return this.tiColleg;
    }

    public void setTiColleg(TiColleg tiColleg) {
        this.tiColleg = tiColleg;
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
    @JoinColumn(name = "ID_ENTE_CONVENZ_CAPOFILA")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @OneToMany(mappedBy = "orgCollegEntiConvenz")
    public List<OrgAppartCollegEnti> getOrgAppartCollegEntis() {
        return this.orgAppartCollegEntis;
    }

    public void setOrgAppartCollegEntis(List<OrgAppartCollegEnti> orgAppartCollegEntis) {
        this.orgAppartCollegEntis = orgAppartCollegEntis;
    }

    public OrgAppartCollegEnti addOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        getOrgAppartCollegEntis().add(orgAppartCollegEnti);
        orgAppartCollegEnti.setOrgCollegEntiConvenz(this);

        return orgAppartCollegEnti;
    }

    public OrgAppartCollegEnti removeOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        getOrgAppartCollegEntis().remove(orgAppartCollegEnti);
        orgAppartCollegEnti.setOrgCollegEntiConvenz(null);

        return orgAppartCollegEnti;
    }

}