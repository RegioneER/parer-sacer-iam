<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Dettaglio sistema versante">
        <script type="text/javascript" src="<c:url value='/js/customDeleteUtentiArkReferentiMessageBox.js'/>" ></script>        
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />

            <c:if test="${!empty requestScope.customDeleteUtentiArkMessageBox}">
                <div class="messages confermaDeleteUtentiArk ">
                    <ul>
                        <li class="message warning ">Desideri eliminare l’utente   
                            <c:out value="${utenteDaEliminare}" /> dalla lista degli archivisti di riferimento del sistema versante?</li>
                    </ul>
                </div>
            </c:if>

            <div class="pulsantieraDeleteUtentiArk">
                <sl:pulsantiera >
                    <slf:buttonList name="<%= AmministrazioneSistemiVersantiForm.UtentiArkCustomMessageButtonList.NAME%>"/>
                </sl:pulsantiera>
            </div>

            <c:if test="${!empty requestScope.customDeleteReferentiDittaProduttriceMessageBox}">
                <div class="messages confermaDeleteReferentiDittaProduttrice ">
                    <ul>
                        <li class="message warning ">Desideri eliminare l’utente   
                            <c:out value="${utenteDaEliminare}" /> dalla lista dei referenti della ditta produttrice?</li>
                    </ul>
                </div>
            </c:if>

            <div class="pulsantieraDeleteReferentiDittaProduttrice">
                <sl:pulsantiera >
                    <slf:buttonList name="<%= AmministrazioneSistemiVersantiForm.ReferentiDittaProduttriceCustomMessageButtonList.NAME%>"/>
                </sl:pulsantiera>
            </div>

            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="DETTAGLIO SISTEMA VERSANTE" />
            <slf:listNavBarDetail name="<%= AmministrazioneSistemiVersantiForm.ListaSistemiVersanti.NAME%>" />  
            <sl:newLine skipLine="true" />
            <slf:fieldSet borderHidden="false">
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.ID_SISTEMA_VERSANTE%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.NM_SISTEMA_VERSANTE%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DS_SISTEMA_VERSANTE%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.CD_VERSIONE%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.ID_ENTE_SIAM%>" colSpan="2"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DT_INI_VAL%>" colSpan="2"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DT_FINE_VAL%>" colSpan="2"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.FL_CESSATO%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.FL_INTEGRAZIONE%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.FL_ASSOCIA_PERSONA_FISICA%>" colSpan="2"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DS_NOTE%>" colSpan="2"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneSistemiVersantiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer">
                <slf:fieldSet borderHidden="false">
                    <slf:lblField name="<%= AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DS_VIA_SEDE_LEGALE%>" colSpan="2" />
                    <sl:newLine />
                    <slf:lblField name="<%= AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DS_CITTA_SEDE_LEGALE%>" colSpan="2" />
                    <sl:newLine />
                    <slf:lblField name="<%= AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.CD_CAP_SEDE_LEGALE%>" colSpan="2" />
                </slf:fieldSet>
            </slf:section>
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneSistemiVersantiForm.PostaElettronicaSection.NAME%>" styleClass="importantContainer">
                <slf:fieldSet borderHidden="false">
                    <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.DS_EMAIL%>" colSpan="2"/>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.SistemaVersanteDetail.FL_PEC%>" colSpan="2"/>
                </slf:fieldSet>
            </slf:section>
            <sl:newLine skipLine="true"/>
            <%--<c:if test="${!(sessionScope['###_FORM_CONTAINER']['ListaSistemiVersanti'].table['empty'])}">--%>
            <slf:section name="<%=AmministrazioneSistemiVersantiForm.StruttureVersantiSection.NAME%>" styleClass="importantContainer">
                <slf:list name="<%= AmministrazioneSistemiVersantiForm.StruttureVersantiList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneSistemiVersantiForm.StruttureVersantiList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneSistemiVersantiForm.UtentiArchivistiSection.NAME%>" styleClass="importantContainer">
                <slf:list name="<%= AmministrazioneSistemiVersantiForm.UtentiArchivistiList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneSistemiVersantiForm.UtentiArchivistiList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneSistemiVersantiForm.ReferentiDittaProduttriceSection.NAME%>" styleClass="importantContainer">
                <slf:list name="<%= AmministrazioneSistemiVersantiForm.ReferentiDittaProduttriceList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneSistemiVersantiForm.ReferentiDittaProduttriceList.NAME%>" />
            </slf:section>
            <%--</c:if>--%>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
