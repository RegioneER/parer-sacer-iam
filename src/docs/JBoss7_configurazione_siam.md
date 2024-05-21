---
title: "Configurazione Sacer Iam"
---

# Configurazione Jboss EAP 7.3

## Versioni 

| Vers. doc | Vers. Iam  | Modifiche  |
| -------- | ---------- | ---------- |
| 1.0.0    |  | Versione iniziale del documento  |

## Datasource

### Console web

`Configuration > Connector > datasources`

Scegliere **DATASOURCES** e premere 

`Add`

Si apre un wizard in 3 passaggi
1. Aggiungere gli attributi del datasource: Nome=**SacerIamPool** e JNDI=**java:/jboss/datasources/SiamDs**;
2. Selezionare il driver **ojdbc8** (predisposto durante la configurazione generale di Jboss);
3. Impostare gli attributi della connessione, ad esempio *URL*.

### JBoss CLI

```bash
data-source add --name=SacerIamPool --jndi-name=java:jboss/datasources/SiamDs --connection-url=jdbc:oracle:thin:@parer-vora-b03.ente.regione.emr.it:1521/PARER19S.ente.regione.emr.it --user-name=SACER_IAM --password=<password> --driver-name=ojdbc8 --max-pool-size=64 --spy=true --jta=true --exception-sorter-class-name=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleExceptionSorter --stale-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleStaleConnectionChecker --valid-connection-checker-class-name=org.jboss.jca.adapters.jdbc.extensions.oracle.OracleValidConnectionChecker --statistics-enabled=true --use-ccm=true --use-fast-fail=true --validate-on-match=true --flush-strategy=FailingConnectionOnly --background-validation=false --min-pool-size=8 --enabled=true
```


## Key Store 

È necessario mettere il keystore in formato JKS in una cartella accessibile all'IDP e poi configurare la system properties saceriam-jks-path con il path al file.

## System properties

### Console web

`Configuration > System properties`

impostare le seguenti properties

Chiave | Valore di esempio | Descrizione
--- | --- | ---
saceriam-key-manager-pass | <password_jks_saceriam> | Chiave del Java Key Store utilizzato per ottenere la chiave privata del circolo di fiducia dell’IDP.
saceriam-timeout-metadata | 10000 | Timeout in secondi per la ricezione dei metadati dall’IDP.
saceriam-temp-file | /var/tmp/tmp-saceriam-federation.xml | Percorso assoluto del file xml che rappresenta l’applicazione all’interno del circolo di fiducia.
saceriam-sp-identity-id | https://parer.regione.emilia-romagna.it/saceriam | Identità dell’applicazione all’interno del circolo di fiducia.
saceriam-refresh-check-interval | 600000 | Intervallo di tempo in secondi utilizzato per ricontattare l’IDP per eventuali variazioni sulla configurazione del circolo di fiducia.
saceriam-jks-path | /opt/jboss-eap/certs/saceriam.jks | Percorso assoluto del Java Key Store dell’applicazione.
saceriam-store-key-name | saceriam | Alias del certificato dell’applicazione all’interno del Java Key Store.

### JBoss CLI

```bash 
/system-property=saceriam-key-manager-pass:add(value="<password_jks_saceriam>")
/system-property=saceriam-timeout-metadata:add(value="10000")
/system-property=saceriam-temp-file:add(value="/var/tmp/tmp-saceriam-federation.xml")
/system-property=saceriam-sp-identity-id:add(value="https://parer-svil.ente.regione.emr.it/saceriam")
/system-property=saceriam-refresh-check-interval:add(value="600000")
/system-property=saceriam-jks-path:add(value="/opt/jboss-eap/certs/saceriam.jks")
/system-property=saceriam-store-key-name:add(value="saceriam")
```

## Logging profile

### JDBC custom handler
Assicurarsi di aver installato il modulo ApplicationLogCustomHandler (Vedi documentazione di configurazione di JBoss del ParER).

Configurare un custom handler nel subsystem **jboss:domain:logging:1.5**.

```xml
<subsystem xmlns="urn:jboss:domain:logging:1.5">
    <!-- ... --> 
    <custom-handler name="saceriam_jdbc_handler" class="it.eng.tools.jboss.module.logger.ApplicationLogCustomHandler" module="it.eng.tools.jboss.module.logger">
        <level name="INFO"/>
        <formatter>
            <named-formatter name="PATTERN"/>
        </formatter>
        <properties>
            <property name="fileName" value="saceriam_jdbc.log"/>
            <property name="deployment" value="saceriam"/>
        </properties>
    </custom-handler>
    <!-- ... -->
</subsystem>
```
I comandi CLI 

