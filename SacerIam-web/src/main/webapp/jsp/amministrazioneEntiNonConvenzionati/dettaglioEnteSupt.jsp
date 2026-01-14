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
    <sl:head title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <c:if test="${!empty requestScope.customDeleteSupportoMessageBox}">                
                <div class="messages confermaDeleteSupporto ">
                    <ul>
                        <li class="message warning ">Attenzione! La cancellazione dell'ente supportato comporterà l'eliminazione delle abilitazioni concesse ai seguenti <c:out value = "${requestScope.numeroUtentiSupporto}"/> utenti:</li>
                    </ul>
                    <p  style="padding-left: 70px">                                                 
                        <c:forTokens items = "${requestScope.listaUtentiSupporto}" delims = "," var = "utentiSupporto">
                            <c:out value = "${utentiSupporto}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>      
            </c:if>
            
            <!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- --><!-- -->
            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customDeleteSupportoMessageBox.js'/>" ></script>
            <sl:contentTitle title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.TI_ENTE_NON_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_INI_VAL%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_CESSAZIONE%>" colSpan="2"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_SUPT_EST_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DT_INI_VAL_SUPT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DT_FIN_VAL_SUPT%>" colSpan="2"/><sl:newLine />
                </slf:section>
            </slf:fieldSet>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
