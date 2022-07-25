<%@ page import="it.eng.saceriam.slite.gen.form.GestioneFatturazioneServiziForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=GestioneFatturazioneServiziForm.IncassoDetail.DESCRIPTION%>" >
        <script type="text/javascript" src="<c:url value='/js/customIncassiMessageBox.js'/>" ></script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />

            <c:if test="${!empty requestScope.customBoxSalvataggioIncasso}">
                <div class="messages customBoxImportiIncasso ">
                    <ul>
                        <li class="message info ">L’importo dell’incasso è superiore all’importo da pagare, vuoi proseguire lo stesso?</li>
                    </ul>                   
                </div>
            </c:if>
            <div class="pulsantieraImportiIncasso">
                <sl:pulsantiera >
                    <slf:buttonList name="<%= GestioneFatturazioneServiziForm.SalvaIncassoCustomMessageButtonList.NAME%>"/>
                </sl:pulsantiera>
            </div>

            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=GestioneFatturazioneServiziForm.IncassoDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaIncassi'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= GestioneFatturazioneServiziForm.IncassoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaIncassi'].status eq 'insert'}"/> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaIncassi'].table['empty']) }">
                <slf:listNavBarDetail name="<%= GestioneFatturazioneServiziForm.ListaIncassi.NAME%>" />  
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.ID_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
            <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.ID_PAGAM_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />   
            <slf:fieldSet borderHidden="true">
                <slf:section name="<%=GestioneFatturazioneServiziForm.EnteConvenzionatoSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CODICE_ENTE%>" colSpan="2"/><sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FISC%>" colSpan="2"/><sl:newLine/>
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CLIENTE_SAP%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_CATEG_ENTE%>" colSpan="2"/><sl:newLine />
                </slf:section>                             
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DatiFatturaTemporaneaSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.AA_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.PG_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_CREAZIONE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DatiFatturaEmessaSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_REGISTRO_EMIS_FATTURA_TEXT%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.AA_EMISS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_SCAD_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.CD_FATTURA_SAP%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.NT_EMIS_FATTURA%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.ImportiSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_NETTO%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_IVA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_FATTURA%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_DA_PAGARE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.IM_TOT_PAGAM%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.StatoFatturaSection.NAME%>" >
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.TI_STATO_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.FatturaDetail.DT_REG_STATO_FATTURA_ENTE%>" colSpan="2"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=GestioneFatturazioneServiziForm.DocumentoIncassoSection.NAME%>" >                                   
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.CD_PROVV_PAGAM%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.CD_REV_PAGAM%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.DT_PAGAM%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.IM_PAGAM%>" colSpan="2"/><sl:newLine />                   
                    <slf:lblField name="<%=GestioneFatturazioneServiziForm.IncassoDetail.NT_PAGAM%>" colSpan="2"/><sl:newLine />                   
                </slf:section>
            </slf:fieldSet>           
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
