package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * The persistent class for the USR_STATO_USER database table.
 *
 */
@Entity
@Table(name = "USR_STATO_USER")
@NamedQuery(name = "UsrStatoUser.findAll", query = "SELECT u FROM UsrStatoUser u")
public class UsrStatoUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idStatoUser;
    private String tiStatoUser;
    private Timestamp tsStato;
    private NtfNotifica ntfNotifica;
    private UsrRichGestUser usrRichGestUser;
    private UsrUser usrUser;

    public UsrStatoUser() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_STATO_USER") // @SequenceGenerator(name =
                                                                     // "USR_STATO_USER_IDSTATOUSER_GENERATOR",
                                                                     // sequenceName = "SUSR_STATO_USER", allocationSize
                                                                     // = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_STATO_USER_IDSTATOUSER_GENERATOR")
    @Column(name = "ID_STATO_USER")
    public Long getIdStatoUser() {
        return this.idStatoUser;
    }

    public void setIdStatoUser(Long idStatoUser) {
        this.idStatoUser = idStatoUser;
    }

    @Column(name = "TI_STATO_USER")
    public String getTiStatoUser() {
        return this.tiStatoUser;
    }

    public void setTiStatoUser(String tiStatoUser) {
        this.tiStatoUser = tiStatoUser;
    }

    @Column(name = "TS_STATO")
    public Timestamp getTsStato() {
        return this.tsStato;
    }

    public void setTsStato(Timestamp tsStato) {
        this.tsStato = tsStato;
    }

    // bi-directional many-to-one association to NtfNotifica
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_NOTIFICA")
    public NtfNotifica getNtfNotifica() {
        return this.ntfNotifica;
    }

    public void setNtfNotifica(NtfNotifica ntfNotifica) {
        this.ntfNotifica = ntfNotifica;
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
    @JoinColumn(name = "ID_USER_IAM", nullable = false)
    public UsrUser getUsrUser() {
        return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
        this.usrUser = usrUser;
    }

}
