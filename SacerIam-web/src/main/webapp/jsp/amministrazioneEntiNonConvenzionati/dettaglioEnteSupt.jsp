<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiNonConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiNonConvenzionatiForm.EntiSupportatiList.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.TI_ENTE_NON_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.ID_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_INI_VAL%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteNonConvenzionatoDetail.DT_CESSAZIONE%>" colSpan="2"/><sl:newLine />
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_SUPT_EST_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <c:choose>
                        <c:when test="${!(sessionScope['###_FORM_CONTAINER']['entiSupportatiList'].status eq 'insert') }">
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:when>
                        <c:otherwise>
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                            <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.ID_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                        </c:otherwise>
                    </c:choose>
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DT_INI_VAL_SUPT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiNonConvenzionatiForm.EnteSupportatoDetail.DT_FIN_VAL_SUPT%>" colSpan="2"/><sl:newLine />
                </slf:section>
            </slf:fieldSet>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
