<%@ page import="it.eng.saceriam.slite.gen.form.AmministrazioneRuoliForm" pageEncoding="UTF-8"%>
<%@ include file="../../include.jsp"%>

<sl:html>
    <sl:head title="Creazione ruolo">
        <script type="text/javascript">
            function initIdApplicTrigger(idTreeParam) {
                // Funzione comune di inizializzazione del bottone di scelta applicazione per l'albero visualizzato
                var tree = $("#" + idTreeParam);
                var idApplic = $("#Nm_applic_tree");
                var type;
                if (idTreeParam === "tree_WizardAzioniTree") {
                    type = 'A';
                } else if (idTreeParam === "tree_WizardEntryMenuTree") {
                    type = 'M';
                }

                idApplic.on("change", function () {
                    var checkedNodes = null;
                 	// NOTA BENE : jstree 3.3.8 introduce l'API get_top_checked, get_checked restituisce sempre e solo TUTTI i nodi "checked" 
                 	// si differenzia per otterne un diverso comportamento tra tree menu e azioni
                    if (type === 'M') {
                        checkedNodes = tree.jstree('get_top_checked');
                    } else {
                        checkedNodes = tree.jstree('get_checked');
                    }
                    var jsonArr = [];
                    var allCheckedNodes = tree.jstree('get_checked');
                    var menuJsonArr = [];
                    if (type === 'A') {
                        $.each(allCheckedNodes, function (property, value) {
                            if ($.inArray(value, checkedNodes) !== -1) {
                                if (value.lastIndexOf('A', 0) === 0) {
                                    jsonArr.push(value);
                                } else if (value.lastIndexOf('P', 0) === 0) {
                                    jsonArr.push(value);
                                }
                            } else {
                                if (value.lastIndexOf('M', 0) === 0) {
                                    menuJsonArr.push(value);
                                }
                            }
                        });
                    } else {
                        $.each(allCheckedNodes, function (property, value) {
                            if ($.inArray(value, checkedNodes) !== -1) {
                                jsonArr.push(value);
                            }
                            menuJsonArr.push(value);
                        });
                    }
                    $.post("AmministrazioneRuoli.html", {
                        operation: "triggerNm_applic_treeOnTriggerJS",
                        Nm_applic_tree: $(this).val(),
                        Id_tree: idTreeParam,
                        Checked_nodes: jsonArr,
                        Menu_checked_nodes: menuJsonArr
                    }).done(function () {
                        tree.removeClass("displayNone");
                        tree.attr("refreshing", true);
                        $.jstree.reference("#" + idTreeParam).refresh(-1);
                    });
                });

                // Eseguo l'ovveride del bottone di salvataggio per modificare le priorità degli handler sull'evento click
                // ( Prima di mostrare il popup di conferma salvataggio richiama il metodo di salvataggio dello stato dell'albero)
                var saveSubmit = $("input:submit[name*='Save'][class*='save']");
                var clickListener = $._data(saveSubmit[0], 'events').click[0].handler;
                saveSubmit.off('click');
                saveSubmit.on('click', function () {
                    saveState(idTreeParam, type);
                });
                saveSubmit.click(clickListener);
                var tabSubmit = $("input:submit[name*='tab']");
                tabSubmit.on('click', function () {
                    saveState(idTreeParam, type);
                });
            }

            function saveState(idTree, type) {
                var idApplic = $("#Nm_applic_tree");
//                var getAll = type ? (type === 'P') : false;
                var checkedNodes = null;
            	// NOTA BENE : jstree 3.3.8 introduce l'API get_top_checked, get_checked restituisce sempre e solo TUTTI i nodi "checked" 
             	// si differenzia per otterne un diverso comportamento tra tree menu e azioni
                if (type === 'M') {
                    checkedNodes = $("#" + idTree).jstree('get_top_checked');
                } else {
                    checkedNodes = $("#" + idTree).jstree('get_checked');
                }
                var jsonArr = [];
                var allCheckedNodes = $("#" + idTree).jstree('get_checked');
                var menuJsonArr = [];
                if (type === 'A') {
                    $.each(allCheckedNodes, function (property, value) {
                        if ($.inArray(value, checkedNodes) !== -1) {
                            if (value.lastIndexOf('A', 0) === 0) {
                                jsonArr.push(value);
                            } else if (value.lastIndexOf('P', 0) === 0) {
                                jsonArr.push(value);
                            }
                        } else {
                            if (value.lastIndexOf('M', 0) === 0) {
                                menuJsonArr.push(value);
                            }
                        }
                    });
                } else {
                    $.each(allCheckedNodes, function (property, value) {
                        if ($.inArray(value, checkedNodes) !== -1) {
                            jsonArr.push(value);
                        }
                        menuJsonArr.push(value);
                    });
                }
                $.ajax({type: 'POST', url: "AmministrazioneRuoli.html", data: {
                        operation: "saveStateOnTriggerJS",
                        Nm_applic_tree: idApplic.val(),
                        Id_tree: idTree,
                        Checked_nodes: jsonArr,
                        Menu_checked_nodes: menuJsonArr
                    }, async: false});
            }

            function showErrorDialog(error) {
                $('<div class="messages ui-state-error errorBox"><span class="ui-icon ui-icon-alert"></span>' + error + '</div>').dialog({
                    autoOpen: true,
                    width: 600,
                    modal: false,
                    resizable: false,
                    dialogClass: "alertBox",
                    closeOnEscape: true,
                    buttons: {
                        "Ok": function () {
                            $(this).dialog("close");
                        }
                    }
                });
            }

            function overlay_loader(show){
                var idOverlayLoader = 'overlay_loading_jstree';
                if (show){
                    $("body").append("<div id='"+idOverlayLoader+"' class='overlay'><div class='overlay__inner'><div class='overlay__content'><span class='spinner'></span></div></div></div>");
                }else{
                    $("#"+idOverlayLoader).remove();
                }
            }
            function populateTree(idRuolo, nmApplic, operation, idTree) {
                if (nmApplic) {
                    // show tree
                	$("#" + idTree).show();
                    overlay_loader(true);
                    $.getJSON("AmministrazioneRuoli.html", {operation: operation,
                        Id_ruolo: idRuolo,
                        Nm_applic_tree: nmApplic
                    }).done(function (data) {
                        checkData(data, idTree);
                    });
                } else {
                    // hiding
                	$("#" + idTree).hide();
                }
            }

            function checkChildren(idTree, idNodo) {
                var tree = $("#" + idTree);
                var children = $.jstree.reference(idTree).get_children_dom($('li#' + idNodo));
                if (children.length > 0 && children[0].id.lastIndexOf('P', 0) === 0) {
                    $.each(children, function (property, value) {
                        if (value.id.lastIndexOf('P', 0) === 0) {
                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                            if (!is_node_checked(idTree,  value.id)) {  
                                tree.jstree('check_node', $(this));
                            }
                        }
                    });
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
                tree.jstree('uncheck_all');
                // NOTA BENE : jstree 3.3.8 cambia la modalita' di check dei nodi, è necessario PRIMA aprire l'intero albero e POI eseguire la fase di check
                tree.jstree('open_all');
                // Gestione menu
                if (readOnlyIds) {
                    tree.data("menu", readOnlyIds);
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
                         		
                         		// dalla >3.3.8 lo stato dei nodi è governato dalla tie_selection (in questo caso false) 
                                // vedi API https://www.jstree.com/api/#/?f=$.jstree.defaults.checkbox.tie_selection
                                // la selezione/deselezione è lo stato ultimo pilotando con l'api check_node
                                // quindi si deve testare lo stato "ultimo" del nodo ossia is_selected         
                                //if (!tree.jstree('is_checked', $('li#' + id))) {
                                if (!is_node_checked(idTree, id)) {  
                                    tree.jstree('check_node', $('li#' + id));
                                }

                                var children = $.jstree.reference(idTree).get_children_dom($('li#' + id));
                                if (children.length > 0) {
                                    $.each(children, function (property, value) {
                                        if (value.id.lastIndexOf('M', 0) === 0) {
                                            //if (!tree.jstree('is_checked', $('li#' + value.id))) {
                                            if (!is_node_checked(idTree, value.id)) {  
                                                tree.jstree('check_node', $(this));
                                            }
                                        }
                                    });
                                }
                            }
                        }
                        //  if (!tree.jstree('is_checked', $('li#' + id))) {
                        if (!is_node_checked(idTree, id)) {
                            tree.jstree('check_node', $('li#' + id));
                        }

                        
                        if (id.lastIndexOf('M', 0) === 0) {
                        	var node = tree.jstree('get_node', $('li#' + id));
                            var nodePath = tree.jstree('get_path', node, '', true);
                            if ((!Array.isArray(ids) || !ids.length) && (!Array.isArray(pageIds) || !pageIds.length)) {
                                if (nodePath.length === 2) {
                                    checkPageMenus(idTree, id, true);
                                } else {
                                    checkChildren(idTree, id);
                                }
                            }
                            nodePath.pop();
                            $.each(nodePath.reverse(), function (property, value) {
                                var parent = $('li#' + value);
                                //if (!tree.jstree('is_checked', parent)) {
                                 if (!is_node_checked(idTree, value)) {  
                                    tree.jstree('check_node', parent);
                                    if ((!Array.isArray(ids) || !ids.length) && (!Array.isArray(pageIds) || !pageIds.length)) {
                                        checkChildren(idTree, value);
                                    }
                                }
                            });
                        }
                    }
                }
                // Gestione check dei nodi di abilitazione principali
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
                    }
                    //if (!tree.jstree('is_checked', $('li#' + id))) {
                    if (!is_node_checked(idTree, id)) {  
                        tree.jstree('check_node', $('li#' + id));
                    }
                    if (type === 'A') {
                        // Se si tratta dell'albero delle azioni e l'id del nodo è un entry menu, può essere solo il caso ALL_ABILITAZIONI
                        if (id.lastIndexOf("M", 0) === 0) {
                            // Tutti i nodi foglia di tipo Azione vengono selezionati
                            $('.jstree-leaf').each(function () {
                                var idLeaf = $(this).attr('id');
                                //if (idLeaf.lastIndexOf("A", 0) === 0 && !tree.jstree('is_checked', $('li#' + idLeaf))) {
                                if (idLeaf.lastIndexOf("A", 0) === 0 && !is_node_checked(idTree, idLeaf)) {
                                    tree.jstree('check_node', $('li#' + idLeaf));
                                }
                            });
                        } else if (id.lastIndexOf("P", 0) === 0 && mixed) {
                            var children = $.jstree.reference(idTree).get_children_dom($('li#' + id));
                            $.each(children, function (property, value) {
                                tree.jstree('check_node', $(this));
                            });
                        }
                    }
                }
                // Gestione check delle pagine
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
                                } else {
                                    // ALL ABILITAZIONI MENU E PAGINE
                                    checkPageMenus(idTree, id, JSON.stringify(readOnlyIds) === JSON.stringify(pageIds));
                                }
                            }
                        }
                    }
                }
                // OBSOLETO dalla >3.3.8 
