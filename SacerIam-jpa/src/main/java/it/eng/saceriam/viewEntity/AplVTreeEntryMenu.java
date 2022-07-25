package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the APL_V_TREE_ENTRY_MENU database table.
 *
 */
@Entity
@Table(name = "APL_V_TREE_ENTRY_MENU")
public class AplVTreeEntryMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dlCompositoEntryMenu;
    private String dlLinkEntryMenu;
    private String dlPathIdEntryMenu;
    private String dsEntryMenu;
    private BigDecimal idApplic;
    private BigDecimal idEntryMenu;
    private BigDecimal idEntryMenuPadre;
    private BigDecimal niLivelloEntryMenu;
    private BigDecimal niOrdEntryMenu;
    private String nmEntryMenu;

    public AplVTreeEntryMenu() {
    }

    @Column(name = "DL_COMPOSITO_ENTRY_MENU")
    public String getDlCompositoEntryMenu() {
        return this.dlCompositoEntryMenu;
    }

    public void setDlCompositoEntryMenu(String dlCompositoEntryMenu) {
        this.dlCompositoEntryMenu = dlCompositoEntryMenu;
    }

    @Column(name = "DL_LINK_ENTRY_MENU")
    public String getDlLinkEntryMenu() {
        return this.dlLinkEntryMenu;
    }

    public void setDlLinkEntryMenu(String dlLinkEntryMenu) {
        this.dlLinkEntryMenu = dlLinkEntryMenu;
    }

    @Column(name = "DL_PATH_ID_ENTRY_MENU")
    public String getDlPathIdEntryMenu() {
        return this.dlPathIdEntryMenu;
    }

    public void setDlPathIdEntryMenu(String dlPathIdEntryMenu) {
        this.dlPathIdEntryMenu = dlPathIdEntryMenu;
    }

    @Column(name = "DS_ENTRY_MENU")
    public String getDsEntryMenu() {
        return this.dsEntryMenu;
    }

    public void setDsEntryMenu(String dsEntryMenu) {
        this.dsEntryMenu = dsEntryMenu;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Id
    @Column(name = "ID_ENTRY_MENU")
    public BigDecimal getIdEntryMenu() {
        return this.idEntryMenu;
    }

    public void setIdEntryMenu(BigDecimal idEntryMenu) {
        this.idEntryMenu = idEntryMenu;
    }

    @Column(name = "ID_ENTRY_MENU_PADRE")
    public BigDecimal getIdEntryMenuPadre() {
        return this.idEntryMenuPadre;
    }

    public void setIdEntryMenuPadre(BigDecimal idEntryMenuPadre) {
        this.idEntryMenuPadre = idEntryMenuPadre;
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
}