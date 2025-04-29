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

package it.eng.saceriam.job.scadenzaFatture.ejb;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.parer.sacerlog.ejb.SacerLogEjb;
import it.eng.parer.sacerlog.util.LogParam;
import it.eng.saceriam.amministrazioneEntiConvenzionati.ejb.EntiConvenzionatiEjb;
import it.eng.saceriam.amministrazioneEntiConvenzionati.helper.EntiConvenzionatiHelper;
import it.eng.saceriam.common.Constants;
import it.eng.saceriam.entity.OrgFatturaEnte;
import it.eng.saceriam.entity.constraint.ConstOrgStatoFatturaEnte;
import it.eng.saceriam.helper.ParamHelper;
import it.eng.saceriam.job.ejb.JobLogger;
import it.eng.saceriam.util.SacerLogConstants;

/**
 *
 * @author Gilioli_P
 */
@Stateless(mappedName = "ScadenzaFattureEjb")
@LocalBean
@Interceptors({
	it.eng.saceriam.aop.TransactionInterceptor.class })
public class ScadenzaFattureEjb {

    private static final Logger log = LoggerFactory.getLogger(ScadenzaFattureEjb.class);

    @EJB
    private JobLogger jobLoggerEjb;
    @EJB
    private EntiConvenzionatiHelper ecHelper;
    @EJB
    private EntiConvenzionatiEjb ecEjb;
    @EJB
    private ParamHelper paramHelper;
    @EJB
    private SacerLogEjb sacerLogEjb;
    @Resource
    private SessionContext context;

    public void scadenzaFatture() {
	log.info(
		"Scadenza fatture - Recupero le fatture in stato EMESSA e in stato PAGATA_PARZIALMENTE");
	String[] statiFattura = {
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.EMESSA.getDescrizione(),
		ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.PAGATA_PARZIALMENTE.getDescrizione() };
	List<String> statiFatturaList = Arrays.asList(statiFattura);
	List<OrgFatturaEnte> fatturaEnteList = ecHelper
		.getOrgFatturaEnteByStatoList(statiFatturaList);
	log.info("Scadenza fatture - Ottenute " + fatturaEnteList.size()
		+ " fatture in stato EMESSA o PAGATA_PARZIALMENTE da elaborare");
	/*
	 * Codice aggiuntivo per il logging...
	 */
	LogParam param = new LogParam();
	param.setNomeApplicazione(paramHelper.getParamApplicApplicationName());
	param.setNomeUtente("Job scadenza fatture");
	param.setNomeTipoOggetto(SacerLogConstants.TIPO_OGGETTO_FATTURA);
	param.setNomeComponenteSoftware("SCADENZA_FATTURE");
	param.setNomeAzione("Controllo scadenza fatture");

	/* Per ogni fattura */
	for (OrgFatturaEnte fatturaEnte : fatturaEnteList) {
	    if (fatturaEnte.getDtScadFattura() != null
		    && fatturaEnte.getDtScadFattura().before(new Date())) {
		// Pongo la fattura in stato INSOLUTA inserendo un nuovo record di stato
		ecEjb.insertStatoFatturaEnte(fatturaEnte,
			ConstOrgStatoFatturaEnte.TiStatoFatturaEnte.INSOLUTA.getDescrizione());
		// Registro il log del cambio di stato della fattura
		// sacerLogEjb.log(param.getTransactionLogContext(),
		// paramHelper.getParamApplicApplicationName(),
		// param.getNomeUtente(), param.getNomeAzione(), param.getNomeTipoOggetto(),
		// BigDecimal.valueOf(fatturaEnte.getIdFatturaEnte()), param.getNomePagina());
	    }
	}

	/* Scrivo nel log del job l'esito finale */
	jobLoggerEjb.writeAtomicLog(Constants.NomiJob.SCADENZA_FATTURE,
		Constants.TipiRegLogJob.FINE_SCHEDULAZIONE, null);
    }

}
