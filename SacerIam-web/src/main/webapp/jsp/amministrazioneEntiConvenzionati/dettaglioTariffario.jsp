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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneTariffariForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneTariffariForm.TariffarioDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTariffariForm.TariffarioDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaTariffario'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneTariffariForm.TariffarioDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaTariffario'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaTariffario'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneTariffariForm.ListaTariffario.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffarioDetail.ID_TARIFFARIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffarioDetail.ID_TIPO_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffarioDetail.NM_TARIFFARIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffarioDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffarioDetail.DT_FINE_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
            </slf:fieldSet>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaTariffario'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneTariffariForm.TariffeSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneTariffariForm.ListaTariffe.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneTariffariForm.ListaTariffe.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneTariffariForm.ListaTariffe.NAME%>" />
                </slf:section>               
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
