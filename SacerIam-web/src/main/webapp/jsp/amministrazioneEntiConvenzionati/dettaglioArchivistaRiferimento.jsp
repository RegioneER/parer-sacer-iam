<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.DESCRIPTION%>" >
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['utentiArchivistiList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['utentiArchivistiList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['utentiArchivistiList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME%>" />  
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
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.ID_ENTE_ARK_RIF%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.NM_USERID%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.NM_USERID_CODIFICATO%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.SELEZIONA_UTENTE_ARCHIVISTA%>" colSpan="1"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.RIMUOVI_UTENTE_ARCHIVISTA%>" colSpan="1"/>
                <sl:newLine />      
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.FL_REFERENTE%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UtenteArchivistaDetail.DL_NOTE%>" colSpan="2"/>
            </slf:section>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
