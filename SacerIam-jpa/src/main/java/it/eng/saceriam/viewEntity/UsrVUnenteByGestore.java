package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_UNENTE_BY_GESTORE database table.
 */
@Entity
@Table(name = "USR_V_UNENTE_BY_GESTORE")
@NamedQuery(name = "UsrVUnenteByGestore.findAll", query = "SELECT u FROM UsrVUnenteByGestore u")
public class UsrVUnenteByGestore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dsAmbienteEnteConvenz;

    private String dsCausaleDich;

    private BigDecimal idAmbienteEnteConvenz;

    private BigDecimal idAppartCollegEnti;

    private BigDecimal idEnteGestore;

    private BigDecimal idEnteProdutColleg;

    private String nmAmbienteEnteConvenz;

    private String nmEnteConvenz;

    public UsrVUnenteByGestore() {
    }

    public UsrVUnenteByGestore(BigDecimal idAmbienteEnteConvenz, String nmAmbienteEnteConvenz) {
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

    @Column(name = "ID_ENTE_GESTORE")
    public BigDecimal getIdEnteGestore() {
        return this.idEnteGestore;
    }

    public void setIdEnteGestore(BigDecimal idEnteGestore) {
        this.idEnteGestore = idEnteGestore;
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

    private UsrVUnenteByGestoreId usrVUnenteByGestoreId;

    @EmbeddedId()
    public UsrVUnenteByGestoreId getUsrVUnenteByGestoreId() {
        return usrVUnenteByGestoreId;
    }

    public void setUsrVUnenteByGestoreId(UsrVUnenteByGestoreId usrVUnenteByGestoreId) {
        this.usrVUnenteByGestoreId = usrVUnenteByGestoreId;
    }
}
