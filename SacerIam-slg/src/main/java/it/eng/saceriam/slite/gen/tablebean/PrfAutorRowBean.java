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

package it.eng.saceriam.slite.gen.tablebean;

import java.math.BigDecimal;

import it.eng.saceriam.entity.AplAzionePagina;
import it.eng.saceriam.entity.AplEntryMenu;
import it.eng.saceriam.entity.AplPaginaWeb;
import it.eng.saceriam.entity.AplServizioWeb;
import it.eng.saceriam.entity.PrfAutor;
import it.eng.saceriam.entity.PrfUsoRuoloApplic;
import it.eng.spagoLite.db.base.BaseRowInterface;
import it.eng.spagoLite.db.base.JEEBaseRowInterface;
import it.eng.spagoLite.db.base.row.BaseRow;
import it.eng.spagoLite.db.oracle.bean.column.TableDescriptor;

/**
 * RowBean per la tabella Prf_Autor
 *
 */
public class PrfAutorRowBean extends BaseRow implements BaseRowInterface, JEEBaseRowInterface {

    /*
     * @Generated( value = "it.eg.dbtool.db.oracle.beangen.Oracle4JPAClientBeanGen$TableBeanWriter",
     * comments = "This class was generated by OraTool", date = "Wednesday, 19 March 2014 10:37" )
     */

    public static PrfAutorTableDescriptor TABLE_DESCRIPTOR = new PrfAutorTableDescriptor();

    public PrfAutorRowBean() {
	super();
    }

    public TableDescriptor getTableDescriptor() {
	return TABLE_DESCRIPTOR;
    }

    // getter e setter
    public BigDecimal getIdAutor() {
	return getBigDecimal("id_autor");
    }

    public void setIdAutor(BigDecimal idAutor) {
	setObject("id_autor", idAutor);
    }

    public BigDecimal getIdUsoRuoloApplic() {
	return getBigDecimal("id_uso_ruolo_applic");
    }

    public void setIdUsoRuoloApplic(BigDecimal idUsoRuoloApplic) {
	setObject("id_uso_ruolo_applic", idUsoRuoloApplic);
    }

    public String getTiDichAutor() {
	return getString("ti_dich_autor");
    }

    public void setTiDichAutor(String tiDichAutor) {
	setObject("ti_dich_autor", tiDichAutor);
    }

    public BigDecimal getIdEntryMenu() {
	return getBigDecimal("id_entry_menu");
    }

    public void setIdEntryMenu(BigDecimal idEntryMenu) {
	setObject("id_entry_menu", idEntryMenu);
    }

    public BigDecimal getIdPaginaWeb() {
	return getBigDecimal("id_pagina_web");
    }

    public void setIdPaginaWeb(BigDecimal idPaginaWeb) {
	setObject("id_pagina_web", idPaginaWeb);
    }

    public BigDecimal getIdAzionePagina() {
	return getBigDecimal("id_azione_pagina");
    }

    public void setIdAzionePagina(BigDecimal idAzionePagina) {
	setObject("id_azione_pagina", idAzionePagina);
    }

    public BigDecimal getIdServizioWeb() {
	return getBigDecimal("id_servizio_web");
    }

    public void setIdServizioWeb(BigDecimal idServizioWeb) {
	setObject("id_servizio_web", idServizioWeb);
    }

    @Override
    public void entityToRowBean(Object obj) {
	PrfAutor entity = (PrfAutor) obj;
	this.setIdAutor(new BigDecimal(entity.getIdAutor()));
	if (entity.getPrfUsoRuoloApplic() != null) {
	    this.setIdUsoRuoloApplic(
		    new BigDecimal(entity.getPrfUsoRuoloApplic().getIdUsoRuoloApplic()));

	}
	this.setTiDichAutor(entity.getTiDichAutor());
	if (entity.getAplEntryMenu() != null) {
	    this.setIdEntryMenu(new BigDecimal(entity.getAplEntryMenu().getIdEntryMenu()));

	}
	if (entity.getAplPaginaWeb() != null) {
	    this.setIdPaginaWeb(new BigDecimal(entity.getAplPaginaWeb().getIdPaginaWeb()));

	}
	if (entity.getAplAzionePagina() != null) {
	    this.setIdAzionePagina(new BigDecimal(entity.getAplAzionePagina().getIdAzionePagina()));

	}
	if (entity.getAplServizioWeb() != null) {
	    this.setIdServizioWeb(new BigDecimal(entity.getAplServizioWeb().getIdServizioWeb()));

	}
    }

    @Override
    public PrfAutor rowBeanToEntity() {
	PrfAutor entity = new PrfAutor();
	if (this.getIdAutor() != null) {
	    entity.setIdAutor(this.getIdAutor().longValue());
	}
	if (this.getIdUsoRuoloApplic() != null) {
	    if (entity.getPrfUsoRuoloApplic() == null) {
		entity.setPrfUsoRuoloApplic(new PrfUsoRuoloApplic());
	    }
	    entity.getPrfUsoRuoloApplic()
		    .setIdUsoRuoloApplic(this.getIdUsoRuoloApplic().longValue());
	}
	entity.setTiDichAutor(this.getTiDichAutor());
	if (this.getIdEntryMenu() != null) {
	    if (entity.getAplEntryMenu() == null) {
		entity.setAplEntryMenu(new AplEntryMenu());
	    }
	    entity.getAplEntryMenu().setIdEntryMenu(this.getIdEntryMenu().longValue());
	}
	if (this.getIdPaginaWeb() != null) {
	    if (entity.getAplPaginaWeb() == null) {
		entity.setAplPaginaWeb(new AplPaginaWeb());
	    }
	    entity.getAplPaginaWeb().setIdPaginaWeb(this.getIdPaginaWeb().longValue());
	}
	if (this.getIdAzionePagina() != null) {
	    if (entity.getAplAzionePagina() == null) {
		entity.setAplAzionePagina(new AplAzionePagina());
	    }
	    entity.getAplAzionePagina().setIdAzionePagina(this.getIdAzionePagina().longValue());
	}
	if (this.getIdServizioWeb() != null) {
	    if (entity.getAplServizioWeb() == null) {
		entity.setAplServizioWeb(new AplServizioWeb());
	    }
	    entity.getAplServizioWeb().setIdServizioWeb(this.getIdServizioWeb().longValue());
	}
	return entity;
    }

    // gestione della paginazione
    public void setRownum(Integer rownum) {
	setObject("rownum", rownum);
    }

    public Integer getRownum() {
	return Integer.parseInt(getObject("rownum").toString());
    }

    public void setRnum(Integer rnum) {
	setObject("rnum", rnum);
    }

    public Integer getRnum() {
	return Integer.parseInt(getObject("rnum").toString());
    }

    public void setNumrecords(Integer numRecords) {
	setObject("numrecords", numRecords);
    }

    public Integer getNumrecords() {
	return Integer.parseInt(getObject("numrecords").toString());
    }

}
