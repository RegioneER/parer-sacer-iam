package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;
import it.eng.saceriam.ws.utils.Costanti;

/**
 *
 * @author Gilioli_P
 */
public class WSDescRestituzioneNewsApplicazione implements IWSDesc {

    @Override
    public String getNomeWs() {
        return Costanti.WS_RESTITUZIONE_NEWS_APPLICAZIONE;
    }

    @Override
    public String getVersione() {
        return Costanti.WS_RESTITUZIONE_NEWS_APPLICAZIONE_VRSN;
    }

    @Override
    public String[] getCompatibilitaWS() {
        return Costanti.WS_RESTITUZIONE_NEWS_APPLICAZIONE_COMP;
    }
}