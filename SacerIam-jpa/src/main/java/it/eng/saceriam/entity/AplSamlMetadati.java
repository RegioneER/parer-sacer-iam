package it.eng.saceriam.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the APL_SAML_METADATI database table.
 */
@Entity
@Table(name = "APL_SAML_METADATI")
public class AplSamlMetadati implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSamlMetadati;

    private String blSamlMetadati;

    public AplSamlMetadati() {
    }

    @Id
    @Column(name = "ID_SAML_METADATI")
    @GenericGenerator(name = "SAPL_SAML_METADATI_ID_SAML_METADATI_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_SAML_METADATI"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_SAML_METADATI_ID_SAML_METADATI_GENERATOR")
    public Long getIdSamlMetadati() {
        return this.idSamlMetadati;
    }

    public void setIdSamlMetadati(Long idSamlMetadati) {
        this.idSamlMetadati = idSamlMetadati;
    }

    @Lob()
    @Column(name = "BL_SAML_METADATI")
    public String getBlSamlMetadati() {
        return this.blSamlMetadati;
    }

    public void setBlSamlMetadati(String blSamlMetadati) {
        this.blSamlMetadati = blSamlMetadati;
    }
}
