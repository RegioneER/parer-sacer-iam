package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ORG_ENTE_ARK_RIF database table.
 *
 */
@Entity
@Table(name = "ORG_ENTE_ARK_RIF")
@NamedQuery(name = "OrgEnteArkRif.findAll", query = "SELECT o FROM OrgEnteArkRif o")
public class OrgEnteArkRif implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEnteArkRif;
    private OrgEnteSiam orgEnteSiam;
    private UsrUser usrUser;
    private String flReferente;
    private String dlNote;

    public OrgEnteArkRif() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_ENTE_ARK_RIF") // @SequenceGenerator(name =
                                                                       // "ORG_ENTE_ARK_RIF_IDENTEARKRIF_GENERATOR",
                                                                       // sequenceName = "SORG_ENTE_ARK_RIF",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_ENTE_ARK_RIF_IDENTEARKRIF_GENERATOR")
    @Column(name = "ID_ENTE_ARK_RIF")
    public Long getIdEnteArkRif() {
        return this.idEnteArkRif;
    }

    public void setIdEnteArkRif(Long idEnteArkRif) {
        this.idEnteArkRif = idEnteArkRif;
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

    @Column(name = "FL_REFERENTE", columnDefinition = "char(1)")
    public String getFlReferente() {
        return flReferente;
    }

    public void setFlReferente(String flReferente) {
        this.flReferente = flReferente;
    }

    @Column(name = "DL_NOTE")
    public String getDlNote() {
        return dlNote;
    }

    public void setDlNote(String dlNote) {
        this.dlNote = dlNote;
    }

}
