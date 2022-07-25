<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head  title="Lista schedulazioni replica utenti" >
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox  />
            <sl:newLine skipLine="true"/>

            <sl:contentTitle title="LISTA SCHEDULAZIONI REPLICA UTENTI"/>
            <sl:newLine skipLine="true"/>

            <slf:fieldSet  borderHidden="false">
                <!-- piazzo i campi del filtro di ricerca -->
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.DT_REG_LOG_JOB_DA%>" colSpan="1" controlWidth="w70" />                
                <slf:doubleLblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.ORE_DT_REG_LOG_JOB_DA%>" name2="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.MINUTI_DT_REG_LOG_JOB_DA%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.DT_REG_LOG_JOB_A%>" colSpan="1" controlWidth="w70"  />
                <slf:doubleLblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.ORE_DT_REG_LOG_JOB_A%>" name2="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.MINUTI_DT_REG_LOG_JOB_A%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.REPLICHE_BLOCCATE%>" width="w20" controlWidth="w10" labelWidth="w80" />
                <sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />

            <sl:pulsantiera>
                <!-- piazzo il bottone -->
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.RICERCA_SCHEDULAZIONI%>" width="w25" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.PULISCI_SCHEDULAZIONI%>" width="w25" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriSchedulazioni.VISUALIZZA_REPLICHE_UTENTI_SCHED%>" width="w25" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:fieldSet  borderHidden="false">
                <!-- piazzo i campi del risultato -->
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.ATTIVO%>" width="w20" labelWidth="w70" controlWidth="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.DT_REG_LOG_JOB_INI%>" width="w20" labelWidth="w10" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.DT_PROSSIMA_ATTIVAZIONE%>" labelWidth="w40"  width = "w50" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.PRESENZA_ERRORI_REPLICA%>" labelWidth="w40"  width = "w50" />
                <sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />

            <!--  piazzo la lista con i risultati -->
            <slf:list   name="<%= AmministrazioneUtentiForm.SchedulazioniReplicaUtentiList.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.SchedulazioniReplicaUtentiList.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>