<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>

    <sl:head title="Utenti - Ricerca" >
        <style>
            a.VisualizzaUtenteRichiedente, a.VisualizzaUtenteArchivista, a.VisualizzaReferenteEnte { 
                background-image: url("img/download.png");
                background-repeat: no-repeat;
                padding-right : 1.5em;
                text-decoration: none;
                display: inline-block;
                width: 22px;
                height: 24px;			
            }
        </style>

        <script type="text/javascript">
            $(document).ready(function () {
                $("#Id_amb_ente_convenz_appart").change(function () {
                    var value = $("#Id_amb_ente_convenz_appart").val();
                    $.getJSON("AmministrazioneUtenti.html", {
                        operation: "triggerFiltriUtentiId_amb_ente_convenz_appart",
                        idAmbienteConvenzAppart: JSON.stringify(value)
                    }).done(function (data) {
                        CAjaxDataFormWalk(data);
                    });
                });
            });
        </script>


    </sl:head>

    <sl:body>
        <%--<c:set var="addUtenteArchivistaPerEnteConvenz" value="${sessionScope['###_FORM_CONTAINER']['utentiArchivistiPerEnteConvenzionato']['id_ente_convenz'].value != null}"/>--%>
        <c:set var="addUtenteArchivistaPerSistemaVersante" value="${sessionScope['###_FORM_CONTAINER']['utentiArchivistiPerSistemaVersante']['id_sistema_versante'].value != null}"/>       
        <c:set var="addReferenteDittaProduttricePerSistemaVersante" value="${sessionScope['###_FORM_CONTAINER']['referentiDittaProduttricePerSistemaVersante']['id_sistema_versante'].value != null}"/>       
        <%--<c:set var="addReferentePerEnteConvenz" value="${sessionScope['###_FORM_CONTAINER']['referentiPerEnteConvenzionato']['id_ente_convenz'].value != null}"/>       --%>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>

            <slf:fieldBarDetailTag name="<%=AmministrazioneUtentiForm.FiltriUtenti.NAME%>" hideBackButton="${!((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}" />

            <sl:contentTitle title="RICERCA UTENTI" />
            <%--<c:choose>
                <c:when test="${sessionScope.flUserAdmin eq 1}">
                    <sl:contentTitle title="RICERCA UTENTI AMMINISTRATORI" />
                </c:when>
                <c:otherwise>
                    <sl:contentTitle title="RICERCA UTENTI" />
                </c:otherwise>
            </c:choose>--%>

            <slf:fieldSet borderHidden="false">
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_COGNOME_USER%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_NOME_USER%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_USERID%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_APPLIC%>" colSpan="2" controlWidth="w50"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.CD_FISC%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_RUOLO_DEFAULT%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.FL_ATTIVO%>" colSpan="2" controlWidth="w10"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.TIPO_USER%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.DS_EMAIL%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:section name="<%=AmministrazioneUtentiForm.UdRichiestaSection.NAME%>" styleClass="importantContainer w100">
                    <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_ORGANIZ_IAM_RICH%>" colSpan="4" controlWidth="w100"/>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.CD_REGISTRO_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.AA_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.CD_KEY_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                </slf:section>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_AMBIENTE_ENTE_CONVENZ_RICH%>" colSpan="2" controlWidth="w50"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_ENTE_CONVENZ_RICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.CD_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.DT_RICH_GEST_USER_DA%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.DT_RICH_GEST_USER_A%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />                
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.TI_STATO_USER%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.DL_COMPOSITO_ORGANIZ%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.NM_RUOLO_DICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.FL_ERR_REPLIC%>" colSpan="2" controlWidth="w50"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_SISTEMA_VERSANTE%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.FL_RESP_ENTE_CONVENZ%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_AMB_ENTE_CONVENZ_APPART%>" colSpan="2" controlWidth="w50"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_ENTE_CONVENZ_APPART%>" colSpan="2" controlWidth="w50"/>
                <%--<c:if test="${sessionScope.flUserAdmin eq 1}">
                    <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_ENTE_CONVENZ_ABIL%>" colSpan="2" controlWidth="w50"/>
                </c:if>--%>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_AMBIENTE_ENTE_CONVENZ_ABIL%>" colSpan="2" controlWidth="w50"/><sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ID_ENTE_CONVENZ_ABIL%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.RICERCA_UTENTI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.PULISCI_UTENTI%>" width="w25"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.SCARICA_INDIRIZZI_MAIL%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <c:choose>
                <c:when test="${(addUtenteArchivistaPerSistemaVersante || addReferenteDittaProduttricePerSistemaVersante || addReferentePerEnteConvenz)}">
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" pageSizeRelated="true"/>
                    <slf:selectList name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" addList="true"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>"/>
                </c:when>
                <c:otherwise>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" pageSizeRelated="true"/>
                    <slf:list name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ListaUtenti.NAME%>" />
                </c:otherwise>
            </c:choose>

            <sl:newLine skipLine="true"/>

            <%--<c:if test="${addUtenteArchivistaPerEnteConvenz}">
                <!-- Inserisco la lista vuota degli utenti archivisti da aggiungere all'ente convenzionato -->
                <slf:section name="<%=AmministrazioneUtentiForm.UtentiArchivistiToEntiConvenzionatiSection.NAME%>" styleClass="importantContainer">                
                    <slf:listNavBar name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" pageSizeRelated="true"/>
                    <slf:selectList name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" addList="false"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" />
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ADD_UTENTI_ARCHIVISTI_TO_ENTE_CONVENZ%>" colSpan="3" />
                    </sl:pulsantiera>
                </slf:section>
            </c:if>--%>                  

            <c:if test="${addUtenteArchivistaPerSistemaVersante}">
                <!-- Inserisco la lista vuota degli utenti archivisti da aggiungere al sistema versante -->
                <slf:section name="<%=AmministrazioneUtentiForm.UtentiArchivistiToSistemiVersantiSection.NAME%>" styleClass="importantContainer">                
                    <slf:listNavBar name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" pageSizeRelated="true"/>
                    <slf:selectList name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" addList="false"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.UtentiArchivistiList.NAME%>" />
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ADD_UTENTI_ARCHIVISTI_TO_SISTEMA_VERSANTE%>" colSpan="3" />
                    </sl:pulsantiera>
                </slf:section>
            </c:if>

            <c:if test="${addReferenteDittaProduttricePerSistemaVersante}">
                <!-- Inserisco la lista vuota dei referenti da aggiungere al sistema versante -->
                <slf:section name="<%=AmministrazioneUtentiForm.ReferentiDittaProduttriceToSistemiVersantiSection.NAME%>" styleClass="importantContainer">                
                    <slf:listNavBar name="<%= AmministrazioneUtentiForm.ReferentiDittaProduttriceList.NAME%>" pageSizeRelated="true"/>
                    <slf:selectList name="<%= AmministrazioneUtentiForm.ReferentiDittaProduttriceList.NAME%>" addList="false"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ReferentiDittaProduttriceList.NAME%>" />
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ADD_REFERENTI_DITTA_PRODUTTRICE_TO_SISTEMA_VERSANTE%>" colSpan="3" />
                    </sl:pulsantiera>
                </slf:section>
            </c:if>

            <%--<c:if test="${addReferentePerEnteConvenz}">
                <!-- Inserisco la lista vuota degli utenti referenti per ente convenzionato -->
                <slf:section name="<%=AmministrazioneUtentiForm.ReferentiEnteConvenzionatoSection.NAME%>" styleClass="importantContainer">                
                    <slf:listNavBar name="<%= AmministrazioneUtentiForm.ReferentiEnteList.NAME%>" pageSizeRelated="true"/>
                    <slf:selectList name="<%= AmministrazioneUtentiForm.ReferentiEnteList.NAME%>" addList="false"/>
                    <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ReferentiEnteList.NAME%>" />
                    <sl:newLine skipLine="true"/>
                    <sl:pulsantiera>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriUtenti.ADD_REFERENTI_ENTE_CONVENZIONATO%>" colSpan="3" />
                    </sl:pulsantiera>
                </slf:section>
            </c:if>--%>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
