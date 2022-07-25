package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ORG_ENTE_USER_RIF database table.
 *
 */
@Entity
@Table(name = "ORG_ENTE_USER_RIF")
@NamedQuery(name = "OrgEnteUserRif.findAll", query = "SELECT o FROM OrgEnteUserRif o")
public class OrgEnteUserRif implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEnteUserRif;
    private OrgEnteSiam orgEnteSiam;
    private UsrUser usrUser;
    private String qualificaUser;
    private String dlNote;

    public OrgEnteUserRif() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_ENTE_USER_RIF") // @SequenceGenerator(name =
                                                                        // "ORG_ENTE_USER_RIF_IDENTEUSERRIF_GENERATOR",
                                                                        // sequenceName = "SORG_ENTE_USER_RIF",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_ENTE_USER_RIF_IDENTEUSERRIF_GENERATOR")
    @Column(name = "ID_ENTE_USER_RIF")
    public Long getIdEnteUserRif() {
        return this.idEnteUserRif;
    }

    public void setIdEnteUserRif(Long idEnteUserRif) {
        this.idEnteUserRif = idEnteUserRif;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_CONVENZ")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to UsrUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM")
    public UsrUser getUsrUser() {
        return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
        this.usrUser = usrUser;
    }

    @Column(name = "QUALIFICA_USER")
    public String getQualificaUser() {
        return qualificaUser;
    }

    public void setQualificaUser(String qualificaUser) {
        this.qualificaUser = qualificaUser;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
        return dlNote;
    }

    public void setDlNote(String dlNote) {
        this.dlNote = dlNote;
    }
}
