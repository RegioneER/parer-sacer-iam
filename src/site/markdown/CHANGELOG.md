
## 4.7.14.2 (05-05-2021)

### MAC (1 change)
- Errore critico al salvataggio di un utente a seguito di modifica abilitazioni

## 4.7.14.1 (28-04-2021)

### MAC (1 change)
- Corretto il problema nella modifica di un ruolo 

## 4.7.14 (26-04-2021)

### MEV (1 change)
- Creazione versione hibernate siam 4.7.14 allineata alla versione 4.6.14

## 4.7.13.3 (19-04-2021)

### MAC (1 change)
- Correzione query per estrazioni dati log

## 4.7.13.2 (14-04-2021)

### MAC (1 change)
- Correzione errore del ws di replica organizzazioni
## 4.7.13.1 (02-04-2021)

### MAC (1 change)
- Correzione dell'errore riscontrato durante la rimozione di un'applicazione dalle abilitazioni utente 

## 4.7.13 (29-03-2021)

### MEV (1 change)
- Creazione versione hibernate siam 4.7.13 allineata alla versione 4.6.13

## 4.6.13 (26-03-2021)

### MAC (5 change)
- Correzione della funzionalità di gestione del periodo di validità di un accordo (considerare anche il giorno di decorrenza)
- Corretto calcolo servizi erogati per attivazione sistema versante già calcolato e fatturato
- Corretto errore critico nell'inserimento/modifica di un collegamento dal Dettaglio ente convenzionato
- Correzione funzionalità di modifica Abilitazioni utenti appartenenti a enti non convenzionati
- Corretto Errore job replica utenti

### MEV (3 change)
- Sostituzione in visualizzazione del codice  categoria con la descrizione categoria
- Migliorata la visualizzazione delle informazioni in "Ricerca enti convenzionati" 
- Inseriti nuovi  dati di registrazione sull'accordo di vigilanza

## 4.7.12.3 (24-03-2021)
### MAC (2 change)
- Corretto errore hibernate in dettaglio fattura
- Corretto errore nel visualizzare dell'annualità di un accordo - (versione hibernate)

## 4.7.12.2 (19-03-2021)
### MAC (1 change)
- Corretto errore nel visualizzare il dettaglio di un accordo 

## 4.7.12.1 (08-03-2021)
### MAC (1 change)
- Hibernate: correzione errori su amministrazione enti e utenti 

## 4.7.12 (26-02-2021)
### MEV (1 change)
- Creazione versione hibernate siam 4.7.12 allineata alla versione 4.6.12

## 4.6.12 (22-02-2021)

### MAC (2 change)
- Correzione popolamento combo tipo accordo 
- Problema calcolo fatture provvisorie da ricerca enti

## 4.6.11 (15-02-2021)

### MAC (1 change)
- correzione precisione importi tariffe-Tariffario + correzione elaborazione fatture multiple ente/anno fatturazione

### MEV (1 change)
- Nuovo tipo ente non convenzionato: Soggetto attuatore

## 4.7.10
### MAC (1 change)
- Corretti errori nelle query Hibernate di ricerca dei ruoli utente

## 4.7.9 (09-02-2021)
### MEV (1 change)
- Migrazione Hibernate

## 4.6.10

### MAC (1 change)
- Rimozione viste obsolete di SACER_IAM

## 4.6.9 (29-01-2021)

### MAC (5 change)
- Dettaglio servizi erogati: errore in fase di salvataggio modifiche
- Recupero UD da Accordo e altri documenti: utilizzare i dati persistiti
- Errore su calcolo servizi erogati e generazione fattura provvisoria
- Messaggi di errore con caratteri non validi
- Ricerca enti: gestire gli accordi non ancora validi in presenza di accordo valido

### MEV (4 change)
- Fatturazione: modifiche all'interfaccia
- Dettaglio accordo: prevedere Totale rimborso costi sulla sezione Annualità
- Consentire la creazione di utenti appartenenti all'ente amministratore senza abilitazioni a SIAM
- Eliminare controlli sulla data di inizio validità dell'ente

## 4.6.8

### MAC (1 change)
- Correzione memorizzazione tariffa su accordo

## 4.6.7

### MAC (1 change)
- cancellazione errata servizi erogati

## 4.6.6

### MAC (1 change)
- Automa creato da utente non di sistema: non è possibile inserire la password

### MEV (1 change)
- [SACERIAM] Aggiunta codice fiscale nel messaggio di errore per l'utente in caso di login non autorizzato via SPID

