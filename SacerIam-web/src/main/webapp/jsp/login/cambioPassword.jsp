<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="it.eng.saceriam.slite.gen.form.ModificaPswForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Cambio Password" />
    <sl:body>
        <sl:header description="" showHomeBtn="false"/>

        <div class="newLine "></div>

        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="CAMBIO PASSWORD"/>
            <form method="post" action="./ModificaPswAction.html" autocomplete="off" >                     

                <div id="contenuto">
                    <br />
                    <div class="w80 modulo">
                        <fieldset class="noborder">
                            <legend>Inserisci la vecchia password e conferma la nuova per procedere</legend>
                            <div class="containerLeft w50">

                                <div class="newLine skipLine"></div>
                                I campi sono tutti obbligatori<br />
                                <br />

                                <div>
                                    <label class="slLabel w40" for="uname">Utente:&nbsp;</label>    
                                    <input class="slText w40" name="uname" type="input" accesskey="U" autocomplete="off" />                                       
                                    <div class="newLine skipLine"></div>
                                    <!--vecchia password-->
                                    <label class="slLabel w40" for="oldpass">Password:&nbsp;</label>
                                    <input class="slText w40" name="oldpass" type="password" accesskey="O" id="oldpass" autocomplete="off"/>

                                    <div class="newLine skipLine"></div>
                                    <!-- nuova password-->
                                    <label class="slLabel w40" for="newpass" accesskey="P" >Nuova password:&nbsp;</label>
                                    <input class="slText w40" name="newpass" type="password" accesskey="P" id="newpass" autocomplete="new-password" />

                                    <div class="newLine skipLine"></div>
                                    <!-- conferma nuova password-->
                                    <label class="slLabel w40" for="confnewpass" accesskey="C" >Conferma nuova password:&nbsp;</label>
                                    <input class="slText w40" name="confnewpass" type="password" accesskey="C" id="newpass2" autocomplete="new-password" />

                                    <div class="newLine skipLine"></div>

                                    <input name="u" value="${fn:escapeXml(param.u)}" type="hidden" />
                                    <input name="r" value="${fn:escapeXml(param.r)}" type="hidden" />
                                    <input name="h" value="${fn:escapeXml(param.h)}" type="hidden" />
                                    <input name="s" value="${fn:escapeXml(param.s)}" type="hidden" />
                                    <input name="kcc" value="${fn:escapeXml(param.kcc)}" type="hidden" />
                                    <input name="kct" value="${fn:escapeXml(param.kct)}" type="hidden" />

                                    <sl:pulsantiera>
                                        <input name="operation__modifica" value="Modifica password" class="pulsante" type="submit" />
                                        <input name="operation__annulla" value="Annulla" class="pulsante" type="submit" />
                                    </sl:pulsantiera>
                                </div>
                            </div>
                        </fieldset>
                        La password deve:
                        <ul>
                            <li>
                                essere lunga almeno 8 caratteri;                            
                            </li>
                            <li>
                                contenere almeno un numero;
                            </li>
                            <li>
                                contenere almeno un carattere speciale (per es. !, *, /, ?, #);
                            </li>
                            <li>
                                essere diversa dalle ultime 5 password utilizzate;
                            </li>
                            <li>
                                essere modificata almeno ogni 90 giorni.
                            </li>                            
                        </ul>
                        <br>
                        Consigli sulla scelta della password:
                        <ul>
                            <li>
                                modificare la password nel caso in cui si sospetti che chiunque altro ne sia venuto a conoscenza;
                            </li>
                            <li>
                                scegliere la password in modo che non sia collegata alla propria vita privata (per es. il nome o il cognome di familiari, la targa dell'auto, la data di nascita, la città di residenza, ecc);
                            </li>
                            <li>
                                non scegliere come password parole comuni riportate in un vocabolario (facilmente attaccabili da software di password cracking).
                            </li>                            
                        </ul>
                        <br>
                    </div>
                </div>                    
            </form>
        </sl:content>

        <!--Footer-->
        <sl:footer />           

    </sl:body>
</sl:html>
