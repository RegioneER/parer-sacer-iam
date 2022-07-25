package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the PRF_ALLINEA_RUOLO database table.
 *
 */
@Entity
@Table(name = "PRF_ALLINEA_RUOLO")
@NamedQuery(name = "PrfAllineaRuolo.findAll", query = "SELECT p FROM PrfAllineaRuolo p")
public class PrfAllineaRuolo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idAllineaRuolo;
    private String dsPathEntryMenuFoglia;
    private String dsPathEntryMenuPadre;
    private PrfRuolo prfRuolo;
    private String nmApplic;
    private String nmAzionePagina;
    private String nmPaginaWeb;
    private String nmServizioWeb;
    private String tiDichAutor;
    private String tiScopoDichAutor;

    public PrfAllineaRuolo() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SPRF_ALLINEA_RUOLO") // @SequenceGenerator(name =
                                                                        // "PRF_ALLINEA_RUOLO_IDALLINEARUOLO_GENERATOR",
                                                                        // sequenceName = "SPRF_ALLINEA_RUOLO",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRF_ALLINEA_RUOLO_IDALLINEARUOLO_GENERATOR")
    @Column(name = "ID_ALLINEA_RUOLO")
    public Long getIdAllineaRuolo() {
        return this.idAllineaRuolo;
    }

    public void setIdAllineaRuolo(Long idAllineaRuolo) {
        this.idAllineaRuolo = idAllineaRuolo;
    }

    @Column(name = "DS_PATH_ENTRY_MENU_FOGLIA")
    public String getDsPathEntryMenuFoglia() {
        return this.dsPathEntryMenuFoglia;
    }

    public void setDsPathEntryMenuFoglia(String dsPathEntryMenuFoglia) {
        this.dsPathEntryMenuFoglia = dsPathEntryMenuFoglia;
    }

    @Column(name = "DS_PATH_ENTRY_MENU_PADRE")
    public String getDsPathEntryMenuPadre() {
        return this.dsPathEntryMenuPadre;
    }

    public void setDsPathEntryMenuPadre(String dsPathEntryMenuPadre) {
        this.dsPathEntryMenuPadre = dsPathEntryMenuPadre;
    }

    @Column(name = "NM_APPLIC")
    public String getNmApplic() {
        return this.nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    @Column(name = "NM_AZIONE_PAGINA")
    public String getNmAzionePagina() {
        return this.nmAzionePagina;
    }

    public void setNmAzionePagina(String nmAzionePagina) {
        this.nmAzionePagina = nmAzionePagina;
    }

    @Column(name = "NM_PAGINA_WEB")
    public String getNmPaginaWeb() {
        return this.nmPaginaWeb;
    }

    public void setNmPaginaWeb(String nmPaginaWeb) {
        this.nmPaginaWeb = nmPaginaWeb;
    }

    @Column(name = "NM_SERVIZIO_WEB")
    public String getNmServizioWeb() {
        return this.nmServizioWeb;
    }

    public void setNmServizioWeb(String nmServizioWeb) {
        this.nmServizioWeb = nmServizioWeb;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RUOLO")
    public PrfRuolo getPrfRuolo() {
        return this.prfRuolo;
    }

    public void setPrfRuolo(PrfRuolo prfRuolo) {
        this.prfRuolo = prfRuolo;
    }

}
