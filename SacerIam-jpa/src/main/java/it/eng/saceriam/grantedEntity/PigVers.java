package it.eng.saceriam.grantedEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent class for the PIG_VERS database table.
 *
 */
@Entity
@XmlRootElement
@Table(name = "PIG_VERS", schema = "SACER_PING")
public class PigVers implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idVers;
    private String dsPathInputFtp;
    private String dsPathOutputFtp;
    private String dsPathTrasf;
    private String dsVers;
    private Date dtFinValAppartAmbiente;
    private Date dtFineValAppartEnteSiam;
    private Date dtFineValVers;
    private Date dtIniValAppartAmbiente;
    private Date dtIniValAppartEnteSiam;
    private Date dtIniValVers;
    private String flArchivioRestituito;
    private String flCessato;
    private BigDecimal idEnteConvenz;
    private BigDecimal idEnteFornitEstern;
    private String nmVers;

    public PigVers() {
        /*
         * empty
         */
    }

    @Id
    @Column(name = "ID_VERS")
    public long getIdVers() {
        return this.idVers;
    }

    public void setIdVers(long idVers) {
        this.idVers = idVers;
    }

    @Column(name = "DS_PATH_INPUT_FTP")
    public String getDsPathInputFtp() {
        return this.dsPathInputFtp;
    }

    public void setDsPathInputFtp(String dsPathInputFtp) {
        this.dsPathInputFtp = dsPathInputFtp;
    }

    @Column(name = "DS_PATH_OUTPUT_FTP")
    public String getDsPathOutputFtp() {
        return this.dsPathOutputFtp;
    }

    public void setDsPathOutputFtp(String dsPathOutputFtp) {
        this.dsPathOutputFtp = dsPathOutputFtp;
    }

    @Column(name = "DS_PATH_TRASF")
    public String getDsPathTrasf() {
        return this.dsPathTrasf;
    }

    public void setDsPathTrasf(String dsPathTrasf) {
        this.dsPathTrasf = dsPathTrasf;
    }

    @Column(name = "DS_VERS")
    public String getDsVers() {
        return this.dsVers;
    }

    public void setDsVers(String dsVers) {
        this.dsVers = dsVers;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_FIN_VAL_APPART_AMBIENTE")
    public Date getDtFinValAppartAmbiente() {
        return this.dtFinValAppartAmbiente;
    }

    public void setDtFinValAppartAmbiente(Date dtFinValAppartAmbiente) {
        this.dtFinValAppartAmbiente = dtFinValAppartAmbiente;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FINE_VAL_APPART_ENTE_SIAM")
    public Date getDtFineValAppartEnteSiam() {
        return this.dtFineValAppartEnteSiam;
    }

    public void setDtFineValAppartEnteSiam(Date dtFineValAppartEnteSiam) {
        this.dtFineValAppartEnteSiam = dtFineValAppartEnteSiam;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FINE_VAL_VERS")
    public Date getDtFineValVers() {
        return this.dtFineValVers;
    }

    public void setDtFineValVers(Date dtFineValVers) {
        this.dtFineValVers = dtFineValVers;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL_APPART_AMBIENTE")
    public Date getDtIniValAppartAmbiente() {
        return this.dtIniValAppartAmbiente;
    }

    public void setDtIniValAppartAmbiente(Date dtIniValAppartAmbiente) {
        this.dtIniValAppartAmbiente = dtIniValAppartAmbiente;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL_APPART_ENTE_SIAM")
    public Date getDtIniValAppartEnteSiam() {
        return this.dtIniValAppartEnteSiam;
    }

    public void setDtIniValAppartEnteSiam(Date dtIniValAppartEnteSiam) {
        this.dtIniValAppartEnteSiam = dtIniValAppartEnteSiam;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_VAL_VERS")
    public Date getDtIniValVers() {
        return this.dtIniValVers;
    }

    public void setDtIniValVers(Date dtIniValVers) {
        this.dtIniValVers = dtIniValVers;
    }

    @Column(name = "FL_ARCHIVIO_RESTITUITO", columnDefinition = "CHAR")
    public String getFlArchivioRestituito() {
        return this.flArchivioRestituito;
    }

    public void setFlArchivioRestituito(String flArchivioRestituito) {
        this.flArchivioRestituito = flArchivioRestituito;
    }

    @Column(name = "FL_CESSATO", columnDefinition = "CHAR")
    public String getFlCessato() {
        return this.flCessato;
    }

    public void setFlCessato(String flCessato) {
        this.flCessato = flCessato;
    }

    @Column(name = "ID_ENTE_CONVENZ")
    public BigDecimal getIdEnteConvenz() {
        return this.idEnteConvenz;
    }

    public void setIdEnteConvenz(BigDecimal idEnteConvenz) {
        this.idEnteConvenz = idEnteConvenz;
    }

    @Column(name = "ID_ENTE_FORNIT_ESTERN")
    public BigDecimal getIdEnteFornitEstern() {
        return this.idEnteFornitEstern;
    }

    public void setIdEnteFornitEstern(BigDecimal idEnteFornitEstern) {
        this.idEnteFornitEstern = idEnteFornitEstern;
    }

    @Column(name = "NM_VERS")
    public String getNmVers() {
        return this.nmVers;
    }

    public void setNmVers(String nmVers) {
        this.nmVers = nmVers;
    }

}