### SUE (1 change)
- Rilascio SIAM versione 4.6.6 - Nuova fatturazione

## 4.6.5

### MAC (3 change)
- Richiesta gestione utente: consentire modifica dell'ente se non ci sono azioni
- Dettaglio fattura: errore al salvataggio delle modifiche
- Ricerca enti convenzionati: il flag Gestioni accordo non viene visualizzato

### MEV (1 change)
- Parametri UD accordo: gestione dei dati memorizzati non corretta

## 4.6.4

### MAC (1 change)
- Richiesta gestione utente: impedire la modifica dell'ente di appartenenza se ci sono azioni

### MEV (2 change)
- Rimozione logo IBACN per passaggio a RER
- Controllo caratteri in campo Username

## 4.6.3

### MAC (1 change)
- Utenti non di sistema: problemi nel passaggio a persona fisica/automa

## 4.6.2

### MEV (2 change)
-  Controllo librerie obsolete terzo quadrimestre 2020
- Introdurre campo Note in Enti non convenzionati, Sistema versante, Accordo e Ambiente

## 4.6.1

### MAC (4 change)
- Nuova fatturazione: problema su Creazione fatture provvisorie
- Eccezione imprevista in salvataggio Tariffario, Tariffe, Tipo servizio e Tipo accordo
- Messaggio di errore con caratteri non validi
- Visualizzazione errata delle Annualità in caso di modifica date accordo

### MEV (2 change)
- Nuovo accordo solo del nuovo tipo
- Modifiche a campi e label dell'interfaccia

## 4.6.0

### EVO (1 change)
- Nuovo calcolo servizi da fatturare

### MEV (2 change)
- Nuova fatturazione: implementazione calcolo servizi erogati
- Nuova fatturazione: interventi sull'interfaccia

## 4.5.4 (18-08-2020)

### MEV (1 change)
- Recepimento aggiornamenti librerie Secondo quadrimestre 2020 - SACERIAM

## 4.5.3 (12-08-2020)

### MAC (1 change)
- problema nel security context

## 4.5.2 (06-08-2020)

### MAC (6 change)
- Allineamento ruoli da aggiornare
- Abilitazioni utente con flag supporto enti alzato
- Controlli su eliminazione nodo ambito territoriale
- Sistemi versanti: correzione formato data
- Visualizzazione enti appartenenti a un collegamento
- Problema visualizzazione ruoli

### MEV (1 change)
- Modifica a foto ente siam

## 4.5.0 (21-07-2020)

### MAC (6 change)
- Mancato aggiornamento foto struttura a fronte operazione su enti convenzionati
- Valorizzazione tabella accordi in dettaglio ente siam
- La ricerca utenti per ambiente non funziona
- Creazione automi senza sistema versanti
- Rimozione username in URL nella pagina di cambio password
- Modifica password utenti AUTOMA

### MEV (10 change)
- Dettaglio ente convenzionato: limitare visualizzazione dettaglio enti collegati
- Modificare date precompilate sui nuovi accordi
- Dettaglio accordo di vigilanza: aggiungere la colonna Ambiente
- Sistemi versanti: aggiungere flag "Cessato"
- Utenti: aggiungere campo Email secondaria
- Enti convenzionati e non convenzionati: gestire l'ambito territoriale in modo nativo
- Non eseguire la cessazione automatica degli utenti
- Utenti appartenenti a fornitori esterni con ruolo Gestore o Conservatore
- Sistema versante obbligatorio per tutti gli utenti, ad eccezione di quelli non di sistema
- Sistemi versati: aggiunta campi di ricerca

### SUE (1 change)
- Rilascio SIAM in preproduzione e produzione 4.5.2 (versione Revisione ruoli)

## 4.4.0 (16-07-2020)

### MEV (2 change)
- Disciplinare tecnico: inserire testo relativo agli SLA
- Integrazione con SPID

## 4.3.3

### MEV (1 change)
- Aggiornamento framework JQUERY

### SUE (1 change)
- Rilascio SIAM in preproduzione e produzione (versione 4.3.3 - Gestione enti, utenti e controllo date)

## 4.3.2 (09-03-2020)

### MAC (1 change)
- Controllo validità date sui collegamenti tra enti

### MEV (2 change)
- Abilitare anche Versatori di enti convenzionati collegati
- Gestione delle date dell'accordo e riflessi sulla gestione degli enti

## 4.3.1 (13-12-2019)

