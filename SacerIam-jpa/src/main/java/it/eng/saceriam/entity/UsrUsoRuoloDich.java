package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the USR_USO_RUOLO_DICH database table.
 *
 */
@Entity
@Table(name = "USR_USO_RUOLO_DICH")
public class UsrUsoRuoloDich implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsoRuoloDich;
    private PrfRuolo prfRuolo;
    private UsrDichAbilOrganiz usrDichAbilOrganiz;
    private UsrOrganizIam usrOrganizIam;
    private String tiScopoRuolo;

    public UsrUsoRuoloDich() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_USO_RUOLO_DICH") // @SequenceGenerator(name =
                                                                         // "USR_USO_RUOLO_DICH_IDUSORUOLODICH_GENERATOR",
                                                                         // sequenceName = "SUSR_USO_RUOLO_DICH",
                                                                         // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_USO_RUOLO_DICH_IDUSORUOLODICH_GENERATOR")
    @Column(name = "ID_USO_RUOLO_DICH")
    public Long getIdUsoRuoloDich() {
        return this.idUsoRuoloDich;
    }

    public void setIdUsoRuoloDich(Long idUsoRuoloDich) {
        this.idUsoRuoloDich = idUsoRuoloDich;
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

    // bi-directional many-to-one association to UsrDichAbilOrganiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DICH_ABIL_ORGANIZ")
    public UsrDichAbilOrganiz getUsrDichAbilOrganiz() {
        return this.usrDichAbilOrganiz;
    }

    public void setUsrDichAbilOrganiz(UsrDichAbilOrganiz usrDichAbilOrganiz) {
        this.usrDichAbilOrganiz = usrDichAbilOrganiz;
    }

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM_RUOLO")
    public UsrOrganizIam getUsrOrganizIam() {
        return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        this.usrOrganizIam = usrOrganizIam;
    }

    @Column(name = "TI_SCOPO_RUOLO")
    public String getTiScopoRuolo() {
        return this.tiScopoRuolo;
    }

    public void setTiScopoRuolo(String tiScopoRuolo) {
        this.tiScopoRuolo = tiScopoRuolo;
    }
}