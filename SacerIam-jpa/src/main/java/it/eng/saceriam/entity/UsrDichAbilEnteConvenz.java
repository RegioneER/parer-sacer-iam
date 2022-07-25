package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_DICH_ABIL_ENTE_CONVENZ database table.
 *
 */
@Entity
@Table(name = "USR_DICH_ABIL_ENTE_CONVENZ")
@NamedQuery(name = "UsrDichAbilEnteConvenz.findAll", query = "SELECT u FROM UsrDichAbilEnteConvenz u")
public class UsrDichAbilEnteConvenz implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idDichAbilEnteConvenz;
    private String dsCausaleDich;
    private String tiScopoDichAbilEnte;
    private List<UsrAbilEnteSiam> usrAbilEnteSiams = new ArrayList<>();
    private OrgAmbienteEnteConvenz orgAmbienteEnteConvenz;
    private OrgAppartCollegEnti orgAppartCollegEnti;
    private OrgEnteSiam orgEnteSiam;
    private UsrUser usrUser;

    public UsrDichAbilEnteConvenz() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_DICH_ABIL_ENTE_CONVENZ") // @SequenceGenerator(name =
                                                                                 // "USR_DICH_ABIL_ENTE_CONVENZ_IDDICHABILENTECONVENZ_GENERATOR",
                                                                                 // sequenceName =
                                                                                 // "SUSR_DICH_ABIL_ENTE_CONVENZ",
                                                                                 // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "USR_DICH_ABIL_ENTE_CONVENZ_IDDICHABILENTECONVENZ_GENERATOR")
    @Column(name = "ID_DICH_ABIL_ENTE_CONVENZ")
    public Long getIdDichAbilEnteConvenz() {
        return this.idDichAbilEnteConvenz;
    }

    public void setIdDichAbilEnteConvenz(Long idDichAbilEnteConvenz) {
        this.idDichAbilEnteConvenz = idDichAbilEnteConvenz;
    }

    @Column(name = "DS_CAUSALE_DICH", columnDefinition = "char")
    public String getDsCausaleDich() {
        return this.dsCausaleDich;
    }

    public void setDsCausaleDich(String dsCausaleDich) {
        this.dsCausaleDich = dsCausaleDich;
    }

    @Column(name = "TI_SCOPO_DICH_ABIL_ENTE")
    public String getTiScopoDichAbilEnte() {
        return this.tiScopoDichAbilEnte;
    }

    public void setTiScopoDichAbilEnte(String tiScopoDichAbilEnte) {
        this.tiScopoDichAbilEnte = tiScopoDichAbilEnte;
    }

    // bi-directional many-to-one association to UsrAbilEnteSiam
    @OneToMany(mappedBy = "usrDichAbilEnteConvenz")
    public List<UsrAbilEnteSiam> getUsrAbilEnteSiams() {
        return this.usrAbilEnteSiams;
    }

    public void setUsrAbilEnteSiams(List<UsrAbilEnteSiam> usrAbilEnteSiams) {
        this.usrAbilEnteSiams = usrAbilEnteSiams;
    }

    public UsrAbilEnteSiam addUsrAbilEnteSiam(UsrAbilEnteSiam usrAbilEnteSiam) {
        getUsrAbilEnteSiams().add(usrAbilEnteSiam);
        usrAbilEnteSiam.setUsrDichAbilEnteConvenz(this);

        return usrAbilEnteSiam;
    }

    public UsrAbilEnteSiam removeUsrAbilEnteSiam(UsrAbilEnteSiam usrAbilEnteSiam) {
        getUsrAbilEnteSiams().remove(usrAbilEnteSiam);
        usrAbilEnteSiam.setUsrDichAbilEnteConvenz(null);

        return usrAbilEnteSiam;
    }

    // bi-directional many-to-one association to OrgAmbienteEnteConvenz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AMBIENTE_ENTE_CONVENZ")
    public OrgAmbienteEnteConvenz getOrgAmbienteEnteConvenz() {
        return this.orgAmbienteEnteConvenz;
    }

    public void setOrgAmbienteEnteConvenz(OrgAmbienteEnteConvenz orgAmbienteEnteConvenz) {
        this.orgAmbienteEnteConvenz = orgAmbienteEnteConvenz;
    }

    // bi-directional many-to-one association to OrgAppartCollegEnti
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPART_COLLEG_ENTI")
    public OrgAppartCollegEnti getOrgAppartCollegEnti() {
        return this.orgAppartCollegEnti;
    }

    public void setOrgAppartCollegEnti(OrgAppartCollegEnti orgAppartCollegEnti) {
        this.orgAppartCollegEnti = orgAppartCollegEnti;
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

}
