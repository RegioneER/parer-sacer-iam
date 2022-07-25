package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the APL_SERVIZIO_WEB database table.
 * 
 */
@Entity
@Cacheable(true)
@Table(name = "APL_SERVIZIO_WEB")
public class AplServizioWeb implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idServizioWeb;
    private String dsServizioWeb;
    private String nmServizioWeb;
    private AplApplic aplApplic;
    private List<PrfAutor> prfAutors = new ArrayList<>();
    private List<PrfDichAutor> prfDichAutors = new ArrayList<>();

    public AplServizioWeb() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_SERVIZIO_WEB") // @SequenceGenerator(name =
                                                                       // "APL_SERVIZIO_WEB_IDSERVIZIOWEB_GENERATOR",
                                                                       // sequenceName = "SAPL_SERVIZIO_WEB",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_SERVIZIO_WEB_IDSERVIZIOWEB_GENERATOR")
    @Column(name = "ID_SERVIZIO_WEB")
    public Long getIdServizioWeb() {
        return this.idServizioWeb;
    }

    public void setIdServizioWeb(Long idServizioWeb) {
        this.idServizioWeb = idServizioWeb;
    }

    @Column(name = "DS_SERVIZIO_WEB")
    public String getDsServizioWeb() {
        return this.dsServizioWeb;
    }

    public void setDsServizioWeb(String dsServizioWeb) {
        this.dsServizioWeb = dsServizioWeb;
    }

    @Column(name = "NM_SERVIZIO_WEB")
    public String getNmServizioWeb() {
        return this.nmServizioWeb;
    }

    public void setNmServizioWeb(String nmServizioWeb) {
        this.nmServizioWeb = nmServizioWeb;
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

    // bi-directional many-to-one association to PrfAutor
    @OneToMany(mappedBy = "aplServizioWeb")
    public List<PrfAutor> getPrfAutors() {
        return this.prfAutors;
    }

    public void setPrfAutors(List<PrfAutor> prfAutors) {
        this.prfAutors = prfAutors;
    }

    // bi-directional many-to-one association to PrfDichAutor
    @OneToMany(mappedBy = "aplServizioWeb")
    public List<PrfDichAutor> getPrfDichAutors() {
        return this.prfDichAutors;
    }

    public void setPrfDichAutors(List<PrfDichAutor> prfDichAutors) {
        this.prfDichAutors = prfDichAutors;
    }

}