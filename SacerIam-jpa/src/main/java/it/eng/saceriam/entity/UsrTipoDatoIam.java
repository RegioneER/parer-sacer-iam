package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the USR_TIPO_DATO_IAM database table.
 *
 */
@Entity
@Table(name = "USR_TIPO_DATO_IAM")
public class UsrTipoDatoIam implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoDatoIam;
    private String dsTipoDato;
    private BigDecimal idTipoDatoApplic;
    private String nmTipoDato;
    private List<UsrAbilDati> usrAbilDatis = new ArrayList<>();
    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();
    private AplClasseTipoDato aplClasseTipoDato;
    private UsrOrganizIam usrOrganizIam;

    public UsrTipoDatoIam() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SUSR_TIPO_DATO_IAM") // @SequenceGenerator(name =
                                                                        // "USR_TIPO_DATO_IAM_IDTIPODATOIAM_GENERATOR",
                                                                        // sequenceName = "SUSR_TIPO_DATO_IAM",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_TIPO_DATO_IAM_IDTIPODATOIAM_GENERATOR")
    @Column(name = "ID_TIPO_DATO_IAM")
    public Long getIdTipoDatoIam() {
        return this.idTipoDatoIam;
    }

    public void setIdTipoDatoIam(Long idTipoDatoIam) {
        this.idTipoDatoIam = idTipoDatoIam;
    }

    @Column(name = "DS_TIPO_DATO")
    public String getDsTipoDato() {
        return this.dsTipoDato;
    }

    public void setDsTipoDato(String dsTipoDato) {
        this.dsTipoDato = dsTipoDato;
    }

    @Column(name = "ID_TIPO_DATO_APPLIC")
    public BigDecimal getIdTipoDatoApplic() {
        return this.idTipoDatoApplic;
    }

    public void setIdTipoDatoApplic(BigDecimal idTipoDatoApplic) {
        this.idTipoDatoApplic = idTipoDatoApplic;
    }

    @Column(name = "NM_TIPO_DATO")
    public String getNmTipoDato() {
        return this.nmTipoDato;
    }

    public void setNmTipoDato(String nmTipoDato) {
        this.nmTipoDato = nmTipoDato;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "usrTipoDatoIam")
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
        return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
        this.usrDichAbilDatis = usrDichAbilDatis;
    }

    // bi-directional many-to-one association to AplClasseTipoDato
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLASSE_TIPO_DATO")
    public AplClasseTipoDato getAplClasseTipoDato() {
        return this.aplClasseTipoDato;
    }

    public void setAplClasseTipoDato(AplClasseTipoDato aplClasseTipoDato) {
        this.aplClasseTipoDato = aplClasseTipoDato;
    }

    // bi-directional many-to-one association to UsrAbilDati
    @OneToMany(mappedBy = "usrTipoDatoIam")
    public List<UsrAbilDati> getUsrAbilDatis() {
        return this.usrAbilDatis;
    }

    public void setUsrAbilDatis(List<UsrAbilDati> usrAbilDatis) {
        this.usrAbilDatis = usrAbilDatis;
    }

    // bi-directional many-to-one association to UsrOrganizIam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZ_IAM")
    public UsrOrganizIam getUsrOrganizIam() {
        return this.usrOrganizIam;
    }

    public void setUsrOrganizIam(UsrOrganizIam usrOrganizIam) {
        this.usrOrganizIam = usrOrganizIam;
    }
}
