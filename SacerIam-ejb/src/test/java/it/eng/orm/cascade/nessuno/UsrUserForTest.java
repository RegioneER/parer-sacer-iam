package it.eng.orm.cascade.nessuno;

import it.eng.orm.cascade.nessuno.LogAgenteForTest;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the USR_USER database table.
 */
@Entity
@Table(name = "USR_USER")
@NamedQuery(name = "UsrUser.findAll", query = "SELECT u FROM UsrUserForTest u")
public class UsrUserForTest implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idUserIam;
    private String cdFisc;
    private String cdPsw;
    private String cdSalt;
    private String dsEmail;
    private String dsEmailSecondaria;
    private Date dtRegPsw;
    private Date dtScadPsw;
    private String flAbilEntiCollegAutom;
    private String flAbilFornitAutom;
    private String flAbilOrganizAutom;
    private String flAttivo;
    private String flContrIp;
    private String flRespEnteConvenz;
    private String flUserAdmin;
    private String flUtenteDitta;
    private BigDecimal idStatoUserCor;
    private String nmCognomeUser;
    private String nmNomeUser;
    private String nmUserid;
    private String tipoUser;
    private LogAgenteForTest logAgente;
    private Long idEnteSiam;

    public UsrUserForTest() {
    }

    @Id
    @SequenceGenerator(name = "USR_USER_IDUSERIAM_GENERATOR", sequenceName = "SUSR_USER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_USER_IDUSERIAM_GENERATOR")
    @Column(name = "ID_USER_IAM")
    public Long getIdUserIam() {
        return this.idUserIam;
    }

    public void setIdUserIam(Long idUserIam) {
        this.idUserIam = idUserIam;
    }

    @Column(name = "CD_FISC")
    public String getCdFisc() {
        return this.cdFisc;
    }

    public void setCdFisc(String cdFisc) {
        this.cdFisc = cdFisc;
    }

    @Column(name = "CD_PSW")
    public String getCdPsw() {
        return this.cdPsw;
    }

    public void setCdPsw(String cdPsw) {
        this.cdPsw = cdPsw;
    }

    @Column(name = "CD_SALT")
    public String getCdSalt() {
        return this.cdSalt;
    }

    public void setCdSalt(String cdSalt) {
        this.cdSalt = cdSalt;
    }

    @Column(name = "DS_EMAIL")
    public String getDsEmail() {
        return this.dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    @Column(name = "DS_EMAIL_SECONDARIA")
    public String getDsEmailSecondaria() {
        return this.dsEmailSecondaria;
    }

    public void setDsEmailSecondaria(String dsEmailSecondaria) {
        this.dsEmailSecondaria = dsEmailSecondaria;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_REG_PSW")
    public Date getDtRegPsw() {
        return this.dtRegPsw;
    }

    public void setDtRegPsw(Date dtRegPsw) {
        this.dtRegPsw = dtRegPsw;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SCAD_PSW")
    public Date getDtScadPsw() {
        return this.dtScadPsw;
    }

    public void setDtScadPsw(Date dtScadPsw) {
        this.dtScadPsw = dtScadPsw;
    }

    @Column(name = "FL_ABIL_ENTI_COLLEG_AUTOM", columnDefinition = "char(1)")
    public String getFlAbilEntiCollegAutom() {
        return this.flAbilEntiCollegAutom;
    }

    public void setFlAbilEntiCollegAutom(String flAbilEntiCollegAutom) {
        this.flAbilEntiCollegAutom = flAbilEntiCollegAutom;
    }

    @Column(name = "FL_ABIL_FORNIT_AUTOM", columnDefinition = "char(1)")
    public String getFlAbilFornitAutom() {
        return this.flAbilFornitAutom;
    }

    public void setFlAbilFornitAutom(String flAbilFornitAutom) {
        this.flAbilFornitAutom = flAbilFornitAutom;
    }

    @Column(name = "FL_ABIL_ORGANIZ_AUTOM", columnDefinition = "char(1)")
    public String getFlAbilOrganizAutom() {
        return this.flAbilOrganizAutom;
    }

    public void setFlAbilOrganizAutom(String flAbilOrganizAutom) {
        this.flAbilOrganizAutom = flAbilOrganizAutom;
    }

    @Column(name = "FL_ATTIVO", columnDefinition = "char(1)")
    public String getFlAttivo() {
        return this.flAttivo;
    }

    public void setFlAttivo(String flAttivo) {
        this.flAttivo = flAttivo;
    }

    @Column(name = "FL_CONTR_IP", columnDefinition = "char(1)")
    public String getFlContrIp() {
        return this.flContrIp;
    }

    public void setFlContrIp(String flContrIp) {
        this.flContrIp = flContrIp;
    }

    @Column(name = "FL_RESP_ENTE_CONVENZ", columnDefinition = "char(1)")
    public String getFlRespEnteConvenz() {
        return this.flRespEnteConvenz;
    }

    public void setFlRespEnteConvenz(String flRespEnteConvenz) {
        this.flRespEnteConvenz = flRespEnteConvenz;
    }

    @Column(name = "FL_USER_ADMIN", columnDefinition = "char(1)")
    public String getFlUserAdmin() {
        return this.flUserAdmin;
    }

    public void setFlUserAdmin(String flUserAdmin) {
        this.flUserAdmin = flUserAdmin;
    }

    @Column(name = "FL_UTENTE_DITTA", columnDefinition = "char(1)")
    public String getFlUtenteDitta() {
        return this.flUtenteDitta;
    }

    public void setFlUtenteDitta(String flUtenteDitta) {
        this.flUtenteDitta = flUtenteDitta;
    }

    @Column(name = "ID_STATO_USER_COR")
    public BigDecimal getIdStatoUserCor() {
        return this.idStatoUserCor;
    }

    public void setIdStatoUserCor(BigDecimal idStatoUserCor) {
        this.idStatoUserCor = idStatoUserCor;
    }

    @Column(name = "NM_COGNOME_USER")
    public String getNmCognomeUser() {
        return this.nmCognomeUser;
    }

    public void setNmCognomeUser(String nmCognomeUser) {
        this.nmCognomeUser = nmCognomeUser;
    }

    @Column(name = "NM_NOME_USER")
    public String getNmNomeUser() {
        return this.nmNomeUser;
    }

    public void setNmNomeUser(String nmNomeUser) {
        this.nmNomeUser = nmNomeUser;
    }

    @Column(name = "NM_USERID")
    public String getNmUserid() {
        return this.nmUserid;
    }

    public void setNmUserid(String nmUserid) {
        this.nmUserid = nmUserid;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
        return this.tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    // bi-directional many-to-one association to LogAgente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENTE", nullable = false)
    public LogAgenteForTest getLogAgente() {
        return this.logAgente;
    }

    public void setLogAgente(LogAgenteForTest logAgente) {
        this.logAgente = logAgente;
    }

    @Column(name = "ID_ENTE_SIAM")
    public Long getIdEnteSiam() {
        return idEnteSiam;
    }

    public void setIdEnteSiam(Long idEnteSiam) {
        this.idEnteSiam = idEnteSiam;
    }
}
