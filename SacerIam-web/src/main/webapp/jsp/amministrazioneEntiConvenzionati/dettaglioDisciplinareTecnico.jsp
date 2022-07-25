<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DESCRIPTION%>">
    </sl:head>
    <script type="text/javascript" src="<c:url value='/js/customDisciplinareMessageBox.js'/>" ></script>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true" >
            <slf:messageBox />
             <c:if test="${!empty requestScope.customBoxSalvataggioDisciplinare}">
                <div class="messages customBoxSalvataggioDisciplinare ">
                    <ul>
                        <li class="message info "><c:out value="${requestScope.customMessageSalvataggioDisciplinare}"/> </li>
                    </ul>                   
                </div>
            </c:if>
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['disciplinariTecniciList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['disciplinariTecniciList'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['disciplinariTecniciList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" />  
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
            <%--<slf:section name="<%=AmministrazioneEntiConvenzionatiForm.IdAccordoSection.NAME%>" styleClass="importantContainer w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.ID_ACCORDO_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.NM_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_REGISTRO_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.AA_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.CD_KEY_REPERTORIO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_REG_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_DEC_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.AccordoDetail.DT_SCAD_ACCORDO%>" colSpan="2"/>
            </slf:section>--%>
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoSection.NAME%>" styleClass="importantContainer w100">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.ENTE_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.STRUTTURA_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.CD_REGISTRO_DISCIP_STRUT%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.UdButtonList.SCARICA_COMP_FILE_UD_DISCIP%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.AA_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.CD_KEY_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.ID_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.FL_INSERITO_MANUALMENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DT_DISCIP_STRUT%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.ID_ORGANIZ_IAM%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.BL_DISCIP_STRUT%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.CANCELLA_FILE_DISCIP%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DS_DISCIP_STRUT%>" colSpan="4"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DS_NOTE_DISCIP_STRUT%>" colSpan="4"/><sl:newLine />
            </slf:section>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinareTecnicoDetail.DOWNLOAD_FILE_DISCIP%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
