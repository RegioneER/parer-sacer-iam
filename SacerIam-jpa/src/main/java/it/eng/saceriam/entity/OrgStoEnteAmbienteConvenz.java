package it.eng.saceriam.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the ORG_STO_ENTE_AMBIENTE_CONVENZ database table.
 * 
 */
@Entity
@Table(name = "ORG_STO_ENTE_AMBIENTE_CONVENZ")
@NamedQuery(name = "OrgStoEnteAmbienteConvenz.findAll", query = "SELECT p FROM OrgStoEnteAmbienteConvenz p")
public class OrgStoEnteAmbienteConvenz implements Serializable {
    private static final long serialVersionUID = 1L;
    private long idStoEnteAmbienteConvenz;
    private Date dtFinVal;
    private Date dtIniVal;
    private OrgAmbienteEnteConvenz orgAmbienteEnteConvenz;
    private OrgEnteSiam orgEnteConvenz;

    public OrgStoEnteAmbienteConvenz() {
    }

    @Id
    @SequenceGenerator(name = "ORG_STO_ENTE_AMBIENTE_CONVENZ_IDSTOENTEAMBIENTECONVENZ_GENERATOR", sequenceName = "SORG_STO_ENTE_AMBIENTE_CONVENZ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_STO_ENTE_AMBIENTE_CONVENZ_IDSTOENTEAMBIENTECONVENZ_GENERATOR")
    @Column(name = "ID_STO_ENTE_AMBIENTE_CONVENZ")
    public long getIdStoEnteAmbienteConvenz() {
        return this.idStoEnteAmbienteConvenz;
    }

    public void setIdStoEnteAmbienteConvenz(long idStoEnteAmbienteConvenz) {
        this.idStoEnteAmbienteConvenz = idStoEnteAmbienteConvenz;
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

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public OrgAmbienteEnteConvenz getOrgAmbienteEnteConvenz() {
        return this.orgAmbienteEnteConvenz;
    }

    public void setOrgAmbienteEnteConvenz(OrgAmbienteEnteConvenz orgAmbienteEnteConvenz) {
        this.orgAmbienteEnteConvenz = orgAmbienteEnteConvenz;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteConvenz() {
        return this.orgEnteConvenz;
    }

    public void setOrgEnteConvenz(OrgEnteSiam orgEnteConvenz) {
        this.orgEnteConvenz = orgEnteConvenz;
    }

}