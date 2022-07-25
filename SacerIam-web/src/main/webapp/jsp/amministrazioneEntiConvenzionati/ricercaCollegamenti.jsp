<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.FiltriCollegamenti.DESCRIPTION%>" ></sl:head>
        
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>            
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
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
