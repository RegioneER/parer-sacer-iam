package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_CLASSE_TIPO_DATO database table.
 * 
 */
@Entity
@Table(name = "APL_CLASSE_TIPO_DATO")
@NamedQuery(name = "AplClasseTipoDato.findAll", query = "SELECT a FROM AplClasseTipoDato a")
public class AplClasseTipoDato implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idClasseTipoDato;
    private String nmClasseTipoDato;
    private AplApplic aplApplic;
    private List<UsrDichAbilDati> usrDichAbilDatis = new ArrayList<>();
    private List<UsrTipoDatoIam> usrTipoDatoIams = new ArrayList<>();

    public AplClasseTipoDato() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_CLASSE_TIPO_DATO") // @SequenceGenerator(name =
                                                                           // "APL_CLASSE_TIPO_DATO_IDCLASSETIPODATO_GENERATOR",
                                                                           // sequenceName = "SAPL_CLASSE_TIPO_DATO",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_CLASSE_TIPO_DATO_IDCLASSETIPODATO_GENERATOR")
    @Column(name = "ID_CLASSE_TIPO_DATO")
    public Long getIdClasseTipoDato() {
        return this.idClasseTipoDato;
    }

    public void setIdClasseTipoDato(Long idClasseTipoDato) {
        this.idClasseTipoDato = idClasseTipoDato;
    }

    @Column(name = "NM_CLASSE_TIPO_DATO")
    public String getNmClasseTipoDato() {
        return this.nmClasseTipoDato;
    }

    public void setNmClasseTipoDato(String nmClasseTipoDato) {
        this.nmClasseTipoDato = nmClasseTipoDato;
    }

    // bi-directional many-to-one association to AplApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")
    public AplApplic getAplApplic() {
        return this.aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
        this.aplApplic = aplApplic;
    }

    // bi-directional many-to-one association to UsrDichAbilDati
    @OneToMany(mappedBy = "aplClasseTipoDato")
    public List<UsrDichAbilDati> getUsrDichAbilDatis() {
        return this.usrDichAbilDatis;
    }

    public void setUsrDichAbilDatis(List<UsrDichAbilDati> usrDichAbilDatis) {
        this.usrDichAbilDatis = usrDichAbilDatis;
    }

    // bi-directional many-to-one association to UsrTipoDatoIam
    @OneToMany(mappedBy = "aplClasseTipoDato")
    public List<UsrTipoDatoIam> getUsrTipoDatoIams() {
        return this.usrTipoDatoIams;
    }

    public void setUsrTipoDatoIams(List<UsrTipoDatoIam> usrTipoDatoIams) {
        this.usrTipoDatoIams = usrTipoDatoIams;
    }

}