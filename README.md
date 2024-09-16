# SIAM (SACER Identity and Access Management) 

Fonte template redazione documento:  https://www.makeareadme.com/.


# Descrizione

SIAM (SACER Identity and Access Management) è il modulo del Sistema di Conservazione SACER che si occupa della gestione dell'autenticazione e profilazione degli utenti e degli enti cui appartengono.

Tramite la possibilità  di definire specifici ruoli e di combinarli tra loro, l'utente può essere profilato alle singole attività previste dal Sistema (p.e. pressione di uno specifico bottone di una specifica videata) e ad ogni livello dei dati gestiti.   

SIAM gestisce l'autenticazione tramite standard SAML utilizzando un Idp esterno e consente agli utenti del Sistema di autenticarsi anche attraverso SPID..   

SIAM consente di gestire l'anagrafe degli enti che utilizzano il sistema di conservazione e dei loro rapporti formali (accordi) con l'ente conservatore. 


# Installazione

Requisiti minimi per installazione: 

- Sistema operativo : consigliato Linux server (in alternativa compatibilità con Windows server);
- Java versione 8 (OpenJDK / Oracle);
- JBoss 7 EAP;
- Oracle DB (versione consigliata 19c).

## Instalazione JDK 

Consigliata adozione della OpenJDK alla versione 8, guida all'installazione https://openjdk.org/install/.

## Setup application server (Jboss 7)

Richiesta l'esecuzione delle seguenti guide secondo l'ordine riportato di seguito: 

1. guida per la configurazione **base** di [guida 1](src/docs/JBoss7_configurazione_generale.md);
2. guida con le configurazioni **specifiche** per il contesto applicativo **SIAM**  di [guida 2](src/docs/JBoss7_configurazione_siam.md).

### Deploy su JBoss 7

Di seguito le indicazioni per il rilascio su application server JBoss7: 

1. generazione dell'artifact attraverso tool maven, eseguire il seguente comando: 

   ```bash
   mvn package
   ```
   
2. viene generato l'artifact .ear all'interno del modulo SacerIam-ear/target (e.g. saceriam-4.18.0.ear)
3. deploy dell'ear generato allo step 1 su JBoss 7 (vedi configurazione [setup JBoss7](#setup-application-server-jboss-7))


## Predisposizione database

L'applicazione utilizza come DBMS di riferimento Oracle DB (https://www.oracle.com/it/database/) alla versione, consigliata, **19c**. Per l'installazione e la configurazione fare riferimento alle guide ufficiali.

