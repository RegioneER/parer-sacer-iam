package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the USR_USER database table.
 *
 */
@Entity
@Table(name = "USR_OLD_PSW")
@NamedQuery(name = "UsrOldPsw.findAll", query = "SELECT u FROM UsrOldPsw u")
public class UsrOldPsw implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idOldPsw;
    private BigDecimal pgOldPsw;
    private String cdPsw;
    private String cdSalt;
    private UsrUser usrUser;

    public UsrOldPsw() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_OLD_PSW") // @SequenceGenerator(name =
                                                                  // "USR_OLD_PSW_IDOLDPSW_GENERATOR", sequenceName =
                                                                  // "SUSR_OLD_PSW", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_OLD_PSW_IDOLDPSW_GENERATOR")
    @Column(name = "ID_OLD_PSW")
    public Long getIdOldPsw() {
        return this.idOldPsw;
    }

    public void setIdOldPsw(Long idOldPsw) {
        this.idOldPsw = idOldPsw;
    }

    @Column(name = "PG_OLD_PSW")
    public BigDecimal getPgOldPsw() {
        return this.pgOldPsw;
    }

    public void setPgOldPsw(BigDecimal pgOldPsw) {
        this.pgOldPsw = pgOldPsw;
    }

    @Column(name = "CD_PSW")
    public String getCdPsw() {
        return this.cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    @Column(name = "CD_SALT")
    public String getCdSalt() {
        return this.cdSalt;
    }

    public void setCdSalt(String cdSalt) {
        this.cdSalt = cdSalt;
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
