package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_TIPO_EVENTO database table.
 *
 */
@Entity
@Table(name = "APL_TIPO_EVENTO")
@NamedQuery(name = "AplTipoEvento.findAll", query = "SELECT a FROM AplTipoEvento a")
public class AplTipoEvento implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoEvento;
    private String nmTipoEvento;
    private String tipoClasseEvento;
    private String tipoClassePremisEvento;
    private String tipoOrigineEvento;
    private List<AplAzioneCompSw> aplAzioneCompSws = new ArrayList<>();
    private List<AplAzionePagina> aplAzionePaginas = new ArrayList<>();
    private AplApplic aplApplic;
    private List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs = new ArrayList<>();

    public AplTipoEvento() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_TIPO_EVENTO") // @SequenceGenerator(name =
                                                                      // "APL_TIPO_EVENTO_IDTIPOEVENTO_GENERATOR",
                                                                      // sequenceName = "SAPL_TIPO_EVENTO",
                                                                      // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_TIPO_EVENTO_IDTIPOEVENTO_GENERATOR")
    @Column(name = "ID_TIPO_EVENTO")
    public Long getIdTipoEvento() {
        return this.idTipoEvento;
    }

    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    @Column(name = "NM_TIPO_EVENTO")
    public String getNmTipoEvento() {
        return this.nmTipoEvento;
    }

    public void setNmTipoEvento(String nmTipoEvento) {
        this.nmTipoEvento = nmTipoEvento;
    }

    @Column(name = "TIPO_CLASSE_EVENTO")
    public String getTipoClasseEvento() {
        return this.tipoClasseEvento;
    }

    public void setTipoClasseEvento(String tipoClasseEvento) {
        this.tipoClasseEvento = tipoClasseEvento;
    }

    @Column(name = "TIPO_CLASSE_PREMIS_EVENTO")
    public String getTipoClassePremisEvento() {
        return this.tipoClassePremisEvento;
    }

    public void setTipoClassePremisEvento(String tipoClassePremisEvento) {
        this.tipoClassePremisEvento = tipoClassePremisEvento;
    }

    @Column(name = "TIPO_ORIGINE_EVENTO")
    public String getTipoOrigineEvento() {
        return this.tipoOrigineEvento;
    }

    public void setTipoOrigineEvento(String tipoOrigineEvento) {
        this.tipoOrigineEvento = tipoOrigineEvento;
    }

    // bi-directional many-to-one association to AplAzioneCompSw
    @OneToMany(mappedBy = "aplTipoEvento")
    public List<AplAzioneCompSw> getAplAzioneCompSws() {
        return this.aplAzioneCompSws;
    }

    public void setAplAzioneCompSws(List<AplAzioneCompSw> aplAzioneCompSws) {
        this.aplAzioneCompSws = aplAzioneCompSws;
    }

    public AplAzioneCompSw addAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
        getAplAzioneCompSws().add(aplAzioneCompSw);
        aplAzioneCompSw.setAplTipoEvento(this);

        return aplAzioneCompSw;
    }

    public AplAzioneCompSw removeAplAzioneCompSw(AplAzioneCompSw aplAzioneCompSw) {
        getAplAzioneCompSws().remove(aplAzioneCompSw);
        aplAzioneCompSw.setAplTipoEvento(null);

        return aplAzioneCompSw;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @OneToMany(mappedBy = "aplTipoEvento")
    public List<AplAzionePagina> getAplAzionePaginas() {
        return this.aplAzionePaginas;
    }

    public void setAplAzionePaginas(List<AplAzionePagina> aplAzionePaginas) {
        this.aplAzionePaginas = aplAzionePaginas;
    }

    public AplAzionePagina addAplAzionePagina(AplAzionePagina aplAzionePagina) {
        getAplAzionePaginas().add(aplAzionePagina);
        aplAzionePagina.setAplTipoEvento(this);

        return aplAzionePagina;
    }

    public AplAzionePagina removeAplAzionePagina(AplAzionePagina aplAzionePagina) {
        getAplAzionePaginas().remove(aplAzionePagina);
        aplAzionePagina.setAplTipoEvento(null);

        return aplAzionePagina;
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

    // bi-directional many-to-one association to AplTipoEventoOggettoTrig
    @OneToMany(mappedBy = "aplTipoEvento")
    public List<AplTipoEventoOggettoTrig> getAplTipoEventoOggettoTrigs() {
        return this.aplTipoEventoOggettoTrigs;
    }

    public void setAplTipoEventoOggettoTrigs(List<AplTipoEventoOggettoTrig> aplTipoEventoOggettoTrigs) {
        this.aplTipoEventoOggettoTrigs = aplTipoEventoOggettoTrigs;
    }

    public AplTipoEventoOggettoTrig addAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().add(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEvento(this);

        return aplTipoEventoOggettoTrig;
    }

    public AplTipoEventoOggettoTrig removeAplTipoEventoOggettoTrig(AplTipoEventoOggettoTrig aplTipoEventoOggettoTrig) {
        getAplTipoEventoOggettoTrigs().remove(aplTipoEventoOggettoTrig);
        aplTipoEventoOggettoTrig.setAplTipoEvento(null);

        return aplTipoEventoOggettoTrig;
    }

}
