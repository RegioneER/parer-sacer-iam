<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTariffariForm.FiltriTariffari.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTariffariForm.FiltriTariffari.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.IS_VALIDO%>" colSpan="2" labelWidth="w10"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.NM_TARIFFARIO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.CD_TIPO_ACCORDO%>" width="w50" labelWidth="w20" controlWidth="w70"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTariffariForm.FiltriTariffari.RICERCA_TARIFFARI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneTariffariForm.ListaTariffario.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
