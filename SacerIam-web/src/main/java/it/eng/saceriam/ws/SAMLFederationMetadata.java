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

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */

package it.eng.saceriam.ws;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eng.saceriam.web.helper.ComboHelper;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Quaranta_M
 */
@WebServlet(name = "SAMLFederationMetadata", urlPatterns = "/federationMetadata", loadOnStartup = 0)
public class SAMLFederationMetadata extends HttpServlet {

    @EJB
    private ComboHelper helper;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("application/xml;charset=UTF-8");
	PrintWriter writer = response.getWriter();
	writer.append(readFederationMetadata());
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the
    // left to edit the
    // code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Sacer Federation metadata provider";
    }// </editor-fold>

    /*
     * Legge l'XML del federation metadata dal DB oppure dal file locale della conf di shibboleth
     * locale in base alla presenza della System property "idpLocale=true" e può essere ridefinito
     * in una classe derivata.
     */
    protected String readFederationMetadata() throws IOException {
	String idpLocale = System.getProperty("idpLocale");
	if (idpLocale != null && idpLocale.trim().equalsIgnoreCase("true")) {
	    return (new String(
		    Files.readAllBytes(
			    Paths.get("/opt/shibboleth/metadata/fedmetadata-local-signed.xml")),
		    "UTF-8"));
	} else {
	    return helper.getFederationMetadata();
	}
    }
}
