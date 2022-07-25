package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_IND_IP_USER database table.
 *
 */
@Entity
@Table(name = "USR_IND_IP_USER")
public class UsrIndIpUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idIndIpUser;
    private String cdIndIpUser;
    private UsrUser usrUser;

    public UsrIndIpUser() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_IND_IP_USER") // @SequenceGenerator(name =
                                                                      // "USR_IND_IP_USER_IDINDIPUSER_GENERATOR",
                                                                      // sequenceName = "SUSR_IND_IP_USER",
                                                                      // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_IND_IP_USER_IDINDIPUSER_GENERATOR")
    @Column(name = "ID_IND_IP_USER")
    public Long getIdIndIpUser() {
        return this.idIndIpUser;
    }

    public void setIdIndIpUser(Long idIndIpUser) {
        this.idIndIpUser = idIndIpUser;
    }

    @Column(name = "CD_IND_IP_USER")
    public String getCdIndIpUser() {
        return this.cdIndIpUser;
    }

    public void setCdIndIpUser(String cdIndIpUser) {
        this.cdIndIpUser = cdIndIpUser;
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
