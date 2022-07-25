package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;
import it.eng.saceriam.ws.utils.Costanti;

/**
 *
 * @author Gilioli_P
 */
public class WSDescInserimentoOrganizzazione implements IWSDesc {

    @Override
    public String getNomeWs() {
        return Costanti.WS_INSERIMENTO_ORGANIZZAZIONE;
    }

    @Override
    public String getVersione() {
        return Costanti.WS_INSERIMENTO_ORGANIZZAZIONE_VRSN;
    }

    @Override
    public String[] getCompatibilitaWS() {
        return Costanti.WS_INSERIMENTO_ORGANIZZAZIONE_COMP;
    }
}