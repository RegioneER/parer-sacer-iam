package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_TIPO_EVENTO_OGGETTO database table.
 *
 */
@Entity
@Table(name = "APL_TIPO_EVENTO_OGGETTO")
@NamedQuery(name = "AplTipoEventoOggetto.findAll", query = "SELECT a FROM AplTipoEventoOggetto a")
public class AplTipoEventoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoEventoOggetto;
    private BigDecimal idQueryTipoOggettoFoto;
    private BigDecimal idTipoEvento;
    private BigDecimal idTipoOggetto;
    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();

    public AplTipoEventoOggetto() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_TIPO_EVENTO_OGGETTO") // @SequenceGenerator(name =
                                                                              // "APL_TIPO_EVENTO_OGGETTO_IDTIPOEVENTOOGGETTO_GENERATOR",
                                                                              // sequenceName =
                                                                              // "SAPL_TIPO_EVENTO_OGGETTO",
                                                                              // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_TIPO_EVENTO_OGGETTO_IDTIPOEVENTOOGGETTO_GENERATOR")
    @Column(name = "ID_TIPO_EVENTO_OGGETTO")
    public Long getIdTipoEventoOggetto() {
        return this.idTipoEventoOggetto;
    }

    public void setIdTipoEventoOggetto(Long idTipoEventoOggetto) {
        this.idTipoEventoOggetto = idTipoEventoOggetto;
    }

    @Column(name = "ID_QUERY_TIPO_OGGETTO_FOTO")
    public BigDecimal getIdQueryTipoOggettoFoto() {
        return this.idQueryTipoOggettoFoto;
    }

    public void setIdQueryTipoOggettoFoto(BigDecimal idQueryTipoOggettoFoto) {
        this.idQueryTipoOggettoFoto = idQueryTipoOggettoFoto;
    }

    @Column(name = "ID_TIPO_EVENTO")
    public BigDecimal getIdTipoEvento() {
        return this.idTipoEvento;
    }

    public void setIdTipoEvento(BigDecimal idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    @Column(name = "ID_TIPO_OGGETTO")
    public BigDecimal getIdTipoOggetto() {
        return this.idTipoOggetto;
    }

    public void setIdTipoOggetto(BigDecimal idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoEventoOggetto")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs() {
        return this.aplTipoEventoOggettoTrigs;
    }

    public void setAplTipoEventoOggettoTrigs(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs) {
        this.aplTipoEventoOggettoTrigs = aplTipoEventoOggettoTrigs;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().add(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEventoOggetto(this);

        return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEventoOggetto(null);

        return aplTipoEventoOggettoTrig;
    }

}
