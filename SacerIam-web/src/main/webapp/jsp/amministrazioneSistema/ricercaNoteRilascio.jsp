<%@ page import="it.eng.saceriam.slite.gen.form.GestioneNoteRilascioForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Ricerca News" />

    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />

        <sl:content>
            
           <slf:messageBox />
            <sl:newLine skipLine="true"/>
            
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.NM_APPLIC%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.CD_VERSIONE%>" colSpan="4" controlWidth="w40"/><sl:newLine />  
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.DT_VERSIONE_DA%>" colSpan="2" controlWidth="w40"/>  
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.DT_VERSIONE_A%>" colSpan="2" controlWidth="w40"/><sl:newLine />  
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.BL_NOTA%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                <slf:lblField name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.DS_EVIDENZA%>" colSpan="4" controlWidth="w40"/><sl:newLine />
            </slf:fieldSet>
            
            <sl:newLine skipLine="true"/>

            <sl:pulsantiera>
                <slf:lblField  name="<%=GestioneNoteRilascioForm.FiltriNoteRilascio.RICERCA_NOTE_RILASCIO%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:list   name="<%=GestioneNoteRilascioForm.ListaNoteRilascio.NAME%>" />
            <slf:listNavBar  name="<%=GestioneNoteRilascioForm.ListaNoteRilascio.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
