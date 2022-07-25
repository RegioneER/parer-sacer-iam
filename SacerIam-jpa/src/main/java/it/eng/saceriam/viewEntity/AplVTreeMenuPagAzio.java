package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The persistent class for the APL_V_TREE_MENU_PAG_AZIO database table.
 *
 */
@Entity
@Table(name = "APL_V_TREE_MENU_PAG_AZIO")
@NamedQuery(name = "AplVTreeMenuPagAzio.findAll", query = "SELECT a FROM AplVTreeMenuPagAzio a")
public class AplVTreeMenuPagAzio implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dsNodo;
    private String dsOrdNodi;
    private BigDecimal idApplic;
    private String idNodo;
    private String idNodoPadre;
    private BigDecimal niLivello;
    private BigDecimal niOrdNodi;
    private String tipoNodo;

    public AplVTreeMenuPagAzio() {
    }

    @Column(name = "DS_NODO")
    public String getDsNodo() {
        return this.dsNodo;
    }

    public void setDsNodo(String dsNodo) {
        this.dsNodo = dsNodo;
    }

    @Column(name = "DS_ORD_NODI")
    public String getDsOrdNodi() {
        return this.dsOrdNodi;
    }

    public void setDsOrdNodi(String dsOrdNodi) {
        this.dsOrdNodi = dsOrdNodi;
    }

    @Column(name = "ID_APPLIC")
    public BigDecimal getIdApplic() {
        return this.idApplic;
    }

    public void setIdApplic(BigDecimal idApplic) {
        this.idApplic = idApplic;
    }

    @Id
    @Column(name = "ID_NODO")
    public String getIdNodo() {
        return this.idNodo;
    }

    public void setIdNodo(String idNodo) {
        this.idNodo = idNodo;
    }

    @Column(name = "ID_NODO_PADRE")
    public String getIdNodoPadre() {
        return this.idNodoPadre;
    }

    public void setIdNodoPadre(String idNodoPadre) {
        this.idNodoPadre = idNodoPadre;
    }

    @Column(name = "NI_LIVELLO")
    public BigDecimal getNiLivello() {
        return this.niLivello;
    }

    public void setNiLivello(BigDecimal niLivello) {
        this.niLivello = niLivello;
    }

    @Column(name = "NI_ORD_NODI")
    public BigDecimal getNiOrdNodi() {
        return this.niOrdNodi;
    }

    public void setNiOrdNodi(BigDecimal niOrdNodi) {
        this.niOrdNodi = niOrdNodi;
    }

    @Column(name = "TIPO_NODO", columnDefinition = "char(1)")
    public String getTipoNodo() {
        return this.tipoNodo;
    }

    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

}
