<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Creazione utente">
        <script type="text/javascript" src="<c:url value='/js/customModificaUtenteMessageBox.js'/>"></script>        
        <script type="text/javascript">
            $(document).ready(function () {
                CustomBox();
                $('#Tipo_auth').on('change', function() {
                    var valore=$(this).val();
//                    alert('valore selezionato: '+$(this).val());
                    if (valore=='AUTH_CERT') {
                        abilitaDatiCertificato(true);
                    } else {
                        abilitaDatiCertificato(false);
                    }
                });
                if ($('#Tipo_auth').val()!='AUTH_CERT') {
                    abilitaDatiCertificato(false);
                }
            });
            
            function abilitaDatiCertificato(flag) {
                if (flag==true) {
                    $('#Dt_ini_cert').attr('disabled', false);
                    $('#Dt_fin_cert').attr('disabled', false);
                    $('#Dl_cert_client').attr('disabled', false);
                } else {
                    $('#Dt_ini_cert').val('')
                    $('#Dt_fin_cert').val('');
                    $('#Dl_cert_client').val('');
                    $('#Dt_ini_cert').attr('disabled', true);
                    $('#Dt_fin_cert').attr('disabled', true);
                    $('#Dl_cert_client').attr('disabled', true);
                }
            }
            
        </script>
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
            <c:if test="${requestScope.warningModificaUtente != null}">
                <div class="messages customModificaUtenteMessageBox ">
                    <ul>
                        <li class="message warning "><c:out value='${requestScope.warningModificaUtente}' /></li>
                    </ul>
                </div>
            </c:if>
            <sl:newLine skipLine="true"/>
            <slf:wizard name="<%= AmministrazioneUtentiForm.InserimentoWizard.NAME%>">
                <slf:wizardNavBar name="<%=AmministrazioneUtentiForm.InserimentoWizard.NAME%>" />
                <sl:newLine skipLine="true"/>   
                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO_RICHIESTA%>">
                    <slf:section name="<%=AmministrazioneUtentiForm.UdRichiestaSection.NAME%>" styleClass="importantContainer w100">
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.ID_ORGANIZ_IAM_RICH%>" colSpan="2" controlWidth="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.CD_REGISTRO_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.AA_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.CD_KEY_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                    </slf:section>
                    <slf:section name="<%=AmministrazioneUtentiForm.RichiestaSection.NAME%>" styleClass="importantContainer w100">
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.ID_AMBIENTE_ENTE_CONVENZ_RICH%>" colSpan="2" controlWidth="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.ID_ENTE_CONVENZ_RICH%>" colSpan="2" controlWidth="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaWizard.CD_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                    </slf:section>
                </slf:step>

                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO_RUOLI_SPECIFICI%>">
                    <sl:newLine skipLine="true" />
                    <slf:fieldSet borderHidden="false">
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_COGNOME_USER%>" colSpan="2" />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_NOME_USER%>" colSpan="2" />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_USERID%>" colSpan="2" />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_APPLIC%>" colSpan="2" />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.TI_SCOPO_DICH_ABIL_ORGANIZ%>" colSpan="2" />                
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.DL_COMPOSITO_ORGANIZ%>" colSpan="4" />                
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.ID_PATH_DL_COMPOSITO_ORGANIZ%>" colSpan="4" />                
                    </slf:fieldSet>

                    <sl:newLine skipLine="true"/>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.TI_SCOPO_RUOLO%>" colSpan="2" />
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.DL_COMPOSITO_ORGANIZ_RUOLO%>" colSpan="2" />
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.NM_RUOLO%>" colSpan="2" />
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.DS_RUOLO%>" colSpan="2" />
                    <sl:newLine skipLine="true"/>

                    <sl:pulsantiera>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.GestioneRuoliOrganizzazione.AGGIUNGI_RUOLO_ORG%>" width="w30"/>
                    </sl:pulsantiera>
                    <sl:newLine skipLine="true"/>

                    <h2 class="titleFiltri">Lista ruoli</h2>

                    <sl:newLine skipLine="true"/>
                    <slf:list name="<%=AmministrazioneUtentiForm.RuoliList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RuoliList.NAME%>" />
                    <sl:newLine skipLine="true"/>
                </slf:step>

                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO1%>">
                    <slf:fieldSet borderHidden="false">
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
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.ID_AMB_ENTE_CONVENZ_APPART%>" colSpan="2"/><sl:newLine />
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
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DL_CERT_CLIENT%>" colSpan="4" controlWidth="w100" />
                    </slf:fieldSet>
                </slf:step>

                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO_IP%>">
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneUtentiForm.IndIpFields.CD_IND_IP_USER%>" colSpan="2" controlWidth="w20"/>
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.IndIpFields.AGGIUNGI_IP%>" colSpan="2" controlWidth="w40" /> 
                    </sl:pulsantiera>
                    <sl:newLine skipLine="true"/>       
                    <slf:container width="w30">
                        <slf:list name="<%= AmministrazioneUtentiForm.IndIpList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneUtentiForm.IndIpList.NAME%>" />
                    </slf:container>
                </slf:step>

                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO_APPLIC%>">
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneUtentiForm.ApplicazioniFields.NM_APPLIC%>" colSpan="4" />
                    <sl:newLine skipLine="true"/>
                    <slf:lblField name="<%= AmministrazioneUtentiForm.ApplicazioniFields.DS_APPLIC%>" colSpan="4" controlWidth="w40" />
                    <sl:newLine/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.ApplicazioniFields.AGGIUNGI_APPLICAZIONE%>" colSpan="2" controlWidth="w40" /> 
                    </sl:pulsantiera>
                    <sl:newLine skipLine="true"/>       
                    <slf:list name="<%= AmministrazioneUtentiForm.ApplicazioniList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ApplicazioniList.NAME%>" />
                </slf:step>
                <slf:step name="<%= AmministrazioneUtentiForm.InserimentoWizard.PASSO2%>">
                    <c:choose>
                        <c:when test="${!empty param.Cd_psw}">
                            <c:set scope="request" var="pswd" value="${fn:escapeXml(param.Cd_psw)}" />
                        </c:when>
                        <c:when test="${!empty sessionScope.passwordUtente}">
                            <c:set scope="request" var="pswd" value="${sessionScope.passwordUtente}" />
                        </c:when>
                    </c:choose>
                    <slf:fieldSet borderHidden="false">
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_COGNOME_USER%>" colSpan="2" />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_NOME_USER%>" colSpan="2" />
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.NM_USERID%>" colSpan="2"/>                        
                        <%--<c:if test="${sessionScope.contaServizi > 0}">
                            <label class="slLabel wlbl forPwd" for="Cd_psw">Password</label>
                            <div class="containerLeft w2ctr">
                                <input id="Cd_psw" class="slText w60" type="password" value="${pswd}" name="Cd_psw" />
                            </div>
                        </c:if>--%>
                        <c:if test="${(sessionScope.userType eq 'AUTOMA' ) && 
                                      ((sessionScope['###_FORM_CONTAINER']['listaUtenti'].status eq 'insert')
                              || (sessionScope['###_FORM_CONTAINER']['listaUtenti'].status eq 'update'))}">
                              <label class="slLabel wlbl forPwd" for="Cd_psw">Password</label>
                              <div class="containerLeft w2ctr">
                                  <input id="Cd_psw" class="slText w60" type="password" value="${pswd}" name="Cd_psw" />
                              </div>
                        </c:if>

                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.FL_ATTIVO%>" colSpan="2"/>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.QUALIFICA_USER%>" colSpan="2"/>
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
                        <slf:lblField name="<%=AmministrazioneUtentiForm.DettaglioUtente.DL_CERT_CLIENT%>" colSpan="4"  controlWidth="w100" />
                    </slf:fieldSet>
                    <%-- Inserisco i tab contenenti le varie liste di dichiarazioni --%>
                    <sl:newLine skipLine="true"/>
                    <slf:tab  name="<%= AmministrazioneUtentiForm.WizardListsTabs.NAME%>" tabElement="ListaWizardRuoliDefault">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_RUOLO%>" colSpan="2" />
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.DS_RUOLO%>" colSpan="2" />
                        <sl:newLine/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>       
                        <slf:list name="<%= AmministrazioneUtentiForm.RuoliDefaultList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RuoliDefaultList.NAME%>" />
                    </slf:tab>
                    <slf:tab  name="<%= AmministrazioneUtentiForm.WizardListsTabs.NAME%>" tabElement="ListaWizardAbilOrganiz">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.TI_SCOPO_DICH_ABIL_ORGANIZ%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.DL_COMPOSITO_ORGANIZ%>" colSpan="4" controlWidth="w100" />
                        <sl:newLine/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>
                        <slf:list name="<%= AmministrazioneUtentiForm.DichAbilOrgList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilOrgList.NAME%>" />
                    </slf:tab>
                    <slf:tab  name="<%= AmministrazioneUtentiForm.WizardListsTabs.NAME%>" tabElement="ListaWizardTipiDato">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_CLASSE_TIPO_DATO%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.TI_SCOPO_DICH_ABIL_DATI%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.DL_COMPOSITO_ORGANIZ%>" colSpan="4" controlWidth="w100" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_TIPO_DATO%>" colSpan="4" controlWidth="w20" />
                        <sl:newLine />
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>
                        <slf:list name="<%= AmministrazioneUtentiForm.DichAbilTipiDatoList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilTipiDatoList.NAME%>" />
                    </slf:tab>
                    <slf:tab  name="<%= AmministrazioneUtentiForm.WizardListsTabs.NAME%>" tabElement="ListaWizardAbilEnteConvenz">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.TI_SCOPO_DICH_ABIL_ENTE%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_AMBIENTE_ENTE_CONVENZ%>" colSpan="4" controlWidth="w100" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.NM_ENTE_CONVENZ%>" colSpan="4" controlWidth="w20" />
                        <sl:newLine />
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneUtentiForm.DichAbilFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>
                        <slf:list name="<%= AmministrazioneUtentiForm.DichAbilEnteConvenzList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneUtentiForm.DichAbilEnteConvenzList.NAME%>" />
                    </slf:tab>
                </slf:step>
            </slf:wizard>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
