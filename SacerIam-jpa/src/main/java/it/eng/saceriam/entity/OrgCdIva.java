package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_CD_IVA database table.
 *
 */
@Entity
@Table(name = "ORG_CD_IVA")
@NamedQuery(name = "OrgCdIva.findAll", query = "SELECT o FROM OrgCdIva o")
public class OrgCdIva implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCdIva;
    private String cdIva;
    private String dsIva;
    private List<OrgAccordoEnte> orgAccordoEntes = new ArrayList<>();
    private List<OrgServizioFattura> orgServizioFatturas = new ArrayList<>();

    public OrgCdIva() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_CD_IVA") // @SequenceGenerator(name =
                                                                 // "ORG_CD_IVA_IDCDIVA_GENERATOR", sequenceName =
                                                                 // "SORG_CD_IVA", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_CD_IVA_IDCDIVA_GENERATOR")
    @Column(name = "ID_CD_IVA")
    public Long getIdCdIva() {
        return this.idCdIva;
    }

    public void setIdCdIva(Long idCdIva) {
        this.idCdIva = idCdIva;
    }

    @Column(name = "CD_IVA")
    public String getCdIva() {
        return this.cdIva;
    }

    public void setCdIva(String cdIva) {
        this.cdIva = cdIva;
    }

    @Column(name = "DS_IVA")
    public String getDsIva() {
        return this.dsIva;
    }

    public void setDsIva(String dsIva) {
        this.dsIva = dsIva;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgCdIva")
    public List<OrgAccordoEnte> getOrgAccordoEntes() {
        return this.orgAccordoEntes;
    }

    public void setOrgAccordoEntes(List<OrgAccordoEnte> orgAccordoEntes) {
        this.orgAccordoEntes = orgAccordoEntes;
    }

    // bi-directional many-to-one association to OrgAccordoEnte
    @OneToMany(mappedBy = "orgCdIva")
    public List<OrgServizioFattura> getOrgServizioFatturas() {
        return orgServizioFatturas;
    }

    public void setOrgServizioFatturas(List<OrgServizioFattura> orgServizioFatturas) {
        this.orgServizioFatturas = orgServizioFatturas;
    }

}
