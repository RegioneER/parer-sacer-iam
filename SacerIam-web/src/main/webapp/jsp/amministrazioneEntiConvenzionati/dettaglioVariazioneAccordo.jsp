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
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DESCRIPTION%>">
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true" >
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DESCRIPTION%>" />

            <c:if test="${sessionScope['###_FORM_CONTAINER']['gestioniAccordoList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['gestioniAccordoList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['gestioniAccordoList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.GestioniAccordoList.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true"/>

            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer w100">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_SIAM%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/>                
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.IdAccordoSection.NAME%>" styleClass="importantContainer w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_REG_ACCORDO%>" colSpan="2"/>
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoSection.NAME%>" styleClass="importantContainer w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.ID_TIPO_GESTIONE_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.TIPO_TRASMISSIONE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.PG_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.ID_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.ENTE_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.STRUTTURA_GEST_ACCORDO %>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.CD_REGISTRO_GEST_ACCORDO%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.AA_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.CD_KEY_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />                    
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DT_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DS_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DS_NOTA_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.NM_FILE_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.BL_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.ID_GEST_ACCORDO_RISPOSTA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.VISUALIZZA_RISP_GEST_ACCORDO%>" colSpan="2"/><sl:newLine />
                <sl:newLine skipLine="true"/>
            </slf:section>

            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.GestioneAccordoDetail.DOWNLOAD_FILE_GEST_ACCORDO%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
