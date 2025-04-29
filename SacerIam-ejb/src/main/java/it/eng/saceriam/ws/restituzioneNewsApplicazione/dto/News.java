/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

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
