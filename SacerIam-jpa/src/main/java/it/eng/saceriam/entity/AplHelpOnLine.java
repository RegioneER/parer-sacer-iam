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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package it.eng.saceriam.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

/**
 * @author Iacolucci_M
 */
@Entity
@Table(name = "APL_HELP_ON_LINE")
@NamedQueries({
	@NamedQuery(name = "AplHelpOnLine.findMaxDtFinWithMenu", query = "SELECT max(a.dtFineVal) FROM AplHelpOnLine a WHERE a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb AND a.aplEntryMenu.idEntryMenu = :idEntryMenu"),
	@NamedQuery(name = "AplHelpOnLine.findMaxDtFinWithoutMenu", query = "SELECT max(a.dtFineVal) FROM AplHelpOnLine a WHERE a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb"),
	@NamedQuery(name = "AplHelpOnLine.existsIntersectionByPage", query = "select DISTINCT 1 FROM AplHelpOnLine a WHERE a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb AND ("
		+ "          ( a.dtIniVal >= :dataInizio AND a.dtIniVal <= :dataFine ) OR "
		+ "          ( a.dtIniVal <= :dataInizio AND a.dtFineVal >= :dataInizio AND a.dtIniVal <= :dataFine ) )"),
	@NamedQuery(name = "AplHelpOnLine.existsIntersectionByPageExcludingHelp", query = "select DISTINCT 1 FROM AplHelpOnLine a WHERE a.idHelpOnLine <> :idHelpOnLine AND a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb AND ("
		+ "          ( a.dtIniVal >= :dataInizio AND a.dtIniVal <= :dataFine ) OR "
		+ "          ( a.dtIniVal <= :dataInizio AND a.dtFineVal >= :dataInizio AND a.dtIniVal <= :dataFine ) )"),
	@NamedQuery(name = "AplHelpOnLine.existsIntersectionByMenu", query = "select DISTINCT 1 FROM AplHelpOnLine a WHERE a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb AND a.aplEntryMenu.idEntryMenu = :idEntryMenu AND ("
		+ "          ( a.dtIniVal >= :dataInizio AND a.dtIniVal <= :dataFine ) OR "
		+ "          ( a.dtIniVal <= :dataInizio AND a.dtFineVal >= :dataInizio AND a.dtIniVal <= :dataFine ) )"),
	@NamedQuery(name = "AplHelpOnLine.existsIntersectionByMenuExcludingHelp", query = "select DISTINCT 1 FROM AplHelpOnLine a WHERE a.idHelpOnLine <> :idHelpOnLine AND a.aplApplic.idApplic = :idApplic AND a.tiHelpOnLine = :tiHelpOnLine AND a.aplPaginaWeb.idPaginaWeb = :idPaginaWeb AND a.aplEntryMenu.idEntryMenu = :idEntryMenu AND ("
		+ "          ( a.dtIniVal >= :dataInizio AND a.dtIniVal <= :dataFine ) OR "
		+ "          ( a.dtIniVal <= :dataInizio AND a.dtFineVal >= :dataInizio AND a.dtIniVal <= :dataFine ) )") })
