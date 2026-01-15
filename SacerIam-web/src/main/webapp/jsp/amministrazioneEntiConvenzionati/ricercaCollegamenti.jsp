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
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DESCRIPTION%>" ></sl:head>
        
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>            
            <slf:messageBox />
            <c:if test="${!empty requestScope.customDeleteCollegamentoMessageBox}">
                <div class="messages confermaDeleteCollegamento ">
                    <ul>
                        <li class="message warning ">Attenzione! La cancellazione del collegamento comporterà l'eliminazione delle abilitazioni concesse ai seguenti <c:out value = "${requestScope.numeroUtentiCollegamento}"/> utenti:</li>
                    </ul>
                    <p  style="padding-left: 70px">                                                 
                        <c:forTokens items = "${requestScope.listaUtentiCollegamento}" delims = "," var = "utentiCollegamenti">
                            <c:out value = "${utentiCollegamenti}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>                
            </c:if> 
            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customDeleteCollegamentoMessageBox.js'/>" ></script>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.NM_COLLEG%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DS_COLLEG%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DT_VALID_DA%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DT_VALID_A%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.RICERCA_COLLEGAMENTI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentiEnteList.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentiEnteList.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentiEnteList.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
