<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTipiAccordoForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaTipiAccordo'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneTipiAccordoForm.TipoAccordoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaTipiAccordo'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaTipiAccordo'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneTipiAccordoForm.ListaTipiAccordo.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.ID_TIPO_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.CD_TIPO_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.DS_TIPO_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.FL_PAGAMENTO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.CD_ALGO_TARIFFARIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.DT_ISTITUZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.DT_SOPPRES%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
            </slf:fieldSet>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTipiAccordoForm.TipoAccordoDetail.LOG_EVENTI%>" width="w30"/>
            </sl:pulsantiera>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaTipiAccordo'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneTipiAccordoForm.TariffariSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneTipiAccordoForm.ListaTariffario.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneTipiAccordoForm.ListaTariffario.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneTipiAccordoForm.ListaTariffario.NAME%>" />
                </slf:section>               
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
