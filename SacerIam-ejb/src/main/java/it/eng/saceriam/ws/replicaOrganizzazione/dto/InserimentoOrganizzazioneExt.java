package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;

/**
 *
 * @author Gilioli_P
 */
public class InserimentoOrganizzazioneExt {

    private IWSDesc descrizione;
    private Long idApplic;
    private InserimentoOrganizzazioneInput inserimentoOrganizzazioneInput;

    public IWSDesc getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(IWSDesc descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdApplic() {
        return idApplic;
    }

    public void setIdApplic(Long idApplic) {
        this.idApplic = idApplic;
    }

    public InserimentoOrganizzazioneInput getInserimentoOrganizzazioneInput() {
        return inserimentoOrganizzazioneInput;
    }

    public void setInserimentoOrganizzazioneInput(InserimentoOrganizzazioneInput inserimentoOrganizzazioneInput) {
        this.inserimentoOrganizzazioneInput = inserimentoOrganizzazioneInput;
    }
}