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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * The persistent class for the NTF_NOTIFICA database table.
 */
@Entity
@Table(name = "NTF_NOTIFICA")
public class NtfNotifica implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idNotifica;

    private String dsDestinatario;

    private String dsEmailDestinatario;

    private String dsOggetto;

    private String dsTestoNotifica;

    private String nmMittente;

    private String tiStatoNotif;

    private Timestamp tsNotif;

    private DecModelloComunic decModelloComunic;

    private List<UsrAppartUserRich> usrAppartUserRiches1 = new ArrayList<>();

    private List<UsrAppartUserRich> usrAppartUserRiches2 = new ArrayList<>();

    private List<UsrRichGestUser> usrRichGestUsers = new ArrayList<>();

    private List<UsrStatoUser> usrStatoUsers = new ArrayList<>();

    public NtfNotifica() {
    }

    @Id
    @Column(name = "ID_NOTIFICA")
    @GenericGenerator(name = "SNTF_NOTIFICA_ID_NOTIFICA_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SNTF_NOTIFICA"),
            @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SNTF_NOTIFICA_ID_NOTIFICA_GENERATOR")
    public Long getIdNotifica() {
        return this.idNotifica;
    }

    public void setIdNotifica(Long idNotifica) {
        this.idNotifica = idNotifica;
    }

    @Column(name = "DS_DESTINATARIO")
    public String getDsDestinatario() {
        return this.dsDestinatario;
    }

    public void setDsDestinatario(String dsDestinatario) {
        this.dsDestinatario = dsDestinatario;
    }

    @Column(name = "DS_EMAIL_DESTINATARIO")
    public String getDsEmailDestinatario() {
        return this.dsEmailDestinatario;
    }

    public void setDsEmailDestinatario(String dsEmailDestinatario) {
        this.dsEmailDestinatario = dsEmailDestinatario;
    }

    @Column(name = "DS_OGGETTO")
    public String getDsOggetto() {
        return this.dsOggetto;
    }

    public void setDsOggetto(String dsOggetto) {
        this.dsOggetto = dsOggetto;
    }

    @Column(name = "DS_TESTO_NOTIFICA")
    public String getDsTestoNotifica() {
        return this.dsTestoNotifica;
    }

    public void setDsTestoNotifica(String dsTestoNotifica) {
        this.dsTestoNotifica = dsTestoNotifica;
    }

    @Column(name = "NM_MITTENTE")
    public String getNmMittente() {
        return this.nmMittente;
    }

    public void setNmMittente(String nmMittente) {
        this.nmMittente = nmMittente;
    }

    @Column(name = "TI_STATO_NOTIF")
    public String getTiStatoNotif() {
        return this.tiStatoNotif;
    }

    public void setTiStatoNotif(String tiStatoNotif) {
        this.tiStatoNotif = tiStatoNotif;
    }

    @Column(name = "TS_NOTIF")
    public Timestamp getTsNotif() {
        return this.tsNotif;
    }

    public void setTsNotif(Timestamp tsNotif) {
        this.tsNotif = tsNotif;
    }

    // bi-directional many-to-one association to DecModelloComunic
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MODELLO_COMUNIC")
    public DecModelloComunic getDecModelloComunic() {
        return this.decModelloComunic;
    }

    public void setDecModelloComunic(DecModelloComunic decModelloComunic) {
        this.decModelloComunic = decModelloComunic;
    }

    // bi-directional many-to-one association to UsrAppartUserRich
    @OneToMany(mappedBy = "ntfNotifica1")
    public List<UsrAppartUserRich> getUsrAppartUserRiches1() {
        return this.usrAppartUserRiches1;
    }

    public void setUsrAppartUserRiches1(List<UsrAppartUserRich> usrAppartUserRiches1) {
        this.usrAppartUserRiches1 = usrAppartUserRiches1;
    }

    // bi-directional many-to-one association to UsrAppartUserRich
    @OneToMany(mappedBy = "ntfNotifica2")
    public List<UsrAppartUserRich> getUsrAppartUserRiches2() {
        return this.usrAppartUserRiches2;
    }

    public void setUsrAppartUserRiches2(List<UsrAppartUserRich> usrAppartUserRiches2) {
        this.usrAppartUserRiches2 = usrAppartUserRiches2;
    }

    // bi-directional many-to-one association to UsrRichGestUser
    @OneToMany(mappedBy = "ntfNotifica")
    public List<UsrRichGestUser> getUsrRichGestUsers() {
        return this.usrRichGestUsers;
    }

    public void setUsrRichGestUsers(List<UsrRichGestUser> usrRichGestUsers) {
        this.usrRichGestUsers = usrRichGestUsers;
    }

    public UsrRichGestUser addUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
        getUsrRichGestUsers().add(usrRichGestUser);
        usrRichGestUser.setNtfNotifica(this);
        return usrRichGestUser;
    }

    public UsrRichGestUser removeUsrRichGestUser(UsrRichGestUser usrRichGestUser) {
        getUsrRichGestUsers().remove(usrRichGestUser);
        usrRichGestUser.setNtfNotifica(null);
        return usrRichGestUser;
    }

    // bi-directional many-to-one association to UsrStatoUser
    @OneToMany(mappedBy = "ntfNotifica")
    public List<UsrStatoUser> getUsrStatoUsers() {
        return this.usrStatoUsers;
    }

    public void setUsrStatoUsers(List<UsrStatoUser> usrStatoUsers) {
        this.usrStatoUsers = usrStatoUsers;
    }
}
