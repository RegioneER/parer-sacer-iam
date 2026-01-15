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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTariffariForm.FiltriTariffari.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTariffariForm.FiltriTariffari.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.IS_VALIDO%>" colSpan="2" labelWidth="w10"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.NM_TARIFFARIO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.CD_TIPO_ACCORDO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.RICERCA_TARIFFARI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
