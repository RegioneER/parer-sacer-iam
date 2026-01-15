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

<%@ page import="it.eng.saceriam.slite.gen.form.GestioneHelpOnLineForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Gestione help online" >
        <script type='text/javascript'>
            $(document).ready(function () {
                
                gestHelpOnLine_onLoad( $('#Tipo_help') );
                
                /*
                 * Applicazione eventi di cambio valore sui ComboBox
                 */
                $('#Tipo_help').change(function () {
                    gestHelpOnLine_onLoad( $(this) );
                });
                
                $('#Nm_applic').change(function () {
                    $('#Nm_menu').val(""); 
                    $('#Pagina').val(""); 
                    //$('#Pagina').attr("disabled", true); 
                    //$('#Nm_menu').attr("disabled", true); 
                });
                
            });
            
            function gestHelpOnLine_onLoad(componenteTipoHelp) {
                var valore=componenteTipoHelp.val();
                if (valore=='HELP_PAGINA') {
                    $('#Pagina').attr("disabled", false); 
                    $('#Nm_menu').val(""); 
                    $('#Nm_menu').attr("disabled", true); 
                } else if (valore=='HELP_RICERCA_DIPS'){
//                    $('#Pagina').val(""); 
                    $('#Pagina').attr("disabled", false); 
                    $('#Nm_menu').attr("disabled", false); 
                } else {
                    $('#Pagina').val(""); 
                    $('#Nm_menu').val(""); 
                    $('#Pagina').attr("disabled", true); 
                    $('#Nm_menu').attr("disabled", true); 
                }
            }
            
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>
            <sl:contentTitle title="GESTIONE HELP"/>
            <slf:fieldSet legend="Ricerca help" >
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.TIPO_HELP%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.NM_APPLIC%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />                
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.PAGINA%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.NM_MENU%>" colSpan="4" controlWidth="w40"/>
                <sl:newLine />
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.DT_RIFERIMENTO%>" colSpan="4" controlWidth="w20"/>
                <sl:newLine />
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.RICERCA_HELP_ON_LINE%>" colSpan="2" />
                <slf:lblField name="<%=GestioneHelpOnLineForm.FiltriHelpOnLine.PULISCI%>" colSpan="2" />
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>
            <!--  piazzo la lista con i risultati -->
            <slf:list name="<%=GestioneHelpOnLineForm.ListaHelpOnLine.NAME%>" />
            <slf:listNavBar  name="<%=GestioneHelpOnLineForm.ListaHelpOnLine.NAME%>" />
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
