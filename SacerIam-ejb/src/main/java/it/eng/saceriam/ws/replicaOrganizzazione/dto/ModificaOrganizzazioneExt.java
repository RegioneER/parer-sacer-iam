package it.eng.saceriam.ws.replicaOrganizzazione.dto;

import it.eng.saceriam.ws.dto.IWSDesc;

/**
 *
 * @author Gilioli_P
 */
public class ModificaOrganizzazioneExt {

    private IWSDesc descrizione;
    private ModificaOrganizzazioneInput modificaOrganizzazioneInput;
    private Long idApplic;
    private Long idOrganizIam;
    private Long idOrganizIamPadre;
    private boolean template;

    public IWSDesc getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(IWSDesc descrizione) {
        this.descrizione = descrizione;
    }

    public it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneInput getModificaOrganizzazioneInput() {
        return modificaOrganizzazioneInput;
    }

    public void setModificaOrganizzazioneInput(
            it.eng.saceriam.ws.replicaOrganizzazione.dto.ModificaOrganizzazioneInput modificaOrganizzazioneInput) {
        this.modificaOrganizzazioneInput = modificaOrganizzazioneInput;
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

    public Long getIdOrganizIamPadre() {
        return idOrganizIamPadre;
    }

    public void setIdOrganizIamPadre(Long idOrganizIamPadre) {
        this.idOrganizIamPadre = idOrganizIamPadre;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

}