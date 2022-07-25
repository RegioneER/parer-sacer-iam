<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.DESCRIPTION%>">

    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />

            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['annualitaAccordoList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['annualitaAccordoList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['annualitaAccordoList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer w100">
                    <%--<slf:lblField name="<%= //AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />--%>
                    <%--<slf:lblField name="<%= //AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />--%>
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ_CAMBIO%>" colSpan="2"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                    <%--<slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />--%>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_GESTORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_GESTORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_CONSERV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_CONSERV%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ENTE_CONVENZ_AMMINISTRATORE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE_CONVENZ_AMMINISTRATORE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AccordoAnnualitaSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.TI_SCOPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_DEC_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_FINE_VALID_ACCORDO%>" colSpan="2"/><sl:newLine />     
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_STRUT%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_SCAD_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/>                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_REG_ACCORDO%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DS_FIRMATARIO_ENTE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.ID_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.AA_ANNO_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.MM_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.DS_ATTO_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.CD_CAPITOLO_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.DS_IMPEGNO_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.CD_CIG_AA_ACCORDO%>" colSpan="2"/><sl:newLine />     
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.CD_CUP_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.CD_ODA_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaAccordoDetail.DS_NOTA_AA_ACCORDO%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.TipiServizioSection.NAME%>" styleClass="importantContainer w100">
                    <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.TipoServizioAnnualitaAccordoList.NAME%>" multiRowEdit="true"/>
                </slf:section>
                <sl:newLine skipLine="true"/>
            </slf:fieldSet> 
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
