<%--
 Engineering Ingegneria Informatica S.p.A.

 Copyright (C) 2023 Regione Emilia-Romagna
 <p/>
 This program is free software: you can redistribute it and/or modify it under the terms of
 the GNU Affero General Public License as published by the Free Software Foundation,
 either version 3 of the License, or (at your option) any later version.
 <p/>
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License for more details.
 <p/>
 You should have received a copy of the GNU Affero General Public License along with this program.
 If not, see <https://www.gnu.org/licenses/>.
 --%>

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
