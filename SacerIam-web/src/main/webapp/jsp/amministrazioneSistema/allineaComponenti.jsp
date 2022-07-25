<%@page import="it.eng.saceriam.entity.AplAzionePagina"%>
<%@page import="it.eng.saceriam.entity.AplPaginaWeb"%>
<%@page import="java.util.List"%>
<%@ page import="it.eng.spagoLite.SessionManager"%>
<%@ page import="it.eng.saceriam.slite.gen.form.AllineaComponentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Allinea componenti applicazione" >
        <script type="text/javascript" src="<c:url value='/js/customAllineamentoComponentiMessageBox.js'/>" ></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#Nm_applic").on('click', function () {
                    var nmApplic = $(this).val();
                    var cdVersioneComp = $("#Cd_versione_comp").val();
                    var cdVersioneCompNew = $("#Cd_versione_comp_new").val();
                    $.post("AllineaComponenti.html", {
                        operation: "triggerFiltriAllineaComponentiNm_applicOnTrigger",
                        Nm_applic: nmApplic,
                        Cd_versione_comp: cdVersioneComp,
                        Cd_versione_comp_new: cdVersioneCompNew
                    }).done(function (data) {
                        CAjaxDataFormWalk(data);
                        var cdVersioneComp = $("#Cd_versione_comp").val();
                        $("#Cd_versione_comp_hidden").val(cdVersioneComp);
                    });
                });

                CustomBoxAllineamentoComponenti();
            });
        </script>        
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <slf:messageBox />
        <c:if test="${!empty requestScope.customBoxAllineamentoComponenti}">
            <div class="messages customBoxAllineamentoComponenti ">
                <ul>
                    <li class="message warning ">Sono presenti pagine e/o azioni da eliminare per cui e’ definita la configurazione di logging:</li>                        
                </ul>                                        
                <%--<c:out value="${requestScope.customBoxAllineamentoComponenti}" />--%>
                <c:forTokens items = "${requestScope.customBoxAllineamentoComponenti}" delims = "," var = "pagineAzioni">
                    <c:out value = "${pagineAzioni}"/><p>
                </c:forTokens>              
            </div>
        </c:if>             
        <div id="content">
            <sl:newLine skipLine="true"/>
            <c:if test="<%= !SessionManager.getMessageBox(pageContext.getSession()).hasFatal()%>" >
                <sl:contentTitle title="ALLINEA COMPONENTI APPLICAZIONE"/>
                <sl:form id="multipartForm" multipartForm="true"> 
                    <slf:fieldSet>
                        <slf:lblField name="<%= AllineaComponentiForm.FiltriAllineaComponenti.NM_APPLIC%>" labelWidth="w40" width="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AllineaComponentiForm.FiltriAllineaComponenti.CD_VERSIONE_COMP%>" labelWidth="w40" width="w50"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AllineaComponentiForm.FiltriAllineaComponenti.CD_VERSIONE_COMP_NEW%>" labelWidth="w40" width="w50"/>
                        <sl:newLine />
                        <div class="containerLeft w100">
                            <label class="slLabel w20" for="ENTRY_MENU" >File entrate menù&nbsp;</label>
                            <div><input type="file" id="ENTRY_MENU"  name="ENTRY_MENU" size="80" /></div>
                        </div>
                        <sl:newLine />
                        <div class="containerLeft w100">
                            <label class="slLabel w20" for="PAGINA">File pagine:&nbsp;</label>
                            <div><input type="file" id="PAGINA"  name="PAGINA" size="80" /></div>
                        </div>
                        <sl:newLine />
                        <div class="containerLeft w100">
                            <label class="slLabel w20" for="AZIONE" >File azioni:&nbsp;</label>
                            <div><input type="file" id="AZIONE"  name="AZIONE" size="80" /></div>
                        </div>
                        <sl:newLine />
                        <div class="containerLeft w100">
                            <label class="slLabel w20" for="SERVIZIO_WEB">File servizi:&nbsp;</label>
                            <div><input type="file" id="SERVIZIO_WEB"  name="SERVIZIO_WEB" size="80" /></div>
                        </div>
                        <sl:newLine skipLine="true"/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AllineaComponentiForm.Bottoni.ATTIVA_ALLINEAMENTO%>" width="w60" />
                        </sl:pulsantiera>
                    </slf:fieldSet>
                </sl:form>
            </c:if>
        </div>
        <sl:footer />
    </sl:body>
</sl:html>
