/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the USR_USER database table.
 */
@Entity
@Table(name = "USR_USER")
@NamedQuery(name = "UsrUser.findAll", query = "SELECT u FROM UsrUser u")
public class UsrUser implements Serializable {

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

    private List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs = new ArrayList<>();

    private List<AplSistemaVersArkRif> aplSistemaVersArkRifs = new ArrayList<>();

    private List<OrgEnteArkRif> orgEnteArkRifs = new ArrayList<>();

    private List<UsrAbilEnteSiam> usrAbilEnteSiams = new ArrayList<>();

    private List<UsrAppartUserRich> usrAppartUserRiches = new ArrayList<>();

    private List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs = new ArrayList<>();

    private List<UsrIndIpUser> usrIndIpUsers = new ArrayList<>();

    private List<UsrRichGestUser> usrRichGestUsers = new ArrayList<>();

    private List<UsrStatoUser> usrStatoUsers = new ArrayList<>();
    private String tipoAuth;
    private Date dtIniCert;
    private Date dtFinCert;
    private AplSistemaVersante aplSistemaVersante;

    private LogAgente logAgente;

    private OrgEnteSiam orgEnteSiam;

    private List<UsrUsoUserApplic> usrUsoUserApplics = new ArrayList<>();

    private List<UsrOldPsw> usrOldPsws = new ArrayList<>();

    private List<OrgEnteUserRif> orgEnteUserRifs = new ArrayList<>();
    private UsrUserExt usrUserExt;

    public UsrUser() {
    }

    @Id
    @Column(name = "ID_USER_IAM")
    @GenericGenerator(name = "SUSR_USER_ID_USER_IAM_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SUSR_USER"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUSR_USER_ID_USER_IAM_GENERATOR")
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

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_INI_CERT")
    public Date getDtIniCert() {
        return dtIniCert;
    }

    public void setDtIniCert(Date dtIniCert) {
        this.dtIniCert = dtIniCert;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_FIN_CERT")
    public Date getDtFinCert() {
        return dtFinCert;
    }

    public void setDtFinCert(Date dtFinCert) {
        this.dtFinCert = dtFinCert;
    }

    // bi-directional many-to-one association to UsrUsoUserApplic
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<UsrUsoUserApplic> getUsrUsoUserApplics() {
        return this.usrUsoUserApplics;
    }

    public void setUsrUsoUserApplics(List<UsrUsoUserApplic> usrUsoUserApplics) {
        this.usrUsoUserApplics = usrUsoUserApplics;
    }

    // bi-directional many-to-one association to AplSistemaVersArkRif
    @OneToMany(mappedBy = "usrUser")
    public List<AplSistemaVersArkRif> getAplSistemaVersArkRifs() {
        return this.aplSistemaVersArkRifs;
    }

    public void setAplSistemaVersArkRifs(List<AplSistemaVersArkRif> aplSistemaVersArkRifs) {
        this.aplSistemaVersArkRifs = aplSistemaVersArkRifs;
    }

    // bi-directional many-to-one association to OrgEnteArkRif
    @OneToMany(mappedBy = "usrUser")
    public List<OrgEnteArkRif> getOrgEnteArkRifs() {
        return this.orgEnteArkRifs;
    }

    public void setOrgEnteArkRifs(List<OrgEnteArkRif> orgEnteArkRifs) {
        this.orgEnteArkRifs = orgEnteArkRifs;
    }

    // bi-directional many-to-one association to AplSistemaVersanteUserRef
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.REMOVE })
    public List<AplSistemaVersanteUserRef> getAplSistemaVersanteUserRefs() {
        return this.aplSistemaVersanteUserRefs;
    }

    public void setAplSistemaVersanteUserRefs(List<AplSistemaVersanteUserRef> aplSistemaVersanteUserRefs) {
        this.aplSistemaVersanteUserRefs = aplSistemaVersanteUserRefs;
    }

    // bi-directional many-to-one association to UsrIndIpUser
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<UsrIndIpUser> getUsrIndIpUsers() {
        return this.usrIndIpUsers;
    }

    public void setUsrIndIpUsers(List<UsrIndIpUser> usrIndIpUsers) {
        this.usrIndIpUsers = usrIndIpUsers;
    }

