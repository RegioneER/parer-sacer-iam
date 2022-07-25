package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ORG_V_ENTE_CONV_BY_DELABILORG database table.
 */
@Entity
@Table(name = "ORG_V_ENTE_CONV_BY_DELABILORG")
@NamedQuery(name = "OrgVEnteConvByDelabilorg.findAll", query = "SELECT o FROM OrgVEnteConvByDelabilorg o")
public class OrgVEnteConvByDelabilorg implements Serializable {

    private static final long serialVersionUID = 1L;

    public OrgVEnteConvByDelabilorg() {
    }

    private OrgVEnteConvByDelabilorgId orgVEnteConvByDelabilorgId;

    @EmbeddedId()
    public OrgVEnteConvByDelabilorgId getOrgVEnteConvByDelabilorgId() {
        return orgVEnteConvByDelabilorgId;
    }

    public void setOrgVEnteConvByDelabilorgId(OrgVEnteConvByDelabilorgId orgVEnteConvByDelabilorgId) {
        this.orgVEnteConvByDelabilorgId = orgVEnteConvByDelabilorgId;
    }
}
