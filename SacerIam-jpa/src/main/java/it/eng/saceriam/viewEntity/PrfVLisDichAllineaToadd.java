package it.eng.saceriam.viewEntity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PRF_V_LIS_DICH_ALLINEA_TOADD database table.
 *
 */
@Entity
@Table(name = "PRF_V_LIS_DICH_ALLINEA_TOADD")
@NamedQuery(name = "PrfVLisDichAllineaToadd.findAll", query = "SELECT p FROM PrfVLisDichAllineaToadd p")
public class PrfVLisDichAllineaToadd implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal idAllineaRuolo;
    private BigDecimal idAzionePagina;
    private BigDecimal idEntryMenuFoglia;
    private BigDecimal idEntryMenuPadre;
    private BigDecimal idPaginaWeb;
    private BigDecimal idRuolo;
    private BigDecimal idServizioWeb;
    private BigDecimal idUsoRuoloApplic;
    private String tiDichAutor;
    private String tiScopoDichAutor;

    public PrfVLisDichAllineaToadd() {
    }

    @Id
    @Column(name = "ID_ALLINEA_RUOLO")
    public BigDecimal getIdAllineaRuolo() {
        return this.idAllineaRuolo;
    }

    public void setIdAllineaRuolo(BigDecimal idAllineaRuolo) {
        this.idAllineaRuolo = idAllineaRuolo;
    }

    @Column(name = "ID_AZIONE_PAGINA")
    public BigDecimal getIdAzionePagina() {
        return this.idAzionePagina;
    }

    public void setIdAzionePagina(BigDecimal idAzionePagina) {
        this.idAzionePagina = idAzionePagina;
    }

    @Column(name = "ID_ENTRY_MENU_FOGLIA")
    public BigDecimal getIdEntryMenuFoglia() {
        return this.idEntryMenuFoglia;
    }

    public void setIdEntryMenuFoglia(BigDecimal idEntryMenuFoglia) {
        this.idEntryMenuFoglia = idEntryMenuFoglia;
    }

    @Column(name = "ID_ENTRY_MENU_PADRE")
    public BigDecimal getIdEntryMenuPadre() {
        return this.idEntryMenuPadre;
    }

    public void setIdEntryMenuPadre(BigDecimal idEntryMenuPadre) {
        this.idEntryMenuPadre = idEntryMenuPadre;
    }

    @Column(name = "ID_PAGINA_WEB")
    public BigDecimal getIdPaginaWeb() {
        return this.idPaginaWeb;
    }

    public void setIdPaginaWeb(BigDecimal idPaginaWeb) {
        this.idPaginaWeb = idPaginaWeb;
    }

    @Column(name = "ID_RUOLO")
    public BigDecimal getIdRuolo() {
        return this.idRuolo;
    }

    public void setIdRuolo(BigDecimal idRuolo) {
        this.idRuolo = idRuolo;
    }

    @Column(name = "ID_SERVIZIO_WEB")
    public BigDecimal getIdServizioWeb() {
        return this.idServizioWeb;
    }

    public void setIdServizioWeb(BigDecimal idServizioWeb) {
        this.idServizioWeb = idServizioWeb;
    }

    @Column(name = "ID_USO_RUOLO_APPLIC")
    public BigDecimal getIdUsoRuoloApplic() {
        return idUsoRuoloApplic;
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
        this.idUsoRuoloApplic = idUsoRuoloApplic;
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

}
