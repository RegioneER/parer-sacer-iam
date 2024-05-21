<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneSistemiVersantiForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Gestione Sistemi versanti" />
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="GESTIONE SISTEMI VERSANTI" />
            <slf:fieldSet borderHidden="false">                
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.NM_SISTEMA_VERSANTE%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.DS_SISTEMA_VERSANTE%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.NM_PRODUTTORE%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.CD_VERSIONE%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.ID_ORGANIZ_APPLIC%>" colSpan="2" controlWidth="w50"/>
                 <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.FL_CESSATO_FILTRO%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.FL_INTEGRAZIONE_FILTRO%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.FL_ASSOCIA_PERSONA_FISICA_FILTRO%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.ARCHIVISTA%>" colSpan="2" controlWidth="w50"/>
                <sl:newLine />
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.NO_ARCHIVISTA%>" colSpan="2" controlWidth="w50"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true" />
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneSistemiVersantiForm.FiltriSistemiVersanti.RICERCA_SISTEMI_VERSANTI%>" width="w25"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:list name="<%= AmministrazioneSistemiVersantiForm.ListaSistemiVersanti.NAME%>" />
            <slf:listNavBar  name="<%= AmministrazioneSistemiVersantiForm.ListaSistemiVersanti.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
