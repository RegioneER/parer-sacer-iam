/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna <p/> This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version. <p/> This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Affero General Public License for more details. <p/> You should
 * have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <https://www.gnu.org/licenses/>.
 */

package it.eng.saceriam.web.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta;
import it.eng.spagoLite.FrameElement;
import it.eng.spagoLite.security.User;
import it.eng.spagoLite.security.menu.impl.Link;
import it.eng.spagoLite.security.menu.impl.Menu;
import it.eng.spagoLite.security.profile.Azione;
import it.eng.spagoLite.security.profile.Pagina;
import it.eng.spagoLite.security.profile.Profile;

public class UserUtil {

    public static void fillComponenti(User utente, RecuperoAutorizzazioniRisposta auth) {
	utente.setMenu(populateMenu(auth.getMenuList()));
	Profile profilo = new Profile(utente.getUsername(), "Profilo");

	// Colleziono in una mappa l'insieme di azioni per ogni pagina
	HashMap<String, List<String>> aplMap = new HashMap<>();
	for (it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Pagina aplPagina : auth
		.getPagineList()) {
	    aplMap.put(aplPagina.getNmPaginaWeb(), new ArrayList<String>());
	}
	for (it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Azione aplAzione : auth
		.getAzioniList()) {
	    List<String> azioniList = aplMap.get(aplAzione.getNmPaginaWeb());
	    azioniList.add(aplAzione.getNmAzionePagina());
	    aplMap.put(aplAzione.getNmPaginaWeb(), azioniList);
	}

	for (String nmPaginaWeb : aplMap.keySet()) {
	    Pagina pagina = new Pagina(nmPaginaWeb, nmPaginaWeb);
	    List<String> azioniList = aplMap.get(nmPaginaWeb);
	    // Aggiungo le azioni alla pagina
	    for (String aplAzione : azioniList) {
		Azione azione = new Azione(aplAzione, aplAzione);
		pagina.addChild(azione);
	    }
	    // Aggiungo le operazioni di menu alla pagina (le azioni di menu sono valide per tutte
	    // le pagine)
	    for (it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu entryMenu : auth
		    .getMenuList()) {
		if (entryMenu.getDsLinkEntryMenu() != null) {
		    Azione azione = new Azione(entryMenu.getNmEntryMenu(),
			    entryMenu.getDsEntryMenu());
		    pagina.addChild(azione);
		}
	    }
	    // Aggiungo la pagina al profilo
	    profilo.addChild(pagina);
	}

	// Aggiungo le operazioni di menu alla pagina con nome vuoto ("") per i casi in cui viene
	// resettato il
	// lastPublisher
	Pagina pagina = new Pagina("", "");
	for (it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu entryMenu : auth
		.getMenuList()) {
	    if (entryMenu.getDsLinkEntryMenu() != null) {
		Azione azione = new Azione(entryMenu.getNmEntryMenu(), entryMenu.getDsEntryMenu());
		pagina.addChild(azione);
	    }
	}
	// Aggiungo la pagina al profilo
	profilo.addChild(pagina);
	utente.setProfile(profilo);
    }

    public static Menu populateMenu(
	    List<it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu> userMenu) {
	Menu menu = new Menu("", "");
	List<Node> list = new ArrayList<Node>();
	Map<Integer, Node> map = new LinkedHashMap<Integer, Node>();
	for (it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu entryMenu : userMenu) {
	    int level = entryMenu.getNiLivelloEntryMenu().intValue();
	    Node node = new Node(entryMenu);

	    if (list.size() < level) {
		list.add(null);
	    }

	    if (level > 1) {
		list.get(level - 2).add(node);
	    }

	    list.set(level - 1, node);
	}
	if (list.size() > 0) {
	    for (Node childNode : list.get(0)) {
		populateMenu(menu, childNode);
	    }
	}
	return menu;
    }

    private static void populateMenu(Menu menu, Node node) {

	if (!node.hasChild()) {
	    Link childLink = new Link(node.getEntryMenu().getNmEntryMenu(),
		    node.getEntryMenu().getDsEntryMenu(), node.getEntryMenu().getDsLinkEntryMenu());
	    menu.add(childLink);
	} else {
	    Menu childMenu = new Menu(node.getEntryMenu().getNmEntryMenu(),
		    node.getEntryMenu().getDsEntryMenu());
	    menu.add(childMenu);

	    for (Node childNode : node) {
		populateMenu(childMenu, childNode);
	    }
	}
    }

    private static class Node extends FrameElement implements Iterable<Node> {

	private List<Node> child;
	private it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu entryMenu;

	public Node(it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu entryMenu) {
	    this.child = new ArrayList<Node>();
	    this.entryMenu = entryMenu;
	}

	public it.eng.saceriam.ws.dto.RecuperoAutorizzazioniRisposta.Menu getEntryMenu() {
	    return entryMenu;
	}

	public boolean hasChild() {
	    return child.size() > 0;
	}

	public void add(Node node) {
	    child.add(node);
	}

	public Iterator<Node> iterator() {
	    return child.iterator();
	}

	@Override
	public Element asXml() {
	    Element element = super.asXml();
	    element.addAttribute("codice", entryMenu.getNmEntryMenu());

	    for (Node child : this) {
		element.add(child.asXml());
	    }

	    return element;
	}
    }

}
