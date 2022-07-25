<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneUtentiForm.AzioneDetail.DESCRIPTION%>" >
        <script>
            $(document).ready(function () {
                abilitaAmministratore();

                // Autocomposizione username
                $("#Nm_cognome_user").change(function () {
                    generaUsername();
                });
                $("#Nm_nome_user").change(function () {
                    generaUsername();
                });
                // Flag utente amministratore
                $("#Ti_azione_rich").change(function () {
                    abilitaAmministratore();
                });

                $("#Ti_azione_rich").change(function () {
                    if ($("#Ti_azione_rich").val() === 'Richiesta di creazione') {
                        $("#Id_ente_user").attr("readonly", true);
                        $('input[name=operation__selezionaUtenteAzione]').hide();
                         $('input[name=operation__rimuoviUtenteAzione]').hide();
                    } else {
                        $("#Id_ente_user").attr("readonly", false);
                          $('input[name=operation__selezionaUtenteAzione]').show();
                         $('input[name=operation__rimuoviUtenteAzione]').show();
                    }
                });
            });
            function generaUsername()
            {
                var cognome = $("#Nm_cognome_user").val().replace(/\s+/g, "").toLowerCase();
                var nome = $("#Nm_nome_user").val().replace(/\s+/g, "").toLowerCase();
                $("#Nm_userid").val(nome + "." + cognome);
            }
            function abilitaAmministratore()
            {
                var selectTipoAzione = $("select#Ti_azione_rich");
                var divTipoAzione = $("div#Ti_azione_rich");
                var tipoAzione;
                var statoCampo;
                if (selectTipoAzione && selectTipoAzione.length > 0) {
                    tipoAzione = selectTipoAzione.val();
                    statoCampo = 'edit';
                } else if (divTipoAzione && divTipoAzione.length > 0) {
                    tipoAzione = divTipoAzione.text();
                    statoCampo = 'view';
                }

                if (tipoAzione) {
                    if (tipoAzione === 'Richiesta di creazione') {
                        $("#Fl_user_admin").attr("disabled", false);

                        // Riutilizzo il metodo presente in classes.js
                        showHideElement($("#Nm_userid_codificato"), 'hidden');
                        showHideElement($('input[type="submit"][name*="selezionaUtenteAzione" i]'), 'hidden');
                        showHideElement($('input[type="submit"][name*="rimuoviUtenteAzione" i]'), 'hidden');

                        showHideElement($("#Nm_cognome_user"), statoCampo);
                        showHideElement($("#Nm_nome_user"), statoCampo);
                    } else {

                        showHideElement($("#Nm_userid_codificato"), 'readonly');
                        showHideElement($('input[type="submit"][name*="selezionaUtenteAzione" i]'), statoCampo);
                        showHideElement($('input[type="submit"][name*="rimuoviUtenteAzione" i]'), statoCampo);

                        showHideElement($("#Nm_cognome_user"), 'hidden');
                        showHideElement($("#Nm_nome_user"), 'hidden');

                        $("#Fl_user_admin").attr("checked", false);
                        $("#Fl_user_admin").attr("disabled", true);
                    }
                }
            }
        </script>
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneUtentiForm.AzioneDetail.DESCRIPTION%>" />
            <c:if test="${sessionScope['###_FORM_CONTAINER']['azioniList'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.AzioneDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['azioniList'].status eq 'insert'}"/>
            </c:if>   
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['azioniList'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneUtentiForm.AzioniList.NAME%>" />  
            </c:if>

            <sl:newLine skipLine="true" />
            <slf:section name="<%=AmministrazioneUtentiForm.AzioneRichiestaSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_RICH_GEST_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_ENTE_RICH%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.TI_RICH_GEST_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NUMERO_PROTOCOLLO%>" colSpan="4"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.CD_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.DT_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NT_RICH_GEST_USER%>" colSpan="4"/>
                <sl:newLine />
            </slf:section>
            <slf:section name="<%=AmministrazioneUtentiForm.AzioneSection.NAME%>" styleClass="importantContainer noborder w100">
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.ID_APPART_USER_RICH%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.TI_AZIONE_RICH%>" colSpan="2"/>
                <%--<c:if test="${sessionScope['###_FORM_CONTAINER']['azioniList'].status ne 'view'}">--%>
                    <sl:newLine />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.ID_AMBIENTE_ENTE_USER%>" colSpan="2" />
                <%--</c:if>--%>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.ID_ENTE_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.NM_NOME_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.NM_COGNOME_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.NM_USERID%>" colSpan="2"/>
                <%--<sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.FL_USER_ADMIN%>" colSpan="2"/>--%>
                <sl:newLine skipLine="true"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.NM_USERID_CODIFICATO%>" colSpan="2"/>                
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.SELEZIONA_UTENTE_AZIONE%>" colSpan="1"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.RIMUOVI_UTENTE_AZIONE%>" colSpan="1"/>
                <sl:newLine />      
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.ID_USER_IAM%>" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.FL_AZIONE_RICH_EVASA%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.AzioneDetail.TI_APPART_USER_RICH%>" colSpan="2"/>
            </slf:section>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
