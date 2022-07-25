package it.eng.saceriam.grantedViewEntity;

import it.eng.saceriam.viewEntity.*;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the LOG_V_RIC_ACCESSI database table.
 * 
 */
@Entity
@Table(name = "LOG_V_RIC_ACCESSI")
@NamedQuery(name = "LogVRicAccessi.findAll", query = "SELECT l FROM LogVRicAccessi l")
public class LogVRicAccessi implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cdIndIpClient;
    private String cdIndServer;
    private String dsEvento;
    private Date dtEvento;
    private BigDecimal idEvento;
    private String nmUserid;
    private String tipoEvento;

    public LogVRicAccessi() {
    }

    @Column(name = "CD_IND_IP_CLIENT")
    public String getCdIndIpClient() {
        return this.cdIndIpClient;
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        this.cdIndIpClient = cdIndIpClient;
    }

    @Column(name = "CD_IND_SERVER")
    public String getCdIndServer() {
        return this.cdIndServer;
    }

    public void setCdIndServer(String cdIndServer) {
        this.cdIndServer = cdIndServer;
    }

    @Column(name = "DS_EVENTO")
    public String getDsEvento() {
        return this.dsEvento;
    }

    public void setDsEvento(String dsEvento) {
        this.dsEvento = dsEvento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_EVENTO")
    public Date getDtEvento() {
        return this.dtEvento;
    }

    public void setDtEvento(Date dtEvento) {
        this.dtEvento = dtEvento;
    }

    @Id
    @Column(name = "ID_EVENTO")
    public BigDecimal getIdEvento() {
        return this.idEvento;
    }

    public void setIdEvento(BigDecimal idEvento) {
        this.idEvento = idEvento;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TIPO_EVENTO")
    public String getTipoEvento() {
        return this.tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

}