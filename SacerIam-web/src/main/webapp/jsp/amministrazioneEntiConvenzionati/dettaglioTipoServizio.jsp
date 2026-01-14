<%--
 Engineering Ingegneria Informatica S.p.A.

 Copyright (C) 2023 Regione Emilia-Romagna
 <p/>
 This program is free software: you can redistribute it and/or modify it under the terms of
 the GNU Affero General Public License as published by the Free Software Foundation,
 either version 3 of the License, or (at your option) any later version.
 <p/>
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License for more details.
 <p/>
 You should have received a copy of the GNU Affero General Public License along with this program.
 If not, see <https://www.gnu.org/licenses/>.
 --%>

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTipiServizioForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaTipiServizio'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneTipiServizioForm.TipoServizioDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaTipiServizio'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaTipiServizio'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneTipiServizioForm.ListaTipiServizio.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.CD_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.DS_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.TI_CLASSE_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.TIPO_FATTURAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.FL_TARIFFA_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.FL_TARIFFA_ANNUALITA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
            </slf:fieldSet>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneTipiServizioForm.TipoServizioDetail.LOG_EVENTI%>" width="w30"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaTipiServizio'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneTipiServizioForm.TariffeSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneTipiServizioForm.ListaTariffeTipoServizio.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneTipiServizioForm.ListaTariffeTipoServizio.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneTipiServizioForm.ListaTariffeTipoServizio.NAME%>" />
                </slf:section> 
                <sl:newLine skipLine="true"/>
                 <slf:section name="<%=AmministrazioneTipiServizioForm.TariffeAccordiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneTipiServizioForm.ListaTariffeAccordiTipoServizio.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneTipiServizioForm.ListaTariffeAccordiTipoServizio.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneTipiServizioForm.ListaTariffeAccordiTipoServizio.NAME%>" />
                </slf:section>  
                 <sl:newLine skipLine="true"/>
                 <slf:section name="<%=AmministrazioneTipiServizioForm.TariffeAnnualitaSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneTipiServizioForm.ListaTariffeAnnualitaTipoServizio.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneTipiServizioForm.ListaTariffeAnnualitaTipoServizio.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneTipiServizioForm.ListaTariffeAnnualitaTipoServizio.NAME%>" />
                </slf:section>  
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
