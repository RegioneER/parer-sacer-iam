package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the PRF_V_LIS_USER_DA_REPLIC database table.
 */
@Entity
@Table(name = "PRF_V_LIS_USER_DA_REPLIC")
@NamedQuery(name = "PrfVLisUserDaReplic.findAll", query = "SELECT p FROM PrfVLisUserDaReplic p")
public class PrfVLisUserDaReplic implements Serializable {

    private static final long serialVersionUID = 1L;

    private BigDecimal idRuolo;

    private String nmApplic;

    private String nmRuolo;

    private String nmUserid;

    public PrfVLisUserDaReplic() {
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_RUOLO")
    public String getNmRuolo() {
        return this.nmRuolo;
    }

    public void setNmRuolo(String nmRuolo) {
        this.nmRuolo = nmRuolo;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    private PrfVLisUserDaReplicId prfVLisUserDaReplicId;

    @EmbeddedId()
    public PrfVLisUserDaReplicId getPrfVLisUserDaReplicId() {
        return prfVLisUserDaReplicId;
    }

    public void setPrfVLisUserDaReplicId(PrfVLisUserDaReplicId prfVLisUserDaReplicId) {
        this.prfVLisUserDaReplicId = prfVLisUserDaReplicId;
    }
}
