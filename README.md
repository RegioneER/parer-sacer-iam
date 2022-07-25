# IAM 

Progetto di gestione anagrafica e autorizzazioni.
<br/>
Nei successivi paragrafi vengono riportate alcune informazioni puntuali legate ai framwerok e/o ai processi dell'applicazione.

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
