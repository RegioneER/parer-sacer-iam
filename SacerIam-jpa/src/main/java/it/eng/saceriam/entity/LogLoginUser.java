package it.eng.saceriam.entity;

import it.eng.sequences.hibernate.NonMonotonicSequenceGenerator;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * The persistent class for the LOG_LOGIN_USER database table.
 *
 */
/*
 * @Entity
 * 
 * @Table(name = "LOG_LOGIN_USER")
 * 
 * @NamedQuery(name = "LogLoginUser.findAll", query = "SELECT l FROM LogLoginUser l")
 */
public class LogLoginUser implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idLoginUser;
    private String cdIndIpClient;
    private String cdIndIpServer;
    private Date dtLogin;
    private String dtVirtualLogin;
    private String nmUserid;

    public LogLoginUser() {
    }

    @Id
    @NonMonotonicSequenceGenerator(sequenceName = "SLOG_LOGIN_USER")
    @Column(name = "ID_LOGIN_USER")
    public Long getIdLoginUser() {
        return this.idLoginUser;
    }

    public void setIdLoginUser(Long idLoginUser) {
        this.idLoginUser = idLoginUser;
    }

    @Column(name = "CD_IND_IP_CLIENT")
    public String getCdIndIpClient() {
        return this.cdIndIpClient;
    }

    public void setCdIndIpClient(String cdIndIpClient) {
        this.cdIndIpClient = cdIndIpClient;
    }

    @Column(name = "CD_IND_IP_SERVER")
    public String getCdIndIpServer() {
        return this.cdIndIpServer;
    }

    public void setCdIndIpServer(String cdIndIpServer) {
        this.cdIndIpServer = cdIndIpServer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_LOGIN")
    public Date getDtLogin() {
        return this.dtLogin;
    }

    public void setDtLogin(Date dtLogin) {
        this.dtLogin = dtLogin;
    }

    @Column(name = "DT_VIRTUAL_LOGIN")
    public String getDtVirtualLogin() {
        return this.dtVirtualLogin;
    }

    public void setDtVirtualLogin(String dtVirtualLogin) {
        this.dtVirtualLogin = dtVirtualLogin;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

}
