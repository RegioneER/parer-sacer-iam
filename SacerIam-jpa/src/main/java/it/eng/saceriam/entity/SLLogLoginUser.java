/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author fioravanti_f
 */
@Entity
@Table(schema = "SACER_LOG", name = "LOG_LOGIN_USER")
public class SLLogLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idLoginUser;
    private String nmUserid;
    private String cdIndIpClient;
    private String cdIndServer;
    private String tipoEvento;
    private Date dtEvento;
    private String cdIdEsterno;
    private String tipoUtenteAuth;

    private AplApplic aplApplic;

    public SLLogLoginUser() {
    }

    @Id
    @NonMonotonicSequenceGenerator(schema = "SACER_LOG", sequenceName = "SLOG_LOGIN_USER") // @SequenceGenerator(name =
                                                                                           // "LOG_LOGIN_USER_IDLOG_LOGIN_USERJOB_GENERATOR",
                                                                                           // sequenceName =
                                                                                           // "SACER_LOG.SLOG_LOGIN_USER",
                                                                                           // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_LOGIN_USER_IDLOG_LOGIN_USERJOB_GENERATOR")
    @Column(name = "ID_LOGIN_USER")
    public Long getIdLoginUser() {
        return idLoginUser;
    }

    public void setIdLoginUser(Long idLoginUser) {
        this.idLoginUser = idLoginUser;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "CD_IND_IP_CLIENT")
    public String getCdIndIpClient() {
        return cdIndIpClient;
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        this.cdIndIpClient = cdIndIpClient;
    }

    @Column(name = "CD_IND_SERVER")
    public String getCdIndServer() {
        return cdIndServer;
    }

    public void setCdIndServer(String cdIndServer) {
        this.cdIndServer = cdIndServer;
    }

    @Column(name = "TIPO_EVENTO")

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_EVENTO")
    public Date getDtEvento() {
        return dtEvento;
    }

    public void setDtEvento(Date dtEvento) {
        this.dtEvento = dtEvento;
    }

    @Column(name = "CD_ID_ESTERNO")
    public String getCdIdEsterno() {
        return cdIdEsterno;
    }

    public void setCdIdEsterno(String cdIdEsterno) {
        this.cdIdEsterno = cdIdEsterno;
    }

    @Column(name = "TIPO_UTENTE_AUTH")
    public String getTipoUtenteAuth() {
        return tipoUtenteAuth;
    }

    public void setTipoUtenteAuth(String tipoUtenteAuth) {
        this.tipoUtenteAuth = tipoUtenteAuth;
    }

    // bi-directional many-to-one association
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APPLIC")

    public AplApplic getAplApplic() {
        return aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
        this.aplApplic = aplApplic;
    }

}