### MAC (8 change)
- Aggiungere parametri nel log per l'ambiente ente convenzionato
- Enti convenzionati: non vengono visualizzati gli organi di vigilanza
- Correzione errore gestione date in anagrafica enti convenzionati
- Correzioni a Variazioni accordo
- Consentire la ricerca utenti per ambiente
- Dettaglio azione: malfunzionamento combo ambiente/ente di appartenenza
- Modifica ente di appartenenza utente non di sistema
- revisione elaborazione cessazione utente

### MEV (6 change)
- Dettaglio ambiente: consentire di accedere al dettaglio enti convenzionati
- Visualizzazione enti collegati
- Sostituzione librerie obsolete spring-security-core-4.2.13.RELEASE.jar e spring-security-saml2-core-1.0.9.RELEASE.jar (pivotal_software:spring_security:4.2.13, pivotal_software:spring_security:1.0.9)
- Sostituzione libreria obsoleta jackson-databind-2.9.10.jar (fasterxml:jackson-databind:2.9.10)
- Dettaglio ente non convenzionato: aggiungere lista dei sistemi versanti 
- Enti non convenzionati: completare le funzioni di gestione

## 4.2.1.2 (25-10-2019)

### EVO (3 change)
- Sostituzione libreria obsoleta commons-collections:commons-collections:3.2.1
- Sostituzione libreria obsoleta org.opensaml:opensaml:2.5.3
- Sostituzione libreria obsoleta commons-fileupload:commons-fileupload:1.2.2

### MEV (5 change)
- Sostituzione libreria obsoleta apache:xalan-java:2.7.1
- Sostituzione libreria obsoleta fasterxml:jackson-databind:2.4.1
- Sostituzione libreria obsoleta org.bouncycastle:bcprov-jdk15:1.46
- Sostituzione libreria obsoleta spring-security-saml2-core:1.0.0.RC2
- Sostituzione libreria obsoleta dom4j:dom4j:1.6.1

## 4.2.1 (25-10-2019)

### EVO (1 change)
- Revisione funzioni utenti e enti convenzionati - IAM 4.2.0

### MAC (10 change)
- Inserimento disciplinare tecnico: non sono selezionabili le organizzazioni
- Allineamento abilitazioni per utenti appartenenti ad organo di vigilanza
- Abilitazione utenti disattivi
- Dettaglio ente convenz: lista utenti abilitati
- Eliminazione logging in seguito ad allineamento componenti
- Modifica di un utente utilizzando richiesta già evasa
- Modifica utente abilitato in PING e lista utenti per ente convenz
- Evasione richiesta di gestione utente
- Ripristinare il riuso dell'azione Creazione utente per correzioni alla configurazione
- Modifica abilitazione di un utente non di sistema

### MEV (14 change)
- Modifica menu e csv
- Aggiungere Informazioni su anche in IAM
- Dettaglio accordo: modifiche a Variazioni accordo
- Dati di registrazione disciplinare da rendere facoltativi
- Pulsante di scarico indirizzi email in ricerca utenti
- Gestione collegamenti da pagina dedicata
- Referenti enti: modifiche alla gestione
- Creazione e modifica ente convenzionato: modifiche per gestione delle fusioni
- Modifica ente appartenenza: estensione degli enti della richiesta
- Utenti enti convenzionato: presentare anche utenti non attivi
- Versatori di tipo fornitore esterno: gestire abilitazioni utenti enti produttori
- Creazione utente: consentire richiesta da enti collegati
- Dettaglio fornitore esterno: inserire tabella con versatori associati
- Richieste gestione utenti: considerare anche le relazioni tra enti

## 4.1.4.1 (23-01-2020)

### MAC (1 change)
- Anagrafica ente convenzionato: non gestisce nomi maggiori di 100 caratteri

### MEV (1 change)
- Dettaglio accordo: introduzione data fine validità

## 4.1.4 (16-09-2019)

### EVO (1 change)
- Presentazione release notes

### MEV (1 change)
- Modifica politiche di cancellazione dei login ok degli automi: vanno tenuti "qualche" giorno.

## 4.1.3 (14-06-2019)

### MAC (1 change)
- Problema popolamento combo conservatore e gestore ambiente

## 4.1.2 (14-06-2019)

### MAC (1 change)
- Modifica abilitazioni richiamando un'azione già evasa

### MEV (1 change)
- Evolutive su multiconservatore 

## 4.1.1 (10-06-2019)

### MAC (2 change)
- Errore nell'accedere al dettaglio ente convenzionato
- Problemi nella creazione utente

