<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.DESCRIPTION%>" >
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.DESCRIPTION%>" />
            
            <c:if test="${sessionScope['###_FORM_CONTAINER']['entiCollegatiList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['entiCollegatiList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['entiCollegatiList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" />  
            </c:if>
                
            <sl:newLine skipLine="true" />
            
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.ID_COLLEG_ENTI_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NM_COLLEG%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DS_COLLEG%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.TI_COLLEG%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NM_ENTE_CONVENZ_CAPOFILA%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_INI_VAL%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_FIN_VAL%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.ID_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.DT_INI_APPART%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteCollegatoDetail.DT_FIN_APPART%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