public class AplHelpOnLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idHelpOnLine;

    private String tiHelpOnLine;

    private Date dtIniVal;

    private Date dtFineVal;

    private String dsFileHelpOnLine;

    private String blHelpOnLine;

    private byte[] blSorgenteHelpOnLine;

    private AplPaginaWeb aplPaginaWeb;

    private AplEntryMenu aplEntryMenu;

    private AplApplic aplApplic;

    public AplHelpOnLine() {
    }

    public AplHelpOnLine(long idHelpOnLine) {
	this.idHelpOnLine = idHelpOnLine;
    }

    public AplHelpOnLine(long idHelpOnLine, String tiHelpOnLine, Date dtIniVal, Date dtFineVal,
	    String dsFileHelpOnLine, String blHelpOnLine, byte[] blSorgenteHelpOnLine) {
	this.idHelpOnLine = idHelpOnLine;
	this.tiHelpOnLine = tiHelpOnLine;
	this.dtIniVal = dtIniVal;
	this.dtFineVal = dtFineVal;
	this.dsFileHelpOnLine = dsFileHelpOnLine;
	this.blHelpOnLine = blHelpOnLine;
	this.blSorgenteHelpOnLine = blSorgenteHelpOnLine;
    }

    @Id
    @Column(name = "ID_HELP_ON_LINE")
    @GenericGenerator(name = "SAPL_HELP_ON_LINE_ID_HELP_ON_LINE_GENERATOR", strategy = "it.eng.sequences.hibernate.NonMonotonicSequenceGenerator", parameters = {
	    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "SAPL_HELP_ON_LINE"),
	    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "1") })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAPL_HELP_ON_LINE_ID_HELP_ON_LINE_GENERATOR")
    public Long getIdHelpOnLine() {
	return idHelpOnLine;
    }

    public void setIdHelpOnLine(Long idHelpOnLine) {
	this.idHelpOnLine = idHelpOnLine;
    }

    @Column(name = "TI_HELP_ON_LINE")
    public String getTiHelpOnLine() {
	return tiHelpOnLine;
    }

    public void setTiHelpOnLine(String tiHelpOnLine) {
	this.tiHelpOnLine = tiHelpOnLine;
    }

    @Column(name = "DT_INI_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDtIniVal() {
	return dtIniVal;
    }

    public void setDtIniVal(Date dtIniVal) {
	this.dtIniVal = dtIniVal;
    }

    @Column(name = "DT_FINE_VAL")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDtFineVal() {
	return dtFineVal;
    }

    public void setDtFineVal(Date dtFineVal) {
	this.dtFineVal = dtFineVal;
    }

    @Column(name = "DS_FILE_HELP_ON_LINE")
    public String getDsFileHelpOnLine() {
	return dsFileHelpOnLine;
    }

    public void setDsFileHelpOnLine(String dsFileHelpOnLine) {
	this.dsFileHelpOnLine = dsFileHelpOnLine;
    }

    @Lob
    @Column(name = "BL_HELP_ON_LINE")
    public String getBlHelpOnLine() {
	return blHelpOnLine;
    }

    public void setBlHelpOnLine(String blHelpOnLine) {
	this.blHelpOnLine = blHelpOnLine;
    }

    @Lob
    @Column(name = "BL_SORGENTE_HELP_ON_LINE")
    public byte[] getBlSorgenteHelpOnLine() {
	return blSorgenteHelpOnLine;
    }

    public void setBlSorgenteHelpOnLine(byte[] blSorgenteHelpOnLine) {
	this.blSorgenteHelpOnLine = blSorgenteHelpOnLine;
    }

    @JoinColumn(name = "ID_PAGINA_WEB", referencedColumnName = "ID_PAGINA_WEB")
    @ManyToOne(optional = false)
    public AplPaginaWeb getAplPaginaWeb() {
	return aplPaginaWeb;
    }

    public void setAplPaginaWeb(AplPaginaWeb aplPaginaWeb) {
	this.aplPaginaWeb = aplPaginaWeb;
    }

    @JoinColumn(name = "ID_ENTRY_MENU", referencedColumnName = "ID_ENTRY_MENU")
    @ManyToOne
    public AplEntryMenu getAplEntryMenu() {
	return aplEntryMenu;
    }

    public void setAplEntryMenu(AplEntryMenu aplEntryMenu) {
	this.aplEntryMenu = aplEntryMenu;
    }

    @JoinColumn(name = "ID_APPLIC", referencedColumnName = "ID_APPLIC")
    @ManyToOne(optional = false)
    public AplApplic getAplApplic() {
	return aplApplic;
    }

    public void setAplApplic(AplApplic aplApplic) {
	this.aplApplic = aplApplic;
    }
}