//                tree.jstree('open_all');
                tree.removeAttr("refreshing");
                overlay_loader(false);
            }

            // Funzione ricorsiva di selezione (a cascata discendente) dei menu e delle pagine
            function checkPageMenus(idTree, idNodo, checkMenus) {
                var tree = $("#" + idTree);
                var children = $.jstree.reference(idTree).get_children_dom($('li#' + idNodo));
                if (children.length > 0) {
                    $.each(children, function (property, value) {
                        if (value.id.lastIndexOf('M', 0) === 0) {
                            //if (checkMenus && !tree.jstree('is_checked', $('li#' + value.id))) {
                            if (checkMenus && !is_node_checked(idTree, value.id)) {                                   
                                tree.jstree('check_node', $(this));
                            }
                            checkPageMenus(idTree, value.id, checkMenus);
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
            function checkMenu(idTree, ids, refreshing) {
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
                            if(!is_node_checked(idTree, nodePath[i])) {
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
                if (refreshing) {
                    tree.removeAttr("refreshing");
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

            function ajaxSelectNode(id_node, node_path, id_dich_autor, operation) {
                $.post("AmministrazioneRuoli.html", {operation: operation,
                    Id_node: id_node,
                    Node_path: node_path,
                    Id_dich_autor: id_dich_autor
                }).done(function (data) {
                    if (!data.map[0].RESULT) {
                        showErrorDialog(data.map[0].ERROR);
                    }
                });
            }

            function ajaxDeselectNode(id_node, id_parent, operation) {
                $.post("AmministrazioneRuoli.html", {operation: operation,
                    Id_node: id_node,
                    Id_parent_node: id_parent
                }).done(function (data) {
                    if (!data.map[0].RESULT) {
                        showErrorDialog(data.map[0].ERROR);
                    }
                });
            }

            function checkAll(idTree) {
                var tree = $("#" + idTree);
                tree.jstree('check_all');
            }

            function uncheckAll(idTree) {
                var tree = $("#" + idTree);
                tree.attr("refreshing", true);
                tree.jstree('uncheck_all');
                var menu = tree.data("menu");
                if (Array.isArray(menu) && menu.length > 0) {
                    checkMenu(idTree, menu, true);
                } else {
                    tree.removeAttr("refreshing");
                }
            }

            function collapseAll(idTree) {
                var tree = $("#" + idTree);
                tree.jstree('close_all');
            }

            function expandAll(idTree) {
                var tree = $("#" + idTree);
                tree.jstree('open_all');
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
			
        	/*
			 dalla >3.3.8 per individuare i figli selezionati (stato : selected = true con api is_checked)
			 necessario introdurre un apposito metodo/function
			*/
			function get_checked_children(id_tree, id_node) {
			    var result = []
			    var tree = $.jstree.reference(id_tree);
			    // get children
			    var children = tree.get_children_dom(id_node);
			    // for each children verify if is checked or not and put on array    
			    $.each(children, function(property, value) {
				    // get node 
				    var node = tree.get_node(value);
			        // verify if checked
			        if (tree.is_checked(node)) {
			            // put if checked
			            result.push(node);
			        }
			    });
			    return result;
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
                //tree.get_node(node).state.checked=false;
                if (is_node_checked(idTree,idNode)){
                    $('#'+idTree).attr("skip_event",true);
                    tree.uncheck_node(node);
                }
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
            <slf:wizard name="<%= AmministrazioneRuoliForm.InserimentoWizard.NAME%>">
                <slf:wizardNavBar name="<%=AmministrazioneRuoliForm.InserimentoWizard.NAME%>" />
                <sl:newLine skipLine="true"/>
                <slf:step name="<%= AmministrazioneRuoliForm.InserimentoWizard.PASSO1%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.ID_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_RUOLO%>" colSpan="4" />
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_RUOLO%>" colSpan="4" controlWidth="w40"/>
                        <%--<sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_CATEG_RUOLO%>" colSpan="4" controlWidth="w40"/>--%>
                    </slf:fieldSet>
                </slf:step>
                
                <slf:step name="<%= AmministrazioneRuoliForm.InserimentoWizard.PASSO_CATEGORIE%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.ID_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_RUOLO%>" colSpan="4"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_RUOLO%>" colSpan="4" controlWidth="w40"/>
                    </slf:fieldSet>
                    <slf:fieldSet borderHidden="true">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.CategorieFields.TI_CATEG_RUOLO%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.CategorieFields.AGGIUNGI_CATEGORIA%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                    </slf:fieldSet>
                    <sl:newLine skipLine="true"/>    
                    <slf:container width="w50">
                    <slf:list name="<%= AmministrazioneRuoliForm.CategorieList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneRuoliForm.CategorieList.NAME%>" />
                    </slf:container>
                </slf:step>
                
                <slf:step name="<%= AmministrazioneRuoliForm.InserimentoWizard.PASSO_APPLIC%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.ID_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_RUOLO%>" colSpan="4"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_RUOLO%>" colSpan="4" controlWidth="w40"/>
                        <%--<sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_CATEG_RUOLO%>" colSpan="4" controlWidth="w40"/>--%>
                    </slf:fieldSet>
                    <slf:fieldSet borderHidden="true">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.ApplicazioniFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.ApplicazioniFields.DS_APPLIC%>" colSpan="4" controlWidth="w40" />
                        <sl:newLine/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.ApplicazioniFields.AGGIUNGI_APPLICAZIONE%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                    </slf:fieldSet>
                    <sl:newLine skipLine="true"/>       
                    <slf:list name="<%= AmministrazioneRuoliForm.ApplicazioniList.NAME%>" />
                    <slf:listNavBar  name="<%= AmministrazioneRuoliForm.ApplicazioniList.NAME%>" />
                </slf:step>
                <slf:step name="<%= AmministrazioneRuoliForm.InserimentoWizard.PASSO2%>">
                    <slf:fieldSet borderHidden="false">
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.ID_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.NM_RUOLO%>" colSpan="4" controlWidth="w20"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.DS_RUOLO%>" colSpan="4"/>
                        <sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_RUOLO%>" colSpan="4" controlWidth="w40"/>
                        <%--<sl:newLine />
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DettaglioRuolo.TI_CATEG_RUOLO%>" colSpan="4" controlWidth="w40"/>--%>
                    </slf:fieldSet>
                    <%-- Inserisco i tab contenenti le varie liste di dichiarazioni --%>                   
                    <slf:tab  name="<%= AmministrazioneRuoliForm.WizardListsTabs.NAME%>" tabElement="ListaWizardMenu">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.NM_APPLIC_TREE%>" colSpan="4" />
                        <%--<slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.TI_SCOPO_DICH_AUTOR%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.DS_ENTRY_MENU%>" colSpan="4" controlWidth="w40" />                        
                        <sl:newLine/>
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>       
                        <slf:list name="<%= AmministrazioneRuoliForm.DichMenuList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneRuoliForm.DichMenuList.NAME%>" />--%>
                        <slf:tree name="<%= AmministrazioneRuoliForm.WizardEntryMenuTree.NAME%>"
                         additionalJsonParams="\"core\" : { \"data\" : { \"url\": \"AmministrazioneRuoli.html?operation=createEntryMenuTree\"}, \"dblclick_toggle\" : false  }" 
                         additionalPluginParams="\"checkbox\" : { \"tie_selection\" : false, \"whole_node\" : false }" />
                        <script type="text/javascript">
                            $(function () {
                                var idTree = "tree_WizardEntryMenuTree";
                                var jsIdTree = "#" + idTree;
                                initIdApplicTrigger(idTree);
                                var tree = $(jsIdTree);
                                // evento check di un nodo, richiama con ajax il metodo selectEntry per modificare l'albero logico
                                tree.on("check_node.jstree", function (event, data) {
                                    var id_node = data.node.id;
                                    var id_dich_autor = $("li#" + id_node).attr("id_dich_autor");
                                    //var node = tree.jstree('get_node', obj.rslt.obj[0]);
                                    var node = data.node;
                                    var nodePath = tree.jstree('get_path', node, '', true);
                                    var refreshing = tree.attr("refreshing");
                                    // Verifico se sto facendo la refresh dell'albero
                                    if (typeof refreshing !== typeof undefined && refreshing !== false) {
                                        // Element has this attribute
                                        refreshing = true;
                                    }
                                    if (refreshing && id_dich_autor) {
                                        ajaxSelectNode(id_node, nodePath, id_dich_autor, "selectEntryMenuNode");
                                    }
                                });

                                // evento uncheck di un nodo, richiama con ajax il metodo deselectEntry per modificare l'albero logico
                                tree.on("uncheck_node.jstree", function (event, data) {
                                    //var parent = $.jstree.reference(jsIdTree).get_parent(data.node);
                                    var id_parent = data.node.parent;
                                    var id_node = data.node.id;
//                                    ajaxDeselectNode(id_node, id_parent, "deselectEntryMenuNode");
                                });

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
                                    tree.removeClass("displayNone");
                                    tree.attr("refreshing", true);
                                }
                            });
                        </script>
                    </slf:tab>
                    <slf:tab  name="<%= AmministrazioneRuoliForm.WizardListsTabs.NAME%>" tabElement="ListaWizardActions">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.NM_APPLIC_TREE%>" colSpan="4" />
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.TreeButtonList.CHECK_ALL%>" colSpan="1" controlWidth="w40" /> 
                            <slf:lblField name="<%= AmministrazioneRuoliForm.TreeButtonList.UNCHECK_ALL%>" colSpan="1" controlWidth="w40" /> 
                            <slf:lblField name="<%= AmministrazioneRuoliForm.TreeButtonList.COLLAPSE_ALL%>" colSpan="1" controlWidth="w40" /> 
                            <slf:lblField name="<%= AmministrazioneRuoliForm.TreeButtonList.EXPAND_ALL%>" colSpan="1" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <%--<sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.TI_SCOPO_DICH_AUTOR%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.DS_PAGINA_WEB_AZIO%>" colSpan="4" controlWidth="w40" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.DS_AZIONE_PAGINA%>" colSpan="4" controlWidth="w40" />
                        <sl:newLine />
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>
                        <slf:list name="<%= AmministrazioneRuoliForm.DichAzioniList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneRuoliForm.DichAzioniList.NAME%>" />--%>
                        <slf:tree name="<%= AmministrazioneRuoliForm.WizardAzioniTree.NAME%>" 
                        additionalJsonParams="\"core\" : { \"data\" : { \"url\": \"AmministrazioneRuoli.html?operation=createAzioniTree\"}, \"dblclick_toggle\" : false  }"
                        additionalPluginParams="\"checkbox\" : { \"tie_selection\" : false, \"whole_node\" : false, \"three_state\" : false }"/>
                        <!--, \"checkbox\" : {\"two_state\":true}-->
                        <script type="text/javascript">
                            $(function () {
                                var idTree = "tree_WizardAzioniTree";
                                var jsIdTree = "#" + idTree;
                                initIdApplicTrigger(idTree);
                                var tree = $(jsIdTree);

//                                $.getJSON("AmministrazioneRuoli.html", {operation: "createPagineTree"});

                                // evento check di un nodo, richiama con ajax il metodo selectEntry per modificare l'albero logico
                                tree.on("check_node.jstree", function (event, data) {
                                    var node = data.node;
                                    var id_node = node.id;
                                    var nodePath = tree.jstree('get_path', node, '', true);
                                    var id_dich_autor = $("li#" + id_node).attr("id_dich_autor");
                                    var page_dich_autor = $("li#" + id_node).attr("page_dich_autor");
                                    var refreshing = tree.attr("refreshing");
                                    // Verifico se sto facendo la refresh dell'albero
                                    if (typeof refreshing !== typeof undefined && refreshing !== false) {
                                        // Element has this attribute
                                        refreshing = true;
                                    }
                                    if (!refreshing &&
                                            (id_node.lastIndexOf("M", 0) === 0)) {
                                        // Non posso toccare un nodo a menu, quindi torno allo stato precedente
                                        var nmApplic = $("#Nm_applic_tree").val();
                                        var idRuolo = $("#Id_ruolo_hidden").val();
                                        tree.attr("refreshing", true);
                                        populateTree(idRuolo, nmApplic, "populateAzioniAbilitateOnTriggerJs", idTree);
                                    } else {
                                        if (refreshing) {
                                            if (id_dich_autor) {
                                                ajaxSelectNode(id_node, nodePath, id_dich_autor, "selectAzioneNode");
                                            }
                                            if (page_dich_autor) {
                                                ajaxSelectNode(id_node, nodePath, page_dich_autor, "selectPaginaNode");
                                            }
                                        }
                                        // NOTA BENE : jstree 3.3.8 introduce una diversa gestione dei node "checked", in particolare 
                                        // al primo caricamento del tree (refreshing = true) è fondamentale NON far scattare la selezione (checked)
                                        // di un nodo parent se viene selezionato un figlio, in quanto questo provoca un errata gestione dei dati 
                                        // lato server e, conseguemente, una errorea persitenza su base dati.
                                        // Il controllo che viene effettuato nella fasi successive (refreshing = false) serve semplicemente qualora 
                                        // si seleziona un nodo di tipo "azione" e il genitore (parent) non è selezionato.
                                        // In questo modo se il genitore e il figlio non sono presenti su base dati, vengono inseriti per la prima volta 
                                        // correttamente.
                                        if (!refreshing && id_node.lastIndexOf("A", 0) === 0) {
                                            //var parent = $.jstree.reference(jsIdTree).get_parent(node);
                                           // if (!tree.jstree('is_checked', $("li#" + id_parent))) {
                                            if (!is_node_checked(jsIdTree, node.parent)) {  
                                                tree.jstree('check_node', $("li#" + node.parent));
                                            }
                                        }
                                        saveState(idTree, 'A');
                                    }
                                });

                                // evento uncheck di un nodo, richiama con ajax il metodo deselectEntry per modificare l'albero logico
                                tree.on("uncheck_node.jstree", function (event, data) {
                                    var skip =tree.attr("skip_event");
                                    if (typeof skip === typeof undefined || !skip){
                                        var node = data.node;
                                        //var parent = $.jstree.reference(jsIdTree).get_parent(node);
                                        //var id_parent = node.parent;
                                        var id_node = node.id;
                                        var refreshing = tree.attr("refreshing");
                                        // Verifico se sto facendo la refresh dell'albero
                                        if (typeof refreshing !== typeof undefined && refreshing !== false) {
                                            // Element has this attribute
                                            refreshing = true;
                                        }
                                        if (!refreshing && (id_node.lastIndexOf("M", 0) === 0)) {
                                            // Non posso toccare un nodo a menu, quindi torno allo stato precedente
                                            var nmApplic = $("#Nm_applic_tree").val();
                                            var idRuolo = $("#Id_ruolo_hidden").val();
                                            tree.attr("refreshing", true);
                                            populateTree(idRuolo, nmApplic, "populateAzioniAbilitateOnTriggerJs", idTree);
                                        } else if (!refreshing && (id_node.lastIndexOf("P", 0) === 0)) {
                                            // OBSOLETO dalla >3.3.8 var checkedNodes = $.jstree.reference(jsIdTree).get_checked(node);
                                            var selected = get_checked_children(idTree, node);
                                            $.each(selected, function (property, value) {
                                                tree.jstree('uncheck_node', $('li#' + value.id));
                                            });
                                            saveState(idTree, 'A');
                                        } else {
                                            if (!refreshing && (id_node.lastIndexOf("A", 0) === 0)) {
                                                var parent = tree.jstree('get_parent',node);
                                                var selectedChildren = get_checked_children(idTree, parent);
                                                //se non sono rimasti figli deseleziono anche il padre
                                                if (selectedChildren.length == 0){
                                                    if (!is_node_checked(idTree,parent.id)){
                                                        tree.jstree('uncheck_node',parent);
                                                    }
                                                }
                                            }
                                            saveState(idTree, 'A');
                                        }
                                    }else{
                                        tree.removeAttr("skip_event");
                                    }
                                });

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
                                    populateTree(idRuolo, nmApplic, "populateAzioniAbilitateOnTriggerJs", idTree);
                                });

                                $("input:submit[name*='checkAll']").off('click');
                                $("input:submit[name*='uncheckAll']").off('click');
                                $("input:submit[name*='collapseAll']").off('click');
                                $("input:submit[name*='expandAll']").off('click');
                                $("input:submit[name*='checkAll']").on('click', function () {
                                    checkAll(idTree);
                                    return false;
                                });
                                $("input:submit[name*='uncheckAll']").on('click', function () {
                                    uncheckAll(idTree);
                                    return false;
                                });
                                $("input:submit[name*='collapseAll']").on('click', function () {
                                    collapseAll(idTree);
                                    return false;
                                });
                                $("input:submit[name*='expandAll']").on('click', function () {
                                    expandAll(idTree);
                                    return false;
                                });

                                tree.on("dblclick.jstree", function (event) {
                                    var node = $(event.target).closest("li");
                                    var id_node = node[0].id;
                                    // Do my action
                                    if (!$.jstree.reference(jsIdTree).is_leaf(node) && id_node.lastIndexOf("P", 0) === 0) {
                                        var children = $.jstree.reference(idTree).get_children_dom(node);
                                        // OBSOLETO dalla >3.3.8 if (children.length > $.jstree.reference(idTree).get_checked(node).length) {
                                        var checked = get_checked_children(idTree, node);
                                        if (children.length > checked.length) {
                                            $.each(children, function (property, value) {
                                                tree.jstree('check_node', $(this));
                                            });
                                        } else {
                                            $.each(children, function (property, value) {
                                                tree.jstree('uncheck_node', $(this));
                                            });
                                        }
                                    }
                                });
							
            	                  
                                if ($("#Nm_applic_tree").val()) {
                                    tree.removeClass("displayNone");
                                    tree.attr("refreshing", true);
                                }
                            });
                        </script>
                    </slf:tab>
                    <slf:tab  name="<%= AmministrazioneRuoliForm.WizardListsTabs.NAME%>" tabElement="ListaWizardServices">
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.NM_APPLIC%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.TI_SCOPO_DICH_AUTOR%>" colSpan="4" />
                        <sl:newLine skipLine="true"/>
                        <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.DS_SERVIZIO_WEB%>" colSpan="4" controlWidth="w40" />
                        <sl:newLine />
                        <sl:pulsantiera>
                            <slf:lblField name="<%= AmministrazioneRuoliForm.DichAutorFields.ADD_ROW_TO_LIST%>" colSpan="2" controlWidth="w40" /> 
                        </sl:pulsantiera>
                        <sl:newLine skipLine="true"/>
                        <slf:list name="<%= AmministrazioneRuoliForm.DichServiziList.NAME%>" />
                        <slf:listNavBar  name="<%= AmministrazioneRuoliForm.DichServiziList.NAME%>" />
                    </slf:tab>
                </slf:step>
            </slf:wizard>
        </sl:content>
        <sl:footer />
    </sl:body>
</sl:html>
