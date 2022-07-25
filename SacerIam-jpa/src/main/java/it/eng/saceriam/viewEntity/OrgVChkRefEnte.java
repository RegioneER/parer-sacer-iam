package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ORG_V_CHK_REF_ENTE database table.
 */
@Entity
@Table(name = "ORG_V_CHK_REF_ENTE")
@NamedQuery(name = "OrgVChkRefEnte.findAll", query = "SELECT u FROM OrgVChkRefEnte u")
public class OrgVChkRefEnte implements Serializable {

    private static final long serialVersionUID = 1L;

    private String flRefOk;

    public OrgVChkRefEnte() {
    }

    @Column(name = "FL_REF_OK", columnDefinition = "char(1)")
    public String getFlRefOk() {
        return this.flRefOk;
    }

    public void setFlRefOk(String flRefOk) {
        this.flRefOk = flRefOk;
    }

    private OrgVChkRefEnteId orgVChkRefEnteId;

    @EmbeddedId()
    public OrgVChkRefEnteId getOrgVChkRefEnteId() {
        return orgVChkRefEnteId;
    }

    public void setOrgVChkRefEnteId(OrgVChkRefEnteId orgVChkRefEnteId) {
        this.orgVChkRefEnteId = orgVChkRefEnteId;
    }
}
