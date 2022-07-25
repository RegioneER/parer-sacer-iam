package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PRF_AUTOR database table.
 *
 */
@Entity
@Table(name = "PRF_AUTOR")
public class PrfAutor implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAutor;
    private String tiDichAutor;
    private AplAzionePagina aplAzionePagina;
    private AplEntryMenu aplEntryMenu;
    private AplPaginaWeb aplPaginaWeb;
    private AplServizioWeb aplServizioWeb;
    private PrfUsoRuoloApplic prfUsoRuoloApplic;

    public PrfAutor() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPRF_AUTOR") // @SequenceGenerator(name =
                                                                // "PRF_AUTOR_IDAUTOR_GENERATOR", sequenceName =
                                                                // "SPRF_AUTOR", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRF_AUTOR_IDAUTOR_GENERATOR")
    @Column(name = "ID_AUTOR")
    public Long getIdAutor() {
        return this.idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    @Column(name = "TI_DICH_AUTOR")
    public String getTiDichAutor() {
        return this.tiDichAutor;
    }

    public void setTiDichAutor(String tiDichAutor) {
        this.tiDichAutor = tiDichAutor;
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
    @JoinColumn(name = "ID_ENTRY_MENU")
    public AplEntryMenu getAplEntryMenu() {
        return this.aplEntryMenu;
    }

    public void setAplEntryMenu(AplEntryMenu aplEntryMenu) {
        this.aplEntryMenu = aplEntryMenu;
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