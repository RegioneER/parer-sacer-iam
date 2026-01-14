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

<%@ page import="it.eng.saceriam.slite.gen.form.GestioneNewsForm" pageEncoding="UTF-8"%>
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
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DS_OGGETTO_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DT_INI_PUBBLIC_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
                <slf:lblField name="<%=GestioneNewsForm.FiltriNews.DT_FIN_PUBBLIC_FILTER%>" colSpan="4" controlWidth="w40"/><sl:newLine />   
            </slf:fieldSet>
            
            <sl:newLine skipLine="true"/>

            <sl:pulsantiera>
                <slf:lblField  name="<%=GestioneNewsForm.FiltriNews.RICERCA_NEWS%>" colSpan="4" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <slf:list   name="<%=GestioneNewsForm.ListaNews.NAME%>" />
            <slf:listNavBar  name="<%=GestioneNewsForm.ListaNews.NAME%>" />

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
