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

package it.eng.saceriam.job.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;

public class RichiestaSacerInput extends RichiestaSacer {

    private String urlRichiesta;
    private Integer timeout;
    private List<NameValuePair> params;

    public RichiestaSacerInput(TipoRichiesta tipoRichiesta, String urlRichiesta, Integer timeout,
	    NameValuePair... paramsCollection) {
	super(tipoRichiesta);
	this.urlRichiesta = urlRichiesta;
	this.timeout = timeout;
	this.params = paramsCollection != null ? Arrays.asList(paramsCollection)
		: new ArrayList<NameValuePair>();
    }

    public RichiestaSacerInput(TipoRichiesta tipoRichiesta, String urlRichiesta, Integer timeout,
	    List<NameValuePair> params) {
	super(tipoRichiesta);
	this.urlRichiesta = urlRichiesta;
	this.timeout = timeout;
	this.params = params;
    }

    public String getUrlRichiesta() {
	return urlRichiesta;
    }

    public void setUrlRichiesta(String urlRichiesta) {
	this.urlRichiesta = urlRichiesta;
    }

    public Integer getTimeout() {
	return timeout;
    }

    public void setTimeout(Integer timeout) {
	this.timeout = timeout;
    }

    public List<NameValuePair> getParams() {
	return params;
    }

    public void setParams(List<NameValuePair> params) {
	this.params = params;
    }
}
