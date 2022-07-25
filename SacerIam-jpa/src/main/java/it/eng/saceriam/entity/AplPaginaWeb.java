package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_PAGINA_WEB database table.
 *
 */
@Entity
@Cacheable(true)
@Table(name = "APL_PAGINA_WEB")
public class AplPaginaWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idPaginaWeb;
    private String dsPaginaWeb;
    private String nmPaginaWeb;
    private String tiHelpOnLine;
    private List<AplAzionePagina> aplAzionePaginas = new ArrayList<>();
    private AplApplic aplApplic;
    private AplEntryMenu aplEntryMenu;
    private List<PrfAutor> prfAutors = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();
    private List<AplHelpOnLine> aplHelpOnLines = new ArrayList<>();

    public AplPaginaWeb() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_PAGINA_WEB") // @SequenceGenerator(name =
                                                                     // "APL_PAGINA_WEB_IDPAGINAWEB_GENERATOR",
                                                                     // sequenceName = "SAPL_PAGINA_WEB", allocationSize
                                                                     // = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_PAGINA_WEB_IDPAGINAWEB_GENERATOR")
    @Column(name = "ID_PAGINA_WEB")
    public Long getIdPaginaWeb() {
        return this.idPaginaWeb;
    }

    public void setIdPaginaWeb(Long idPaginaWeb) {
        this.idPaginaWeb = idPaginaWeb;
    }

    @Column(name = "DS_PAGINA_WEB")
    public String getDsPaginaWeb() {
        return this.dsPaginaWeb;
    }

    public void setDsPaginaWeb(String dsPaginaWeb) {
        this.dsPaginaWeb = dsPaginaWeb;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
        return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "TI_HELP_ON_LINE")
    public String getTiHelpOnLine() {
        return this.tiHelpOnLine;
    }

    public void setTiHelpOnLine(String tiHelpOnLine) {
        this.tiHelpOnLine = tiHelpOnLine;
    }

    // bi-directional many-to-one association to AplAzionePagina
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<AplAzionePagina> getAplAzionePaginas() {
        return this.aplAzionePaginas;
    }

    public void setAplAzionePaginas(List<AplAzionePagina> aplAzionePaginas) {
        this.aplAzionePaginas = aplAzionePaginas;
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

    // bi-directional many-to-one association to AplEntryMenu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRY_MENU")
    public AplEntryMenu getAplEntryMenu() {
        return this.aplEntryMenu;
    }

    public void setAplEntryMenu(AplEntryMenu aplEntryMenu) {
        this.aplEntryMenu = aplEntryMenu;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<PrfDichAutor> getPrfDichAutors() {
        return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
        this.prfDichAutors = prfDichAutors;
    }

    // bi-directional many-to-one association to AplHelpOnLine
    @OneToMany(mappedBy = "aplPaginaWeb")
    public List<AplHelpOnLine> getAplHelpOnLines() {
        return this.aplHelpOnLines;
    }

    public void setAplHelpOnLines(List<AplHelpOnLine> aplHelpOnLines) {
        this.aplHelpOnLines = aplHelpOnLines;
    }

}
