<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DESCRIPTION%>" >
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['referentiEnteList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['referentiEnteList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['referentiEnteList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true" />
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer noborder w100">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_SIAM%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/>                
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.ID_ENTE_USER_RIF%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.NM_USERID%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.NM_USERID_CODIFICATO%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.SELEZIONA_REFERENTE_ENTE%>" colSpan="1"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.RIMUOVI_REFERENTE_ENTE%>" colSpan="1"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.QUALIFICA_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DL_NOTE%>" colSpan="2"/>
            </slf:section>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
