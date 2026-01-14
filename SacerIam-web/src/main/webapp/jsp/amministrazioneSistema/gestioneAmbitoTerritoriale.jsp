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

<%@ page import="it.eng.saceriam.slite.gen.form.GestioneAmbitoTerritorialeForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>



<sl:html>
    <sl:head title="Gestione Ambito Territoriale" >
        <script type="text/javascript" src="<c:url value="/js/customGestAmbMessageBox.js"/>" ></script>
       
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
      
        <sl:content>
            <sl:contentTitle title="Gestione Ambito Territoriale"/>
            <slf:messageBox />
            <c:if test="${!empty requestScope.confRemove}">
                <div class="messages confRemove ">
                    <ul>
                        <li class="message warning ">La procedura cancellerà il nodo selezionato. Procedere?</li>
                    </ul>
                </div>
            </c:if>
            
            <slf:fieldSet>
                <slf:lblField name="<%=GestioneAmbitoTerritorialeForm.AmbitoTerritoriale.CD_AMBITO_TERRIT%>" colSpan="2" /> 
                <slf:lblField name="<%=GestioneAmbitoTerritorialeForm.AmbitoTerritoriale.SALVA_AMBITO_TERRITORIALE %>" colSpan="2" /> 
                <sl:newLine />  
                <slf:lblField name="<%=GestioneAmbitoTerritorialeForm.AmbitoTerritoriale.TI_AMBITO_TERRIT%>" colSpan="4" /> <sl:newLine />  
                <slf:lblField name="<%=GestioneAmbitoTerritorialeForm.AmbitoTerritoriale.ID_AMBITO_TERRIT_PADRE%>" colSpan="4" /> <sl:newLine />   
                
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <slf:tree name="<%=GestioneAmbitoTerritorialeForm.GestAmbTree.NAME%>"  additionalJsonParams="\"core\" : { \"open_parents\" : true, \"load_open\" : true }"/>
           <script type="text/javascript" src="<c:url value="/js/custom/customGestAmbTree.js" />" ></script>
            <sl:newLine skipLine="true"/>
      

        </sl:content>
        
        <sl:footer />
    </sl:body>

</sl:html>

