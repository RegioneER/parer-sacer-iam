/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the ORG_STRUT database table.
 *
 */
@Entity
@XmlRootElement
@Cacheable(true)
@Table(schema = "SACER", name = "ORG_STRUT")
public class OrgStrut implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idStrut;
    private String cdIpa;
    private String dsStrut;
    private String flTemplate;
    private String nmStrut;
    private List<DecTipoUnitaDoc> decTipoUnitaDocs = new ArrayList<>();
    private String flCessato;

    public OrgStrut() {
        /*
         * empty
         */
    }

    @Id
    // @NonMonotonicSequenceGenerator(sequenceName = "SORG_STRUT") //@SequenceGenerator(name =
    // "ORG_STRUT_IDSTRUT_GENERATOR", sequenceName = "SORG_STRUT", allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORG_STRUT_IDSTRUT_GENERATOR")
    @Column(name = "ID_STRUT")
    public Long getIdStrut() {
        return this.idStrut;
    }

    public void setIdStrut(Long idStrut) {
        this.idStrut = idStrut;
    }

    @Column(name = "CD_IPA")
    public String getCdIpa() {
        return cdIpa;
    }

    public void setCdIpa(String cdIpa) {
        this.cdIpa = cdIpa;
    }

    @Column(name = "DS_STRUT")
    public String getDsStrut() {
        return this.dsStrut;
    }

    public void setDsStrut(String dsStrut) {
        this.dsStrut = dsStrut;
    }

    @Column(name = "FL_CESSATO", columnDefinition = "char")
    public String getFlCessato() {
        return this.flCessato;
    }

    public void setFlCessato(String flCessato) {
        this.flCessato = flCessato;
    }

    @Column(name = "FL_TEMPLATE", columnDefinition = "char")
    public String getFlTemplate() {
        return this.flTemplate;
    }

    public void setFlTemplate(String flTemplate) {
        this.flTemplate = flTemplate;
    }

    @Column(name = "NM_STRUT")
    public String getNmStrut() {
        return this.nmStrut;
    }

    public void setNmStrut(String nmStrut) {
        this.nmStrut = nmStrut;
    }

    // bi-directional many-to-one association to DecTipoUnitaDoc
    @OneToMany(mappedBy = "orgStrut", cascade = CascadeType.PERSIST)
    public List<DecTipoUnitaDoc> getDecTipoUnitaDocs() {
        return this.decTipoUnitaDocs;
    }

    public void setDecTipoUnitaDocs(List<DecTipoUnitaDoc> decTipoUnitaDocs) {
        this.decTipoUnitaDocs = decTipoUnitaDocs;
    }

}
