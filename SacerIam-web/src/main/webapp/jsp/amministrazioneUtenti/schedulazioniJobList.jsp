<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head  title="Monitoraggio - Lista Schedulazioni Job" >

    </sl:head>
    <sl:body>
        <sl:header changeOrganizationBtnDescription="Cambia versatore"/>
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox  />

            <sl:contentTitle title="Lista Schedulazioni Job"/>
                <slf:fieldBarDetailTag name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.NAME%>" hideBackButton="true"/> 
            <slf:fieldSet  borderHidden="false">
                <!-- piazzo i campi del filtro di ricerca -->
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.NM_JOB%>" colSpan="4"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.DT_REG_LOG_JOB_DA%>" colSpan="1" controlWidth="w70" />                
                <slf:doubleLblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.ORE_DT_REG_LOG_JOB_DA%>" name2="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.MINUTI_DT_REG_LOG_JOB_DA%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.DT_REG_LOG_JOB_A%>" colSpan="1" controlWidth="w70"  />
                <slf:doubleLblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.ORE_DT_REG_LOG_JOB_A%>" name2="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.MINUTI_DT_REG_LOG_JOB_A%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <sl:newLine />                
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.RICERCA_JOB_SCHEDULATI%>" width="w25" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.PULISCI_JOB_SCHEDULATI%>" width="w25" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriJobSchedulati.GESTIONE_JOB_PAGE%>" width="w25" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true" />
            <slf:fieldSet  borderHidden="false">
                <!-- piazzo i campi del risultato -->
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.ATTIVO%>" width="w20" labelWidth="w70" controlWidth="w30"/>
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.DT_REG_LOG_JOB_INI%>" width="w20" labelWidth="w10" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.StatoJob.DT_PROSSIMA_ATTIVAZIONE%>" labelWidth="w40"  width = "w50" />
                <sl:newLine />
                <sl:pulsantiera>                    
                    <slf:lblField name="<%=AmministrazioneUtentiForm.InformazioniJob.START_JOB_SCHEDULATI%>" width="w25" />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.InformazioniJob.STOP_JOB_SCHEDULATI%>" width="w25" />
                    <slf:lblField name="<%=AmministrazioneUtentiForm.InformazioniJob.ESECUZIONE_SINGOLA_JOB_SCHEDULATI%>" width="w25" />
                </sl:pulsantiera>
            </slf:fieldSet>
            <sl:newLine skipLine="true" />

            <!--  piazzo la lista con i risultati -->
            <slf:listNavBar name="<%= AmministrazioneUtentiForm.JobSchedulatiList.NAME%>" pageSizeRelated="true"/>
            <slf:list   name="<%= AmministrazioneUtentiForm.JobSchedulatiList.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.JobSchedulatiList.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>