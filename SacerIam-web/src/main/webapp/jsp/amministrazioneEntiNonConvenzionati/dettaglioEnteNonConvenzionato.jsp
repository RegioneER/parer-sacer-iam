<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<sl:html>
    <sl:head title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DESCRIPTION%>" ></sl:head>
    <sl:body>
        <c:set var="showAccordiVigil" value="${sessionScope['###_FORM_CONTAINER']['enteNonConvenzionatoDetail']['ti_ente_non_convenz'].value == 'ORGANO_VIGILANZA'}"/>       
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />

            <%--    <c:if test="${!empty requestScope.customDeleteUtentiArkMessageBox}">
                    <div class="messages confermaDeleteUtentiArk ">
                        <ul>
                            <li class="message warning ">Desideri eliminare l’utente   
                                <c:out value="${utenteDaEliminare}" /> dalla lista degli archivisti di riferimento dell’ente convenzionato?</li>
                        </ul>
                    </div>

                <div class="pulsantieraDeleteUtentiArk">
                    <sl:pulsantiera >
                        <slf:buttonList name="<%= AmministrazioneEntiConvenzionatiForm.UtentiArkCustomMessageButtonList.NAME%>"/>
                    </sl:pulsantiera>
                </div>
            </c:if>

            <c:if test="${!empty requestScope.customDeleteReferentiEnteConvenzMessageBox}">
                <div class="messages confermaDeleteReferentiEnteConvenz ">
                    <ul>
                        <li class="message warning ">Desideri eliminare l’utente   
                            <c:out value="${referenteEnteConvenzDaEliminare}" /> dalla lista degli utenti referenti dell’ente convenzionato?</li>
                    </ul>
                </div>

                <div class="pulsantieraDeleteReferentiEnteConvenz">
                    <sl:pulsantiera >
                        <slf:buttonList name="<%= AmministrazioneEntiConvenzionatiForm.ReferentiEnteCustomMessageButtonList.NAME%>"/>
                    </sl:pulsantiera>
                </div>

            </c:if>--%>

            <%--
            <c:if test="${!empty requestScope.customModificaEnteNonConvenzMessageBox}">
                <div class="messages confermaModificaEnteNonConvenz ">
                    <ul>
                        <li class="message warning ">Sono stati inseriti i dati di approvazione di un nuovo accordo, si vuole procedere?</li>
                    </ul>
                </div>
            </c:if>        
            --%>

            <sl:newLine skipLine="true"/>
            <%--<script type="text/javascript" src="<c:url value='/js/customDeleteUtentiArkReferentiMessageBox.js'/>" ></script>--%>
            <%--<script type="text/javascript" src="<c:url value='/js/customDeleteUtentiReferentiEnteMessageBox.js'/>" ></script>--%>
            <%--<script type="text/javascript" src="<c:url value='/js/customModificaEnteConvenzMessageBox.js'/>" ></script>--%>
            <sl:contentTitle title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DESCRIPTION%>" />

            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].table['empty']}">                        
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].table['empty']) }">                        
                <slf:listNavBarDetail name="<%= AmministrazioneEntiNonConvenzionatiForm.ListaEntiNonConvenzionati.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.TI_ENTE_NON_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_CESSAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DS_NOTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteConvenzCorrispSection.NAME%>" styleClass="importantContainer w100">
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />   
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />   
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_ENTE_SIAM_PRODUT_CORRISP%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NM_ENTE_SIAM_PRODUT_CORRISP%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />   
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_ENTE_SIAM_PRODUT_CORRISP%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DS_VIA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_CAP_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DS_CITTA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_PROV_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_NAZIONE_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.CodiciSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_FISC%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.LOG_EVENTI%>" width="w30"/>              
            </slf:fieldSet>

            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].status eq 'view') }">
                <c:choose>
                    <c:when test="${showAccordiVigil}">
                        <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordiVigilanzaSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.AccordiVigilList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.AccordiVigilList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.AccordiVigilList.NAME%>" />
                        </slf:section>
                    </c:when>
                    <c:otherwise>
                        <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiSection.NAME%>" styleClass="noborder w100">
                            <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiList.NAME%>" pageSizeRelated="true"/>
                            <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiList.NAME%>" />
                            <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiList.NAME%>" />
                        </slf:section>
                    </c:otherwise>
                </c:choose>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.UtentiEnteNonConvenzionatoSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiEnteList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiEnteList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiEnteList.NAME%>" />
                </slf:section>
                <c:if test="${!showAccordiVigil}">
                    <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.VersatoriAssociatiSection.NAME%>" styleClass="noborder w100">
                        <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.VersatoriAssociatiList.NAME%>" pageSizeRelated="true"/>
                        <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.VersatoriAssociatiList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.VersatoriAssociatiList.NAME%>" />
                    </slf:section>
                    <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.SistemiVersantiSection.NAME%>" styleClass="noborder w100">
                        <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.SistemiVersantiList.NAME%>" pageSizeRelated="true"/>
                        <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.SistemiVersantiList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.SistemiVersantiList.NAME%>" />
                    </slf:section>
                </c:if>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.UtentiArchivistiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiArchivistiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiArchivistiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.UtentiArchivistiList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.ReferentiEnteSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.ReferentiEnteList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.ReferentiEnteList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.ReferentiEnteList.NAME%>" />
                </slf:section>               
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
