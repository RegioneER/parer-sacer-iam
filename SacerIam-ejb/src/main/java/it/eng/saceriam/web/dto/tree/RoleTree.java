package it.eng.saceriam.web.dto.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.entity.constraint.ConstPrfDichAutor.TiDichAutor;
import it.eng.saceriam.exception.ParerUserError;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorRowBean;
import it.eng.saceriam.slite.gen.viewbean.PrfVLisDichAutorTableBean;

/**
 * Struttura dati atta alla gestione dell'alberatura dei ruoli
 *
 * Questa classe gestisce l'elemento root dell'albero che gestisce i ruoli, contenendo tutte le informazioni necessarie
 * per l'intera gestione
 *
 * @author Bonora_L
 */
public class RoleTree extends RoleTreeElement {

    /**
     * Tipo di albero da creare, corrisponde a {@link TiDichAutor}
     */
    private final String treeType;
    /**
     * Id dell'applicazione assegnata al ruolo
     */
    private final BigDecimal idApplication;
    /**
     * Nome dell'applicazione assegnata al ruolo
     */
    private final String nmApplication;
    /**
     * Collezione di dichiarazioni di elementi figli da eliminare
     */
    private final Set<BigDecimal> idDichAutorsToDelete;
    /**
     * True se l'albero ha ricevuto modifiche
     */
    private boolean edited = false;
    /**
     * Collezione di elementi selezionati, corrisponde alla get_checked di jstree
     */
    private List<String> selectedNodes;
    /**
     * Collezione di elementi di tipo MENU selezionati, necessario nel caso di albero di azioni, per tenere la lista di
     * menu selezionati nell'albero dei menu
     */
    private List<String> menuNodes;
    /**
     * Numero totale di nodi di tipo menu
     */
    private BigDecimal totalMenuNodes;
    /**
     * Numero totale di nodi di tipo pagina
     */
    private BigDecimal totalPageNodes;
    /**
     * Numero totale di nodi di tipo azione
     */
    private BigDecimal totalActionNodes;

    public RoleTree(BigDecimal idApplication, String nmApplication, String treeType, String type) {
        super(type, null, 1);
        this.treeType = treeType;
        this.idApplication = idApplication;
        this.nmApplication = nmApplication;
        this.idDichAutorsToDelete = new HashSet<>();
        this.selectedNodes = new ArrayList<>();
    }

    public RoleTree(BigDecimal idApplication, String nmApplication, String idRootElement, String treeType,
            String type) {
        super(type, idRootElement, 1);
        this.treeType = treeType;
        this.idApplication = idApplication;
        this.nmApplication = nmApplication;
        this.idDichAutorsToDelete = new HashSet<>();
        this.selectedNodes = new ArrayList<>();
    }

    public RoleTree(BigDecimal idDichAuthor, BigDecimal idApplication, String nmApplication, String idRootElement,
            String treeType, String type) {
        super(type, idDichAuthor, idRootElement, 1);
        this.treeType = treeType;
        this.idApplication = idApplication;
        this.nmApplication = nmApplication;
        this.idDichAutorsToDelete = new HashSet<>();
        this.selectedNodes = new ArrayList<>();
        if (getIdDichAuthor() != null) {
            setSelected(true);
            selectedNodes.add(idRootElement);
        }
    }

    public BigDecimal getIdApplication() {
        return idApplication;
    }

    public String getNmApplication() {
        return nmApplication;
    }

    public String getTreeType() {
        return treeType;
    }

