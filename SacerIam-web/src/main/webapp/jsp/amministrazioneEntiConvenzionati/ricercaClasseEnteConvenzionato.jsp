<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneClasseEnteForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneClasseEnteForm.FiltriClassiEnti.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneClasseEnteForm.FiltriClassiEnti.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneClasseEnteForm.FiltriClassiEnti.CD_CLASSE_ENTE_CONVENZ%>" colSpan="2" labelWidth="w10"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneClasseEnteForm.FiltriClassiEnti.DS_CLASSE_ENTE_CONVENZ%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneClasseEnteForm.FiltriClassiEnti.RICERCA_CLASSI_ENTI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneClasseEnteForm.ListaClassiEnti.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneClasseEnteForm.ListaClassiEnti.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneClasseEnteForm.ListaClassiEnti.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
