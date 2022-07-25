---
title: "Configurazione Sacer Iam"
---

# Configurazione Jboss EAP 6.4

## Versioni 

| Vers. doc | Vers. Iam  | Modifiche  |
| -------- | ---------- | ---------- |
| 1.0.0    | 4.6.13 | Versione iniziale del documento  |

## Datasource

Per configurare il datasource dell'applicativo dalla console web di amministrazione bisogna andare su
d
`Configuration > Connector > Datasources`

Scegliere **DATASOURCES** e premere 

`Add`

Si apre un wizard in 3 passaggi
1. Aggiungere gli attributi del datasource: Nome=**SacerIamPool** e JNDI=**java:/jdbc/SiamDs**;
2. Selezionare il driver **ojdbc8** (predisposto durante la configurazione generale di Jboss);
3. Impostare gli attributi della connessione, ad esempio *URL*.

## Key Store 

È necessario mettere il keystore in formato JKS in una cartella accessibile all'IDP e poi configurare la system properties saceriam-jks-path con il path al file.

## System properties

Dalla console web di amministrazione 

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
            <max-backup-index value="1">
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

