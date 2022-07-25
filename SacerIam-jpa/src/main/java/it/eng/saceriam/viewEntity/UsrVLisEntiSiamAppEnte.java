package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the USR_V_LIS_ENTI_SIAM_APP_ENTE database table.
 * 
 */
@Entity
@Table(name = "USR_V_LIS_ENTI_SIAM_APP_ENTE")
@NamedQuery(name = "UsrVLisEntiSiamAppEnte.findAll", query = "SELECT u FROM UsrVLisEntiSiamAppEnte u")
public class UsrVLisEntiSiamAppEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsAmbienteEnteConvenz;
    private BigDecimal idAmbienteEnteConvenz;
    private BigDecimal idEnteSiam;
    private BigDecimal idUserIamCor;
    private String nmAmbienteEnteConvenz;
    private String nmEnteSiam;

    public UsrVLisEntiSiamAppEnte() {
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Id
    @Column(name = "ID_ENTE_SIAM")
    public BigDecimal getIdEnteSiam() {
        return this.idEnteSiam;
    }

    public void setIdEnteSiam(BigDecimal idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }

    @Column(name = "ID_USER_IAM_COR")
    public BigDecimal getIdUserIamCor() {
        return this.idUserIamCor;
    }

    public void setIdUserIamCor(BigDecimal idUserIamCor) {
        this.idUserIamCor = idUserIamCor;
    }

    /*
     * @Column(name = "NM_AMBIENTE_ENTE") public String getNmAmbienteEnte() { return this.nmAmbienteEnte; }
     * 
     * public void setNmAmbienteEnte(String nmAmbienteEnte) { this.nmAmbienteEnte = nmAmbienteEnte; }
     */
    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_ENTE_SIAM")
    public String getNmEnteSiam() {
        return this.nmEnteSiam;
    }

    public void setNmEnteSiam(String nmEnteSiam) {
        this.nmEnteSiam = nmEnteSiam;
    }

}