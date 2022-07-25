<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTipiServizioForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.CD_TIPO_SERVIZIO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.TI_CLASSE_TIPO_SERVIZIO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.TIPO_FATTURAZIONE%>" colSpan="2" labelWidth="w10" /><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.FiltriTipiServizio.RICERCA_TIPI_SERVIZIO%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneTipiServizioForm.ListaTipiServizio.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneTipiServizioForm.ListaTipiServizio.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneTipiServizioForm.ListaTipiServizio.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
