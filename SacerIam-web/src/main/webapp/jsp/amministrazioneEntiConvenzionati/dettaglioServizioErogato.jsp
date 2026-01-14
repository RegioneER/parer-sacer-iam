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

<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.DESCRIPTION%>">
        <script type="text/javascript">
            $(document).ready(function () {
                $('#Id_tipo_servizio').change(function () {
                    var value = $(this).val();
                    if (!value) {
                        $('#Id_servizio_erogato').text('');
                        $('#Nm_servizio_erogato').text('');
                        $('#Tipo_fatturazione').text('');
                        $('#Nm_tariffa').text('');
                    }
                });
            });
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.DESCRIPTION%>" />
            <%--            <c:if test="${sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].table['empty']}">
                            <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].status eq 'insert'}"/> 
                        </c:if>   
                        <c:if test="${!(sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].table['empty']) }">
                            <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />  
                        </c:if>--%>
            <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NAME%>" hideBackButton="${!(sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].status eq 'view')}"/> 
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_REG_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_SCAD_ACCORDO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.ID_SERVIZIO_EROGATO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NM_SERVIZIO_EROGATO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.ID_TIPO_SERVIZIO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.FL_PAGAMENTO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.DT_EROG%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.ID_SISTEMA_VERSANTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.TIPO_FATTURAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NM_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.IM_VALORE_TARIFFA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NT_SERVIZIO_EROG%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <sl:newLine skipLine="true"/>
            </slf:fieldSet>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ServiziFatturatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ServiziFatturatiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ServiziFatturatiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ServiziFatturatiList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
