package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_QUERY_TIPO_OGGETTO database table.
 *
 */
@Entity
@Table(name = "APL_QUERY_TIPO_OGGETTO")
@NamedQuery(name = "AplQueryTipoOggetto.findAll", query = "SELECT a FROM AplQueryTipoOggetto a")
public class AplQueryTipoOggetto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idQueryTipoOggetto;
    private String blQueryTipoOggetto;
    private String nmQueryTipoOggetto;
    private String tipoUsoQuery;
    private AplTipoOggetto aplTipoOggetto1;
    private AplTipoOggetto aplTipoOggetto2;
    private AplTipoOggetto aplTipoOggetto3;
    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs1 = new ArrayList<>();
    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs2 = new ArrayList<>();

    public AplQueryTipoOggetto() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_QUERY_TIPO_OGGETTO") // @SequenceGenerator(name =
                                                                             // "APL_QUERY_TIPO_OGGETTO_IDQUERYTIPOOGGETTO_GENERATOR",
                                                                             // sequenceName =
                                                                             // "SAPL_QUERY_TIPO_OGGETTO",
                                                                             // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "APL_QUERY_TIPO_OGGETTO_IDQUERYTIPOOGGETTO_GENERATOR")
    @Column(name = "ID_QUERY_TIPO_OGGETTO")
    public Long getIdQueryTipoOggetto() {
        return this.idQueryTipoOggetto;
    }

    public void setIdQueryTipoOggetto(Long idQueryTipoOggetto) {
        this.idQueryTipoOggetto = idQueryTipoOggetto;
    }

    @Lob
    @Column(name = "BL_QUERY_TIPO_OGGETTO")
    public String getBlQueryTipoOggetto() {
        return this.blQueryTipoOggetto;
    }

    public void setBlQueryTipoOggetto(String blQueryTipoOggetto) {
        this.blQueryTipoOggetto = blQueryTipoOggetto;
    }

    @Column(name = "NM_QUERY_TIPO_OGGETTO")
    public String getNmQueryTipoOggetto() {
        return this.nmQueryTipoOggetto;
    }

    public void setNmQueryTipoOggetto(String nmQueryTipoOggetto) {
        this.nmQueryTipoOggetto = nmQueryTipoOggetto;
    }

    @Column(name = "TIPO_USO_QUERY")
    public String getTipoUsoQuery() {
        return this.tipoUsoQuery;
    }

    public void setTipoUsoQuery(String tipoUsoQuery) {
        this.tipoUsoQuery = tipoUsoQuery;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_SEL")
    public AplTipoOggetto getAplTipoOggetto1() {
        return this.aplTipoOggetto1;
    }

    public void setAplTipoOggetto1(AplTipoOggetto aplTipoOggetto1) {
        this.aplTipoOggetto1 = aplTipoOggetto1;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO")
    public AplTipoOggetto getAplTipoOggetto2() {
        return this.aplTipoOggetto2;
    }

    public void setAplTipoOggetto2(AplTipoOggetto aplTipoOggetto2) {
        this.aplTipoOggetto2 = aplTipoOggetto2;
    }

    // bi-directional many-to-one association to AplTipoOggetto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_OGGETTO_ACCESSO")
    public AplTipoOggetto getAplTipoOggetto3() {
        return this.aplTipoOggetto3;
    }

    public void setAplTipoOggetto3(AplTipoOggetto aplTipoOggetto3) {
        this.aplTipoOggetto3 = aplTipoOggetto3;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplQueryTipoOggetto1")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs1() {
        return this.aplTipoEventoOggettoTrigs1;
    }

    public void setAplTipoEventoOggettoTrigs1(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs1) {
        this.aplTipoEventoOggettoTrigs1 = aplTipoEventoOggettoTrigs1;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrigs1(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs1) {
        getAplTipoEventoOggettoTrigs1().add(aplTipoEventoOggettoTrigs1);
        aplTipoEventoOggettoTrigs1.setAplQueryTipoOggetto1(this);

        return aplTipoEventoOggettoTrigs1;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrigs1(
            AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs1) {
        getAplTipoEventoOggettoTrigs1().remove(aplTipoEventoOggettoTrigs1);
        aplTipoEventoOggettoTrigs1.setAplQueryTipoOggetto1(null);

        return aplTipoEventoOggettoTrigs1;
    }

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplQueryTipoOggetto2")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs2() {
        return this.aplTipoEventoOggettoTrigs2;
    }

    public void setAplTipoEventoOggettoTrigs2(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs2) {
        this.aplTipoEventoOggettoTrigs2 = aplTipoEventoOggettoTrigs2;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrigs2(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs2) {
        getAplTipoEventoOggettoTrigs2().add(aplTipoEventoOggettoTrigs2);
        aplTipoEventoOggettoTrigs2.setAplQueryTipoOggetto2(this);

        return aplTipoEventoOggettoTrigs2;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrigs2(
            AplTipoEventoOggettoTrig aplTipoEventoOggettoTrigs2) {
        getAplTipoEventoOggettoTrigs2().remove(aplTipoEventoOggettoTrigs2);
        aplTipoEventoOggettoTrigs2.setAplQueryTipoOggetto2(null);

        return aplTipoEventoOggettoTrigs2;
    }

}
