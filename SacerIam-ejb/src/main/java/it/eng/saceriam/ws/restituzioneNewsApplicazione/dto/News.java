package it.eng.saceriam.ws.restituzioneNewsApplicazione.dto;

import java.util.Date;

/**
 *
 * @author Gilioli_P
 */
public class News {
    private String dsOggetto;
    private Date dtIniPubblic;
    private Date dtFinPubblic;
    private String dlTesto;

    public String getDsOggetto() {
        return dsOggetto;
    }

    public void setDsOggetto(String dsOggetto) {
        this.dsOggetto = dsOggetto;
    }

    public Date getDtIniPubblic() {
        return dtIniPubblic;
    }

    public void setDtIniPubblic(Date dtIniPubblic) {
        this.dtIniPubblic = dtIniPubblic;
    }

    public Date getDtFinPubblic() {
        return dtFinPubblic;
    }

    public void setDtFinPubblic(Date dtFinPubblic) {
        this.dtFinPubblic = dtFinPubblic;
    }

    public String getDlTesto() {
        return dlTesto;
    }

    public void setDlTesto(String dlTesto) {
        this.dlTesto = dlTesto;
    }

}