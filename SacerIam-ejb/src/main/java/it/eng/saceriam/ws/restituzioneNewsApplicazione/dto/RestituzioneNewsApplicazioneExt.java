package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;

/**
 *
 * @author Gilioli_P
 */
public class RestituzioneNewsApplicazioneExt {

    private IWSDesc descrizione;
    private Long idApplic;
    private String nmApplic;

    public IWSDesc getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(IWSDesc descrizione) {
        this.descrizione = descrizione;
    }

    public String getNmApplic() {
        return nmApplic;
    }

    public void setNmApplic(String nmApplic) {
        this.nmApplic = nmApplic;
    }

    public Long getIdApplic() {
        return idApplic;
    }

    public void setIdApplic(Long idApplic) {
        this.idApplic = idApplic;
    }
}