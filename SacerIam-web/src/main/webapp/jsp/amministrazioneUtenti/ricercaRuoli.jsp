<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneRuoliForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%= AmministrazioneRuoliForm.ListaRuoli.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="LISTA RUOLI" />
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.NM_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w40"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w60"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_CATEG_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w60"/><sl:newLine/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.NM_APPLIC%>" width="w100" labelWidth="w20" controlWidth="w60"/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_STATO_RICH_ALLINEA_RUOLI_1%>" width="w100" labelWidth="w20" controlWidth="w60"/>
                <slf:lblField name="<%=AmministrazioneRuoliForm.FiltriRuoli.TI_STATO_RICH_ALLINEA_RUOLI_2%>" width="w100" labelWidth="w20" controlWidth="w60"/>
            </slf:fieldSet>

            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField  name="<%=AmministrazioneRuoliForm.FiltriRuoli.RICERCA_RUOLI%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <!--  piazzo la lista con i risultati -->
            <slf:list name="<%= AmministrazioneRuoliForm.ListaRuoli.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneRuoliForm.ListaRuoli.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
