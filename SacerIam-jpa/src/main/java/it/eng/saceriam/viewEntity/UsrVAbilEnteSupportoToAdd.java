package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_ABIL_ENTE_SUPPORTO_TO_ADD database table.
 */
@Entity
@Table(name = "USR_V_ABIL_ENTE_SUPPORTO_TO_ADD")
@NamedQuery(name = "UsrVAbilEnteSupportoToAdd.findAll", query = "SELECT u FROM UsrVAbilEnteSupportoToAdd u")
public class UsrVAbilEnteSupportoToAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleAbil;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idEnteConvenz;

    private BigDecimal idEnteFornitEst;

    private BigDecimal idSuptEstEnteConvenz;

    private BigDecimal idUserIamCor;

    private BigDecimal idUserIamGestito;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    public UsrVAbilEnteSupportoToAdd() {
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "DS_CAUSALE_ABIL", columnDefinition = "CHAR")
    public String getDsCausaleAbil() {
        return this.dsCausaleAbil;
    }

    public void setDsCausaleAbil(String dsCausaleAbil) {
        this.dsCausaleAbil = dsCausaleAbil;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_ENTE_FORNIT_EST")
    public BigDecimal getIdEnteFornitEst() {
        return this.idEnteFornitEst;
    }

    public void setIdEnteFornitEst(BigDecimal idEnteFornitEst) {
        this.idEnteFornitEst = idEnteFornitEst;
    }

    @Column(name = "ID_SUPT_EST_ENTE_CONVENZ")
    public BigDecimal getIdSuptEstEnteConvenz() {
        return this.idSuptEstEnteConvenz;
    }

    public void setIdSuptEstEnteConvenz(BigDecimal idSuptEstEnteConvenz) {
        this.idSuptEstEnteConvenz = idSuptEstEnteConvenz;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_CONVENZ")
    public String getNmEnteConvenz() {
        return this.nmEnteConvenz;
    }

    public void setNmEnteConvenz(String nmEnteConvenz) {
        this.nmEnteConvenz = nmEnteConvenz;
    }

    private UsrVAbilEnteSupportoToAddId usrVAbilEnteSupportoToAddId;

    @EmbeddedId()
    public UsrVAbilEnteSupportoToAddId getUsrVAbilEnteSupportoToAddId() {
        return usrVAbilEnteSupportoToAddId;
    }

    public void setUsrVAbilEnteSupportoToAddId(UsrVAbilEnteSupportoToAddId usrVAbilEnteSupportoToAddId) {
        this.usrVAbilEnteSupportoToAddId = usrVAbilEnteSupportoToAddId;
    }
}
