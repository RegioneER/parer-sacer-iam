package it.eng.saceriam.grantedEntity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the LOG_EVENTO_LOGIN_USER database table.
 *
 */
@Entity
@Table(schema = "SACER_LOG", name = "LOG_EVENTO_LOGIN_USER")
@NamedQuery(name = "LogEventoLoginUser.findAll", query = "SELECT l FROM LogEventoLoginUser l")
public class LogEventoLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idEventoLoginUser;
    private String cdIndIpClient;
    private String cdIndServer;
    private String dsEvento;
    private Date dtEvento;
    private String nmUserid;
    private String tipoEvento;

    public LogEventoLoginUser() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SACER_LOG.SLOG_EVENTO_LOGIN_USER")
    @Column(name = "ID_EVENTO_LOGIN_USER")
    public Long getIdEventoLoginUser() {
        return this.idEventoLoginUser;
    }

    public void setIdEventoLoginUser(Long idEventoLoginUser) {
        this.idEventoLoginUser = idEventoLoginUser;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EVENTO")
    public Date getDtEvento() {
        return this.dtEvento;
    }

    public void setDtEvento(Date dtEvento) {
        this.dtEvento = dtEvento;
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
