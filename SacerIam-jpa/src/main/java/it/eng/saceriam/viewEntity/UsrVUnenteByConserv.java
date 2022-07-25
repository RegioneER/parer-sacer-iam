package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNENTE_BY_CONSERV database table.
 */
@Entity
@Table(name = "USR_V_UNENTE_BY_CONSERV")
@NamedQuery(name = "UsrVUnenteByConserv.findAll", query = "SELECT u FROM UsrVUnenteByConserv u")
public class UsrVUnenteByConserv implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idEnteConserv;

    private BigDecimal idEnteProdutColleg;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    public UsrVUnenteByConserv() {
    }

    public UsrVUnenteByConserv(BigDecimal idAmbienteEnteConvenz, String nmAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
        this.nmAmbienteEnteConvenz = nmAmbienteEnteConvenz;
    }

    @Column(name = "DS_AMBIENTE_ENTE_CONVENZ")
    public String getDsAmbienteEnteConvenz() {
        return this.dsAmbienteEnteConvenz;
    }

    public void setDsAmbienteEnteConvenz(String dsAmbienteEnteConvenz) {
        this.dsAmbienteEnteConvenz = dsAmbienteEnteConvenz;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public BigDecimal getIdAmbienteEnteConvenz() {
        return this.idAmbienteEnteConvenz;
    }

    public void setIdAmbienteEnteConvenz(BigDecimal idAmbienteEnteConvenz) {
        this.idAmbienteEnteConvenz = idAmbienteEnteConvenz;
    }

    @Column(name = "ID_APPART_COLLEG_ENTI")
    public BigDecimal getIdAppartCollegEnti() {
        return this.idAppartCollegEnti;
    }

    public void setIdAppartCollegEnti(BigDecimal idAppartCollegEnti) {
        this.idAppartCollegEnti = idAppartCollegEnti;
    }

    @Column(name = "ID_ENTE_CONSERV")
    public BigDecimal getIdEnteConserv() {
        return this.idEnteConserv;
    }

    public void setIdEnteConserv(BigDecimal idEnteConserv) {
        this.idEnteConserv = idEnteConserv;
    }

    @Column(name = "ID_ENTE_PRODUT_COLLEG")
    public BigDecimal getIdEnteProdutColleg() {
        return this.idEnteProdutColleg;
    }

    public void setIdEnteProdutColleg(BigDecimal idEnteProdutColleg) {
        this.idEnteProdutColleg = idEnteProdutColleg;
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

    private UsrVUnenteByConservId usrVUnenteByConservId;

    @EmbeddedId()
    public UsrVUnenteByConservId getUsrVUnenteByConservId() {
        return usrVUnenteByConservId;
    }

    public void setUsrVUnenteByConservId(UsrVUnenteByConservId usrVUnenteByConservId) {
        this.usrVUnenteByConservId = usrVUnenteByConservId;
    }
}
