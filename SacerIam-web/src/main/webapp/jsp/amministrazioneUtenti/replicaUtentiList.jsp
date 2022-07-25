<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head  title="Lista repliche utenti" >
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox  />
            <sl:newLine skipLine="true"/>

            <sl:contentTitle title="LISTA REPLICHE UTENTI"/>

            <slf:fieldBarDetailTag name="<%= AmministrazioneUtentiForm.FiltriReplica.NAME%>" hideBackButton="${!((fn:length(sessionScope['###_NAVHIS_CONTAINER'])) gt 1 )}" hideDeleteButton="true" hideUpdateButton="true"/>

            <sl:newLine skipLine="true"/>

            <slf:fieldSet  borderHidden="false">
                <!-- piazzo i campi del filtro di ricerca -->
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.NM_USERID%>" colSpan="2" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.DT_LOG_USER_DA_REPLIC%>" colSpan="1" controlWidth="w70"/>
                <slf:doubleLblField name="<%=AmministrazioneUtentiForm.FiltriReplica.ORE_DT_LOG_USER_DA_REPLIC%>" name2="<%=AmministrazioneUtentiForm.FiltriReplica.MINUTI_DT_LOG_USER_DA_REPLIC%>" controlWidth="w20" controlWidth2="w20" labelWidth="w5" colSpan="1" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.NM_APPLIC%>" colSpan="2" controlWidth="w70"  />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.TI_OPER_REPLIC%>" colSpan="2" controlWidth="w70"  />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.TI_STATO_REPLIC%>" colSpan="2" controlWidth="w70"  />
                <sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true" />

            <sl:pulsantiera>
                <!-- piazzo il bottone -->
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.RICERCA_REPLICHE%>" width="w25" />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriReplica.PULISCI_REPLICHE%>" width="w25" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <!--  piazzo la lista con i risultati -->
            <slf:list   name="<%= AmministrazioneUtentiForm.ReplicaUtentiList.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.ReplicaUtentiList.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>