Il Template presente in questa directory serve come base per creare con Microsoft Word l'Help On-line per le funzioni delle nostre applicazioni (SACER, SACER_IAM ecc.). 
Una volta creato l'Help con Wird viene esportato in HTML direttamente da Word e poi caricato nel DB di IAM mediante le funzionalità di gestione Help on-line. 
Tutto l'help on-line viene gestito a livello di framework Spagolite.

Logging-profile:
Questa cartella contiene la configurazione dei log sulle varie macchine.
Per maggiori informazioni leggete qui:
https://access.redhat.com/documentation/en-US/JBoss_Enterprise_Application_Platform/6.4/html/Administration_and_Configuration_Guide/sect-Logging_Profiles.html

Nel caso dobbiate creare un batch occhio a questo bug: https://issues.jboss.org/browse/WFCORE-37

Causa quel bug queste 3 righe 
/subsystem=logging/logging-profile=SACERIAM/root-logger=ROOT:add
/subsystem=logging/logging-profile=SACERIAM/root-logger=ROOT:add-handler(name="saceriam_handler")
/subsystem=logging/logging-profile=SACERIAM/root-logger=ROOT:add-handler(name="saceriam_console_handler")

devono essere scritte così: 

/subsystem=logging/logging-profile=SACERIAM/root-logger=ROOT:add(handlers=[saceriam_handler, saceriam_console_handler])

NOTA: il "profile-name" di produzione e Puglia (test e prod) non è stato specificato.