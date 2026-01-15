<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.DESCRIPTION%>">
    </sl:head>
    <script type="text/javascript" src="<c:url value='/js/customDocumentoProcessoConservMessageBox.js'/>" ></script>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true" >
            <slf:messageBox />
             <c:if test="${!empty requestScope.customBoxSalvataggioDocumentoProcessoConserv}">
                <div class="messages customBoxSalvataggioDocumentoProcessoConserv ">
                    <ul>
                        <li class="message info "><c:out value="${requestScope.customMessageSalvataggioDocumentoProcessoConserv}"/> </li>
                    </ul>                   
                </div>
            </c:if>
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['documentiProcessoConservList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['documentiProcessoConservList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['documentiProcessoConservList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.DocumentiProcessoConservList.NAME%>" />  
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
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DocumentiProcessoConservSection.NAME%>" styleClass="importantContainer w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.ID_ORGANIZ_IAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.ENTE_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.STRUTTURA_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.ID_TIPO_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.CD_REGISTRO_DOC_PROCESSO_CONSERV%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.AA_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.CD_KEY_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.PG_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.DT_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.BL_DOC_PROCESSO_CONSERV%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.DS_DOC_PROCESSO_CONSERV%>" colSpan="4"/><sl:newLine />
            </slf:section>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DocumentoProcessoConservDetail.DOWNLOAD_FILE_DOC_PROCESSO%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
