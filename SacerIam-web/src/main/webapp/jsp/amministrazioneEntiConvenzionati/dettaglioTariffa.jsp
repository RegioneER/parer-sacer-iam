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
    <sl:head title="<%=AmministrazioneTariffariForm.TariffaDetail.DESCRIPTION%>" >
         <script type="text/javascript">
            $(document).ready(function () {
                abilitaCampiTariffa();
            });

           function abilitaCampiTariffa() {
                $("#Cd_tipo_servizio").change(function () {
                    var value = $("#Cd_tipo_servizio").val();
                    if (value === 'ATTIVAZIONE_TIPO_UD') {
                        $("#Cd_classe_ente_convenz").attr("disabled", true);
                        $("#Cd_classe_ente_convenz").val("");
                    } else {
                        $("#Cd_classe_ente_convenz").attr("disabled", false);
                    }

                }).trigger('change');
                
               
                  
               
            }
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneTariffariForm.TariffaDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaTariffe'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneTariffariForm.TariffaDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaTariffe'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaTariffe'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneTariffariForm.ListaTariffe.NAME%>" />  
            </c:if>            
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.ID_CLASSE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.DS_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.TIPO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.IM_VALORE_FISSO_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneTariffariForm.TariffaDetail.NI_QUANTITA_UNIT%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaTariffe'].status eq 'view') }">
                <slf:container width="w30">
                    <slf:section name="<%=AmministrazioneTariffariForm.ScaglioniSection.NAME%>" styleClass="noborder w100">
                        <slf:listNavBar name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" pageSizeRelated="true"/>
                        <slf:list name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneTariffariForm.ListaScaglioni.NAME%>" />
                    </slf:section>               
                </slf:container>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
