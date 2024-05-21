<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DESCRIPTION%>" >
    </sl:head>
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
                        <c:forTokens items = "${requestScope.listaUtentiCollegamento}" delims = "," var = "utentiCollegamento">
                            <c:out value = "${utentiCollegamento}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>      
            </c:if> 
            
            <c:if test="${!empty requestScope.customDeleteAppartenenzaCollegamentoMessageBox}">                
                <div class="messages confermaDeleteAppartenenzaCollegamento ">
                    <ul>
                        <li class="message warning ">Attenzione! La cancellazione dell'appartenenza al collegamento comporterà l'eliminazione delle abilitazioni concesse ai seguenti <c:out value = "${requestScope.numeroUtentiAppartenenzaCollegamento}"/> utenti:</li>
                    </ul>
                    <p  style="padding-left: 70px">                                                 
                        <c:forTokens items = "${requestScope.listaUtentiAppartenenzaCollegamento}" delims = "," var = "utentiAppartenenzaCollegamento">
                            <c:out value = "${utentiAppartenenzaCollegamento}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>      
            </c:if> 

            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customDeleteCollegamentoMessageBox.js'/>" ></script>
            <script type="text/javascript" src="<c:url value='/js/customDeleteAppartenenzaCollegamentoMessageBox.js'/>" ></script>

            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DESCRIPTION%>" />
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].table['empty'])}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].table['empty'])}">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true" />
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.ID_COLLEG_ENTI_CONVENZ%>" colSpan="2" />
                <sl:newLine /> 
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NM_COLLEG%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DS_COLLEG%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.TI_COLLEG%>" colSpan="2"/>
                <sl:newLine />
                <c:choose>
                    <c:when test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'view') }">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NM_ENTE_CONVENZ_CAPOFILA%>" colSpan="2"/>
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.ID_ENTE_CONVENZ_CAPOFILA%>" colSpan="2"/>
                    </c:otherwise>
                </c:choose>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_INI_VAL%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_FIN_VAL%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'view')}">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EntiCollegatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
