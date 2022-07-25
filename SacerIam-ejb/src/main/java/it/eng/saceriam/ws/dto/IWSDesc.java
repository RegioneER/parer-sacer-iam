package it.eng.saceriam.ws.dto;

/**
 *
 * @author Fioravanti_F
 */
public interface IWSDesc {

    String getNomeWs();

    String getVersione(); // versione standard, senza modifiche indotte dalla versione chiamata

    public String[] getCompatibilitaWS(); // lista di versioni compatibili con il parser
}
