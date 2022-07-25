<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneUtentiForm.RichiestaDetail.DESCRIPTION%>" >
        <script>
            $(document).ready(function () {
                // Trigger su campo file
                $("#Bl_rich_gest_user").on("change", function () {
                    var nomeFile = $("#Bl_rich_gest_user").val().split('\\').pop();
                    $("div#Nm_file_rich_gest_user").text(nomeFile);
                    $("input[name='Nm_file_rich_gest_user']").val(nomeFile);
                });

            });
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content multipartForm="true">
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneUtentiForm.RichiestaDetail.DESCRIPTION%>" />

            <c:choose>
                <c:when test="${((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}">
                    <c:if test="${sessionScope['###_FORM_CONTAINER']['richiesteList'].table['empty']}">
                        <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.RichiestaDetail.NAME%>" hideBackButton="${sessionScope['###_FORM_CONTAINER']['richiesteList'].status eq 'insert'}"/>
                    </c:if>
                    <c:if test="${!(sessionScope['###_FORM_CONTAINER']['richiesteList'].table['empty']) }">
                        <slf:listNavBarDetail name="<%= AmministrazioneUtentiForm.RichiesteList.NAME%>" />
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${sessionScope['###_FORM_CONTAINER']['richiesteList'].status eq 'view'}">
                        <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.RichiestaDetail.NAME%>" hideBackButton="true"/>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <sl:newLine skipLine="true" />
            <slf:fieldSet borderHidden="true" styleClass="importantContainer">
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_RICH_GEST_USER%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_ENTE_RICH%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.TI_RICH_GEST_USER%>" colSpan="2" />

                <sl:newLine />
                <c:if test="${((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}">
                    <fieldset id="<%=AmministrazioneUtentiForm.IdentificativoUdRichSection.NAME%>" style="border: 1px solid #999999; margin-top: 0.2em; padding: 1em 0 1em 0;   margin-bottom: 0.2em;">
                        <span style="color: #333333; font-weight: bold; font-size: 11px; text-align: left; display: block; padding: 0.5em;"><%=AmministrazioneUtentiForm.IdentificativoUdRichSection.DESCRIPTION%></span>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_ORGANIZ_IAM%>" colSpan="4"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.CD_REGISTRO_RICH_GEST_USER%>" colSpan="2"/>
                        <slf:lblField name="<%=AmministrazioneUtentiForm.UdButtonList.SCARICA_COMP_FILE_UD_RICHIESTA%>" colSpan="2"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.AA_RICH_GEST_USER%>" colSpan="2" controlWidth="w10"/>
                        <sl:newLine />
                        <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.CD_KEY_RICH_GEST_USER%>" colSpan="2"/>
                    </fieldset>
                </c:if>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.CD_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.DT_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NM_FILE_RICH_GEST_USER%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.SCARICA_FILE_RICHIESTA%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.BL_RICH_GEST_USER%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NM_USERID_MANUALE%>" colSpan="2"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.TI_USER_RICH%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NM_USERID_CODIFICATO%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.ID_USER_IAM_RICH%>" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.DS_EMAIL_RICH%>" colSpan="2"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.NT_RICH_GEST_USER%>" colSpan="4"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.TI_STATO_RICH_GEST_USER%>" colSpan="2"/>               
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.LOG_EVENTI_RICHIESTA%>" colSpan="2"/>
                <c:if test="${((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}">
                    <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.RICHIESTA_COMPLETATA%>" colSpan="2"/>
                </c:if>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <c:if test="${!((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}">
                <slf:lblField name="<%=AmministrazioneUtentiForm.RichiestaDetail.SALVA_RICHIESTA%>" colSpan="2"/>
                <sl:newLine skipLine="true"/>
            </c:if>
            <c:if test="${((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}">
                <c:if test="${(sessionScope['###_FORM_CONTAINER']['richiesteList'].status eq 'view') }">
                    <slf:section name="<%=AmministrazioneUtentiForm.ListaAzioniSection.NAME%>" styleClass="importantContainer">
                        <slf:list name="<%= AmministrazioneUtentiForm.AzioniList.NAME%>" />
                        <slf:listNavBar name="<%= AmministrazioneUtentiForm.AzioniList.NAME%>" />
                    </slf:section>
                </c:if>
            </c:if>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
