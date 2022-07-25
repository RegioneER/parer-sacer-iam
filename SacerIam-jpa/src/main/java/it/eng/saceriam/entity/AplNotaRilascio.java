package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the APL_NOTA_RILASCIO database table.
 * 
 */
@Entity
@Table(name = "APL_NOTA_RILASCIO")
public class AplNotaRilascio implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idNotaRilascio;
    private String cdVersione;
    private Date dtVersione;
    private Date dtIniVal;
    private Date dtFineVal;
    private String dsEvidenza;
    private String blNota;
    private AplApplic aplApplic;

    public AplNotaRilascio() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SAPL_NOTA_RILASCIO") // @SequenceGenerator(name =
                                                                        // "APL_NOTA_RILASCIO_IDNOTARILASCIO_GENERATOR",
                                                                        // sequenceName = "SAPL_NOTA_RILASCIO",
                                                                        // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APL_NOTA_RILASCIO_IDNOTARILASCIO_GENERATOR")
    @Column(name = "ID_NOTA_RILASCIO")
    public Long getIdNotaRilascio() {
        return this.idNotaRilascio;
    }

    public void setIdNotaRilascio(Long idNotaRilascio) {
        this.idNotaRilascio = idNotaRilascio;
    }

    @Column(name = "CD_VERSIONE")
    public String getCdVersione() {
        return this.cdVersione;
    }

    public void setCdVersione(String cdVersione) {
        this.cdVersione = cdVersione;
    }

    @Column(name = "DS_EVIDENZA")
    public String getDsEvidenza() {
        return this.dsEvidenza;
    }

    public void setDsEvidenza(String dsEvidenza) {
        this.dsEvidenza = dsEvidenza;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_VERSIONE")
    public Date getDtVersione() {
        return this.dtVersione;
    }

    public void setDtVersione(Date dtVersione) {
        this.dtVersione = dtVersione;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_INI_VAL")
    public Date getDtIniVal() {
        return this.dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
        this.dtIniVal = dtIniVal;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FINE_VAL")
    public Date getDtFineVal() {
        return this.dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
        this.dtFineVal = dtFineVal;
    }

    @Lob
    @Column(name = "BL_NOTA")
    public String getBlNota() {
        return this.blNota;
    }

    public void setBlNota(String blNota) {
        this.blNota = blNota;
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

}