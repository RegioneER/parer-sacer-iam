package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the PRF_RUOLO_CATEGORIA database table.
 *
 */
@Entity
@Table(name = "PRF_RUOLO_CATEGORIA")
public class PrfRuoloCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idRuoloCategoria;
    private String tiCategRuolo;
    private PrfRuolo prfRuolo;

    public PrfRuoloCategoria() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPRF_RUOLO_CATEGORIA") // @SequenceGenerator(name =
                                                                          // "PRF_RUOLO_CATEGORIA_IDRUOLOCATEGORIA_GENERATOR",
                                                                          // sequenceName = "SPRF_RUOLO_CATEGORIA",
                                                                          // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRF_RUOLO_CATEGORIA_IDRUOLOCATEGORIA_GENERATOR")
    @Column(name = "ID_RUOLO_CATEGORIA")
    public Long getIdRuoloCategoria() {
        return this.idRuoloCategoria;
    }

    public void setIdRuoloCategoria(Long idRuoloCatgoria) {
        this.idRuoloCategoria = idRuoloCatgoria;
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

    @Column(name = "TI_CATEG_RUOLO")
    public String getTiCategRuolo() {
        return this.tiCategRuolo;
    }

    public void setTiCategRuolo(String tiCategRuolo) {
        this.tiCategRuolo = tiCategRuolo;
    }
}