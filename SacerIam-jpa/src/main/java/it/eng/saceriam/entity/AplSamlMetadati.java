package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the APL_SAML_METADATI database table.
 *
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
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_SAML_METADATI") // @SequenceGenerator(name =
                                                                        // "APL_SAML_METADATI_IDSAMLMETADATI_GENERATOR",
                                                                        // sequenceName = "SAPL_SAML_METADATI",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_SAML_METADATI_IDSAMLMETADATI_GENERATOR")
    @Column(name = "ID_SAML_METADATI")
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
