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
import java.util.ArrayList;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.UserManager;
import org.apache.jackrabbit.demo.blog.model.WikiPage;

/**
 * Servlet implementation class for Servlet: WikiChangesControllerServlet
 *
 */
 public class WikiChangesControllerServlet extends ControllerServlet {

	
	 /**
	 * Serial version id
	 */
	private static final long serialVersionUID = -9214209681522982127L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			try {
				//log in to the repository and aquire a session
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
				Node frontPage = session.getRootNode().getNode("wiki/frontPage");
				Version baseVersion = frontPage.getBaseVersion();
				Version rootVersion = frontPage.getVersionHistory().getRootVersion();
				
				ArrayList<WikiPage> history = new ArrayList<WikiPage>();
				
					
				VersionIterator versions = frontPage.getVersionHistory().getAllVersions();
				
				while ( versions.hasNext()) {
					Version version = versions.nextVersion();
					
					if (!version.isSame(rootVersion)){
						WikiPage wikiPage = new WikiPage();
						
						frontPage.restore(version, false);
						
						wikiPage.setChangeNote(frontPage.getProperty("wiki:changeNote").getString());
						String userUUID = frontPage.getProperty("wiki:savedBy").getString();
						wikiPage.setSavedBy(UserManager.getUsername(userUUID,session));
						wikiPage.setVersion(version.getName());
						
						history.add(wikiPage);
					}
				}
				
				frontPage.restore(baseVersion, false);
				
				request.setAttribute("history",history);
				
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/wikiHistory.jsp");
	            requestDispatcher.forward(request, response);
	           
				
			} catch (RepositoryException e) {
				throw new ServletException("Couldn't retrive the wiki page history. Error occured while accessing the repository.",e);
			} finally {
				if (session != null) {
					session.logout();
				}
			}
	}   	  	    
}