<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.FiltriAmbientiEntiConvenzionati.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.FiltriAmbientiEntiConvenzionati.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAmbientiEntiConvenzionati.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAmbientiEntiConvenzionati.DS_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />          
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriAmbientiEntiConvenzionati.RICERCA_AMBIENTI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaAmbientiEntiConvenzionati.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.ListaAmbientiEntiConvenzionati.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaAmbientiEntiConvenzionati.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
