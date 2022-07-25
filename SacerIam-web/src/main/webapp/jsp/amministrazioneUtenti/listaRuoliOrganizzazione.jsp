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