package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_TIPO_OGGETTO database table.
 *
 */
@Entity
@Table(name = "APL_TIPO_OGGETTO")
@NamedQuery(name = "AplTipoOggetto.findAll", query = "SELECT a FROM AplTipoOggetto a")
public class AplTipoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoOggetto;
    private String nmTipoOggetto;
    private List<AplQueryTipoOggetto> aplQueryTipoOggettos1 = new ArrayList<>();
    private List<AplQueryTipoOggetto> aplQueryTipoOggettos2 = new ArrayList<>();
    private List<AplQueryTipoOggetto> aplQueryTipoOggettos3 = new ArrayList<>();
    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();
    private AplApplic aplApplic;
    private AplTipoOggetto aplTipoOggetto;
    private List<AplTipoOggetto> aplTipoOggettos = new ArrayList<>();

    public AplTipoOggetto() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_TIPO_OGGETTO") // @SequenceGenerator(name =
                                                                       // "APL_TIPO_OGGETTO_IDTIPOOGGETTO_GENERATOR",
                                                                       // sequenceName = "SAPL_TIPO_OGGETTO",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_TIPO_OGGETTO_IDTIPOOGGETTO_GENERATOR")
    @Column(name = "ID_TIPO_OGGETTO")
    public Long getIdTipoOggetto() {
        return this.idTipoOggetto;
    }

    public void setIdTipoOggetto(Long idTipoOggetto) {
        this.idTipoOggetto = idTipoOggetto;
    }

    @Column(name = "NM_TIPO_OGGETTO")
    public String getNmTipoOggetto() {
        return this.nmTipoOggetto;
    }

    public void setNmTipoOggetto(String nmTipoOggetto) {
        this.nmTipoOggetto = nmTipoOggetto;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto1")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos1() {
        return this.aplQueryTipoOggettos1;
    }

    public void setAplQueryTipoOggettos1(List<AplQueryTipoOggetto> aplQueryTipoOggettos1) {
        this.aplQueryTipoOggettos1 = aplQueryTipoOggettos1;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos1(AplQueryTipoOggetto aplQueryTipoOggettos1) {
        getAplQueryTipoOggettos1().add(aplQueryTipoOggettos1);
        aplQueryTipoOggettos1.setAplTipoOggetto1(this);

        return aplQueryTipoOggettos1;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos1(AplQueryTipoOggetto aplQueryTipoOggettos1) {
        getAplQueryTipoOggettos1().remove(aplQueryTipoOggettos1);
        aplQueryTipoOggettos1.setAplTipoOggetto1(null);

        return aplQueryTipoOggettos1;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto2")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos2() {
        return this.aplQueryTipoOggettos2;
    }

    public void setAplQueryTipoOggettos2(List<AplQueryTipoOggetto> aplQueryTipoOggettos2) {
        this.aplQueryTipoOggettos2 = aplQueryTipoOggettos2;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos2(AplQueryTipoOggetto aplQueryTipoOggettos2) {
        getAplQueryTipoOggettos2().add(aplQueryTipoOggettos2);
        aplQueryTipoOggettos2.setAplTipoOggetto2(this);

        return aplQueryTipoOggettos2;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos2(AplQueryTipoOggetto aplQueryTipoOggettos2) {
        getAplQueryTipoOggettos2().remove(aplQueryTipoOggettos2);
        aplQueryTipoOggettos2.setAplTipoOggetto2(null);

        return aplQueryTipoOggettos2;
    }

    // bi-directional many-to-one association to AplQueryTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto3")
    public List<AplQueryTipoOggetto> getAplQueryTipoOggettos3() {
        return this.aplQueryTipoOggettos3;
    }

    public void setAplQueryTipoOggettos3(List<AplQueryTipoOggetto> aplQueryTipoOggettos3) {
        this.aplQueryTipoOggettos3 = aplQueryTipoOggettos3;
    }

    public AplQueryTipoOggetto addAplQueryTipoOggettos3(AplQueryTipoOggetto aplQueryTipoOggettos3) {
        getAplQueryTipoOggettos3().add(aplQueryTipoOggettos3);
        aplQueryTipoOggettos3.setAplTipoOggetto3(this);

        return aplQueryTipoOggettos3;
    }

    public AplQueryTipoOggetto removeAplQueryTipoOggettos3(AplQueryTipoOggetto aplQueryTipoOggettos3) {
        getAplQueryTipoOggettos3().remove(aplQueryTipoOggettos3);
        aplQueryTipoOggettos3.setAplTipoOggetto3(null);

        return aplQueryTipoOggettos3;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoOggetto")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs() {
        return this.aplTipoEventoOggettoTrigs;
    }

    public void setAplTipoEventoOggettoTrigs(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs) {
        this.aplTipoEventoOggettoTrigs = aplTipoEventoOggettoTrigs;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().add(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoOggetto(this);

        return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoOggetto(null);

        return aplTipoEventoOggettoTrig;
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

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_PADRE")
    public AplTipoOggetto getAplTipoOggetto() {
        return this.aplTipoOggetto;
    }

    public void setAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        this.aplTipoOggetto = aplTipoOggetto;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @OneToMany(mappedBy = "aplTipoOggetto")
    public List<AplTipoOggetto> getAplTipoOggettos() {
        return this.aplTipoOggettos;
    }

    public void setAplTipoOggettos(List<AplTipoOggetto> aplTipoOggettos) {
        this.aplTipoOggettos = aplTipoOggettos;
    }

    public AplTipoOggetto addAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        getAplTipoOggettos().add(aplTipoOggetto);
        aplTipoOggetto.setAplTipoOggetto(this);

        return aplTipoOggetto;
    }

    public AplTipoOggetto removeAplTipoOggetto(AplTipoOggetto aplTipoOggetto) {
        getAplTipoOggettos().remove(aplTipoOggetto);
        aplTipoOggetto.setAplTipoOggetto(null);

        return aplTipoOggetto;
    }

}
