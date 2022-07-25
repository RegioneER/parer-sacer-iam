<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DESCRIPTION%>" >
        <script type="text/javascript" src="<c:url value='/js/customCalcoloFattureProvvisorieRiemissioneFattureMessageBox.js'/>" ></script>             
    </sl:head>
        
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            
            <c:if test="${!empty requestScope.customCalcoloFattureProvvisorieMessageBox}">
                <div class="messages customCalcoloFattureProvvisorie ">
                    <div class="containerLeft w4ctr">        
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.ANNO_TESTATA_PROVV%>" width="w100" labelWidth="w40" controlWidth="w30" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField  name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.ANNO_FATT_SERVIZI_PROVV%>" width="w100" labelWidth="w40" controlWidth="w30" />                        
                    </div>
                </div>
            </c:if>
            
            <c:if test="${!empty requestScope.customRiemissioneFattureStornateMessageBox}">
                <div class="messages customRiemissioneFattureStornate ">
                    <div class="containerLeft w4ctr">      
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.RiemissioneFattureFields.ANNO_TESTATA_STORN%>" width="w100" labelWidth="w40" controlWidth="w30" />
                    </div>
                </div>
            </c:if>
            
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DESCRIPTION%>" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.NM_ENTE_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_ENTE_ATTIVO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_ENTE_CESSATO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_CHIUSO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DS_CATEG_ENTE%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.CD_AMBITO_TERRIT_REGIONE%>" colSpan="2" /><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.CD_AMBITO_TERRIT_PROVINCIA%>" colSpan="2" /><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" colSpan="2" /><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.CD_TIPO_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_DEC_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_DEC_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_FINE_VALID_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_FINE_VALID_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_DEC_ACCORDO_INFO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_DEC_ACCORDO_INFO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_SCAD_ACCORDO_DA%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.DT_SCAD_ACCORDO_A%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.ARCHIVISTA%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.NO_ARCHIVISTA%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_RICHIESTA_MODULO_INF%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_RICEZIONE_MODULO_INF%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_NON_CONVENZ%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_RECESSO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_ESISTONO_GEST_ACC%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.TIPO_GESTIONE_ACCORDO%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.FL_GEST_ACC_NO_RISP%>" colSpan="2"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.TI_STATO_ACCORDO%>" colSpan="2"/><sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.RICERCA_ENTI_CONVENZIONATI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.PULISCI_RICERCA_ENTI_CONVENZIONATI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.INSERISCI_ENTE_CONVENZIONATO_WIZARD%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FiltriEntiConvenzionati.ANNUALITA_SENZA_ATTO%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" />
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoSection.NAME%>" styleClass="importantContainer">
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" pageSizeRelated="true" />
            <slf:list name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" />
            <slf:listNavBar  name="<%=AmministrazioneEntiConvenzionatiForm.AnnualitaSenzaAttoList.NAME%>" />
            </slf:section> 
            <sl:newLine skipLine="true"/>
            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.CALCOLO_FATTURE_PROVVISORIE%>" width="w25"/>
            <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.RiemissioneFattureFields.RIEMETTI_FATTURE_STORNATE%>" width="w25"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
