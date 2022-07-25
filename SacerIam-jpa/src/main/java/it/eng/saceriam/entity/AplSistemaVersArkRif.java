package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_SISTEMA_VERS_ARK_RIF database table.
 *
 */
@Entity
@Table(name = "APL_SISTEMA_VERS_ARK_RIF")
@NamedQuery(name = "AplSistemaVersArkRif.findAll", query = "SELECT a FROM AplSistemaVersArkRif a")
public class AplSistemaVersArkRif implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSistemaVersArkRif;
    private AplSistemaVersante aplSistemaVersante;
    private UsrUser usrUser;

    public AplSistemaVersArkRif() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_SISTEMA_VERS_ARK_RIF") // @SequenceGenerator(name =
                                                                               // "APL_SISTEMA_VERS_ARK_RIF_IDSISTEMAVERSARKRIF_GENERATOR",
                                                                               // sequenceName =
                                                                               // "SAPL_SISTEMA_VERS_ARK_RIF",
                                                                               // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_SISTEMA_VERS_ARK_RIF_IDSISTEMAVERSARKRIF_GENERATOR")
    @Column(name = "ID_SISTEMA_VERS_ARK_RIF")
    public Long getIdSistemaVersArkRif() {
        return this.idSistemaVersArkRif;
    }

    public void setIdSistemaVersArkRif(Long idSistemaVersArkRif) {
        this.idSistemaVersArkRif = idSistemaVersArkRif;
    }

    // bi-directional many-to-one association to AplSistemaVersante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SISTEMA_VERSANTE")
    public AplSistemaVersante getAplSistemaVersante() {
        return this.aplSistemaVersante;
    }

    public void setAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
        this.aplSistemaVersante = aplSistemaVersante;
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
