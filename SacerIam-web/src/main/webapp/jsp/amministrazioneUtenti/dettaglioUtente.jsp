<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Dettaglio Utente" >
    <style>
            pre {
                height: 80px;
                overflow-y: scroll;  
                 resize: both;
                overflow: auto;
            }
            </style>
            </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="DETTAGLIO UTENTE" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaUtenti'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.DettaglioUtente.NAME%>" /> 
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaUtenti'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true" />
            <slf:fieldSet borderHidden="true">
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ID_USER_IAM%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_COGNOME_USER%>" colSpan="2" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_NOME_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_USERID%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.QUALIFICA_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ATTIVO%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.CD_FISC%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DS_EMAIL%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DS_EMAIL_SECONDARIA%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_CONTR_IP%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ERR_REPLIC%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ID_AMB_ENTE_CONVENZ_APPART%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ID_ENTE_SIAM_APPART%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.TIPO_USER%>" colSpan="2"/>                
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ID_SISTEMA_VERSANTE%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_RESP_ENTE_CONVENZ%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ABIL_ENTI_COLLEG_AUTOM%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ABIL_ORGANIZ_AUTOM%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ABIL_FORNIT_AUTOM%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.TIPO_AUTH%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DT_INI_CERT%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DT_FIN_CERT%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DL_CERT_CLIENT%>" colSpan="4" controlWidth="w100"  />
            </slf:fieldSet>
            <slf:section name="<%=AmministrazioneUtentiForm.UltimaRichiestaSection.NAME%>" styleClass="noborder w100">
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUltimaRichiestaUtente.KEY_RICH_GEST_USER%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUltimaRichiestaUtente.DT_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUltimaRichiestaUtente.DS_LISTA_AZIONI%>" colSpan="4"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUltimaRichiestaUtente.FL_AZIONI_EVASE%>" colSpan="2"/>
                <sl:newLine />
            </slf:section>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DUPLICA_UTENTE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.INVIA_MAIL_ATTIVAZIONE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NUOVA_PASSWORD%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.VISUALIZZA_REPLICHE_UTENTI%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ATTIVA_UTENTE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DISATTIVA_UTENTE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.CESSA_UTENTE%>" width="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.LOG_EVENTI%>" width="w30"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <%-- Inserisco i tab contenenti le varie liste di abilitazioni --%>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaIndirizziIp">
                <slf:container width="w40">
                    <slf:list name="<%= AmministrazioneUtentiForm.IndIpList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.IndIpList.NAME%>" />
                </slf:container>
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaApplicazioni">
                <slf:list name="<%= AmministrazioneUtentiForm.ApplicazioniList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ApplicazioniList.NAME%>" />
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaRuoliDefault">
                <slf:list name="<%= AmministrazioneUtentiForm.RuoliDefaultList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RuoliDefaultList.NAME%>" />
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaDichAbilOrganiz">
                <slf:list name="<%= AmministrazioneUtentiForm.DichAbilOrgList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilOrgList.NAME%>" />
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaDichAbilTipiDato">
                <slf:list name="<%= AmministrazioneUtentiForm.DichAbilTipiDatoList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilTipiDatoList.NAME%>" />
            </slf:tab>                        
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaDichAbilEnteConvenz">
                <slf:list name="<%= AmministrazioneUtentiForm.DichAbilEnteConvenzList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilEnteConvenzList.NAME%>" />
            </slf:tab>            
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaStatiUtente">
                <slf:list name="<%= AmministrazioneUtentiForm.StatiUtenteList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.StatiUtenteList.NAME%>" />
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaAbilOrganiz">
                <slf:list name="<%= AmministrazioneUtentiForm.AbilOrganizList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.AbilOrganizList.NAME%>" />
            </slf:tab>
            <slf:tab  name="<%= AmministrazioneUtentiForm.AbilListsTabs.NAME%>" tabElement="ListaAbilEnti">
                <slf:list name="<%= AmministrazioneUtentiForm.AbilEntiList.NAME%>" />
                <slf:listNavBar  name="<%= AmministrazioneUtentiForm.AbilEntiList.NAME%>" />
            </slf:tab>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
