package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_AZIONE_COMP_SW database table.
 *
 */
@Entity
@Table(name = "APL_AZIONE_COMP_SW")
@NamedQuery(name = "AplAzioneCompSw.findAll", query = "SELECT a FROM AplAzioneCompSw a")
public class AplAzioneCompSw implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAzioneCompSw;
    private String dsAzioneCompSw;
    private String nmAzioneCompSw;
    private AplCompSw aplCompSw;
    private AplTipoEvento aplTipoEvento;

    public AplAzioneCompSw() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_AZIONE_COMP_SW") // @SequenceGenerator(name =
                                                                         // "APL_AZIONE_COMP_SW_IDAZIONECOMPSW_GENERATOR",
                                                                         // sequenceName = "SAPL_AZIONE_COMP_SW",
                                                                         // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_AZIONE_COMP_SW_IDAZIONECOMPSW_GENERATOR")
    @Column(name = "ID_AZIONE_COMP_SW")
    public Long getIdAzioneCompSw() {
        return this.idAzioneCompSw;
    }

    public void setIdAzioneCompSw(Long idAzioneCompSw) {
        this.idAzioneCompSw = idAzioneCompSw;
    }

    @Column(name = "DS_AZIONE_COMP_SW")
    public String getDsAzioneCompSw() {
        return this.dsAzioneCompSw;
    }

    public void setDsAzioneCompSw(String dsAzioneCompSw) {
        this.dsAzioneCompSw = dsAzioneCompSw;
    }

    @Column(name = "NM_AZIONE_COMP_SW")
    public String getNmAzioneCompSw() {
        return this.nmAzioneCompSw;
    }

    public void setNmAzioneCompSw(String nmAzioneCompSw) {
        this.nmAzioneCompSw = nmAzioneCompSw;
    }

    // bi-directional many-to-one association to AplCompSw
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_COMP_SW")
    public AplCompSw getAplCompSw() {
        return this.aplCompSw;
    }

    public void setAplCompSw(AplCompSw aplCompSw) {
        this.aplCompSw = aplCompSw;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    public AplTipoEvento getAplTipoEvento() {
        return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
        this.aplTipoEvento = aplTipoEvento;
    }

}
