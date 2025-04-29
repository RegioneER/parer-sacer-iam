<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <c:if test="${nascondiCodiceEnte == null}">
        <sl:head title="Dettaglio referente dell'ente convenzionato" ></sl:head>
    </c:if>
    <c:if test="${nascondiCodiceEnte != null}">
        <sl:head title="Dettaglio referente dell'ente non convenzionato" ></sl:head>
    </c:if>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true" >
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <c:if test="${nascondiCodiceEnte == null}">
                <sl:contentTitle title="Dettaglio referente dell'ente convenzionato" />
            </c:if>
            <c:if test="${nascondiCodiceEnte != null}">
                <sl:contentTitle title="Dettaglio referente dell'ente non convenzionato" />
            </c:if>
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
                <c:if test="${nascondiCodiceEnte == null}">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" colSpan="2"/>
                <sl:newLine />
                </c:if>
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

		<slf:section
			name="<%=AmministrazioneEntiConvenzionatiForm.RiferimentiSection.NAME%>"
			styleClass="importantContainer noborder w100">
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.ID_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.CD_REGISTRO_ENTE_USER_RIF%>"
				colSpan="2" />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_ENTE_USER_RIF%>" colSpan="2"/><sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.AA_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.CD_KEY_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DT_REG_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.BL_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.NM_FILE_ENTE_USER_RIF%>"
				colSpan="2" />
			<sl:newLine />
			<slf:lblField
				name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DS_ENTE_USER_RIF%>"
				colSpan="2" />
		</slf:section>
		<sl:newLine skipLine="true"/>
                <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.ReferenteEnteDetail.DOWNLOAD_FILE_ENTE_USER_RIF%>" colSpan="2" />
            </sl:pulsantiera>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
