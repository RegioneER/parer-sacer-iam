package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;

/**
 *
 * @author Gilioli_P
 */
public class CancellaOrganizzazioneExt {

    private IWSDesc descrizione;
    private Long idApplic;
    private Long idOrganizIam;
    private CancellaOrganizzazioneInput cancellaOrganizzazioneInput;

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

    public Long getIdOrganizIam() {
        return idOrganizIam;
    }

    public void setIdOrganizIam(Long idOrganizIam) {
        this.idOrganizIam = idOrganizIam;
    }

    public CancellaOrganizzazioneInput getCancellaOrganizzazioneInput() {
        return cancellaOrganizzazioneInput;
    }

    public void setCancellaOrganizzazioneInput(CancellaOrganizzazioneInput cancellaOrganizzazioneInput) {
        this.cancellaOrganizzazioneInput = cancellaOrganizzazioneInput;
    }
}