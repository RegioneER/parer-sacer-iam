package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * The persistent class for the IAM_PARAM_APPLIC database table.
 *
 */
@Entity
@Table(name = "IAM_PARAM_APPLIC")
@NamedQuery(name = "IamParamApplic.findAll", query = "SELECT i FROM IamParamApplic i")
public class IamParamApplic implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idParamApplic;
    private String dsParamApplic;
    private String dsListaValoriAmmessi;
    private String flAppartAmbiente;
    private String flAppartApplic;
    private String flApparteEnte;
    private String nmParamApplic;
    private String tiGestioneParam;
    private String tiParamApplic;
    private String tiValoreParamApplic;
    private List<IamValoreParamApplic> iamValoreParamApplics = new ArrayList<>();

    public IamParamApplic() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SIAM_PARAM_APPLIC") // @SequenceGenerator(name =
                                                                       // "IAM_PARAM_APPLIC_IDPARAMAPPLIC_GENERATOR",
                                                                       // sequenceName = "SIAM_PARAM_APPLIC",
                                                                       // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IAM_PARAM_APPLIC_IDPARAMAPPLIC_GENERATOR")
    @Column(name = "ID_PARAM_APPLIC")
    public Long getIdParamApplic() {
        return this.idParamApplic;
    }

    public void setIdParamApplic(Long idParamApplic) {
        this.idParamApplic = idParamApplic;
    }

    @Column(name = "DS_PARAM_APPLIC")
    public String getDsParamApplic() {
        return this.dsParamApplic;
    }

    public void setDsParamApplic(String dsParamApplic) {
        this.dsParamApplic = dsParamApplic;
    }

    @Column(name = "DS_LISTA_VALORI_AMMESSI")
    public String getDsListaValoriAmmessi() {
        return this.dsListaValoriAmmessi;
    }

    public void setDsListaValoriAmmessi(String dsListaValoriAmmessi) {
        this.dsListaValoriAmmessi = dsListaValoriAmmessi;
    }

    @Column(name = "FL_APPART_AMBIENTE", columnDefinition = "char(1)")
    public String getFlAppartAmbiente() {
        return this.flAppartAmbiente;
    }

    public void setFlAppartAmbiente(String flAppartAmbiente) {
        this.flAppartAmbiente = flAppartAmbiente;
    }

    @Column(name = "FL_APPART_APPLIC", columnDefinition = "char(1)")
    public String getFlAppartApplic() {
        return this.flAppartApplic;
    }

    public void setFlAppartApplic(String flAppartApplic) {
        this.flAppartApplic = flAppartApplic;
    }

    @Column(name = "FL_APPARTE_ENTE", columnDefinition = "char(1)")
    public String getFlApparteEnte() {
        return this.flApparteEnte;
    }

    public void setFlApparteEnte(String flApparteEnte) {
        this.flApparteEnte = flApparteEnte;
    }

    @Column(name = "NM_PARAM_APPLIC")
    public String getNmParamApplic() {
        return this.nmParamApplic;
    }

    public void setNmParamApplic(String nmParamApplic) {
        this.nmParamApplic = nmParamApplic;
    }

    @Column(name = "TI_GESTIONE_PARAM")
    public String getTiGestioneParam() {
        return this.tiGestioneParam;
    }

    public void setTiGestioneParam(String tiGestioneParam) {
        this.tiGestioneParam = tiGestioneParam;
    }

    @Column(name = "TI_PARAM_APPLIC")
    public String getTiParamApplic() {
        return this.tiParamApplic;
    }

    public void setTiParamApplic(String tiParamApplic) {
        this.tiParamApplic = tiParamApplic;
    }

    // bi-directional many-to-one association to IamValoreParamApplic
    @OneToMany(mappedBy = "iamParamApplic")
    public List<IamValoreParamApplic> getIamValoreParamApplics() {
        return this.iamValoreParamApplics;
    }

    public void setIamValoreParamApplics(List<IamValoreParamApplic> iamValoreParamApplics) {
        this.iamValoreParamApplics = iamValoreParamApplics;
    }

    public IamValoreParamApplic addIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
        getIamValoreParamApplics().add(iamValoreParamApplic);
        iamValoreParamApplic.setIamParamApplic(this);

        return iamValoreParamApplic;
    }

    public IamValoreParamApplic removeIamValoreParamApplic(IamValoreParamApplic iamValoreParamApplic) {
        getIamValoreParamApplics().remove(iamValoreParamApplic);
        iamValoreParamApplic.setIamParamApplic(null);

        return iamValoreParamApplic;
    }

    // MEV26588
    @Column(name = "TI_VALORE_PARAM_APPLIC")
    public String getTiValoreParamApplic() {
        return tiValoreParamApplic;
    }

    public void setTiValoreParamApplic(String tiValoreParamApplic) {
        this.tiValoreParamApplic = tiValoreParamApplic;
    }

}