## 4.1.0 (05-06-2019)

### EVO (1 change)
- Modifiche a enti convenzionati per Multiconservatore

### MAC (5 change)
- Modifica utente: malfunzionamento indicazione ente di appartenenza
- Problemi sulla cessazione ente convenzionato
- Errore in modifica accordo
- Dettaglio sistema versante: errore nel salvataggio
- Archivisti di riferimento su enti e sistemi versanti

### MEV (2 change)
- Dettaglio ente convenzionato: modifiche alla pagina
- Controllo caratteri non ammessi nello user id

## 4.0.5

### MEV (1 change)
- Modifica a ente convenzionato per visualizzare versatore ping

## 4.0.4 (22-05-2019)

### MAC (2 change)
- Filtro ambiente su abilitazione utenti su enti convenzionati
- Errore salvataggio utente

### MEV (4 change)
- Dettaglio ente convenzionato: aggiungere enti non convenzionati
- Cessazione utente: elimina info dell'utente non cancellabile
- Nuova azione di gestione utenti: modifica ente di appartenza
- Cancellare ruolo quando assegnato solo a utenti cessati

## 4.0.3 (08-05-2019)

### MAC (1 change)
- Errori su gestione ruoli e utenti

## 4.0.2 (03-05-2019)

### MEV (1 change)
-  Java 8 e re-factor parer-pom / framework

## 4.0.1 (26-04-2019)

### EVO (1 change)
- Modifiche a utenti e enti convenzionati per Multiconservatore

### MAC (3 change)
- Errore nell'apertura della pagina anagrafica enti convenzionati
- Errore nella cancellazione di utente non di sistema
- Elimina parametro definito su ambiente

### MEV (3 change)
- Consentire la creazione tramite richiesta anche per utenti non di sistema
- Abilitazione token (csrf) su tag libray di spago-lite utilizzate per generare apposite form (HTTP POST) in IAM
- Gestione utenti: modificare gestione utenti amministratori

## 3.13.1.1 (13-02-2019)

### MAC (1 change)
- Recupero valore parametro STRUT_UNITA_DOC_ACCORDO

## 3.13.0.1

### MEV (1 change)
- Eliminazione jks dal sorgente dell'applicazione.

## 3.13.0 (15-01-2019)

### EVO (1 change)
- Gestione parametri per multiconservatore SIAM

### MAC (3 change)
- Errore nel confronto tra le date di fine validità ente e accordo
- Errore nel download dell'ud su sacer
- Inserimento utente archivista in un sistema versante

### MEV (2 change)
- Ricerca enti convenzionati: modifiche alla pagina
- Lista ruoli: nuovi campi di ricerca

## 3.12.1 (05-12-2018)

### MAC (1 change)
- Inserimento assicurando non monoticità

## 3.12.0 (26-11-2018)

### MAC (6 change)
- Troncare messaggio di errore in LOG_USER_DA_REPLIC
- Duplica utente senza richiesta
- Modifica utente:scelta organizzazione Sacer anzichè versatore ping
- Mancato aggiornamento stato fattura all'inserimento di un sollecito
- Gestione fatture: mancato aggiornamento stato "pagata parzialmente"
- Errata visualizzazione di fatture stornate

### MEV (11 change)
- Dettaglio enti convenzionati: considerare utenti non di sistema tra quelli appartenenti all'ente
- Dettaglio accordo: nuova sezione Disciplinari tecnici
- Sistema versante: aggiunta campo integrazione in corso
- Modifica modulo informazioni
- Estensione dell'associazione archivista di riferimento/ente convenzionato
- Sistemi versanti: indicare quali sono associabili a persone fisiche
- Documenti protocollati da IBC: download file unità documentarie 
- Variazioni accordo: gestire il processo di variazione 
- Dettaglio ente convenzionato: aggiungere campo Note ai Referenti dell'ente
- Enti convenzionati: introdurre il concetto di ente cessato
- Enti convenzionati: valorizzare la data di inizio validità con la data di decorrenza

## 3.11.3 (11-10-2018)

### MAC (3 change)
- Aumento tempo di deploy - timeout
- Contromisure alla vulnerabilità session termination
- Impostazione flag HttpOnly e secure sul cookie JSESSIONID 

## 3.11.2 (10-08-2018)

### MAC (1 change)
- Salvataggio di utenti non di sistema non corretto

## 3.11.1 (08-08-2018)

### MAC (1 change)
- Salvataggio di utenti non di sistema non corretto

