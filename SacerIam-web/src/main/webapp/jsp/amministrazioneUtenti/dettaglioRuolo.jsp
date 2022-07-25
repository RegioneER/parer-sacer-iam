<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneRuoliForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Dettaglio ruolo" >
        <script type="text/javascript">
            function initIdApplicTrigger(idTreeParam) {
                // Funzione comune di inizializzazione del bottone di scelta applicazione per l'albero visualizzato
                var tree = $("#" + idTreeParam);
                var idApplic = $("#Nm_applic_tree");

                idApplic.on("change", function () {
                	// NOTA BENE : jstree 3.3.8 l'API get_checked cambia il comportamento, retituisce tutti i nodi checked in due modalità
                	// o array di IDs oppure di oggetti
                 	var checkedNodes = tree.jstree('get_checked');
                    var jsonArr = [];
                    $.each(checkedNodes, function (property, value) {
                        jsonArr.push(value);
                    });
                    $.post("AmministrazioneRuoli.html", {
                        operation: "triggerNm_applic_treeOnTriggerJS",
                        Nm_applic_tree: $(this).val(),
                        Id_tree: idTreeParam,
                        Checked_nodes: jsonArr
                    }).done(function () {
                        tree.removeClass("displayNone");
                        //tree.jstree('unlock');
                        // enable tree
                        treeunlock(idTreeParam);
                        tree.attr("refreshing", true);
                        $.jstree.reference("#" + idTreeParam).refresh(-1);
                    });
                });
            }

            function populateTree(idRuolo, nmApplic, operation, idTree) {
                if (nmApplic) {
                	// show tree
                	$("#" + idTree).show();
                    $.getJSON("AmministrazioneRuoli.html", {operation: operation,
                        Id_ruolo: idRuolo,
                        Nm_applic_tree: nmApplic
                    }).done(function (data) {
                        checkData(data, idTree);
                    });
                }  else {
                	// hiding
                	$("#" + idTree).hide();
                }
            }

            function checkData(data, idTree) {
                var ids;
                var readOnlyIds;
                var dichAutors;
                var mixedTypes;
                var pageIds;
                var pageDichAutors;
                var pageMixedTypes;
                var tree = $("#" + idTree);
                var type = data.map[0].type ? data.map[0].type : '';
                var totalMenuNodes = data.map[0].totalMenuNodes ? data.map[0].totalMenuNodes : '';
                var mixed = data.map[0].mixedType ? data.map[0].mixedType : '';
                var pageMixed = data.map[0].pageMixedType ? data.map[0].pageMixedType : '';
                $.each(data.map[0], function (property, value) {
                    if (property === 'id') {
                        ids = value;
                    } else if (property === 'id_dich_autor') {
                        dichAutors = value;
                    } else if (property === 'mixedType') {
                        mixedTypes = value;
                    } else if (property === 'readOnlyIds') {
                        readOnlyIds = value;
                    } else if (property === 'pageIds') {
                        pageIds = value;
                    } else if (property === 'pageIdDichAutor') {
                        pageDichAutors = value;
                    } else if (property === 'pageMixedType') {
                        pageMixedTypes = value;
                    }
                });
                
                tree.jstree('unselect_all');
                // NOTA BENE : jstree 3.3.8 cambia la modalita' di check dei nodi, è necessario PRIMA 
                // aprire l'intero albero e POI eseguire la fase di check
                tree.jstree('open_all');
                if (readOnlyIds && JSON.stringify(readOnlyIds) !== JSON.stringify(ids)) {
                    for (var index in readOnlyIds) {
                        var noType;
                        var id = readOnlyIds[index] + "";
                        if (id.lastIndexOf('M', 0) !== 0
                                && id.lastIndexOf('P', 0) !== 0
                                && id.lastIndexOf('A', 0) !== 0) {
                            id = 'M' + readOnlyIds[index];
                            noType = true;
                        }
                        
                        var node = tree.jstree('get_node', $('li#' + id));
                        var nodePath = tree.jstree('get_path', node, '', true);
                        if (noType) {
                            if (nodePath.length === 1) {
                                // Se sto prendendo i nodi dal DB e è un nodo di primo livello, 
                                // devo fare check di tutti i nodi MENU figli ( ALL_ABILITAZIONI)
                                checkMenu(idTree, readOnlyIds);
                            } else if (nodePath.length === 2) {
                                // Se sto prendendo i nodi dal DB e è un nodo di secondo livello, 
                                // devo fare check di tutti i nodi MENU figli ( ALL_ABILITAZIONI_CHILD)
                                var children = $.jstree.reference(idTree).get_children_dom($('li#' + id));
                                if (children.length > 0) {
                                    $.each(children, function (property, value) {
                                        if (value.id.lastIndexOf('M', 0) === 0) {
                                        	// dalla >3.3.8 lo stato dei nodi è governato dalla tie_selection (in questo caso false) 
                                           	// vedi API https://www.jstree.com/api/#/?f=$.jstree.defaults.checkbox.tie_selection
                                           	// la selezione/deselezione è lo stato ultimo pilotando con l'api check_node
                                           	// quindi si deve testare lo stato "ultimo" del nodo ossia is_selected          
                                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                                            if (!is_node_checked(idTree, value.id)) {                                                
                                                tree.jstree('check_node', $(this));
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        //if (!tree.jstree('is_checked', $('li#' + id))) {
                        if (!is_node_checked(idTree, id)) {                      
                            tree.jstree('check_node', $('li#' + id));
                        }
                        if (id.lastIndexOf('M', 0) === 0) {
                        	var node = tree.jstree('get_node', $('li#' + id));
                            var nodePath = tree.jstree('get_path', node, '', true);
                            nodePath.pop();
                            $.each(nodePath.reverse(), function (property, value) {
                                var parent = $('li#' + value);
                                //if (!tree.jstree('is_checked', parent)) {
                                if (!is_node_checked(idTree, value)) {    
                                    tree.jstree('check_node', parent);
                                }
                            });
                        }
                    }
                }
                for (var index in ids) {
                    var id = ids[index] + "";
                    if (id.lastIndexOf('M', 0) !== 0
                            && id.lastIndexOf('P', 0) !== 0
                            && id.lastIndexOf('A', 0) !== 0) {
                        // Se ha già il suffisso del nodo va bene, altrimenti
                        if (mixed) {
                            // => Caso ALL_ABILITAZIONI o ALL_ABILITAZIONI_CHILD
                            var nodeType = mixedTypes[index];
                            id = nodeType + ids[index];
                        } else {
                            id = type + ids[index];
                        }
                    }

                    if (dichAutors && dichAutors[index] !== null) {
                        $('li#' + id).attr("id_dich_autor", dichAutors[index]);
                        $('li#' + id).data.id_dich_autor=dichAutors[index];
                    }
                    //if (!tree.jstree('is_checked', $('li#' + id))) {
                    if (!is_node_checked(idTree, id)) {
                        tree.jstree('check_node', $('li#' + id));
                        if (type === 'A') {
                            // Se si tratta dell'albero delle azioni e l'id del nodo è un entry menu, può essere solo il caso ALL_ABILITAZIONI
                            if (id.lastIndexOf("M", 0) === 0) {
                                checkAll(idTree);
                            } else if (id.lastIndexOf("P", 0) === 0 && mixed) {
                                var children = $.jstree.reference(idTree).get_children_dom($('li#' + id));
                                $.each(children, function (property, value) {
                                    tree.jstree('check_node', $(this));
                                });
                            }
                        }
                    }
                }
                if (pageIds) {
                    for (var index in pageIds) {
                        var id = pageIds[index] + "";
                        if (id.lastIndexOf('M', 0) !== 0
                                && id.lastIndexOf('P', 0) !== 0
                                && id.lastIndexOf('A', 0) !== 0) {
                            // Se ha già il suffisso del nodo va bene, altrimenti
                            if (pageMixed) {
                                // => Caso ALL_ABILITAZIONI o ALL_ABILITAZIONI_CHILD
                                var nodeType = pageMixedTypes[index];
                                id = nodeType + pageIds[index];
                            } else {
                                id = 'P' + pageIds[index];
                            }
                        }

                        if (pageDichAutors && pageDichAutors[index] !== null) {
                            $('li#' + id).attr("page_dich_autor", pageDichAutors[index]);
                            $('li#' + id).data.page_dich_autor=pageDichAutors[index];
                        }
                        // Non verifico il check appositamente, in modo che vada a chiamare la selectPageNode
                        check_node_force_event(idTree,id);
                        if (type === 'A') {
                            // Se si tratta dell'albero delle azioni e l'id del nodo è un entry menu, può essere solo il caso ALL_ABILITAZIONI
                            if (id.lastIndexOf("M", 0) === 0) {
                                if (((totalMenuNodes && totalMenuNodes === readOnlyIds.length) || (JSON.stringify(readOnlyIds) === JSON.stringify(pageIds)))
                                        && JSON.stringify(pageIds) === JSON.stringify(ids)) {
                                    // ALL_ABILITAZIONI MENU, PAGINE e AZIONI
                                    checkAll(idTree);
                                } else if (JSON.stringify(readOnlyIds) === JSON.stringify(pageIds)) {
                                    // ALL ABILITAZIONI MENU E PAGINE
                                    checkMenus(idTree, id);
                                }
                            }
                        }
                    }
                }
                // OBSOLETO dalla >3.3.8 tree.jstree('open_all');
                tree.removeAttr("refreshing");
                if (ids) {
                	// OBSOLETO dalla >3.3.8 unlock non esiste 
                	// create apposite function di lock/unlock
                   // tree.jstree('lock');
                   // tree read only
                   treelock(idTree);
                }
            }

            function treelock(idTree) {
                var tree = $("#" + idTree);
                // disable visible nodes
                $("#" + idTree+' li.jstree-node').each(function() {
             	   tree.jstree("disable_node", this.id);
             	   // remove jstree-disabled style
             	   $(this.childNodes).each(function() {
             		   if(this.className.indexOf("jstree-disabled") >= 0) {
        					$('#' + this.id).removeClass("jstree-disabled");
                 	   }
                 	});
             	});
             	  // block open new nodes
                $("#" + idTree+' i.jstree-ocl')
             	 .off('click.block')
             	 .on('click.block', function() {
             	   return false;
                 });
                // dbclik event blocked
             	tree.jstree().settings.core.dblclick_toggle = false;
                // dbclik event blocked
                tree.jstree().settings.core.check_callback = false;
             } 

            function treeunlock(idTree) {
            	 var tree = $("#" + idTree);
                 // disable visible nodes
                 $("#" + idTree+' li.jstree-node').each(function() {
              	   tree.jstree("enable_node", this.id);
              	});
              	 // allow open new nodes (serve?!)
                $("#" + idTree+' i.jstree-ocl')
             	 .on('click.block', function() {
             	   return true;
                 });
                // dbclik event unblocked
              	tree.jstree().settings.core.dblclick_toggle = true;
                // call bacl unblocked
              	tree.jstree().settings.core.check_callback = true;
            }

            
            function checkMenus(idTree, idNodo) {
                var tree = $("#" + idTree);
                var children = $.jstree.reference(idTree).get_children_dom($('li#' + idNodo));
                if (children.length > 0) {
                    $.each(children, function (property, value) {
                        if (value.id.lastIndexOf('M', 0) === 0) {
                            checkMenus(idTree, value.id);
                        } else if (value.id.lastIndexOf('P', 0) === 0) {
                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                            if (!is_node_checked(idTree, value.id)) {
                                tree.jstree('check_node', $(this));
                            }
                        }
                    });
                }
            }

            // Funzione di selezione dei menu nell'albero delle azioni (in quanto readonly)
            function checkMenu(idTree, ids) {
                var tree = $("#" + idTree);
                var noType = false;
                for (var index in ids) {
                    var id = ids[index] + "";
                    if (id.lastIndexOf('M', 0) !== 0
                            && id.lastIndexOf('P', 0) !== 0
                            && id.lastIndexOf('A', 0) !== 0) {
                        id = 'M' + ids[index];
                        noType = true;
                    }
                    //if (!tree.jstree('is_checked', $('li#' + id))) {
                    if (!is_node_checked(idTree, id)) {
                        tree.jstree('check_node', $('li#' + id));
                        var node = tree.jstree('get_node', $('li#' + id));
                        var nodePath = tree.jstree('get_path', node, '', true);
                        for (var i = 0; i < nodePath.length; i++) {
                            //if (!tree.jstree('is_checked', $('li#' + nodePath[i]))) {
                            if (!is_node_checked(idTree, nodePath[i])) {
                                tree.jstree('check_node', $('li#' + nodePath[i]));
                            }
                        }
                        if (noType) {
                            if (nodePath.length < 3) {
                                // Se sto prendendo i nodi dal DB e è un nodo di primo o secondo livello, 
                                // devo fare check di tutti i nodi MENU figli ( ALL_ABILITAZIONI / ALL_ABILITAZIONI_CHILD)
                                checkMenuRecursive(idTree, id);
                            }
                        }
                    }
                }
            }

            function checkMenuRecursive(idTree, id) {
                var tree = $("#" + idTree);
                var children = $.jstree.reference(idTree).get_children_dom($('li#' + id));
                if (children.length > 0) {
                    $.each(children, function (property, value) {
                        if (value.id.lastIndexOf('M', 0) === 0) {
                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                            if (!is_node_checked(idTree, value.id)) {
                                tree.jstree('check_node', $(this));
                            }
                            checkMenuRecursive(idTree, value.id);
                        }
                    });
                }
            }

            function checkPageChildren(idTree, idNodo) {
                var tree = $("#" + idTree);
                var children = $.jstree.reference(idTree).get_children_dom($('li#' + idNodo));
                if (children.length > 0 && children[0].id.lastIndexOf('P', 0) === 0) {
                    $.each(children, function (property, value) {
                        if (value.id.lastIndexOf('P', 0) === 0) {
                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                            if (!is_node_checked(idTree, value.id)) {
                                tree.jstree('check_node', $(this));
                            }
                        }
                    });
                }
            }

            function checkAll(idTree) {
                var tree = $("#" + idTree);
                tree.jstree('check_all');
            }

            /*
			 dalla >3.3.8 cambia la modalità con cui verificare lo stato di "selezionato" 
			*/
           function is_node_checked(id_tree, id_node) {
			    var tree = $.jstree.reference(id_tree);
			    // get children
			    var node = tree.get_node($("li#" + id_node));
			 	return tree.is_checked(node);
			}
            /* dalla >3.3.8 l'API "check_node" scatena l'evento "check_node.jstree" solo se 
            il nodo non era già stato selezionato.
            Questa funzione riporta lo stato "checked" a false prima del check_node così
            che si scateni anche l'evento "check_node.jstree"
            Intervenire direttamente sullo state del nodo permette di deselezionarlo senza 
            scatenare eventi di uncheck*/

            function check_node_force_event(idTree,idNode){
                var tree = $.jstree.reference(idTree);
                var node = $('li#' + idNode);
                tree.get_node(node).state.checked=false;
                tree.check_node(node);
            }
        </script>
    </sl:head>
    <sl:body>
        <sl:header showChangeOrganizationBtn="false" />
        <sl:menu showChangePasswordBtn="true" />
        <sl:content>
            <slf:messageBox />
            <sl:newLine skipLine="true"/>

            <sl:contentTitle title="DETTAGLIO RUOLO"/>

            <!-- rimpiazzo la barra di scorrimento record -->
            <c:if test="${sessionScope['###_FORM_CONTAINER']['listaRuoli'].table['empty']}">
                <slf:fieldBarDetailTag name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NAME%>" /> 
            </c:if>
            <c:if test="${!(sessionScope['###_FORM_CONTAINER']['listaRuoli'].table['empty']) }">
                <slf:listNavBarDetail name="<%= AmministrazioneRuoliForm.ListaRuoli.NAME%>" /> 
            </c:if>
            <sl:newLine skipLine="true" />
            <slf:fieldSet borderHidden="false">
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.ID_RUOLO%>" colSpan="1" controlWidth="w20"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w70"/>
               <%-- <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_CATEG_RUOLO%>" width="w100" labelWidth="w20" controlWidth="w70"/>--%>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_STATO_RICH_ALLINEA_RUOLI_1%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_ESITO_RICH_ALLINEA_RUOLI_1%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_STATO_RICH_ALLINEA_RUOLI_2%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_ESITO_RICH_ALLINEA_RUOLI_2%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.FL_ALLINEAMENTO_IN_CORSO%>" width="w100" labelWidth="w20" controlWidth="w70"/>
                <sl:newLine />
                <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_MSG_ALLINEAMENTO_PARZ%>" width="w100" labelWidth="w20" controlWidth="w70"/>
            </slf:fieldSet>
            <sl:newLine skipLine="true"/>
            <sl:pulsantiera>
                <slf:lblField name="<%=AmministrazioneRuoliForm.DettaglioRuolo.ALLINEA_RUOLO1%>" width="w20" />
                <slf:lblField name="<%=AmministrazioneRuoliForm.DettaglioRuolo.ALLINEA_RUOLO2%>" width="w20" />
                <slf:lblField name="<%=AmministrazioneRuoliForm.DettaglioRuolo.LOG_EVENTI%>" width="w20"/>
            </sl:pulsantiera>
            <sl:newLine skipLine="true"/>

            <%-- Inserisco i tab contenenti le varie liste di dichiarazioni --%>
             <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaCategorie">
             <slf:container width="w50">
                <slf:list name="<%= AmministrazioneRuoliForm.CategorieList.NAME%>" />
                <slf:listNavBar name="<%= AmministrazioneRuoliForm.CategorieList.NAME%>" />
             </slf:container>
            </slf:tab>
            <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaApplicazioni">
                <slf:list name="<%= AmministrazioneRuoliForm.ApplicazioniList.NAME%>" />
                <slf:listNavBar name="<%= AmministrazioneRuoliForm.ApplicazioniList.NAME%>" />
            </slf:tab>
            <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaEntryMenusDich">
                <sl:pulsantiera>
                    <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.SHOW_LIST%>" /> 
                </sl:pulsantiera>
                <c:choose>
                    <c:when test="${!(sessionScope['###_FORM_CONTAINER']['dettaglioRuolo']['show_tree'].value) }">
                        <slf:list name="<%= AmministrazioneRuoliForm.DichMenuList.NAME%>" />
                        <slf:listNavBar name="<%= AmministrazioneRuoliForm.DichMenuList.NAME%>" />
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_APPLIC_TREE%>" colSpan="4" /><sl:newLine />
                        <slf:tree name="<%= AmministrazioneRuoliForm.WizardEntryMenuTree.NAME%>" 
                        additionalJsonParams="\"core\" : { \"data\" : { \"url\": \"AmministrazioneRuoli.html?operation=createEntryMenuTree\"} }"
                        additionalPluginParams="\"checkbox\" : { \"tie_selection\" : false, \"whole_node\" : false}"/>
                        <script type="text/javascript">
                            $(function () {
                                var idTree = "tree_WizardEntryMenuTree";
                                var jsIdTree = "#" + idTree;
                                initIdApplicTrigger(idTree);
                                var tree = $(jsIdTree);
                                // evento refresh, eseguito dopo aver richiamato il metodo di refresh alla modifica del campo idApplicazione
                                // esegue la chiamata per ricaricare i nodi selezionati
                                tree.on('refresh.jstree', function (event, data) {
                                    //$.jstree.reference(jsIdTree).loaded();
                                    var current = $.jstree.reference(jsIdTree);
                                    current.trigger('loaded.jstree', data);
                                });

                                // evento 'jstree caricato', una volta inseriti tutti i nodi va a chiamare il metodo per selezionare i nodi
                                // dal database (o da una precedente modifica)
                                tree.on('loaded.jstree', function () {
                                    var nmApplic = $("#Nm_applic_tree").val();
                                    var idRuolo = $("#Id_ruolo_hidden").val();
                                    populateTree(idRuolo, nmApplic, "populateEntryMenuAbilitatiOnTriggerJs", idTree);
                                });

                                if ($("#Nm_applic_tree").val()) {
                                    //tree.jstree('unlock');
                                    // enable tree
                                    treeunlock(idTree);
                                    tree.removeClass("displayNone");
                                    tree.attr("refreshing", true);
                                }
                            });
                        </script>
                    </c:otherwise>
                </c:choose>
            </slf:tab>
            <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaPagesDich">
                <slf:list name="<%= AmministrazioneRuoliForm.DichPagineList.NAME%>" />
                <slf:listNavBar name="<%= AmministrazioneRuoliForm.DichPagineList.NAME%>" />
            </slf:tab>
            <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaActionsDich">
                <sl:pulsantiera>
                    <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.SHOW_LIST%>" /> 
                </sl:pulsantiera>
                <c:choose>
                    <c:when test="${!(sessionScope['###_FORM_CONTAINER']['dettaglioRuolo']['show_tree'].value) }">
                        <slf:list name="<%= AmministrazioneRuoliForm.DichAzioniList.NAME%>" />
                        <slf:listNavBar name="<%= AmministrazioneRuoliForm.DichAzioniList.NAME%>" />
                    </c:when>
                    <c:otherwise>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_APPLIC_TREE%>" colSpan="4" /><sl:newLine />
                        <slf:tree name="<%= AmministrazioneRuoliForm.WizardAzioniTree.NAME%>" 
                        additionalJsonParams="\"core\" : { \"data\" : { \"url\": \"AmministrazioneRuoli.html?operation=createAzioniTree\"} }"
                        additionalPluginParams="\"checkbox\" : { \"tie_selection\" : false, \"whole_node\" : false, \"three_state\" : false }" />
                        <!--, \"checkbox\" : {\"two_state\":true}-->
                        <script type="text/javascript">
                            $(function () {
                                var idTree = "tree_WizardAzioniTree";
                                var jsIdTree = "#" + idTree;
                                initIdApplicTrigger(idTree);
                                var tree = $(jsIdTree);

                                $.getJSON("AmministrazioneRuoli.html", {operation: "createPagineTree"});
                                // evento refresh, eseguito dopo aver richiamato il metodo di refresh alla modifica del campo idApplicazione
                                // esegue la chiamata per ricaricare i nodi selezionati
                                tree.on('refresh.jstree', function (event, data) {
                                	// OBSOLETO dalla >3.3.8 loaded() non esiste 
                                	// si può gestire l'evento 'loaded.jstree' (vedi sotto)
                                	// scatenato con un trigger manuale
                                	//$.jstree.reference(jsIdTree).loaded();
                                    var current = $.jstree.reference(jsIdTree);
                                    current.trigger('loaded.jstree', data);
                                });

                                // evento 'jstree caricato', una volta inseriti tutti i nodi va a chiamare il metodo per selezionare i nodi
                                // dal database (o da una precedente modifica)
                                tree.on('loaded.jstree', function () {
                                    var nmApplic = $("#Nm_applic_tree").val();
                                    var idRuolo = $("#Id_ruolo_hidden").val();
                                    populateTree(idRuolo, nmApplic, "populateAzioniAbilitateOnTriggerJs", idTree);
                                });

                                if ($("#Nm_applic_tree").val()) {
                                	// OBSOLETO dalla >3.3.8 unlock non esiste 
                                	// create apposite function di lock/unlock
                                    //tree.jstree('unlock');
                                    // unlock tree
                                    treeunlock(idTree);
                                    tree.removeClass("displayNone");
                                    tree.attr("refreshing", true);
                                }
                            });
                        </script>
                    </c:otherwise>
                </c:choose>
            </slf:tab>
            <slf:tab name="<%= AmministrazioneRuoliForm.DichiarazioniListsTabs.NAME%>" tabElement="ListaServicesDich">
                <slf:list name="<%= AmministrazioneRuoliForm.DichServiziList.NAME%>" />
                <slf:listNavBar name="<%= AmministrazioneRuoliForm.DichServiziList.NAME%>" />
            </slf:tab>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
