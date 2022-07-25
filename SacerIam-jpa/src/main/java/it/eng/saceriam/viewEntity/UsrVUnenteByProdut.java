package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNENTE_BY_PRODUT database table.
 */
@Entity
@Table(name = "USR_V_UNENTE_BY_PRODUT")
@NamedQuery(name = "UsrVUnenteByProdut.findAll", query = "SELECT u FROM UsrVUnenteByProdut u")
public class UsrVUnenteByProdut implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idEnteProdut;

    private BigDecimal idEnteProdutColleg;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    public UsrVUnenteByProdut() {
    }

    public UsrVUnenteByProdut(BigDecimal idAmbienteEnteConvenz, String nmAmbienteEnteConvenz) {
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

    @Column(name = "ID_ENTE_PRODUT")
    public BigDecimal getIdEnteProdut() {
        return this.idEnteProdut;
    }

    public void setIdEnteProdut(BigDecimal idEnteProdut) {
        this.idEnteProdut = idEnteProdut;
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

    private UsrVUnenteByProdutId usrVUnenteByProdutId;

    @EmbeddedId()
    public UsrVUnenteByProdutId getUsrVUnenteByProdutId() {
        return usrVUnenteByProdutId;
    }

    public void setUsrVUnenteByProdutId(UsrVUnenteByProdutId usrVUnenteByProdutId) {
        this.usrVUnenteByProdutId = usrVUnenteByProdutId;
    }
}
