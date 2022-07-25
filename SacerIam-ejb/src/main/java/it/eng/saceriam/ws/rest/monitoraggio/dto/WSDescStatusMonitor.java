/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.saceriam.ws.rest.monitoraggio.dto;

import it.eng.saceriam.ws.dto.IWSDesc;
import it.eng.saceriam.ws.utils.Costanti;

/**
 *
 * @author fioravanti_f
 */
public class WSDescStatusMonitor implements IWSDesc {

    @Override
    public String getVersione() {
        return Costanti.WS_STATUS_MONITOR_VRSN;
    }

    @Override
    public String getNomeWs() {
        return Costanti.WS_STATUS_MONITOR_NOME;
    }

    @Override
    public String[] getCompatibilitaWS() {
        return Costanti.WS_STATUS_MONITOR_COMP;
    }
}