Per la creazione del modello E-R consultare il seguente [README.md](https://github.com/RegioneER/parer-db-init/blob/master/README.md) (progetto di riferimento https://github.com/RegioneER/parer-db-init).


# Utilizzo

Di seguito la descrizione delle principali funzionalità.
 
## Amministrazione Utenti 
   
<img src="src/docs/img/amm_utenti.png"> 


Gli utenti possono essere di due tipi: persona fisica o automa. La loro creazione e successiva gestione deve essere preceduta da una specifica richiesta da parte del proprio ente di appartenenza in cui viene indicato il grado richiesto di operatività sui dati e sul sistema. L'operatività è definita con un ruolo o una combinazione di ruoli che possono essere attribuiti all'utente. 
 
  
## Amministrazione Enti
  
<img src="src/docs/img/amm_enti.png"> 

  
### Enti convenzionati 
 
Sono enti convenzionati tutti gli enti che conservano utilizzando il Sistema a seguito della stipula di una convenzione o di un accordo. 
Gli enti si suddividono in:  

- CONSERVATORI: svolgono la funzione di conservazione per sé e per gli enti produttori; 
- GESTORI: supportano gli enti produttori nei rapporti con il conservatore;  
- PRODUTTORI: utilizzano il sistema per conservare i propri documenti e archivi.
  

<img src="src/docs/img/ricerca_enti.png"> 
  
### Enti non convenzionati 

L'anagrafe degli enti non convenzionati è costituita da: 
- fornitori esterni: aziende che supportano gli enti convenzionati nell'interfacciamento dei sistemi di gestione documentale e nel monitoraggio dei versamenti; 
- organi di vigilanza: le Soprintendenze Archivistiche con le quali il conservatore definisce accordi per l'accesso al sistema a scopo di vigilanza. 

   
<img src="src/docs/img/ricerca_enti_conv.png"> 

### Gestione collegamenti 

Siam permette di individuare collegamenti tra gli Enti per consentire politiche comuni di accesso agli archivi degli enti collegati 
I collegamenti che si possono creare sono di due tipi:  

- GERARCHICI: quando esiste un legame gerarchico tra gli enti collegati. Un esempio è rappresentato dalle unioni di comuni in cui esiste un ente capofila che è l'ente unione;
- RETICOLARI: quando non esiste una gerarchia tra gli enti collegati che sono tutti allo stesso livello tra loro. 
  
### Gestione accordi 

L'accordo/convenzione contiene le informazioni relative a decorrenze, scadenze, servizi erogati. 


# Librerie utilizzate


|  GroupId | ArtifactId  | Version |
|:---:|:---:|:---:|
|none|||
|antlr|antlr|2.7.7.redhat-7|
|com.fasterxml.jackson.core|jackson-annotations|2.12.7.redhat-00003|
|com.fasterxml.jackson.core|jackson-core|2.12.7.redhat-00003|
|com.fasterxml.jackson.core|jackson-databind|2.12.7.redhat-00003|
|com.fasterxml.woodstox|woodstox-core|6.4.0.redhat-00001|
|com.fasterxml|classmate|1.5.1.redhat-00001|
|com.io7m.xom|xom|1.2.10|
|com.narupley|not-going-to-be-commons-ssl|0.3.20|
|com.sun.activation|jakarta.activation|1.2.2.redhat-00001|
|com.sun.istack|istack-commons-runtime|3.0.10.redhat-00001|
|com.sun.mail|jakarta.mail|1.6.7.redhat-00001|
|com.zaxxer|SparseBitSet|1.2|
|commons-beanutils|commons-beanutils|1.9.4|
|commons-codec|commons-codec|1.15.0.redhat-00001|
|commons-fileupload|commons-fileupload|1.5|
|commons-io|commons-io|2.12.0|
|commons-logging|commons-logging|1.2|
|commons-net|commons-net|3.9.0|
|it.eng.parer|spagofat-core|5.14.0|
|it.eng.parer|spagofat-middle|5.14.0|
|it.eng.parer|spagofat-paginator-ejb|5.14.0|
|it.eng.parer|spagofat-paginator-gf|5.14.0|
|it.eng.parer|spagofat-sl-jpa|5.14.0|
|it.eng.parer|spagofat-timer-wrapper-common|5.14.0|
|jakarta.enterprise|jakarta.enterprise.cdi-api|2.0.2.redhat-00002|
|jakarta.inject|jakarta.inject-api|1.0.3.redhat-00001|
|jakarta.json.bind|jakarta.json.bind-api|1.0.2.redhat-00001|
|jakarta.json|jakarta.json-api|1.1.6.redhat-00001|
|jakarta.persistence|jakarta.persistence-api|2.2.3.redhat-00001|
|jakarta.security.enterprise|jakarta.security.enterprise-api|1.0.2.redhat-00001|
|jakarta.validation|jakarta.validation-api|2.0.2.redhat-00001|
|jakarta.xml.bind|jakarta.xml.bind-api|2.3.2|
|javax.jws|jsr181-api|1.0.0.MR1-redhat-8|
|javax.xml.bind|jaxb-api|2.4.0-b180830.0359|
|joda-time|joda-time|2.12.5|
|junit|junit|4.13.2|
|net.bytebuddy|byte-buddy|1.11.12.redhat-00002|
|org.apache-extras.beanshell|bsh|2.0b6|
|org.apache.commons|commons-collections4|4.4|
|org.apache.commons|commons-lang3|3.8.1|
|org.apache.commons|commons-math3|3.6.1|
|org.apache.commons|commons-text|1.10.0|
|org.apache.httpcomponents|httpclient|4.5.13.redhat-00001|
|org.apache.httpcomponents|httpcore|4.4.14.redhat-00001|
|org.apache.logging.log4j|log4j-api|2.18.0|
|org.apache.poi|poi|5.2.3|
|org.apache.santuario|xmlsec|2.2.3.redhat-00001|
|org.apache.taglibs|taglibs-standard-impl|1.2.6.RC1-redhat-1|
|org.apache.taglibs|taglibs-standard-spec|1.2.6.RC1-redhat-1|
|org.apache.velocity|velocity-engine-core|2.0|
|org.apache.xmlbeans|xmlbeans|3.1.0|
|org.bouncycastle|bcpkix-jdk15on|1.70|
|org.bouncycastle|bcprov-jdk15on|1.70|
|org.bouncycastle|bcutil-jdk15on|1.70|
|org.codehaus.jettison|jettison|1.5.2.redhat-00002|
|org.codehaus.woodstox|stax2-api|4.2.1.redhat-00001|
|org.dom4j|dom4j|2.1.3.redhat-00001|
|org.glassfish.jaxb|jaxb-runtime|2.3.3.b02-redhat-00002|
|org.glassfish.jaxb|txw2|2.3.3.b02-redhat-00002|
|org.hamcrest|hamcrest-core|1.3|
|org.hibernate.common|hibernate-commons-annotations|5.0.5.Final-redhat-00002|
|org.hibernate.validator|hibernate-validator|6.0.23.Final-redhat-00001|
|org.hibernate|hibernate-core|5.3.29.Final-redhat-00001|
|org.hibernate|hibernate-entitymanager|5.3.29.Final-redhat-00001|
|org.hibernate|hibernate-jpamodelgen|5.3.29.Final-redhat-00001|
|org.javassist|javassist|3.27.0.GA-redhat-00001|
|org.jboss.logging|jboss-logging|3.4.1.Final-redhat-00001|
|org.jboss.spec.javax.annotation|jboss-annotations-api_1.3_spec|2.0.1.Final-redhat-00001|
|org.jboss.spec.javax.batch|jboss-batch-api_1.0_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.ejb|jboss-ejb-api_3.2_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.el|jboss-el-api_3.0_spec|2.0.1.Final-redhat-00001|
|org.jboss.spec.javax.enterprise.concurrent|jboss-concurrency-api_1.0_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.faces|jboss-jsf-api_2.3_spec|3.0.0.SP07-redhat-00001|
|org.jboss.spec.javax.interceptor|jboss-interceptors-api_1.2_spec|2.0.0.Final-redhat-00002|
|org.jboss.spec.javax.jms|jboss-jms-api_2.0_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.management.j2ee|jboss-j2eemgmt-api_1.1_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.resource|jboss-connector-api_1.7_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.security.auth.message|jboss-jaspi-api_1.1_spec|2.0.1.Final-redhat-00001|
|org.jboss.spec.javax.security.jacc|jboss-jacc-api_1.5_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.servlet.jsp|jboss-jsp-api_2.3_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.servlet|jboss-servlet-api_4.0_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.transaction|jboss-transaction-api_1.3_spec|2.0.0.Final-redhat-00005|
|org.jboss.spec.javax.websocket|jboss-websocket-api_1.1_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec.javax.ws.rs|jboss-jaxrs-api_2.1_spec|2.0.1.Final-redhat-00001|
|org.jboss.spec.javax.xml.bind|jboss-jaxb-api_2.3_spec|2.0.1.Final-redhat-00001|
|org.jboss.spec.javax.xml.soap|jboss-saaj-api_1.4_spec|1.0.2.Final-redhat-00002|
|org.jboss.spec.javax.xml.ws|jboss-jaxws-api_2.3_spec|2.0.0.Final-redhat-00001|
|org.jboss.spec|jboss-jakartaee-8.0|1.0.1.Final-redhat-00007|
|org.jboss|jandex|2.4.2.Final-redhat-00001|
|org.jboss|jboss-vfs|3.2.15.Final-redhat-00001|
|org.keycloak|keycloak-adapter-core|21.1.1|
|org.keycloak|keycloak-adapter-spi|21.1.1|
|org.keycloak|keycloak-authz-client|21.1.1|
|org.keycloak|keycloak-common|21.1.1|
|org.keycloak|keycloak-core|21.1.1|
|org.keycloak|keycloak-crypto-default|21.1.1|
|org.keycloak|keycloak-policy-enforcer|21.1.1|
|org.keycloak|keycloak-server-spi-private|21.1.1|
|org.keycloak|keycloak-server-spi|21.1.1|
|org.keycloak|keycloak-servlet-adapter-spi|21.1.1|
|org.keycloak|keycloak-servlet-filter-adapter|21.1.1|
|org.opensaml|opensaml|2.6.6|
|org.opensaml|openws|1.5.6|
|org.opensaml|xmltooling|1.4.6|
|org.owasp.esapi|esapi|2.2.0.0|
|org.slf4j|slf4j-api|1.7.22.redhat-2|
|org.springframework.security.extensions|spring-security-saml2-core|1.0.10.RELEASE|
|org.springframework.security|spring-security-config|5.8.8|
|org.springframework.security|spring-security-core|5.8.8|
|org.springframework.security|spring-security-crypto|5.8.8|
|org.springframework.security|spring-security-web|5.8.8|
|org.springframework|spring-aop|5.3.30|
|org.springframework|spring-beans|5.3.30|
|org.springframework|spring-context|5.3.30|
|org.springframework|spring-core|5.3.30|
|org.springframework|spring-expression|5.3.30|
|org.springframework|spring-jcl|5.3.30|
|org.springframework|spring-web|5.3.30|
|org.springframework|spring-webmvc|5.3.30|
|xml-apis|xml-apis|1.4.01|


## Lista licenze in uso


 * agpl_v3     : GNU Affero General Public License (AGPL) version 3.0
 * apache_v2   : Apache License version 2.0
 * bsd_2       : BSD 2-Clause License
 * bsd_3       : BSD 3-Clause License
 * cddl_v1     : COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * epl_only_v1 : Eclipse Public License - v 1.0
 * epl_only_v2 : Eclipse Public License - v 2.0
 * epl_v1      : Eclipse Public + Distribution License - v 1.0
 * epl_v2      : Eclipse Public License - v 2.0 with Secondary License
 * eupl_v1_1   : European Union Public License v1.1
 * fdl_v1_3    : GNU Free Documentation License (FDL) version 1.3
 * gpl_v1      : GNU General Public License (GPL) version 1.0
 * gpl_v2      : GNU General Public License (GPL) version 2.0
 * gpl_v3      : GNU General Public License (GPL) version 3.0
 * lgpl_v2_1   : GNU General Lesser Public License (LGPL) version 2.1
 * lgpl_v3     : GNU General Lesser Public License (LGPL) version 3.0
 * mit         : MIT-License

# Supporto

Mantainer del progetto è [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Contributi

Se interessati a crontribuire alla crescita del progetto potete scrivere all'indirizzo email <a href="mailto:areasviluppoparer@regione.emilia-romagna.it">areasviluppoparer@regione.emilia-romagna.it</a>.

# Credits

Progetto di proprietà di [Regione Emilia-Romagna](https://www.regione.emilia-romagna.it/) sviluppato a cura di [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Licenza

Questo progetto è rilasciato sotto licenza GNU Affero General Public License v3.0 or later ([LICENSE.txt](LICENSE.txt)).

# Appendice

## Documentazione aggiuntiva

Alcuni riferimenti:

- Manuale di conservazione: https://poloarchivistico.regione.emilia-romagna.it/documentazione/documenti_open/manualeconservazione_v5-0.pdf/@@download/file/ManualeConservazione_v2.0.pdf 


## JStree vesione 1 vs 3

Fare riferimento a : https://www.jstree.com/api/

<b>Attenzione</b> tie_selection true/false e proplematiche nella gestione degli stati di selezione delle checkbox  

Nota bene : dopo attenta analisi e sperimentazione la configurazione scelta per il plugin "checkbox" al fine di mantenere il comportamento precedente è : tie_selection = false e 
 whole_node  false, laddove sia necessaria la gestione "two_state" della precedente versione è necessario introdurre la three_state = false

* whole_node = false
   * funziona esclusivamente se tie_selection false
* tie_selection = false 
   * gli eventi di chech_node.jstree e uncheck_node.jstree vengono intercentati altrimenti no
   * la function is_checked re-implementata con una function appositamente implementata, che richiama l'apposita api https://www.jstree.com/api/#/?f=is_checked(obj)
* json_data
    * integrato, non esiste un plugin è sufficiente modificare la logica di back-end in modo da restituire un json compliant (vedi buildJSONNode su AmministrazioneRuoliAction
    * vedere inoltre su taglibrary dichiarazione "core / data / uri" per la nuova modalità di integrazione con un modello JSON (vedi core : data + url) 
* check_node 
    * serve prima eseguire il triggering dell'open_all altrimenti non riesce a checkare i nodi selezionati
* get_path 
    * necessario passare il nodo (vedi get_node) https://www.jstree.com/api/#/?f=get_path(obj%20[,%20glue,%20ids])
* metodo lock non esiste 
    * è stata creata apposita function che locka il tree (in sola lettura)
* loaded() 
    * non esiste, è necessario scatenare l'evento loaded.jstree
* populateTreee 
    * è necessario gestire l'hiding/show del tree (come elemnto all'interno del DOM) quando non sono presenti valori selezionati (vedi nome applicazione/azione)
* checkbox two_state 
    * non esiste più, si pone a "false" il three_state 
* get_checked(node) 
    * non esiste più la possibilità di ottenere i soli "figli" selezionati è stata indorotta apposita function
```javascript
function get_selected_children(node) {
    [....]
    return result;
}
```
* è stata modificata la taglibrary JTreeTag in modo tale da gestire delle proprietà legate ai plugin in uso (vedi checkbox : tree_state & altro) questo per fare in modo di pilotare determinate caratteristiche secondo il bisogno, e.g. nel caso dell'abilitazione al menu il three_state NON risulta necessario a differenza invece delle azioni (attenzione che in questo caso è necesssaria la javascript function che gestisce l'interazione in fase di editing)
* non esiste il metodo unlock quindi è necessario creare un'apposita function per rimuovere eventuali lock sul tree

### Ulteriori note

Sono stati osservati dei comportamenti anomali su web application [sacer-iam](https://gitlab.ente.regione.emr.it/parer/sacer-iam) in cui il componente JStree viene utilizzato per la gestione delle autorizzazioni. 

**get_cheked vs get_top_checked**

Il passaggio dalla versione 1 alla 3 di JStree ha portato al cambiamento generale delle API utilizzate in precedenza, nello specifico, un diverso comportamento legato alla API [get_checked](https://www.jstree.com/api/#/?f=get_checked([full])) che nella precedente versione [link](https://old.jstree.com/documentation/checkbox), presentava una diversa logica.<br/>
Se nella logica precedente la lista delle checkbox selezionate poteva riguardare, da un lato tutti i livelli dell'albero (governato da apposito boolean get_all) mentre dall'altro solo i "top level" (utilizzato per l'autorizzazione sui Menu), con la nuova api questo è possibile solo richiamando la API [get_top_checked](https://www.jstree.com/api/#/?f=get_top_checked([full])).<br/> Per esibire lo stesso comportamento e relativa logica, della precedente versione dell'applicativo con JStree v.1, si è dovuto differenziare per Menu e Azioni la selezione degli IDs (lista) delle checkbox selezionate, per cui le azioni utilizzano la get_checked mentre i menu la get_top_checked.


**popolamento JStree albero Azioni e refreshing**

Altra importante osservazione del comportamento precedente, ossia con JStree v.1, è legato al **primo** caricamento dei due alberi (Menu vs Azioni), e nello specifico, ad una problematica importante in fase di persistenza, delle modifiche effettuate sull'albero delle azioni. <br/>Sulla modifica del ruolo attraverso una logica di chiamate ad enpoint REST esposti sul relativo controller, si effettuano i popolamenti degli elementi presenti sull'alberto attraverso un evento di "check" (o selezione) del singolo nodo in cui inoltre vengono aggiunti degli attributi "custom" sotto forma di identificati (id_rich_autor / id_page_autor) resituiti dal JSON, risposta alla chiamata sul banck-end effettuata in precedenza. <br/>La selezione degli elementi checkbox su albero scatena il triggering dell'evento "check_node.jstree" il quale viene intercettato da logiche presenti sul client e, proprio su tali logiche, si è dovuti intervenire (vedi variabile "refreshing" su pagina) per evitare che, alla selezionata di una foglia dovendo selezionare anche il padre, si torni a scatenare il medesimo evento, togliendo quindi il controllo alla function "checkData", invocata in fase di popolamento. Il parametro "refreshing" interviene, appunto, come "driver" di gestione del diverso comportamento, dal primo popalmento con JSON a tutte le fasi successive di selezione dei vari nodi e relative logiche collegate.

**trigger evento check_node.jstree**

In JStree v.1 tutte le volte che veniva chiamata 
```js
tree.jstree('check_node', node);
```
 si scatenava l'evento "check_node.jstree", mentre nella versione 3 l'evento viene scatenato solo se il nodo non risulta già checked. 
Poiché durante il popolamento della struttura dell'albero possono esserci check multilpli sullo stesso nodo nell'upgrade della libreria si ha un cambio del comportamento proprio per la mancata chiamata dell'evento check_node.jstree.
Per emulare il comportamento della versione 1 bisogna quindi fare l'uncheck del nodo prima di fare nuovamente la check.
Per evitare che questo triggeri anche l'evento di uncheck, con risvolti non predicibili, è stato introdotto un attributo custom sull'albero, "skip_event". 
La logica descritta è stata incapsulata nella funzione
```js
function check_node_force_event(idTree,idNode)
```
ed è stato aggiunto un controllo nella funzione dell'evento "uncheck_node.jstree". 
```js
tree.on("uncheck_node.jstree", function (event, data) {
    var skip =tree.attr("skip_event");
    if (typeof skip === typeof undefined || !skip){
        //logica di business
    }else{
        tree.removeAttr("skip_event");
    }
});
```

Il problema riscontrato a causa della mancata invocazione di check_node.jstree era la mancata chiamata di questa funzione che avveniva solo al secondo check del nodo radice di tutto l'albero delle abilitazioni. 
```js
ajaxSelectNode(id_node, nodePath, id_dich_autor, "selectAzioneNode");
```
Di conseguenza non veniva valorizzato il campo idDichAuthor di RoleTree.java. 
