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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DESCRIPTION%>">
        <script type="text/javascript" src="<c:url value='/js/customModificaAmbienteMessageBox.js'/>" ></script>
        <script type='text/javascript' >
            $(document).ready(function () {
                // TODO: VERIFICARE
//                $("#Id_ente_gestore").unbind('change');
//
//                $("#Id_ente_gestore").on('change', function () {
//                    var parameters = $('#spagoLiteAppForm').serializeArray();
//                    $.post("AmministrazioneEntiConvenzionati.html?operation=triggerAmbienteEnteConvenzionatoDetailId_ente_gestoreOnTriggerAjax", parameters).done(function (jsonData) {
//                        CAjaxDataFormWalk(jsonData);
//                        $('#loading').remove();
//                    });
//                });
                // end TODO
                CustomBoxModificaAmbiente();

            });
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <c:if test="${!empty requestScope.customModificaAmbiente}">
                <div class="messages customModificaAmbienteMessageBox ">
                    <ul>
                        <li class="message warning "><c:out value="${requestScope.messaggioModificaAmbiente}"/></li>
                    </ul>   
                </div>
            </c:if>
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ListaAmbientiEntiConvenzionati.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DS_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DS_NOTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DT_INI_VAL%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.DT_FINE_VAL%>" colSpan="2"/><sl:newLine />                
                <%--
                <c:choose>
                    <c:when test="${(sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].status eq 'view') || 
                                    ((sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].status eq 'update') && !(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].table['empty']))}">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NM_ENTE_GESTORE%>" colSpan="2"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NM_ENTE_CONSERV%>" colSpan="2"/><sl:newLine />
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.ID_ENTE_GESTORE%>" colSpan="2"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.ID_ENTE_CONSERV%>" colSpan="2"/><sl:newLine />
                    </c:otherwise>
                </c:choose>
                --%>
                <c:choose>
                    <c:when test="${(sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].status eq 'view')}">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NM_ENTE_GESTORE%>" colSpan="2"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.NM_ENTE_CONSERV%>" colSpan="2"/><sl:newLine />
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.ID_ENTE_GESTORE%>" colSpan="2"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.ID_ENTE_CONSERV%>" colSpan="2"/><sl:newLine />
                    </c:otherwise>
                </c:choose>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AmbienteEnteConvenzionatoDetail.LOG_EVENTI_AMBIENTE%>" width="w30"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaAmbientiEntiConvenzionati'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EntiConvenzionatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" />
                </slf:section>               
            </c:if>
             <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneAmbienteList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneAmbienteList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriConservazioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriConservazioneAmbienteList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriConservazioneAmbienteList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriGestioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriGestioneAmbienteList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriGestioneAmbienteList.NAME%>" />
            </slf:section>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
