/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var name;

//metodi al click dei vari bottoni
$("#gestAmbTree_createBtn").click( function() {
    window.location = "GestioneAmbitoTerritoriale.html?operation=createNode";
});
$("#gestAmbTree_renameBtn").click( function() {
    window.location = "GestioneAmbitoTerritoriale.html?operation=updateNode";
});
//$("#gestAmbTree_deleteBtn").click( function() {  window.location = "Strutture.html?operation=deleteNode&nodeName="+name;  });
$("#gestAmbTree_deleteBtn").click( function() {
    window.location = "GestioneAmbitoTerritoriale.html?operation=confRemove";
});
                
var tree = $("#tree_GestAmbTree");
                
tree.bind("move_node.jstree",function(e, data){
                   
//    if(nodeDestId === "tree_GestAmbTree"){
//        nodeDestId = "0";
//    }
    var nodeDestId = data.node.parent;          
    var nodeId= data.node.id;
    window.location = "GestioneAmbitoTerritoriale.html?operation=moveNode&nodeId="+nodeId+"&nodeDestId="+nodeDestId; 
});
               
tree.bind("select_node.jstree", function (e, data) {
	name =  data.node.text;
    window.location = "GestioneAmbitoTerritoriale.html?operation=loadNode&nodeName="+name; 
//name= selectedObj[0].textContent;
//selectedObj.attr("id") + selectedObj.attr("data"));
//window.location = "GestioneAmbitoTerritoriale.html?operation=loadNode";
});
tree.bind("open_node.jstree", function () {
                    
    });
                
tree.on('loaded.jstree', function() {
    tree.jstree('open_all');
});  