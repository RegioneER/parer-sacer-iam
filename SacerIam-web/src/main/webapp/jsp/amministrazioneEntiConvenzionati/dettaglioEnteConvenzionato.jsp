<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneEntiConvenzionatiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>
<sl:html>
    <sl:head title="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DESCRIPTION%>" >
        <script type="text/javascript">
                // Quando ha finito di disegnare tutta la pagina
                $(document).ready(function(){  
                    $("[name='operation__estraiRigheFattureEnte']").click(function() {
                        $('.overlay').hide();
                    });                    
                });
        </script>
    </sl:head>
    <sl:body>
        <c:set var="showNuovoEnteConvenzionatoSection" value="${!empty sessionScope['###_FORM_CONTAINER']['enteConvenzionatoDetail']['id_ente_convenz_nuovo'].decodedValue}"/>       
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
           

            <c:if test="${!empty requestScope.customCalcoloFattureProvvisorieMessageBox}">
                <div class="messages customCalcoloFattureProvvisorie ">
                    <div class="containerLeft w4ctr">        
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.ANNO_TESTATA_PROVV%>" width="w100" labelWidth="w40" controlWidth="w30" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField  name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.ANNO_FATT_SERVIZI_PROVV%>" width="w100" labelWidth="w40" controlWidth="w30" />                        
                    </div>
                </div>
            </c:if>

            <c:if test="${!empty requestScope.customModificaEnteConvenzMessageBox}">
                <div class="messages confermaModificaEnteConvenz ">
                    <ul>
                        <li class="message warning ">Attenzione: esiste almeno un utente abilitato all'ente. Si desidera procedere?</li>
                    </ul>
                </div>
            </c:if> 
            
            <c:if test="${!empty requestScope.customDeleteAppartenenzaCollegamentoDaDettaglioEnteMessageBox}">                
                <div class="messages confermaDeleteAppartenenzaCollegamentoDaDettaglioEnte ">
                    <ul>
                        <li class="message warning ">Attenzione! La cancellazione dell'appartenenza al collegamento comporterà l'eliminazione delle abilitazioni concesse ai seguenti <c:out value = "${requestScope.numeroUtentiAppartenenzaCollegamentoDaDettaglioEnte}"/> utenti:</li>
                    </ul>
                    <p  style="padding-left: 70px">                                                 
                        <c:forTokens items = "${requestScope.listaUtentiAppartenenzaCollegamentoDaDettaglioEnte}" delims = "," var = "utentiAppartenenzaCollegamentoDaDettaglioEnte">
                            <c:out value = "${utentiAppartenenzaCollegamentoDaDettaglioEnte}"/><br>                        
                        </c:forTokens>
                    <p>
                    <ul style="text-indent: 30px">
                    Si desidera procedere?
                    </ul>
                </div>      
            </c:if> 

            <sl:newLine skipLine="true"/>
            <script type="text/javascript" src="<c:url value='/js/customCalcoloFattureProvvisorieMessageBox.js'/>" ></script>
            <script type="text/javascript" src="<c:url value='/js/customModificaEnteConvenzMessageBox.js'/>" ></script>
            <script type="text/javascript" src="<c:url value='/js/customDeleteAppartenenzaCollegamentoDaDettaglioEnteMessageBox.js'/>" ></script>
            <sl:contentTitle title="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DESCRIPTION%>" />

            <c:choose>
                <c:when test='<%= session.getAttribute("navTableEntiConvenz").equals(AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME)
                        || session.getAttribute("navTableEntiConvenz").equals(AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoWizardDetail.NAME) %>'>
                    <c:if test="${sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].table['empty']}">                        
                        <slf:fieldBarDetailTag name="<%= AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'insert'}"/> 
                    </c:if>   
                    <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].table['empty']) }">                        
                        <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.ListaEntiConvenzionati.NAME%>" />  
                    </c:if>
                </c:when>
                <c:otherwise>
                    <slf:listNavBarDetail name="<%= AmministrazioneEntiConvenzionatiForm.EntiCessatiDetailList.NAME%>" />  
                </c:otherwise>
            </c:choose>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_SIAM%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_ENTE_SIAM%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_NOTE%>" colSpan="2"/><sl:newLine /> 
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />               
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_INI_VAL%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_CESSAZIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.FL_CHIUSO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                 <c:choose>
                    <c:when test="${!(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'insert') && empty sessionScope.cambioAmbiente}">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NM_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    </c:otherwise>
                </c:choose>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_INI_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_FIN_VAL_APPART_AMBIENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <c:if test="${(showNuovoEnteConvenzionatoSection || !(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'view'))}">
                    <sl:newLine skipLine="true"/>
                    <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.NuovoEnteSection.NAME%>" styleClass="importantContainer w100">
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_AMBIENTE_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                        <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_ENTE_CONVENZ_NUOVO%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    </slf:section>
                </c:if>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.SedeLegaleSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_VIA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_CAP_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_CITTA_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_PROV_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_NAZIONE_SEDE_LEGALE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CodiciSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_CD_ENTE_CONVENZ%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_FISC%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_CATEG_ENTE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DT_RICH_MODULO_INFO%>" width="w100" controlWidth="w20" labelWidth="w20"/><sl:newLine />                            
                <sl:newLine skipLine="true"/>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AmbitoTerritorialeSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_REGIONE%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_PROVINCIA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_AMBITO_TERRIT_FORMA_ASSOCIATA%>" width="w100" controlWidth="w40" labelWidth="w20"/><sl:newLine />
                </slf:section>
                <sl:newLine skipLine="true"/>
                 <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DatiFatturazioneSection.NAME%>" styleClass="importantContainer w100">                    
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CD_UFE%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_UFE%>" colSpan="2"/><sl:newLine />
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ID_CD_IVA%>" colSpan="2"/>
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.DS_IVA%>" colSpan="2"/><sl:newLine />                                       
                    <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.TI_MOD_PAGAM%>" colSpan="2"/><sl:newLine />                                       
                </slf:section>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.LOG_EVENTI%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.NUOVO_ACCORDO%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CAMBIO_GESTORE%>" width="w30"/>
                <sl:newLine />                                       
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.CAMBIO_AMBIENTE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.FattureProvvisorieFields.CALCOLO_FATTURE_PROVVISORIE%>" width="w30"/>                
                <slf:lblField name="<%=AmministrazioneEntiConvenzionatiForm.EnteConvenzionatoDetail.ESTRAI_RIGHE_FATTURE_ENTE%>" width="w30"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <c:if test="${(sessionScope['###_FORM_CONTAINER']['listaEntiConvenzionati'].status eq 'view') }">
                  <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.PrecedentiAppartenenzeAmbientiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.PrecedentiAppartenenzeAmbientiConvenzList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.PrecedentiAppartenenzeAmbientiConvenzList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.PrecedentiAppartenenzeAmbientiConvenzList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AnagraficheSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.AnagraficheList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.AnagraficheList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.AnagraficheList.NAME%>" />
                </slf:section>
                <c:if test="${!(sessionScope['###_FORM_CONTAINER']['entiCessatiList'].table['empty']) }">
                    <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.EntiCessatiSection.NAME%>" styleClass="noborder w100">
                        <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.EntiCessatiList.NAME%>" pageSizeRelated="true"/>
                        <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.EntiCessatiList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.EntiCessatiList.NAME%>" />
                    </slf:section>
                </c:if>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.CollegamentiEnteSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteAppartList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteAppartList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.CollegamentiEnteAppartList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.AccordiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.AccordiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.AccordiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.AccordiList.NAME%>" />
                </slf:section>
                   <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ModuliInformazioniSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ModuliInformazioniList.NAME%>" />
                </slf:section>
                  <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.DisciplinariTecniciList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.UtentiEnteConvenzionatoSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.UtentiEnteList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.UtentiEnteList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.UtentiEnteList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.StruttureVersantiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.OrganizzazioniVersantiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.OrganizzazioniVersantiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.OrganizzazioniVersantiList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.UtentiPersonaFisicaAbilitatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.UtentiPersonaFisicaList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.UtentiPersonaFisicaList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.UtentiPersonaFisicaList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.UtentiAutomaAbilitatiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.UtentiAutomaList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.UtentiAutomaList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.UtentiAutomaList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.UtentiArchivistiSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.UtentiArchivistiList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ReferentiEnteSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ReferentiEnteList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.FornitoriEsterniSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.FornitoriEsterniList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.FornitoriEsterniList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.FornitoriEsterniList.NAME%>" />
                </slf:section>
                 <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.SoggettiAttuatoriSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.SoggettiAttuatoriList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.SoggettiAttuatoriList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.SoggettiAttuatoriList.NAME%>" />
                </slf:section>
                <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.OrganiVigilanzaSection.NAME%>" styleClass="noborder w100">
                    <slf:listNavBar name="<%= AmministrazioneEntiConvenzionatiForm.OrganiVigilanzaList.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneEntiConvenzionatiForm.OrganiVigilanzaList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.OrganiVigilanzaList.NAME%>" />
                </slf:section>
            </c:if>

            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriAmministrazioneList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriConservazioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriConservazioneList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriConservazioneList.NAME%>" />
            </slf:section>
            <slf:section name="<%=AmministrazioneEntiConvenzionatiForm.ParametriGestioneSection.NAME%>" styleClass="noborder w100">
                <slf:editableList name="<%= AmministrazioneEntiConvenzionatiForm.ParametriGestioneList.NAME%>" multiRowEdit="true"/>
                <slf:listNavBar  name="<%= AmministrazioneEntiConvenzionatiForm.ParametriGestioneList.NAME%>" />
            </slf:section>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
