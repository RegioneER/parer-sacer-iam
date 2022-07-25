<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['accordiVigilList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['accordiVigilList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['accordiVigilList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiNonConvenzionatiForm.AccordiVigilList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.TI_ENTE_NON_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.ID_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DT_INI_VAL_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DT_CESSAZIONE_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.IdAccordoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.ID_ACCORDO_VIGIL%>" colSpan="2"/><sl:newLine />
                      <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/>                    
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DT_REG_ACCORDO%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DS_FIRMATARIO_ENTE%>" colSpan="2"/><sl:newLine />                    
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteConvenzCorrispSection.NAME%>" styleClass="importantContainer w100">
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiNonConvenzionati'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_ENTE_SIAM_PRODUT_CORRISP%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.NM_ENTE_CONSERV%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DS_NOTE_ENTE_ACCORDO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DT_INI_VAL%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.AccordoVigilDetail.DT_FIN_VAL%>" colSpan="2"/><sl:newLine />
                </slf:section>
            </slf:fieldSet>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['accordiVigilList'].status eq 'view') }">
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EntiProdutVigilSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiNonConvenzionatiForm.VigilEnteProdutList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiNonConvenzionatiForm.VigilEnteProdutList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiNonConvenzionatiForm.VigilEnteProdutList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
