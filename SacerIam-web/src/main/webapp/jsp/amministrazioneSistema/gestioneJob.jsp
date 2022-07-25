<%@ page import="it.eng.saceriam.slite.gen.form.GestioneJobForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Gestione Job" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="GESTIONE JOB IAM"/>
            <slf:fieldSet legend="Replica utenti" >
                <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.ATTIVO%>" colSpan="3" controlWidth="w20"/>
                <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.DT_REG_LOG_JOB_INI%>" colSpan="3" />
                <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.DT_PROSSIMA_ATTIVAZIONE%>" colSpan="3" />
                <sl:newLine />
                <sl:pulsantiera>
                    <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.START_REPLICA_UTENTI%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.STOP_REPLICA_UTENTI%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.ReplicaUtenti.START_ONCE_REPLICA_UTENTI%>" colSpan="2" position="right" />
                </sl:pulsantiera>
                <slf:lblField name ="<%=GestioneJobForm.ReplicaUtenti.FL_DATA_ACCURATA%>" colSpan="4"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet legend="Scadenza fatture" >
                <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.ATTIVO%>" colSpan="3" controlWidth="w20"/>
                <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.DT_REG_LOG_JOB_INI%>" colSpan="3" />
                <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.DT_PROSSIMA_ATTIVAZIONE%>" colSpan="3" />
                <sl:newLine />
                <sl:pulsantiera>
                    <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.START_SCADENZA_FATTURE%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.STOP_SCADENZA_FATTURE%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.ScadenzaFatture.START_ONCE_SCADENZA_FATTURE%>" colSpan="2" position="right" />
                </sl:pulsantiera>
                <slf:lblField name ="<%=GestioneJobForm.ScadenzaFatture.FL_DATA_ACCURATA%>" colSpan="4"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <slf:fieldSet legend="<%=GestioneJobForm.InizializzazioneLog.DESCRIPTION%>" >
                <slf:lblField name="<%=GestioneJobForm.InizializzazioneLog.ATTIVO%>" colSpan="3" controlWidth="w20"/>
                <slf:lblField name="<%=GestioneJobForm.InizializzazioneLog.DT_REG_LOG_JOB_INI%>" colSpan="3" />
                <slf:lblField name="<%=GestioneJobForm.InizializzazioneLog.DT_PROSSIMA_ATTIVAZIONE%>" colSpan="3" />
                <sl:newLine />
                <sl:pulsantiera>
                    <slf:lblField name="<%=GestioneJobForm.InizializzazioneLog.START_ONCE_INIZIALIZZAZIONE_LOG%>" colSpan="2" position="right" />
                </sl:pulsantiera>
                <slf:lblField name ="<%=GestioneJobForm.InizializzazioneLog.FL_DATA_ACCURATA%>" colSpan="4"/>
            </slf:fieldSet>
            <slf:fieldSet legend="<%=GestioneJobForm.AllineamentoLog.DESCRIPTION%>" >
                <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.ATTIVO%>" colSpan="3" controlWidth="w20"/>
                <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.DT_REG_LOG_JOB_INI%>" colSpan="3" />
                <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.DT_PROSSIMA_ATTIVAZIONE%>" colSpan="3" />
                <sl:newLine />
                <sl:pulsantiera>
                    <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.START_ALLINEAMENTO_LOG%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.STOP_ALLINEAMENTO_LOG%>" colSpan="2" />
                    <slf:lblField name="<%=GestioneJobForm.AllineamentoLog.START_ONCE_ALLINEAMENTO_LOG%>" colSpan="2" position="right" />
                </sl:pulsantiera>
                <slf:lblField name ="<%=GestioneJobForm.AllineamentoLog.FL_DATA_ACCURATA%>" colSpan="4"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