### MEV (1 change)
- Tipo azione Modifica abilitazioni: controllo al salvataggio delle modifiche 

## 3.11.0

### EVO (1 change)
- Gestione amministrativa enti - Gestione fatturazione, accertamento e incassi - Fase Due

### MAC (7 change)
- Visualizzazione sistema versante
- Salvataggio di utenti non di sistema
- Password utenti team
- Accordo: errato controllo sul periodo di validità
- Dettaglio accordo: in modifica compare pulsante Ricalcola
- Pop up al salvataggio modifica: correzioni
- Tipo azione Modifica abilitazioni: disabilitare il controllo sull'ente convenzionato di appartenenza

### MEV (12 change)
- Sistema versante: aggiunta campo email 
- Verifica IP di provenienza basata su RERFwFor e non su X-Forwarded-For
- Uniformare i messaggi di errore in gestione richieste utenti
- Abilitazione automatico al tipo dato Tipo fascicolo
- Reset password: rendere ripetibile la richiesta/azione
- Filtrare utenti cessati da Dettaglio ente convenzionato
- Gestione campo IVA 
- Gestione fatturazione: modifica importo e servizi fatturati
- Log accessi: introdurre filtri su tipi utente e tipi evento
- Dettaglio ente convenzionato: eliminare pop up di cancellazione di un archivista o referente
- Dettaglio servizio erogato: modifica alla tabella Fatturazioni servizio erogato
- Dettaglio accordo: gestire le variazioni dell'accordo

## 3.10.2

### MEV (1 change)
- Ruoli specifici su organizzazioni in sostituzione di quelli standard

## 3.10.1 (10-05-2018)

### MEV (1 change)
- Ruoli specifici: consentirne la definizione per singola organizzazione appartenente a un'insieme definito in dichiarativa

## 3.9.2 (14-03-2018)

### MAC (2 change)
- Modifica servizio fatturato
- Ricalcolo servizi erogati

## 3.9.1 (07-03-2018)

### MAC (1 change)
- Inibizione dei pulsanti nella pagina di gestione job

## 3.9.0 (26-02-2018)

### MAC (1 change)
- Importo servizio erogato e fatturato devono accettare numeri decimali

### MEV (2 change)
- Controllo su associazione ente convenzionato / struttura - lato IAM
- Ricerca utenti: prevedere come filtro di ricerca l'indirizzo email 

## 3.8.4 (05-02-2018)

### MAC (4 change)
- Mancata replica associazione ente convenzionato struttura partendo da importa e duplica struttura standard
- Controllo sul campo Codice fiscale / Partita IVA
- Dettaglio servizio erogato: i dati dei servizi fatturati sono errati
- Correzioni sulla pagina di fatturazione emerse dalla prima fase della fatturazione

## 3.8.3 (23-01-2018)

### MAC (2 change)
- Modifica dati approvazione schema accordo e data atto: modificare pop up
- Errore recupero servizi fatturati nella pagina Dettaglio servizio erogato

### MEV (3 change)
- Messaggio di utente disattivato in Modifica password
- Visualizzazione accessi falliti: indicare l'orario
- Ente convenzionato: modificare gestione campi Partita Iva e Codice fiscale

## 3.8.1 (05-01-2018)

### EVO (1 change)
- Gestione amministrativa enti - Gestione fatturazione, accertamento e incassi

### MAC (8 change)
- Modifica abilitazioni utente 
- L'inserimento di  un nuovo sistema versante  va in errore
- Ricalcolo servizi erogati 
- Allineamento ruoli: messaggi di errore "sporchi"
- Allineamento ruoli: utente errato nel messaggio di errore
- Gestione ruoli: problemi visualizzazione delle voci di menu nell'albero delle azioni quando sono tutte abilitate
- Dettaglio servizio erogato: errore generico se è valorizzata la data di erogazione 
- Eliminare ambiente ente convenzionato nell'inserimento di un'anagrafica

### MEV (7 change)
- Pop up con password dell'utente: aggiungere anche email
- Ricerca utenti: inserire email nella lista dei risultati
- Revisione gestione modifica password
- Dettaglio enti convenzionati: modifiche alla presentazione delle informazioni
- Enti convenzionati: ricerca per data accordo e aggiunta data atto di approvazione accordo
- Gestione dei referenti degli enti convenzionati
- Anagrafe ente convenzionato: consentire modifica alle informazioni inserite

## 3.7.4 (12-01-2018)

