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

<%@ page import="it.eng.saceriam.slite.gen.form.VerificaRuoliForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Verifica ruoli" >
        <script type="text/javascript">
 
            let canLoad = false;
            
            $(document).ready(function () {
                // Inizializzazione jsTree
                $('#tree').jstree({
                    core: {
                        data: function (node, callback) {
                            
                            if (!canLoad) {
                              // Non caricare nulla finché non si clicca sul pulsante
                              return callback([]);
                            }
                            /* Questo controllo sulla pienezza dei due campi ruolo l'ho dovuto mettere altrimenti
                             * sbiancando l'applicazione partiva il trigger sul server e il jstree veniva caricato 
                             * da zero chiedendo dati al server con i campi vuoti!
                             */
                            if ($("select[name='Nm_ruolo']").val()!=='' && $("select[name='Nm_ruolo_2']").val()!=='') {  
                                $.ajax({
                                    url: 'VerificaRuoli.html?operation=getAlberoJson',
                                    method: 'POST',
                                    dataType: 'json',
                                    timeout: 20000,            
                                    success: function (data) {
                                        var albero=data.map[0].array;
                                        callback(albero);
                                    },
                                    error: function (xhr, status, errorThrown) {
                                        console.error("Errore jsTree AJAX:", status, errorThrown);
                                        alert("Errore durante il caricamento dell'albero.");

                                        try {
                                          const errObj = JSON.parse(xhr.responseText);
                                          console.log("Payload errore:", errObj);
                                        } catch (e) {}
                                    }
                                });
                            }
                        },                        
                        themes: { stripes: true },

                        check_callback: false // impedisce qualsiasi modifica
                    },
                    plugins: [] // nessun plugin = sola lettura
                });
                
                // disabilita tutti gli eventuali eventi precedenti e li sostituisce con questo evento
                $("input[name='operation__calcolaDifferenze']").off("click").on("click", function() {
                    
                    if ($("select[name='Nm_ruolo']").val()==='' || $("select[name='Nm_ruolo_2']").val()==='') {
                        alert('Selezionare i due ruoli prima di avviare il calcolo delle differenze.');
                    } else {
                        canLoad=true; // abilita il caricamento dei dati da remoto
                        $('#tree').jstree(true).refresh();
                    }
                    event.preventDefault();
                });

                $("select[name='Nm_applic']").on("change", function() {
//                    $("select[name='Nm_ruolo']").val(""); // non so se serve
//                    $("select[name='Nm_ruolo_2']").val(""); // non so se serve
//                    $("select[name='Nm_ruolo']").empty(); // non so se serve
//                    $("select[name='Nm_ruolo_2']").empty(); // non so se serve
                    canLoad=false; // forza lo svuotamento dell'albero!
                    $('#tree').jstree(true).refresh();
                });
                
            });
        </script>           
    </sl:head>

    <sl:body>
        <sl:header showChangeOrganizationBtn="false"/>
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>

            <slf:messageBox />    
            <sl:contentTitle title="Verifica ruoli"/>
            <sl:newLine skipLine="true"/>
            
            <slf:section name="<%=VerificaRuoliForm.VerificaRuoliSection.NAME%>" styleClass="importantContainer w100">
                <slf:fieldSet legend="${legend}">
                    <slf:lblField name="<%=VerificaRuoliForm.FiltriRuoli.NM_APPLIC%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                    <slf:lblField name="<%=VerificaRuoliForm.FiltriRuoli.NM_RUOLO%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                    <slf:lblField name="<%=VerificaRuoliForm.FiltriRuoli.NM_RUOLO_2%>" colSpan="4" controlWidth="w40"/><sl:newLine />
                </slf:fieldSet> 
                <sl:newLine skipLine="true"/>
                <sl:pulsantiera>
                    <slf:lblField  name="<%=VerificaRuoliForm.FiltriRuoli.CALCOLA_DIFFERENZE%>" colSpan="4" />
                </sl:pulsantiera>
            </slf:section>  
            
            <sl:newLine skipLine="true"/>
            <slf:section name="<%=VerificaRuoliForm.DifferenzeRuoliSection.NAME%>" styleClass="importantContainer w100">
                <div id="tree"></div>
            </slf:section>  

        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
