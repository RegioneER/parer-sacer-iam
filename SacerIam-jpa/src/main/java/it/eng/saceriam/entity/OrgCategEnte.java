package it.eng.saceriam.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ORG_CATEG_ENTE database table.
 * 
 */
/* ORG_CATEG_ENTE */
@Entity
@Table(name = "ORG_CATEG_ENTE", schema = "SACER")
public class OrgCategEnte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idCategEnte;
    private String cdCategEnte;
    private String dsCategEnte;
    // private List<OrgEnte> orgEntes = new ArrayList<>();

    public OrgCategEnte() {
    }

    @Id
    // @SequenceGenerator(name = "ORG_CATEG_ENTE_IDCATEGSTRUT_GENERATOR", sequenceName = "SORG_CATEG_ENTE",
    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_CATEG_ENTE_IDCATEGSTRUT_GENERATOR")
    @Column(name = "ID_CATEG_ENTE")
    public Long getIdCategEnte() {
        return this.idCategEnte;
    }

    public void setIdCategEnte(Long idCategEnte) {
        this.idCategEnte = idCategEnte;
    }

    @Column(name = "CD_CATEG_ENTE")
    public String getCdCategEnte() {
        return this.cdCategEnte;
    }

    public void setCdCategEnte(String cdCategEnte) {
        this.cdCategEnte = cdCategEnte;
    }

    @Column(name = "DS_CATEG_ENTE")
    public String getDsCategEnte() {
        return this.dsCategEnte;
    }

    public void setDsCategEnte(String dsCategEnte) {
        this.dsCategEnte = dsCategEnte;
    }

    // //bi-directional many-to-one association to OrgEnte
    // @OneToMany(mappedBy="orgCategEnte")
    // public List<OrgEnte> getOrgEntes() {
    // return this.orgEntes;
    // }
    //
    // public void setOrgEntes(List<OrgEnte> orgEntes) {
    // this.orgEntes = orgEntes;
    // }

}