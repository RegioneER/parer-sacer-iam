package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the ORG_TIPI_GESTIONE_ACCORDO database table.
 *
 */
@Entity
@Table(name = "ORG_TIPI_GESTIONE_ACCORDO")
@NamedQuery(name = "OrgTipiGestioneAccordo.findAll", query = "SELECT o FROM OrgTipiGestioneAccordo o")
public class OrgTipiGestioneAccordo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoGestioneAccordo;
    private String cdTipoGestioneAccordo;
    private List<OrgGestioneAccordo> orgGestioneAccordos = new ArrayList<>();

    public OrgTipiGestioneAccordo() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SORG_TIPI_GESTIONE_ACCORDO") // @SequenceGenerator(name =
                                                                                // "ORG_TIPI_GESTIONE_ACCORDO_IDTIPIGESTIONEACCORDO_GENERATOR",
                                                                                // sequenceName =
                                                                                // "SORG_TIPI_GESTIONE_ACCORDO",
                                                                                // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "ORG_TIPI_GESTIONE_ACCORDO_IDTIPIGESTIONEACCORDO_GENERATOR")
    @Column(name = "ID_TIPO_GESTIONE_ACCORDO")
    public Long getIdTipoGestioneAccordo() {
        return this.idTipoGestioneAccordo;
    }

    public void setIdTipoGestioneAccordo(Long idTipoGestioneAccordo) {
        this.idTipoGestioneAccordo = idTipoGestioneAccordo;
    }

    @Column(name = "CD_TIPO_GESTIONE_ACCORDO")
    public String getCdTipoGestioneAccordo() {
        return this.cdTipoGestioneAccordo;
    }

    public void setCdTipoGestioneAccordo(String cdTipoGestioneAccordo) {
        this.cdTipoGestioneAccordo = cdTipoGestioneAccordo;
    }

    // bi-directional many-to-one association to OrgGestioneAccordo
    @OneToMany(mappedBy = "orgTipiGestioneAccordo", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<OrgGestioneAccordo> getOrgGestioneAccordos() {
        return this.orgGestioneAccordos;
    }

    public void setOrgGestioneAccordos(List<OrgGestioneAccordo> orgGestioneAccordos) {
        this.orgGestioneAccordos = orgGestioneAccordos;
    }

    public OrgGestioneAccordo addOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().add(orgGestioneAccordo);
        orgGestioneAccordo.setOrgTipiGestioneAccordo(this);

        return orgGestioneAccordo;
    }

    public OrgGestioneAccordo removeOrgGestioneAccordo(OrgGestioneAccordo orgGestioneAccordo) {
        getOrgGestioneAccordos().remove(orgGestioneAccordo);
        orgGestioneAccordo.setOrgTipiGestioneAccordo(null);

        return orgGestioneAccordo;
    }

}
