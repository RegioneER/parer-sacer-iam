package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_ENTRY_MENU database table.
 *
 */
@Entity
@Cacheable(true)
@Table(name = "APL_ENTRY_MENU")
public class AplEntryMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEntryMenu;
    private String dlLinkEntryMenu;
    private String dsEntryMenu;
    private BigDecimal niLivelloEntryMenu;
    private BigDecimal niOrdEntryMenu;
    private String nmEntryMenu;
    private AplApplic aplApplic;
    private AplEntryMenu entryMenuPadre;
    private List<AplEntryMenu> aplEntryMenus = new ArrayList<>();
    private List<AplHelpOnLine> aplHelpOnLines = new ArrayList<>();
    private List<AplPaginaWeb> aplPaginaWebs = new ArrayList<>();
    private List<PrfAutor> prfAutors = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutorsFoglia = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutorsPadre = new ArrayList<>();

    public AplEntryMenu() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_ENTRY_MENU") // @SequenceGenerator(name =
                                                                     // "APL_ENTRY_MENU_IDENTRYMENU_GENERATOR",
                                                                     // sequenceName = "SAPL_ENTRY_MENU", allocationSize
                                                                     // = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_ENTRY_MENU_IDENTRYMENU_GENERATOR")
    @Column(name = "ID_ENTRY_MENU")
    public Long getIdEntryMenu() {
        return this.idEntryMenu;
    }

    public void setIdEntryMenu(Long idEntryMenu) {
        this.idEntryMenu = idEntryMenu;
    }

    @Column(name = "DL_LINK_ENTRY_MENU")
    public String getDlLinkEntryMenu() {
        return this.dlLinkEntryMenu;
    }

    public void setDlLinkEntryMenu(String dlLinkEntryMenu) {
        this.dlLinkEntryMenu = dlLinkEntryMenu;
    }

    @Column(name = "DS_ENTRY_MENU")
    public String getDsEntryMenu() {
        return this.dsEntryMenu;
    }

    public void setDsEntryMenu(String dsEntryMenu) {
        this.dsEntryMenu = dsEntryMenu;
    }

    @Column(name = "NI_LIVELLO_ENTRY_MENU")
    public BigDecimal getNiLivelloEntryMenu() {
        return this.niLivelloEntryMenu;
    }

    public void setNiLivelloEntryMenu(BigDecimal niLivelloEntryMenu) {
        this.niLivelloEntryMenu = niLivelloEntryMenu;
    }

    @Column(name = "NI_ORD_ENTRY_MENU")
    public BigDecimal getNiOrdEntryMenu() {
        return this.niOrdEntryMenu;
    }

    public void setNiOrdEntryMenu(BigDecimal niOrdEntryMenu) {
        this.niOrdEntryMenu = niOrdEntryMenu;
    }

    @Column(name = "NM_ENTRY_MENU")
    public String getNmEntryMenu() {
        return this.nmEntryMenu;
    }

    public void setNmEntryMenu(String nmEntryMenu) {
        this.nmEntryMenu = nmEntryMenu;
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
    @JoinColumn(name = "ID_ENTRY_MENU_PADRE")
    public AplEntryMenu getEntryMenuPadre() {
        return this.entryMenuPadre;
    }

    public void setEntryMenuPadre(AplEntryMenu entryMenuPadre) {
        this.entryMenuPadre = entryMenuPadre;
    }

    // bi-directional many-to-one association to AplEntryMenu
    @OneToMany(mappedBy = "entryMenuPadre")
    public List<AplEntryMenu> getAplEntryMenus() {
        return this.aplEntryMenus;
    }

    public void setAplEntryMenus(List<AplEntryMenu> aplEntryMenus) {
        this.aplEntryMenus = aplEntryMenus;
    }

    // bi-directional many-to-one association to AplPaginaWeb
    @OneToMany(mappedBy = "aplEntryMenu")
    public List<AplPaginaWeb> getAplPaginaWebs() {
        return this.aplPaginaWebs;
    }

    public void setAplPaginaWebs(List<AplPaginaWeb> aplPaginaWebs) {
        this.aplPaginaWebs = aplPaginaWebs;
    }

    public AplPaginaWeb addAplPaginaWeb(AplPaginaWeb aplPaginaWeb) {
        getAplPaginaWebs().add(aplPaginaWeb);
        aplPaginaWeb.setAplEntryMenu(this);

        return aplPaginaWeb;
    }

    public AplPaginaWeb removeAplPaginaWeb(AplPaginaWeb aplPaginaWeb) {
        getAplPaginaWebs().remove(aplPaginaWeb);
        aplPaginaWeb.setAplEntryMenu(null);

        return aplPaginaWeb;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplEntryMenu")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplEntryMenuFoglia")
    public List<PrfDichAutor> getPrfDichAutorsFoglia() {
        return this.prfDichAutorsFoglia;
    }

    public void setPrfDichAutorsFoglia(List<PrfDichAutor> prfDichAutorsFoglia) {
        this.prfDichAutorsFoglia = prfDichAutorsFoglia;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplEntryMenuPadre")
    public List<PrfDichAutor> getPrfDichAutorsPadre() {
        return this.prfDichAutorsPadre;
    }

    public void setPrfDichAutorsPadre(List<PrfDichAutor> prfDichAutorsPadre) {
        this.prfDichAutorsPadre = prfDichAutorsPadre;
    }

    // bi-directional many-to-one association to AplHelpOnLine
    @OneToMany(mappedBy = "aplEntryMenu")
    public List<AplHelpOnLine> getAplHelpOnLines() {
        return this.aplHelpOnLines;
    }

    public void setAplHelpOnLines(List<AplHelpOnLine> aplHelpOnLines) {
        this.aplHelpOnLines = aplHelpOnLines;
    }
}