    public Set<BigDecimal> getIdDichAuthorsToDelete() {
        return idDichAutorsToDelete;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setSelectedNodes(String[] nodes) {
        List<String> list = new ArrayList<>();
        if (nodes != null) {
            list = Arrays.asList(nodes);
        }
        this.selectedNodes = list;
    }

    public List<String> getSelectedNodes() {
        return this.selectedNodes;
    }

    public void setMenuNodes(String[] nodes) {
        List<String> list = new ArrayList<>();
        if (nodes != null) {
            list = Arrays.asList(nodes);
        }
        this.menuNodes = list;
    }

    public List<String> getMenuNodes() {
        return this.menuNodes;
    }

    public BigDecimal getTotalMenuNodes() {
        return totalMenuNodes;
    }

    public void setTotalMenuNodes(BigDecimal totalMenuNodes) {
        this.totalMenuNodes = totalMenuNodes;
    }

    public BigDecimal getTotalPageNodes() {
        return totalPageNodes;
    }

    public void setTotalPageNodes(BigDecimal totalPageNodes) {
        this.totalPageNodes = totalPageNodes;
    }

    public BigDecimal getTotalActionNodes() {
        return totalActionNodes;
    }

    public void setTotalActionNodes(BigDecimal totalActionNodes) {
        this.totalActionNodes = totalActionNodes;
    }

    /**
     * Ritorna il tableBean necessario per il salvataggio delle autorizzazioni. Contemporaneamente alla creazione delle
     * righe eseguo anche il controllo sui nodi selezionati
     *
     * @return il tableBean dei nodi selezionati
     * 
     * @throws it.eng.saceriam.exception.ParerUserError
     *             errore di incoerenza nei nodi
     */
    public PrfVLisDichAutorTableBean getSelectedNodesTableBean() throws ParerUserError {
        PrfVLisDichAutorTableBean table = new PrfVLisDichAutorTableBean();
        // Scorro ogni nodo dell'albero
        // Verifico ricorsivamente se il nodo è tra i nodi da salvare e se era già salvato (== ha idDichAutor != null)
        // Non fare remove sui nodi selezionati, rischi che in caso di errore non si possa ricaricare l'albero
        if (getIdDichAuthor() != null && getSelectedNodes().contains(getIdElement())) {
            /*
             * Se è selezionato il padre, non sto nemmeno a guardare i figli dato che ha anche già la dichiarazione
             *
             */
            table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(), getIdDichAuthor(),
                    getTreeType(), ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(), null, null));
        } else {
            /*
             * Mi scorro tutto l'albero, potrebbero esserci abilitazioni da modificare o da rimuovere
             */
            if (getIdDichAuthor() != null && (getTreeType().equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())
                    || (getTreeType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())
                            && getSelectedNodes().size() != getTotalPageNodes().intValue())
                    || (getTreeType().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name()) && (getSelectedNodes()
                            .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))))) {
                // Rimuovo, nel caso, la dichiarazione ALL_ABILITAZIONI di ROOT
                getIdDichAuthorsToDelete().add(getIdDichAuthor());
            } else if ((getTreeType().equals(ConstPrfDichAutor.TiDichAutor.ENTRY_MENU.name())
                    && getSelectedNodes().contains(getIdElement()))
                    || (getTreeType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())
                            && getSelectedNodes().size() == getTotalPageNodes().intValue())
                    /*
                     * QUESTE DUE CONDIZIONI POTREBBERO ESSERE UNITE IN UNA UNICA, MA OGGI SONO PIGRO
                     */
                    || (getTreeType().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name()) && getSelectedNodes()
                            .size() == (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))) {
                // Il nodo è comunque selezionato, creo l'abilitazione e scorro i figli per disabilitarli
                table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(), getIdDichAuthor(),
                        getTreeType(), ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI.name(), null, null));
            }
            for (Map.Entry<String, RoleTreeElement> childEntry : getChildrenEntrySet()) {
                final RoleTreeElement child = childEntry.getValue();
                getSelectedNodesRicorsiva(child, table, getRealIdElement(), new ArrayList<String>());
            }
        }
        return table;
    }

    private void getSelectedNodesRicorsiva(RoleTreeElement element, PrfVLisDichAutorTableBean table,
            BigDecimal idParentNode, List<String> addedNodes) {
        ConstPrfDichAutor.TiDichAutor tipoAlbero = ConstPrfDichAutor.TiDichAutor.valueOf(getTreeType());
        switch (tipoAlbero) {
        case ENTRY_MENU:
            // <editor-fold defaultstate="collapsed" desc="Gestione ricorsiva Entry Menu">
            if (element.getIdDichAuthor() != null && getSelectedNodes().contains(element.getIdElement())) {
                // Dichiarazione già esistente, la ricreo
                if (element.getChildrenNumber() > 0) {
                    // Nodo ALL_ABILITAZIONI_CHILD
                    table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                            element.getIdDichAuthor(), getTreeType(),
                            ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(),
                            element.getRealIdElement(), null));
                } else {
                    // Nodo UNA_ABILITAZIONE
                    table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                            element.getIdDichAuthor(), getTreeType(),
                            ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(), element.getRealIdElement(),
                            idParentNode));
                }
            } else {
                if (element.getIdDichAuthor() != null) {
                    // Rimuovo, nel caso, la dichiarazione ALL_ABILITAZIONI_CHILD
                    getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                } else if (getSelectedNodes().contains(element.getIdElement())) {
                    // La voce è selezionata, devo creare l'abilitazione
                    if (element.getChildrenNumber() > 0) {
                        // Nodo ALL_ABILITAZIONI_CHILD - solo se non è pagina
                        table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                element.getIdDichAuthor(), getTreeType(),
                                ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(),
                                element.getRealIdElement(), null));
                    } else {
                        // Nodo UNA_ABILITAZIONE
                        table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                element.getIdDichAuthor(), getTreeType(),
                                ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(), element.getRealIdElement(),
                                idParentNode));
                    }
                }
                if (element.getChildrenNumber() > 0) {
                    // Devo scorrere ricorsivamente i figli
                    for (Map.Entry<String, RoleTreeElement> grandChild : element.getChildrenEntrySet()) {
                        final RoleTreeElement grandChildElement = grandChild.getValue();
                        getSelectedNodesRicorsiva(grandChildElement, table, element.getRealIdElement(), addedNodes);
                    }
                }
            }
            // </editor-fold>
            break;
        case PAGINA:
            // <editor-fold defaultstate="collapsed" desc="Gestione ricorsiva Pagine">
            if (element.getIdDichAuthor() != null && getSelectedNodes().contains(element.getIdElement())) {
                // Dichiarazione già esistente, la ricreo
                if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())
                        && element.getChildrenNumber() == 0) {
                    if (getSelectedNodes().size() != getTotalPageNodes().intValue()) {
                        // Nodo UNA_ABILITAZIONE
                        table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                element.getIdDichAuthor(), getTreeType(),
                                ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(), element.getRealIdElement(),
                                idParentNode));
                    } else {
                        // Rimuovo, nel caso, la dichiarazione UNA_ABILITAZIONE
                        getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                    }
                }
            } else {
                if (element.getIdDichAuthor() != null) {
                    // Rimuovo, nel caso, la dichiarazione ALL_ABILITAZIONI_CHILD
                    getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                } else if (getSelectedNodes().contains(element.getIdElement())) {
                    // La voce è selezionata, devo creare l'abilitazione
                    if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())
                            && element.getChildrenNumber() == 0) {
                        if (getSelectedNodes().size() != getTotalPageNodes().intValue()) {
                            // Nodo UNA_ABILITAZIONE
                            table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                    element.getIdDichAuthor(), getTreeType(),
                                    ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(),
                                    element.getRealIdElement(), idParentNode));
                        }
                    }
                }
                if (element.getChildrenNumber() > 0) {
                    // Devo scorrere ricorsivamente i figli
                    for (Map.Entry<String, RoleTreeElement> grandChild : element.getChildrenEntrySet()) {
                        final RoleTreeElement grandChildElement = grandChild.getValue();
                        getSelectedNodesRicorsiva(grandChildElement, table, element.getRealIdElement(), addedNodes);
                    }
                }
            }
            // </editor-fold>
            break;
        case AZIONE:
            // <editor-fold defaultstate="collapsed" desc="Gestione ricorsiva Azioni">
            if (element.getIdDichAuthor() != null && getSelectedNodes().contains(element.getIdElement())) {
                if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
                    boolean allAbilitazioniChild = isAllAbilitazioniChild(element);
                    if ((getSelectedNodes()
                            .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))
                            && !addedNodes.contains(element.getIdElement()) && allAbilitazioniChild) {
                        // Forse ci vuole un altro &&
                        table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                element.getIdDichAuthor(), getTreeType(),
                                ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(), null,
                                element.getRealIdElement()));
                        addedNodes.add(element.getIdElement());
                    } else {
                        // Rimuovo, nel caso, la dichiarazione ALL_ABILITAZIONI_CHILD
                        getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                        if ((getSelectedNodes()
                                .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))
                                && !addedNodes.contains(element.getIdElement()) && !allAbilitazioniChild) {
                            if (element.getChildrenNumber() > 0) {
                                // Devo scorrere ricorsivamente i figli e aggiungere gli UNA_ABILITAZIONE
                                for (Map.Entry<String, RoleTreeElement> grandChild : element.getChildrenEntrySet()) {
                                    final RoleTreeElement grandChildElement = grandChild.getValue();
                                    getSelectedNodesRicorsiva(grandChildElement, table, element.getRealIdElement(),
                                            addedNodes);
                                }
                            }
                        }
                    }
                } else if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
                    if ((getSelectedNodes()
                            .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))
                            && !addedNodes.contains(element.getIdElement())
                            && !addedNodes.contains("P" + idParentNode.toPlainString())) {
                        // Forse ci vuole un altro &&
                        table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                element.getIdDichAuthor(), getTreeType(),
                                ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(), element.getRealIdElement(),
                                idParentNode));
                        addedNodes.add(element.getIdElement());
                    } else {
                        // Rimuovo, nel caso, la dichiarazione UNA_ABILITAZIONE
                        getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                    }
                }
            } else {
                if (element.getIdDichAuthor() != null) {
                    // Rimuovo, nel caso, la dichiarazione ALL_ABILITAZIONI_CHILD
                    getIdDichAuthorsToDelete().add(element.getIdDichAuthor());
                } else if (getSelectedNodes().contains(element.getIdElement())) {
                    // La voce è selezionata, devo creare l'abilitazione
                    if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.PAGINA.name())) {
                        boolean allAbilitazioniChild = isAllAbilitazioniChild(element);
                        if ((getSelectedNodes()
                                .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))
                                && !addedNodes.contains(element.getIdElement()) && allAbilitazioniChild) {
                            // Nodo ALL_ABILITAZIONI_CHILD - solo se è pagina
                            table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                    element.getIdDichAuthor(), getTreeType(),
                                    ConstPrfDichAutor.TiScopoDichAutor.ALL_ABILITAZIONI_CHILD.name(), null,
                                    element.getRealIdElement()));
                            addedNodes.add(element.getIdElement());
                        }
                    } else if (element.getType().equals(ConstPrfDichAutor.TiDichAutor.AZIONE.name())) {
                        if ((getSelectedNodes()
                                .size() != (getTotalPageNodes().intValue() + getTotalActionNodes().intValue()))
                                && !addedNodes.contains(element.getIdElement())
                                && !addedNodes.contains("P" + idParentNode.toPlainString())) {
                            // Nodo UNA_ABILITAZIONE
                            table.add(createPrfVLisDichAutorRowBean(getIdApplication(), getNmApplication(),
                                    element.getIdDichAuthor(), getTreeType(),
                                    ConstPrfDichAutor.TiScopoDichAutor.UNA_ABILITAZIONE.name(),
                                    element.getRealIdElement(), idParentNode));
                            addedNodes.add(element.getIdElement());
                        }
                    }
                }
                if (element.getChildrenNumber() > 0) {
                    // Devo scorrere ricorsivamente i figli
                    for (Map.Entry<String, RoleTreeElement> grandChild : element.getChildrenEntrySet()) {
                        final RoleTreeElement grandChildElement = grandChild.getValue();
                        getSelectedNodesRicorsiva(grandChildElement, table, element.getRealIdElement(), addedNodes);
                    }
                }
            }
            // </editor-fold>
            break;
        case SERVIZIO_WEB:
        default:
            throw new AssertionError(tipoAlbero.name() + " non abilitato per la gestione ad albero");
        }
    }

    private boolean isAllAbilitazioniChild(RoleTreeElement element) {
        boolean allAbilitazioniChild = false;
        int childrenSelected = 0;
        for (Map.Entry<String, RoleTreeElement> entry : element.getChildrenEntrySet()) {
            if (getSelectedNodes().contains(entry.getKey())) {
                childrenSelected++;
            }
        }
        if (element.getChildrenNumber() > 0 && childrenSelected == element.getChildrenNumber()) {
            allAbilitazioniChild = true;
        }
        return allAbilitazioniChild;
    }

    private PrfVLisDichAutorRowBean createPrfVLisDichAutorRowBean(BigDecimal idApplic, String nmApplic,
            BigDecimal idDichAutor, String type, String tiScopoDichAutor, BigDecimal idNode, BigDecimal idParentNode) {
        PrfVLisDichAutorRowBean allAbilRow = new PrfVLisDichAutorRowBean();
        allAbilRow.setIdDichAutor(idDichAutor);
        allAbilRow.setTiDichAutor(type);
        allAbilRow.setTiScopoDichAutor(tiScopoDichAutor);
        allAbilRow.setIdApplic(idApplic);
        allAbilRow.setNmApplic(nmApplic);

        ConstPrfDichAutor.TiDichAutor tiDichAutor = ConstPrfDichAutor.TiDichAutor.valueOf(type);
        switch (tiDichAutor) {
        case ENTRY_MENU:
            allAbilRow.setIdEntryMenu(idNode);
            break;
        case AZIONE:
            allAbilRow.setIdPaginaWebAzio(idParentNode);
            allAbilRow.setIdAzionePagina(idNode);
            break;
        case PAGINA:
            allAbilRow.setIdPaginaWeb(idNode);
            break;
        case SERVIZIO_WEB:
        default:
            throw new AssertionError(tiDichAutor.name());
        }

        return allAbilRow;
    }

}
