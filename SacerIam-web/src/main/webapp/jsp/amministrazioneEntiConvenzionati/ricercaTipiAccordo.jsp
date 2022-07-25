<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTipiAccordoForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.CD_TIPO_ACCORDO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.DS_TIPO_ACCORDO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.FL_PAGAMENTO%>" colSpan="2" labelWidth="w10" /><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.IS_ATTIVO%>" colSpan="2" labelWidth="w10"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.FiltriTipiAccordi.RICERCA_TIPI_ACCORDI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneTipiAccordoForm.ListaTipiAccordo.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneTipiAccordoForm.ListaTipiAccordo.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneTipiAccordoForm.ListaTipiAccordo.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
