package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_TIPO_EVENTO_OGGETTO_TRIG database table.
 *
 */
@Entity
@Table(name = "APL_TIPO_EVENTO_OGGETTO_TRIG")
@NamedQuery(name = "AplTipoEventoOggettoTrig.findAll", query = "SELECT a FROM AplTipoEventoOggettoTrig a")
public class AplTipoEventoOggettoTrig implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoEventoOggettoTrig;
    private AplQueryTipoOggetto aplQueryTipoOggetto1;
    private AplQueryTipoOggetto aplQueryTipoOggetto2;
    private AplTipoEvento aplTipoEvento;
    private AplTipoEventoOggetto aplTipoEventoOggetto;
    private AplTipoOggetto aplTipoOggetto;

    public AplTipoEventoOggettoTrig() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_TIPO_EVENTO_OGGETTO_TRIG") // @SequenceGenerator(name =
                                                                                   // "APL_TIPO_EVENTO_OGGETTO_TRIG_IDTIPOEVENTOOGGETTOTRIG_GENERATOR",
                                                                                   // sequenceName =
                                                                                   // "SAPL_TIPO_EVENTO_OGGETTO_TRIG",
                                                                                   // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_TIPO_EVENTO_OGGETTO_TRIG_IDTIPOEVENTOOGGETTOTRIG_GENERATOR")
    @Column(name = "ID_TIPO_EVENTO_OGGETTO_TRIG")
    public Long getIdTipoEventoOggettoTrig() {
        return this.idTipoEventoOggettoTrig;
    }

    public void setIdTipoEventoOggettoTrig(Long idTipoEventoOggettoTrig) {
        this.idTipoEventoOggettoTrig = idTipoEventoOggettoTrig;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUERY_TIPO_OGGETTO_TRIG")
    public AplQueryTipoOggetto getAplQueryTipoOggetto1() {
        return this.aplQueryTipoOggetto1;
    }

    public void setAplQueryTipoOggetto1(AplQueryTipoOggetto aplQueryTipoOggetto1) {
        this.aplQueryTipoOggetto1 = aplQueryTipoOggetto1;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUERY_TIPO_OGGETTO_SEL")
    public AplQueryTipoOggetto getAplQueryTipoOggetto2() {
        return this.aplQueryTipoOggetto2;
    }

    public void setAplQueryTipoOggetto2(AplQueryTipoOggetto aplQueryTipoOggetto2) {
        this.aplQueryTipoOggetto2 = aplQueryTipoOggetto2;
    }

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO_TRIG")
    public AplTipoEvento getAplTipoEvento() {
        return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
        this.aplTipoEvento = aplTipoEvento;
    }

    // bi-directional many-to-one association to AplTipoEventoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO_OGGETTO")
    public AplTipoEventoOggetto getAplTipoEventoOggetto() {
        return this.aplTipoEventoOggetto;
    }

    public void setAplTipoEventoOggetto(AplTipoEventoOggetto aplTipoEventoOggetto) {
        this.aplTipoEventoOggetto = aplTipoEventoOggetto;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_TRIG")
    public AplTipoOggetto getAplTipoOggetto() {
        return this.aplTipoOggetto;
    }

    public void setAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        this.aplTipoOggetto = aplTipoOggetto;
    }

}
