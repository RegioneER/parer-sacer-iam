package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the PRF_USO_RUOLO_APPLIC database table.
 *
 */
@Entity
@Table(name = "PRF_USO_RUOLO_APPLIC")
public class PrfUsoRuoloApplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUsoRuoloApplic;
    private List<PrfAutor> prfAutors = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();
    private AplApplic aplApplic;
    private PrfRuolo prfRuolo;

    public PrfUsoRuoloApplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPRF_USO_RUOLO_APPLIC") // @SequenceGenerator(name =
                                                                           // "PRF_USO_RUOLO_APPLIC_IDUSORUOLOAPPLIC_GENERATOR",
                                                                           // sequenceName = "SPRF_USO_RUOLO_APPLIC",
                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "PRF_USO_RUOLO_APPLIC_IDUSORUOLOAPPLIC_GENERATOR")
    @Column(name = "ID_USO_RUOLO_APPLIC")
    public Long getIdUsoRuoloApplic() {
        return this.idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(Long idUsoRuoloApplic) {
        this.idUsoRuoloApplic = idUsoRuoloApplic;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "prfUsoRuoloApplic")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "prfUsoRuoloApplic", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH })
    public List<PrfDichAutor> getPrfDichAutors() {
        return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
        this.prfDichAutors = prfDichAutors;
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

    // bi-directional many-to-one association to PrfRuolo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
        return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
        this.prfRuolo = prfRuolo;
    }
}