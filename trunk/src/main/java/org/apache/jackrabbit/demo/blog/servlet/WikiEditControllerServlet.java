/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.demo.blog.servlet;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.WikiPage;


/**
 * Servlet implementation class for Servlet: WikiEditControllerServlet
 *
 */
 public class WikiEditControllerServlet extends ControllerServlet {


	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 4621694324606346198L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// log in to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));

            Node frontPage = session.getRootNode().getNode("wiki/frontPage");

			if (!frontPage.isCheckedOut()) {
				frontPage.checkout();	
			} else {
				request.setAttribute("msg","Someone else has started edting the page. One of you may not be able to save the changes");
			}
			
			WikiPage wikiPage = new WikiPage();
			wikiPage.setTitle(frontPage.getProperty("wiki:title").getString());
			wikiPage.setContent(frontPage.getProperty("wiki:content").getString());
			
			request.setAttribute("wikiPage", wikiPage);
			
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/wikiEdit.jsp");
            requestDispatcher.forward(request, response);
			
		} catch (RepositoryException e) {
			throw new ServletException("Could start editing wiki page. Error occured while accessing the repository.",e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}   	  	    
}