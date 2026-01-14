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
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.DESCRIPTION%>">
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true" >
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.DESCRIPTION%>" />
            <%--            <c:if test="${sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].table['empty']}">
                            <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.ServizioErogatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].status eq 'insert'}"/> 
                        </c:if>   
                        <c:if test="${!(sessionScope['###_FORM_CONTAINER']['serviziErogatiList'].table['empty']) }">
                            <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ServiziErogatiList.NAME%>" />  
                        </c:if>--%>
            <c:if test="${sessionScope['###_FORM_CONTAINER']['moduliInformazioniList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['moduliInformazioniList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['moduliInformazioniList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" />  
            </c:if>
            
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.ID_MODULO_INFO_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.CD_MODULO_INFO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.CD_REGISTRO_MODULO_INFO%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_MOD_INFO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.AA_MODULO_INFO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.CD_KEY_MODULO_INFO%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.DT_RICEV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.BL_MODULO_INFO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.DS_MODULO_INFO%>" colSpan="2"/><sl:newLine />
                <sl:newLine skipLine="true"/>
            </slf:fieldSet>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ModuloInformazioniDetail.DOWNLOAD_FILE%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
                
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
