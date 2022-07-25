<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DESCRIPTION%>" >
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DESCRIPTION%>" />
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].table['empty'])}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].table['empty'])}">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteList.NAME%>" />  
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
                <c:choose>
                    <c:when test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'view') }">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.NM_ENTE_CONVENZ_CAPOFILA%>" colSpan="2"/>
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.ID_ENTE_CONVENZ_CAPOFILA%>" colSpan="2"/>
                    </c:otherwise>
                </c:choose>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_INI_VAL%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentoEnteDetail.DT_FIN_VAL%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['collegamentiEnteList'].status eq 'view')}">
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EntiCollegatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.EntiCollegatiList.NAME%>" />
                </slf:section>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
