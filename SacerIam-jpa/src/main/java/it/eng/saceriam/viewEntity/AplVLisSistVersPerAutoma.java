package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the APL_V_LIS_SIST_VERS_PER_AUTOMA database table.
 * 
 */
@Entity
@Table(name = "APL_V_LIS_SIST_VERS_PER_AUTOMA")
@NamedQuery(name = "AplVLisSistVersPerAutoma.findAll", query = "SELECT a FROM AplVLisSistVersPerAutoma a")
public class AplVLisSistVersPerAutoma implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cdVersione;
    private String dsSistemaVersante;
    private BigDecimal idEnteUtente;
    private BigDecimal idSistemaVersante;
    private String nmSistemaVersante;

    public AplVLisSistVersPerAutoma() {
    }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
        return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
        this.cdVersione = cdVersione;
    }

    @Column(name = "DS_SISTEMA_VERSANTE")
    public String getDsSistemaVersante() {
        return this.dsSistemaVersante;
    }

    public void setDsSistemaVersante(String dsSistemaVersante) {
        this.dsSistemaVersante = dsSistemaVersante;
    }

    @Column(name = "ID_ENTE_UTENTE")
    public BigDecimal getIdEnteUtente() {
        return this.idEnteUtente;
    }

    public void setIdEnteUtente(BigDecimal idEnteUtente) {
        this.idEnteUtente = idEnteUtente;
    }

    @Id
    @Column(name = "ID_SISTEMA_VERSANTE")
    public BigDecimal getIdSistemaVersante() {
        return this.idSistemaVersante;
    }

    public void setIdSistemaVersante(BigDecimal idSistemaVersante) {
        this.idSistemaVersante = idSistemaVersante;
    }

    @Column(name = "NM_SISTEMA_VERSANTE")
    public String getNmSistemaVersante() {
        return this.nmSistemaVersante;
    }

    public void setNmSistemaVersante(String nmSistemaVersante) {
        this.nmSistemaVersante = nmSistemaVersante;
    }

}