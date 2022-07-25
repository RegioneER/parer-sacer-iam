package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_APPART_USER_RICH database table.
 *
 */
@Entity
@Table(name = "USR_APPART_USER_RICH")
@NamedQuery(name = "UsrAppartUserRich.findAll", query = "SELECT u FROM UsrAppartUserRich u")
public class UsrAppartUserRich implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAppartUserRich;
    private String flAzioneRichEvasa;
    private String nmCognomeUser;
    private String nmNomeUser;
    private String nmUserid;
    private String tiAppartUserRich;
    private String tiAzioneRich;
    private UsrRichGestUser usrRichGestUser;
    private OrgEnteSiam orgEnteSiam;
    private NtfNotifica ntfNotifica1;
    private NtfNotifica ntfNotifica2;
    private UsrUser usrUser;

    public UsrAppartUserRich() {
    }

    public UsrAppartUserRich(UsrUser usrUser, String nmCognomeUser, String nmNomeUser, String nmUserid) {
        this.usrUser = usrUser;
        this.nmCognomeUser = nmCognomeUser;
        this.nmNomeUser = nmNomeUser;
        this.nmUserid = nmUserid;
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_APPART_USER_RICH") // @SequenceGenerator(name =
                                                                           // "USR_APPART_USER_RICH_IDAPPARTUSERRICH_GENERATOR",
                                                                           // sequenceName = "SUSR_APPART_USER_RICH",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "USR_APPART_USER_RICH_IDAPPARTUSERRICH_GENERATOR")
    @Column(name = "ID_APPART_USER_RICH")
    public Long getIdAppartUserRich() {
        return this.idAppartUserRich;
    }

    public void setIdAppartUserRich(Long idAppartUserRich) {
        this.idAppartUserRich = idAppartUserRich;
    }

    @Column(name = "FL_AZIONE_RICH_EVASA", columnDefinition = "char(1)")
    public String getFlAzioneRichEvasa() {
        return this.flAzioneRichEvasa;
    }

    public void setFlAzioneRichEvasa(String flAzioneRichEvasa) {
        this.flAzioneRichEvasa = flAzioneRichEvasa;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
        return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
        return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TI_APPART_USER_RICH")
    public String getTiAppartUserRich() {
        return this.tiAppartUserRich;
    }

    public void setTiAppartUserRich(String tiAppartUserRich) {
        this.tiAppartUserRich = tiAppartUserRich;
    }

    @Column(name = "TI_AZIONE_RICH")
    public String getTiAzioneRich() {
        return this.tiAzioneRich;
    }

    public void setTiAzioneRich(String tiAzioneRich) {
        this.tiAzioneRich = tiAzioneRich;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_USER")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA_AZIONE_2")
    public NtfNotifica getNtfNotifica1() {
        return this.ntfNotifica1;
    }

    public void setNtfNotifica1(NtfNotifica ntfNotifica1) {
        this.ntfNotifica1 = ntfNotifica1;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA_AZIONE_1")
    public NtfNotifica getNtfNotifica2() {
        return this.ntfNotifica2;
    }

    public void setNtfNotifica2(NtfNotifica ntfNotifica2) {
        this.ntfNotifica2 = ntfNotifica2;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RICH_GEST_USER")
    public UsrRichGestUser getUsrRichGestUser() {
        return this.usrRichGestUser;
    }

    public void setUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
        this.usrRichGestUser = usrRichGestUser;
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

}