```bash 
/subsystem=logging/custom-handler=saceriam_jdbc_handler:add(class=it.eng.tools.jboss.module.logger.ApplicationLogCustomHandler,module=it.eng.tools.jboss.module.logger,level=INFO)

/subsystem=logging/custom-handler=saceriam_jdbc_handler:write-attribute(name=named-formatter,value=PATTERN)

/subsystem=logging/custom-handler=saceriam_jdbc_handler:write-attribute(name=properties,value={fileName=>"saceriam_jdbc.log", deployment=>"sacerdips"})
```

Associare l'handler ai logger **jboss.jdbc.spy** e **org.hibernate**, sempre nel subsystem **jboss:domain:logging:1.5**. 


```xml
<subsystem xmlns="urn:jboss:domain:logging:1.5">
    <!-- ... -->
    <logger category="jboss.jdbc.spy" use-parent-handlers="false">
        <level name="DEBUG"/>
        <filter-spec value="match(&quot;Statement|prepareStatement&quot;)"/>
        <handlers>
            <handler name="saceriam_jdbc_handler"/>
        </handlers>
    </logger>
    <logger category="org.hibernate" use-parent-handlers="false">
        <level name="WARNING"/>
        <handlers>
            <handler name="saceriam_jdbc_handler"/>
        </handlers>
    </logger>
    <!-- ... -->
</subsystem>
```

I comandi CLI

```bash
/subsystem=logging/logger=org.hibernate:add-handler(name=saceriam_jdbc_handler)

/subsystem=logging/logger=jboss.jdbc.spy:add-handler(name=saceriam_jdbc_handler)
```
### Profilo Sacer Iam
```xml
<logging-profiles>
    <!-- ... -->
    <logging-profile name="SACERIAM">
        <periodic-rotating-file-handler name="saceriam_handler" autoflush="true">
            <level name="INFO"/>
            <formatter>
                <pattern-formatter pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n"/>
            </formatter>
            <file relative-to="jboss.server.log.dir" path="saceriam.log"/>
            <suffix value=".yyyy-MM-dd"/>
            <append value="true"/>
        </periodic-rotating-file-handler>
        <periodic-size-rotating-file-handler name="saceriam_tx_connection_handler" autoflush="true">
            <level name="DEBUG"/>
            <formatter>
                <pattern-formatter pattern="%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n"/>
            </formatter>
            <file relative-to="jboss.server.log.dir" path="saceriam_conn_handler.log"/>
            <append value="true"/>
            <max-backup-index value="1"/>
            <rotate-size value="256m"/>
        </periodic-size-rotating-file-handler>
        <logger category="org.springframework" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="org.opensaml" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="it.eng.saceriam" use-parent-handlers="true">
            <level name="INFO"/>
        </logger>
        <logger category="it.eng.parer.sacerlog" use-parent-handlers="true">
            <level name="INFO"/>
        </logger>
        <logger category="org.hibernate" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="jboss.jdbc.spy" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="org.jboss.jca.core.connectionmanager.listener.TxConnectionListener" use-parent-handlers="false">
            <level name="DEBUG"/>
            <handlers>
                <handler name="saceriam_tx_connection_handler"/>
            </handlers>
        </logger>
        <!-- ... -->
        <root-logger>
            <level name="INFO"/>
            <handlers>
                <handler name="saceriam_handler"/>
            </handlers>
        </root-logger>
    </logging-profile>
    <!-- ... -->
</logging-profiles>
```

### JBoss CLI

```bash 
/subsystem=logging/logging-profile=SACERIAM:add()
/subsystem=logging/logging-profile=SACERIAM/periodic-rotating-file-handler=saceriam_handler:add(level=INFO,formatter="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n",file={path="saceriam.log",relative-to="jboss.server.log.dir"},suffix=".yyyy-MM-dd",append=true)
/subsystem=logging/logging-profile=SACERIAM/size-rotating-file-handler=saceriam_tx_connection_handler:add(level=INFO,formatter="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n",file={path="saceriam_conn_handler.log",relative-to="jboss.server.log.dir"},append=true,max-backup-index=1,rotate-size="256m")
/subsystem=logging/logging-profile=SACERIAM/logger=org.jboss.jca.core.connectionmanager.listener.TxConnectionListener:add(level=INFO,handlers=[saceriam_tx_connection_handler],use-parent-handlers=false)
/subsystem=logging/logging-profile=SACERIAM/root-logger=ROOT:add(level=INFO,handlers=[saceriam_handler])
/subsystem=logging/logging-profile=SACERIAM/logger=org.springframework:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=SACERIAM/logger=org.opensaml:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=SACERIAM/logger=it.eng.saceriam:add(level=INFO,use-parent-handlers=true)
/subsystem=logging/logging-profile=SACERIAM/logger=it.eng.parer.sacerlog:add(level=INFO,use-parent-handlers=true)
/subsystem=logging/logging-profile=SACERIAM/logger=org.hibernate:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=SACERIAM/logger=jboss.jdbc.spy:add(level=ERROR,use-parent-handlers=true)
```

