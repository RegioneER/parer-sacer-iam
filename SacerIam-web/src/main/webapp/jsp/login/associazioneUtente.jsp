<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="it.eng.saceriam.slite.gen.form.ModificaPswForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Associazione Utente" />
    <sl:body>
        <sl:header description="" showHomeBtn="false"/>
        <div class="newLine "></div>
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="ASSOCIAZIONE UTENTE"/>
            <!-- Al terzo tentativo fallito non permette di fare più nulla! -->
            <c:if test="${sessionScope.TENTATIVI_CF_UTENTE != '0'}" >
                <form method="post" action="./AssociazioneUtenteAction.html" autocomplete="off">                     
                    <div id="contenuto">
                        <br />
                        <div class="w80 modulo">
                            <fieldset class="noborder">
                                <legend>Inserisci lo username da associare all'utente attualmente loggato</legend>
                                <div class="containerLeft w50">
                                    <div class="newLine skipLine"></div>
                                    I campi sono tutti obbligatori<br />
                                    <br />
                                    <div>
                                        <label class="slLabel w40" for="uname">Utente:&nbsp;</label>    
                                        <input class="slText w40" name="uname" type="input" accesskey="U" autocomplete="off" />                                       
                                        <div class="newLine skipLine"></div>
                                        <label class="slLabel w40" for="password">Password:&nbsp;</label>
                                        <input class="slText w40" name="password" type="password" accesskey="O" id="password" autocomplete="new-password" />

                                        <div class="newLine skipLine"></div>

                                        <input name="u" value="${fn:escapeXml(param.u)}" type="hidden" />
                                        <input name="r" value="${fn:escapeXml(param.r)}" type="hidden" />
                                        <input name="h" value="${fn:escapeXml(param.h)}" type="hidden" />
                                        <input name="s" value="${fn:escapeXml(param.s)}" type="hidden" />
                                        <input name="f" value="${fn:escapeXml(param.f)}" type="hidden" />

                                        <sl:pulsantiera>
                                            <input name="operation__associa" value="Associa utente" class="pulsante" type="submit" />
                                        </sl:pulsantiera>
                                    </div>
                                </div>
                            </fieldset>
                            <br>
                        </div>
                    </div>                    
                </form>
            </c:if>                                     
        </sl:content>
        <!--Footer-->
        <sl:footer />           
    </sl:body>
</sl:html>
