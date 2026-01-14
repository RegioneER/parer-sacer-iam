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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.DESCRIPTION%>" >
        <script type="text/javascript" src="<c:url value='/js/customCalcoloFattureProvvisorieRiemissioneFattureMessageBox.js'/>" ></script>             
    </sl:head>
        
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <%--<slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />--%>
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.NM_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.TI_ENTE_NON_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.FL_ENTE_CESSATO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.ARCHIVISTA%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.NO_ARCHIVISTA%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.RICERCA_ENTI_NON_CONVENZ%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.FiltriEntiNonConvenzionati.PULISCI_RICERCA%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneEntiNonConvenzionatiForm.ListaEntiNonConvenzionati.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiNonConvenzionatiForm.ListaEntiNonConvenzionati.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiNonConvenzionatiForm.ListaEntiNonConvenzionati.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
