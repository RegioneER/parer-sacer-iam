/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.dto;

import it.eng.saceriam.ws.utils.Costanti;
import java.util.EnumSet;

/**
 *
 * @author Fioravanti_F
 */
public interface IRestWSBase {

    String getDatiXml();

    void setDatiXml(String datiXml);

    public IWSDesc getDescrizione();

    public void setDescrizione(IWSDesc descrizione);

    // necessari a gestire l'EJB come stateless
    public String getLoginName();

    public void setLoginName(String loginName);

    public String getVersioneWsChiamata();

    public void setVersioneWsChiamata(String versioneWsChiamata);

    // <editor-fold defaultstate="collapsed" desc="Nuova gestione retrocompatibilità">
    public RispostaControlli checkVersioneRequest(String versione);

    public String getVersioneCalc();

    public EnumSet<Costanti.ModificatoriWS> getModificatoriWSCalc();
    // </editor-fold>
}
