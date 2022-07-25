package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_AZIONE_PAGINA database table.
 * 
 */
@Entity
@Cacheable(true)
@Table(name = "APL_AZIONE_PAGINA")
@NamedQuery(name = "AplAzionePagina.findAll", query = "SELECT a FROM AplAzionePagina a")
public class AplAzionePagina implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idAzionePagina;
    private String dsAzionePagina;
    private String nmAzionePagina;
    private AplPaginaWeb aplPaginaWeb;
    private AplTipoEvento aplTipoEvento;
    private List<PrfAutor> prfAutors = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    public AplAzionePagina() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_AZIONE_PAGINA") // @SequenceGenerator(name =
                                                                        // "APL_AZIONE_PAGINA_IDAZIONEPAGINA_GENERATOR",
                                                                        // sequenceName = "SAPL_AZIONE_PAGINA",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_AZIONE_PAGINA_IDAZIONEPAGINA_GENERATOR")
    @Column(name = "ID_AZIONE_PAGINA")
    public Long getIdAzionePagina() {
        return this.idAzionePagina;
    }

    public void setIdAzionePagina(Long idAzionePagina) {
        this.idAzionePagina = idAzionePagina;
    }

    @Column(name = "DS_AZIONE_PAGINA")
    public String getDsAzionePagina() {
        return this.dsAzionePagina;
    }

    public void setDsAzionePagina(String dsAzionePagina) {
        this.dsAzionePagina = dsAzionePagina;
    }

    @Column(name = "NM_AZIONE_PAGINA")
    public String getNmAzionePagina() {
        return this.nmAzionePagina;
    }

    public void setNmAzionePagina(String nmAzionePagina) {
        this.nmAzionePagina = nmAzionePagina;
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

    // bi-directional many-to-one association to AplTipoEvento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    public AplTipoEvento getAplTipoEvento() {
        return this.aplTipoEvento;
    }

    public void setAplTipoEvento(AplTipoEvento aplTipoEvento) {
        this.aplTipoEvento = aplTipoEvento;
    }

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplAzionePagina")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplAzionePagina")
    public List<PrfDichAutor> getPrfDichAutors() {
        return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
        this.prfDichAutors = prfDichAutors;
    }

}