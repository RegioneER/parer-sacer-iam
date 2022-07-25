package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_COMP_SW database table.
 *
 */
@Entity
@Table(name = "APL_COMP_SW")
@NamedQuery(name = "AplCompSw.findAll", query = "SELECT a FROM AplCompSw a")
public class AplCompSw implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCompSw;
    private String nmCompSw;
    private List<AplAzioneCompSw> aplAzioneCompSws = new ArrayList<>();
    private AplApplic aplApplic;
    private LogAgente logAgente;

    public AplCompSw() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_COMP_SW") // @SequenceGenerator(name =
                                                                  // "APL_COMP_SW_IDCOMPSW_GENERATOR", sequenceName =
                                                                  // "SAPL_COMP_SW", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_COMP_SW_IDCOMPSW_GENERATOR")
    @Column(name = "ID_COMP_SW")
    public Long getIdCompSw() {
        return this.idCompSw;
    }

    public void setIdCompSw(Long idCompSw) {
        this.idCompSw = idCompSw;
    }

    @Column(name = "NM_COMP_SW")
    public String getNmCompSw() {
        return this.nmCompSw;
    }

    public void setNmCompSw(String nmCompSw) {
        this.nmCompSw = nmCompSw;
    }

    // bi-directional many-to-one association to AplAzioneCompSw
    @OneToMany(mappedBy = "aplCompSw")
    public List<AplAzioneCompSw> getAplAzioneCompSws() {
        return this.aplAzioneCompSws;
    }

    public void setAplAzioneCompSws(List<AplAzioneCompSw> aplAzioneCompSws) {
        this.aplAzioneCompSws = aplAzioneCompSws;
    }

    public AplAzioneCompSw addAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
        getAplAzioneCompSws().add(aplAzioneCompSw);
        aplAzioneCompSw.setAplCompSw(this);

        return aplAzioneCompSw;
    }

    public AplAzioneCompSw removeAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
        getAplAzioneCompSws().remove(aplAzioneCompSw);
        aplAzioneCompSw.setAplCompSw(null);

        return aplAzioneCompSw;
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

    // bi-directional many-to-one association to LogAgente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENTE")
    public LogAgente getLogAgente() {
        return this.logAgente;
    }

    public void setLogAgente(LogAgente logAgente) {
        this.logAgente = logAgente;
    }

}
