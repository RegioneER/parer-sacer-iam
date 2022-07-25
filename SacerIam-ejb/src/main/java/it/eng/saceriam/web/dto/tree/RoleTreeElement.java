package it.eng.saceriam.web.dto.tree;

import it.eng.saceriam.entity.constraint.ConstPrfDichAutor;
import it.eng.saceriam.exception.ParerUserError;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * Struttura dati atta alla gestione dell'alberatura dei ruoli
 *
 * @author Bonora_L
 */
public class RoleTreeElement implements Serializable {

    /**
     * Tipo di alberatura, al momento i tipi di valore possono essere ENTRY_MENU o PAGINA o AZIONE
     */
    private final String type;
    /**
     * Id dell'elemento sul db, relativo alla voce di tipo {@link RoleTree#type}
     */
    private final String idElement;
    /**
     * Id della dichiarazione di autorizzazione della voce
     */
    private BigDecimal idDichAuthor;
    /**
     * Collezione di elementi figli, la cui chiave è {@link RoleTreeElement#idElement}
     */
    private final Map<String, RoleTreeElement> children;
    /**
     * Numero d'ordine del nodo
     */
    private int numOrdine;
    /**
     * Numero di figli selezionati
     */
    private int selectedChildren;
    /**
     * Flag che indica se la voce è stata selezionata. In questo caso la struttura identifica una dichiarazione di tipo
     * ALL_ABILITAZIONI_CHILD se {@link RoleTreeElement#isLeaf()} = false, oppure una dichiarazione di tipo
     * UNA_ABILITAZIONE se {@link RoleTreeElement#isLeaf()} = true
     */
    private boolean selected = false;

    public RoleTreeElement(String type, String idElement, int numOrdine) {
        this.type = type;
        this.idElement = idElement;
        this.numOrdine = numOrdine;
        this.children = new HashMap<>();
        this.selectedChildren = 0;
    }

    public RoleTreeElement(String type, BigDecimal idDichAuthor, String idElement, int numOrdine) {
        this.idDichAuthor = idDichAuthor;
        this.type = type;
        this.idElement = idElement;
        this.numOrdine = numOrdine;
        this.children = new HashMap<>();
        this.selectedChildren = 0;
        if (idDichAuthor != null) {
            this.selected = true;
        }
    }

    public BigDecimal getIdDichAuthor() {
        return idDichAuthor;
    }

    public void setIdDichAuthor(BigDecimal idDichAuthor) {
        this.idDichAuthor = idDichAuthor;
    }

    public String getType() {
        return type;
    }

    public String getIdElement() {
        return idElement;
    }

    public BigDecimal getRealIdElement() {
        ConstPrfDichAutor.TiDichAutor type = ConstPrfDichAutor.TiDichAutor.valueOf(getType());
        BigDecimal realId = new BigDecimal(StringUtils.removeStart(getIdElement(), type.getValue()));
        return realId;
    }

    public RoleTreeElement getChild(String idElement) {
        return children.get(idElement);
    }

    public RoleTreeElement addChild(String idElement, RoleTreeElement element) {
        return children.put(idElement, element);
    }

    public Set<Entry<String, RoleTreeElement>> getChildrenEntrySet() {
        return children.entrySet();
    }

    public int getChildrenNumber() {
        return children.size();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getNumOrdine() {
        return numOrdine;
    }

    public void setNumOrdine(int numOrdine) {
        this.numOrdine = numOrdine;
    }

    public void increaseSelectedChildren() {
        if (this.selectedChildren < getChildrenNumber()) {
            this.selectedChildren++;
        }
    }

    public void decreaseSelectedChildren() {
        if (this.selectedChildren > 0) {
            this.selectedChildren--;
        }
    }

    public int getSelectedChildren() {
        return selectedChildren;
    }

    public void setSelectedChildren(int selectedChildren) {
        this.selectedChildren = selectedChildren;
    }

    /**
     * Flag che indica se questo elemento è 'foglia' all'interno della struttura
     *
     * @return True se il nodo non ha figli
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isAllAbilitazioniChild() {
        return (children.size() == selectedChildren) && !isLeaf();
    }

    /**
     * Deseleziona tutti i figli del presente nodo, ritornando gli idDichAutor da eliminare
     *
     * @return la lista di idDichAutor da eliminare
     */
    public List<BigDecimal> deselectAllChilds() {
        List<BigDecimal> idDichAutorsToDelete = new ArrayList<>();
        for (Map.Entry<String, RoleTreeElement> roleTreeElementEntry : children.entrySet()) {
            RoleTreeElement roleTreeElement = roleTreeElementEntry.getValue();
            roleTreeElement.setSelected(false);
            decreaseSelectedChildren();
            if (roleTreeElement.getIdDichAuthor() != null) {
                idDichAutorsToDelete.add(roleTreeElement.getIdDichAuthor());
            }
        }
        return idDichAutorsToDelete;
    }

    public void selectChild(String idNode, List<String> nodesPath, BigDecimal idDichAutor) throws ParerUserError {
        List<String> tmpList = new ArrayList<>(nodesPath);
        if (getIdElement().equals(idNode) && tmpList.size() == 1) {
            setIdDichAuthor(idDichAutor);
        } else if (getIdElement().equals(tmpList.get(0))) {
            // Rimuovo me stesso
            tmpList.remove(0);
            // Prendo il nodo figlio
            RoleTreeElement child = getChild(tmpList.get(0));
            if (child != null) {
                child.selectChild(idNode, tmpList, idDichAutor);
            } else {
                throw new ParerUserError("Errore inatteso nella navigazione dell'albero");
            }
        } else {
            throw new ParerUserError("Errore inatteso nella navigazione dell'albero");
        }
    }
}
