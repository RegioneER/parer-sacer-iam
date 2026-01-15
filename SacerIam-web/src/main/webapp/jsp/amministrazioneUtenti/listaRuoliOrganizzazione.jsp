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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Dettaglio ruoli specifici su organizzazioni" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="DETTAGLIO RUOLI SPECIFICI SU ORGANIZZAZIONI" />
            <%--<c:if test="${sessionScope['###_FORM_CONTAINER']['listaUtenti'].table['empty']}">--%>
                <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.DettaglioUtente.NAME%>" /> 
            <%--</c:if>   --%>
            <%--<c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaUtenti'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" />  
            </c:if>--%>
            <sl:newLine skipLine="true" />
            <slf:fieldSet borderHidden="false">
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_COGNOME_USER%>" colSpan="2" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_NOME_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_USERID%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_APPLIC%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.TI_SCOPO_DICH_ABIL_ORGANIZ%>" colSpan="2" />                
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.DL_COMPOSITO_ORGANIZ%>" colSpan="4" />                
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <h2 class="titleFiltri">Lista ruoli</h2>
            <sl:newLine skipLine="true"/>
            <slf:list name="<%=AmministrazioneUtentiForm.RuoliList.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RuoliList.NAME%>" />
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
