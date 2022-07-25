package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_V_LIS_USER_VIGIL_BY_STRUT database table.
 */
@Entity
@Table(name = "USR_V_LIS_USER_VIGIL_BY_STRUT")
@NamedQuery(name = "UsrVLisUserVigilByStrut.findAll", query = "SELECT u FROM UsrVLisUserVigilByStrut u")
public class UsrVLisUserVigilByStrut implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idAccordoVigil;

    private BigDecimal idEnteOrganoVigil;

    private BigDecimal idEnteProdut;

    public UsrVLisUserVigilByStrut() {
    }

    @Column(name = "ID_ACCORDO_VIGIL")
    public BigDecimal getIdAccordoVigil() {
        return this.idAccordoVigil;
    }

    public void setIdAccordoVigil(BigDecimal idAccordoVigil) {
        this.idAccordoVigil = idAccordoVigil;
    }

    @Column(name = "ID_ENTE_ORGANO_VIGIL")
    public BigDecimal getIdEnteOrganoVigil() {
        return this.idEnteOrganoVigil;
    }

    public void setIdEnteOrganoVigil(BigDecimal idEnteOrganoVigil) {
        this.idEnteOrganoVigil = idEnteOrganoVigil;
    }

    @Column(name = "ID_ENTE_PRODUT")
    public BigDecimal getIdEnteProdut() {
        return this.idEnteProdut;
    }

    public void setIdEnteProdut(BigDecimal idEnteProdut) {
        this.idEnteProdut = idEnteProdut;
    }

    private UsrVLisUserVigilByStrutId usrVLisUserVigilByStrutId;

    @EmbeddedId()
    public UsrVLisUserVigilByStrutId getUsrVLisUserVigilByStrutId() {
        return usrVLisUserVigilByStrutId;
    }

    public void setUsrVLisUserVigilByStrutId(UsrVLisUserVigilByStrutId usrVLisUserVigilByStrutId) {
        this.usrVLisUserVigilByStrutId = usrVLisUserVigilByStrutId;
    }
}