### MAC (1 change)
- Modifica password per utente con ruolo comprendente servizi

## 3.7.3 (13-11-2017)

### MAC (2 change)
- Allineamento ruoli: errore nell'invocazione del servizio
- Interventi su alcune interfacce GUI

## 3.7.2 (08-11-2017)

### MEV (1 change)
- Adeguamento release 3.7.1 di IAM a Framework 2.0.0

## 3.7.1 (06-11-2017)

### MAC (1 change)
- Visualizza dettaglio utente

## 3.7.0 (30-10-2017)

### MAC (3 change)
- Gestione ruoli: mancato salvataggio delle abilitazioni
- Gestione ruoli: visualizzazione delle voci di menu abilitate nell'albero pagine/azioni
- Ricerca utenti: modificare ordinamento menu a discesa

### MEV (9 change)
- Dettaglio richiesta: consentire la modifica delle richieste anche se evase
- Dettaglio azione: modifiche all'inserimento dei dati per le richieste non di creazione
- Ruolo: modifiche al wizard
- Allineamento ruoli: controllo sulle versioni degli applicativi
- Allineamento ruoli: flag su allineamento parziale
- Dettaglio azione: riportare le informazioni della Richiesta
- Log SacerIAM su Ruolo e Richiesta
- Dettaglio sistema versante: consentire l'accesso al dettaglio della struttura
- IAM - Repliche utenti

## 3.6.7 (19-10-2017)

### MAC (1 change)
- La password degli utenti AUTOMA deve essere creata sempre senza scadenza

## 3.6.6 (18-10-2017)

### MAC (1 change)
- Dettaglio azione: consentire la definizione di username con lettere maiuscole

## 3.6.5 (17-10-2017)

### MAC (1 change)
- Creazione utente da Duplica utente: errore critico

## 3.6.4 (17-10-2017)

### MAC (1 change)
- Gestione ruoli: problemi nella visualizzazione dei dati nel diagramma ad albero

## 3.6.3

### MAC (2 change)
- Dettaglio richiesta: visualizzazione parziale del campo Note
- Gestione ruoli: malfunzionamento interfaccia 

## 3.6.2

### MAC (1 change)
- Richiesta con file va in errore

## 3.6.1

### EVO (1 change)
- Replica dei ruoli tra SIAM di PreProduzione e SIAM di Produzione

### MAC (1 change)
- Errore critico se si opera la cessazione dal dettaglio utente

### MEV (3 change)
- Modifica controlli sulla richiesta in configurazione utenti
- Gestione richieste: modifiche all'interfaccia
- Modulo informazioni: modificare controlli inserimento

## 3.6.0 (21-09-2017)

### EVO (1 change)
- Gestione utenti (gestione richieste; disattivazione e cessazione automatica)

### MAC (2 change)
- Malfunzionamenti su HelpOnLIne
- Valorizzazione colonna Ente convenzionato abilitato in ricerca utenti

### MEV (3 change)
- Password con scadenza anche per utenti TEAM
- Configurazioni utenti privi di abilitazioni sui sistemi
- Duplicazione utenti

## 3.5.1 (11-09-2017)

### MAC (1 change)
- Problema nel salvataggio di un nuovo ruolo

## 3.5.0 (08-09-2017)

### MAC (2 change)
- Modulo informazioni: modifiche ai controlli al salvataggio
- Risoluzione problemi di visualizzazione nuova interfaccia ruoli

### MEV (3 change)
- Revisione gestione utenti 
- Gestione ambiente degli enti convenzionati
- Gestione informazioni di recesso di un ente convenzionato

## 3.4.2 (23-05-2017)

### MAC (2 change)
- Integrazione con versione 1.0.17 del framework
- Integrazione a versione sacerlog 1.0.8

## 3.4.3 (05-06-2017)

### MAC (3 change)
- Integrazione con versione 1.0.9 Sacer log
- Problema visualizzazione azioni su Gestione ruoli 
- Controllo sui campi dati relativi al modulo informazioni

## 3.4.1 (22-05-2017)

### MAC (1 change)
- Eliminazione vista APL_V_TIPO_OGGETTO da Sacer Log

## 3.3.3 (08-03-2017)

### MAC (1 change)
- Modifica di un utente con indicazione indirizzo IP

## 3.3.2 (20-02-2017)

### MAC (1 change)
- Replica utenti e versatori ping

## 3.3.1 (16-02-2017)

### MEV (1 change)
- Ottimizzazione ricerca utenti
