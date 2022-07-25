package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the IAM_V_GETVAL_PARAM_BY_AMB database table.
 * 
 */
@Entity
@Table(name = "IAM_V_GETVAL_PARAM_BY_AMB")
@NamedQuery(name = "IamVGetvalParamByAmb.findAll", query = "SELECT i FROM IamVGetvalParamByAmb i")
public class IamVGetvalParamByAmb implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dsValoreParamApplic;
    private BigDecimal idAmbienteEnteConvenz;
    private BigDecimal idParamApplic;
    private BigDecimal idValoreParamApplic;
    private String nmAmbienteEnteConvenz;
    private String nmParamApplic;
    private String tiAppart;

    public IamVGetvalParamByAmb() {
    }

    @Column(name = "DS_VALORE_PARAM_APPLIC")
    public String getDsValoreParamApplic() {
        return this.dsValoreParamApplic;
    }

    public void setDsValoreParamApplic(String dsValoreParamApplic) {
        this.dsValoreParamApplic = dsValoreParamApplic;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_PARAM_APPLIC")
    public BigDecimal getIdParamApplic() {
        return this.idParamApplic;
    }

    public void setIdParamApplic(BigDecimal idParamApplic) {
        this.idParamApplic = idParamApplic;
    }

    @Id
    @Column(name = "ID_VALORE_PARAM_APPLIC")
    public BigDecimal getIdValoreParamApplic() {
        return this.idValoreParamApplic;
    }

    public void setIdValoreParamApplic(BigDecimal idValoreParamApplic) {
        this.idValoreParamApplic = idValoreParamApplic;
    }

    @Column(name = "NM_AMBIENTE_ENTE_CONVENZ")
    public String getNmAmbienteEnteConvenz() {
        return this.nmAmbienteEnteConvenz;
    }

    public void setNmAmbienteEnteConvenz(String nmAmbienteEnteConvenz) {
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "NM_PARAM_APPLIC")
    public String getNmParamApplic() {
        return this.nmParamApplic;
    }

    public void setNmParamApplic(String nmParamApplic) {
        this.nmParamApplic = nmParamApplic;
    }

    @Column(name = "TI_APPART")
    public String getTiAppart() {
        return this.tiAppart;
    }

    public void setTiAppart(String tiAppart) {
        this.tiAppart = tiAppart;
    }

}