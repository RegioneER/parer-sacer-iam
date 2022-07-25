package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_SISTEMA_VERSANTE_USER_REF database table.
 *
 */
@Entity
@Table(name = "APL_SISTEMA_VERSANTE_USER_REF")
@NamedQuery(name = "AplSistemaVersanteUserRef.findAll", query = "SELECT a FROM AplSistemaVersanteUserRef a")
public class AplSistemaVersanteUserRef implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idSistemaVersanteUserRef;
    private AplSistemaVersante aplSistemaVersante;
    private UsrUser usrUser;

    public AplSistemaVersanteUserRef() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_SISTEMA_VERSANTE_USER_REF") // @SequenceGenerator(name =
                                                                                    // "APL_SISTEMA_VERSANTE_USER_REF_IDSISTEMAVERSANTEUSERREF_GENERATOR",
                                                                                    // sequenceName =
                                                                                    // "SAPL_SISTEMA_VERSANTE_USER_REF",
                                                                                    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_SISTEMA_VERSANTE_USER_REF_IDSISTEMAVERSANTEUSERREF_GENERATOR")
    @Column(name = "ID_SISTEMA_VERSANTE_USER_REF")
    public Long getIdSistemaVersanteUserRef() {
        return this.idSistemaVersanteUserRef;
    }

    public void setIdSistemaVersanteUserRef(Long idSistemaVersanteUserRef) {
        this.idSistemaVersanteUserRef = idSistemaVersanteUserRef;
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
    @JoinColumn(name = "ID_USER_IAM_REF")
    public UsrUser getUsrUser() {
        return this.usrUser;
    }

    public void setUsrUser(UsrUser usrUser) {
        this.usrUser = usrUser;
    }

}