    // bi-directional many-to-one association to UsrOldPsw
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE })
    public List<UsrOldPsw> getUsrOldPsws() {
        return this.usrOldPsws;
    }

    public void setUsrOldPsws(List<UsrOldPsw> usrOldPsws) {
        this.usrOldPsws = usrOldPsws;
    }

    @Column(name = "TIPO_USER")
    public String getTipoUser() {
        return this.tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    @Column(name = "TIPO_AUTH")
    public String getTipoAuth() {
        return this.tipoAuth;
    }

    public void setTipoAuth(String tipoAuth) {
        this.tipoAuth = tipoAuth;
    }

    // bi-directional many-to-one association to AplSistemaVersante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SISTEMA_VERSANTE")
    public AplSistemaVersante getAplSistemaVersante() {
        return this.aplSistemaVersante;
    }

    public void setAplSistemaVersante(AplSistemaVersante aplSistemaVersante) {
        this.aplSistemaVersante = aplSistemaVersante;
    }

    // bi-directional many-to-one association to UsrAbilEnteSiam
    @OneToMany(mappedBy = "usrUser")
    public List<UsrAbilEnteSiam> getUsrAbilEnteSiams() {
        return this.usrAbilEnteSiams;
    }

    public void setUsrAbilEnteSiams(List<UsrAbilEnteSiam> usrAbilEnteSiams) {
        this.usrAbilEnteSiams = usrAbilEnteSiams;
    }

    // bi-directional many-to-one association to UsrAppartUserRich
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.REMOVE })
    public List<UsrAppartUserRich> getUsrAppartUserRiches() {
        return this.usrAppartUserRiches;
    }

    public void setUsrAppartUserRiches(List<UsrAppartUserRich> usrAppartUserRiches) {
        this.usrAppartUserRiches = usrAppartUserRiches;
    }

    // bi-directional many-to-one association to UsrDichAbilEnteConvenz
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    public List<UsrDichAbilEnteConvenz> getUsrDichAbilEnteConvenzs() {
        return this.usrDichAbilEnteConvenzs;
    }

    public void setUsrDichAbilEnteConvenzs(List<UsrDichAbilEnteConvenz> usrDichAbilEnteConvenzs) {
        this.usrDichAbilEnteConvenzs = usrDichAbilEnteConvenzs;
    }

    // bi-directional many-to-one association to LogAgente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AGENTE")
    public LogAgente getLogAgente() {
        return this.logAgente;
    }

    public void setLogAgente(LogAgente logAgente) {
        this.logAgente = logAgente;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @OneToMany(mappedBy = "usrUser")
    public List<UsrRichGestUser> getUsrRichGestUsers() {
        return this.usrRichGestUsers;
    }

    public void setUsrRichGestUsers(List<UsrRichGestUser> usrRichGestUsers) {
        this.usrRichGestUsers = usrRichGestUsers;
    }

    // bi-directional many-to-one association to OrgEnteSiam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTE_SIAM")
    public OrgEnteSiam getOrgEnteSiam() {
        return this.orgEnteSiam;
    }

    public void setOrgEnteSiam(OrgEnteSiam orgEnteSiam) {
        this.orgEnteSiam = orgEnteSiam;
    }

    // bi-directional many-to-one association to UsrStatoUser
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.PERSIST })
    public List<UsrStatoUser> getUsrStatoUsers() {
        return this.usrStatoUsers;
    }

    public void setUsrStatoUsers(List<UsrStatoUser> usrStatoUsers) {
        this.usrStatoUsers = usrStatoUsers;
    }

    // bi-directional many-to-one association to OrgEnteUserRif
    @OneToMany(mappedBy = "usrUser", cascade = { CascadeType.REMOVE })
    public List<OrgEnteUserRif> getOrgEnteUserRifs() {
        return this.orgEnteUserRifs;
    }

    public void setOrgEnteUserRifs(List<OrgEnteUserRif> orgEnteUserRifs) {
        this.orgEnteUserRifs = orgEnteUserRifs;
    }

    @OneToOne // (optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER_IAM", referencedColumnName = "ID_USER_IAM")
    public UsrUserExt getUsrUserExt() {
        return usrUserExt;
    }

    public void setUsrUserExt(UsrUserExt usrUserExt) {
        this.usrUserExt = usrUserExt;
    }

}
