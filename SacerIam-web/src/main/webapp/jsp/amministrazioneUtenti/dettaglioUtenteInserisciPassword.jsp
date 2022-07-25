<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Inserisci password utente" />
    <sl:body>
        <sl:header changeOrganizationBtnDescription="Cambia struttura" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="INSERISCI PASSWORD UTENTE" />
            <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.EditPasswordUtente.NAME%>" hideOperationButton="true"/> 
            <sl:newLine skipLine="true"/>
            <slf:fieldSet borderHidden="false">
                <label class="slLabel wlbl" for="Cd_psw">Password</label>
                <div class="containerLeft w2ctr">
                    <input id="Cd_psw" class="slText w60" type="password" value="" name="Cd_psw" />
                </div>
                <sl:newLine skipLine="true"/>
                <label class="slLabel wlbl" for="Cd_psw_repeated">Conferma password</label>
                <div class="containerLeft w2ctr">
                    <input id="Cd_psw_repeated" class="slText w60" type="password" value="" name="Cd_psw_repeated" />
                </div>
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.EditPasswordUtente.SALVA_PASSWORD%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true" />
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
            <b>Attenzione: al terzo tentativo di consecutivo fallito, l’utente viene bloccato. Per sbloccarlo contattare ParER via email a: 
                helpdeskparer@regione.emilia-romagna.it 
            </b>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
