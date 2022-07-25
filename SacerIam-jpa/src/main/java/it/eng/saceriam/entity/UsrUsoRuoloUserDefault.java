package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_USO_RUOLO_USER_DEFAULT database table.
 *
 */
@Entity
@Table(name = "USR_USO_RUOLO_USER_DEFAULT")
public class UsrUsoRuoloUserDefault implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsoRuoloUserDefault;
    private PrfRuolo prfRuolo;
    private UsrUsoUserApplic usrUsoUserApplic;

    public UsrUsoRuoloUserDefault() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_USO_RUOLO_USER_DEFAULT") // @SequenceGenerator(name =
                                                                                 // "USR_USO_RUOLO_USER_DEFAULT_IDUSORUOLOUSERDEFAULT_GENERATOR",
                                                                                 // sequenceName =
                                                                                 // "SUSR_USO_RUOLO_USER_DEFAULT",
                                                                                 // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "USR_USO_RUOLO_USER_DEFAULT_IDUSORUOLOUSERDEFAULT_GENERATOR")
    @Column(name = "ID_USO_RUOLO_USER_DEFAULT")
    public Long getIdUsoRuoloUserDefault() {
        return this.idUsoRuoloUserDefault;
    }

    public void setIdUsoRuoloUserDefault(Long idUsoRuoloUserDefault) {
        this.idUsoRuoloUserDefault = idUsoRuoloUserDefault;
    }

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
        return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
        this.prfRuolo = prfRuolo;
    }

    // bi-directional many-to-one association to UsrUsoUserApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_USER_APPLIC")
    public UsrUsoUserApplic getUsrUsoUserApplic() {
        return this.usrUsoUserApplic;
    }

    public void setUsrUsoUserApplic(UsrUsoUserApplic usrUsoUserApplic) {
        this.usrUsoUserApplic = usrUsoUserApplic;
    }
}