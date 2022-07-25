<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneUtentiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="<%=AmministrazioneUtentiForm.FiltriRichieste.DESCRIPTION%>" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="<%=AmministrazioneUtentiForm.FiltriRichieste.DESCRIPTION%>" />

            <slf:section name="<%=AmministrazioneUtentiForm.EnteConvenzionatoSection.NAME%>" styleClass="importantContainer">
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.ID_AMBIENTE_ENTE_CONVENZ%>" colSpan="2" />
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.ID_ENTE_CONVENZ%>" colSpan="2" />
            </slf:section>

            <sl:newLine skipLine="true" />

            <slf:section name="<%=AmministrazioneUtentiForm.UtenteRichiestaRifSection.NAME%>" styleClass="importantContainer">                
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_COGNOME_USER_APP_RICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_NOME_USER_APP_RICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_USERID_APP_RICH%>" colSpan="2" controlWidth="w50"/>
            </slf:section>

            <sl:newLine skipLine="true" />

            <slf:section name="<%=AmministrazioneUtentiForm.UtenteRichiedenteSection.NAME%>" styleClass="importantContainer">                
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_COGNOME_USER_RICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_NOME_USER_RICH%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.NM_USERID_RICH%>" colSpan="2" controlWidth="w50"/>                
            </slf:section>

            <sl:newLine skipLine="true" />

            <slf:section name="<%=AmministrazioneUtentiForm.IdentificativoUdRichSection.NAME%>" styleClass="importantContainer">
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.ID_ORGANIZ_IAM%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.CD_REGISTRO_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.AA_RICH_GEST_USER%>" colSpan="2" controlWidth="w10"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.CD_KEY_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>                
            </slf:section>

            <sl:newLine skipLine="true" />

            <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.CD_RICH_GEST_USER%>" colSpan="2" controlWidth="w50"/>
            <sl:newLine />
            <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.TI_STATO_RICH_GEST_USER%>" colSpan="2" controlWidth="50"/>

            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneUtentiForm.FiltriRichieste.RICERCA_RICHIESTE%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RichiesteList.NAME%>" pageSizeRelated="true"/>
            <slf:selectList name="<%= AmministrazioneUtentiForm.RichiesteList.NAME%>" addList="true"/>
            <slf:listNavBar  name="<%= AmministrazioneUtentiForm.RichiesteList.NAME%>"/>

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
