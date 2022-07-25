package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the PRF_DICH_AUTOR database table.
 *
 */
@Entity
@Table(name = "PRF_DICH_AUTOR")
public class PrfDichAutor implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idDichAutor;
    private String tiDichAutor;
    private String tiScopoDichAutor;
    private AplAzionePagina aplAzionePagina;
    private AplEntryMenu aplEntryMenuFoglia;
    private AplEntryMenu aplEntryMenuPadre;
    private AplPaginaWeb aplPaginaWeb;
    private AplServizioWeb aplServizioWeb;
    private PrfUsoRuoloApplic prfUsoRuoloApplic;

    public PrfDichAutor() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPRF_DICH_AUTOR") // @SequenceGenerator(name =
                                                                     // "PRF_DICH_AUTOR_IDDICHAUTOR_GENERATOR",
                                                                     // sequenceName = "SPRF_DICH_AUTOR", allocationSize
                                                                     // = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRF_DICH_AUTOR_IDDICHAUTOR_GENERATOR")
    @Column(name = "ID_DICH_AUTOR")
    public Long getIdDichAutor() {
        return this.idDichAutor;
    }

    public void setIdDichAutor(Long idDichAutor) {
        this.idDichAutor = idDichAutor;
    }

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
        return this.tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
    }

    @Column(name = "TI_SCOPO_DICH_AUTOR")
    public String getTiScopoDichAutor() {
        return this.tiScopoDichAutor;
    }

    public void setTiScopoDichAutor(String tiScopoDichAutor) {
        this.tiScopoDichAutor = tiScopoDichAutor;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AZIONE_PAGINA")
    public AplAzionePagina getAplAzionePagina() {
        return this.aplAzionePagina;
    }

    public void setAplAzionePagina(AplAzionePagina aplAzionePagina) {
        this.aplAzionePagina = aplAzionePagina;
    }

    // bi-directional many-to-one association to AplEntryMenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRY_MENU_FOGLIA")
    public AplEntryMenu getAplEntryMenuFoglia() {
        return this.aplEntryMenuFoglia;
    }

    public void setAplEntryMenuFoglia(AplEntryMenu aplEntryMenuFoglia) {
        this.aplEntryMenuFoglia = aplEntryMenuFoglia;
    }

    // bi-directional many-to-one association to AplEntryMenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRY_MENU_PADRE")
    public AplEntryMenu getAplEntryMenuPadre() {
        return this.aplEntryMenuPadre;
    }

    public void setAplEntryMenuPadre(AplEntryMenu aplEntryMenuPadre) {
        this.aplEntryMenuPadre = aplEntryMenuPadre;
    }

    // bi-directional many-to-one association to AplPaginaWeb
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PAGINA_WEB")
    public AplPaginaWeb getAplPaginaWeb() {
        return this.aplPaginaWeb;
    }

    public void setAplPaginaWeb(AplPaginaWeb aplPaginaWeb) {
        this.aplPaginaWeb = aplPaginaWeb;
    }

    // bi-directional many-to-one association to AplServizioWeb
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SERVIZIO_WEB")
    public AplServizioWeb getAplServizioWeb() {
        return this.aplServizioWeb;
    }

    public void setAplServizioWeb(AplServizioWeb aplServizioWeb) {
        this.aplServizioWeb = aplServizioWeb;
    }

    // bi-directional many-to-one association to PrfUsoRuoloApplic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USO_RUOLO_APPLIC")
    public PrfUsoRuoloApplic getPrfUsoRuoloApplic() {
        return this.prfUsoRuoloApplic;
    }

    public void setPrfUsoRuoloApplic(PrfUsoRuoloApplic prfUsoRuoloApplic) {
        this.prfUsoRuoloApplic = prfUsoRuoloApplic;
    }
}