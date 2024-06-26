<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.DESCRIPTION%>" >
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <c:if test="${!empty requestScope.customDeleteAppartenenzaCollegamentoDaDettaglioEnteMessageBox}">                
                <div class="messages confermaDeleteAppartenenzaCollegamentoDaDettaglioEnte ">
                    <ul>
                        <li class="message warning ">Attenzione! La cancellazione dell'appartenenza al collegamento comporterà l'eliminazione delle abilitazioni concesse ai seguenti <c:out value = "${requestScope.numeroUtentiAppartenenzaCollegamentoDaDettaglioEnte}"/> utenti:</li>
                    </ul>
                    <p  style="padding-left: 70px">                                                 
                        <c:forTokens items = "${requestScope.listaUtentiAppartenenzaCollegamentoDaDettaglioEnte}" delims = "," var = "utentiAppartenenzaCollegamentoDaDettaglioEnte">
                            <c:out value = "${utentiAppartenenzaCollegamentoDaDettaglioEnte}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>      
            </c:if> 
            
            
            
            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customDeleteAppartenenzaCollegamentoDaDettaglioEnteMessageBox.js'/>" ></script>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.DESCRIPTION%>" />
            
            <c:if test="${sessionScope['###_FORM_CONTAINER']['collegamentiEnteAppartList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['collegamentiEnteAppartList'].status eq 'insert'}" />
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['collegamentiEnteAppartList'].table['empty']) }" >
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteAppartList.NAME%>" />  
            </c:if>
                
            <sl:newLine skipLine="true" />
            
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer noborder w100">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_SIAM%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/>          
                <sl:newLine />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.ID_APPART_COLLEG_ENTI%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.ID_COLLEG_ENTI_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.DT_INI_VAL%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteAppartDetail.DT_FIN_VAL%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteAppartList'].status eq 'view')}">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EntiCollegatiAppartSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiAppartList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiAppartList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiAppartList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
