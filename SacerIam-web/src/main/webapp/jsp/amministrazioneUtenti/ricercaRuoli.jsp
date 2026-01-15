<%--
 Engineering Ingegneria Informatica S.p.A.

 Copyright (C) 2023 Regione Emilia-Romagna
 <p/>
 This program is free software: you can redistribute it and/or modify it under the terms of
 the GNU Affero General Public License as published by the Free Software Foundation,
 either version 3 of the License, or (at your option) any later version.
 <p/>
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License for more details.
 <p/>
 You should have received a copy of the GNU Affero General Public License along with this program.
 If not, see <https://www.gnu.org/licenses/>.
 --%>

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneRuoliForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%= AmministrazioneRuoliForm.ListaRuoli.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="LISTA RUOLI" />
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.NM_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w40"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w60"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_CATEG_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w60"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.NM_APPLIC%>" width="w100" labelWidth="w20" controlWidth="w60"/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_STATO_RICH_ALLINEA_RUOLI_1%>" width="w100" labelWidth="w20" controlWidth="w60"/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_STATO_RICH_ALLINEA_RUOLI_2%>" width="w100" labelWidth="w20" controlWidth="w60"/>
            </slf:fieldSet>

            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField  name="<%=AmministrazioneRuoliForm.FiltriRuoli.RICERCA_RUOLI%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <!--  piazzo la lista con i risultati -->
            <slf:list name="<%= AmministrazioneRuoliForm.ListaRuoli.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneRuoliForm.ListaRuoli.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
