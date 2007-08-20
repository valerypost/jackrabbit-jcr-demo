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
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.UserManager;
import org.apache.jackrabbit.demo.blog.model.WikiPage;

/**
 * Servlet implementation class for Servlet: WikiViewControllerServlet
 *
 */
 public class WikiViewControllerServlet extends ControllerServlet {
 	
	
	 /**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5313193989137911990L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 String versionName = request.getParameter("version");
		 
			try {
				//log in to the repository and aquire a session
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
				Node frontPage = session.getRootNode().getNode("wiki/frontPage");
				Version baseVersion = frontPage.getBaseVersion();
				Version rootVersion = frontPage.getVersionHistory().getRootVersion();
				
				if (versionName == null) {
					versionName = baseVersion.getName();
				}
				
				try {
				
					Version version = frontPage.getVersionHistory().getVersion(versionName);
					
					if (!rootVersion.isSame(version)) {
						frontPage.restore(version, false);
					}
				
				} catch (VersionException e) {
					request.setAttribute("msgTitle", "Version not found");
					request.setAttribute("msgBody", "Sorry Version: \""+versionName+"\" is not available, please try other version");
					request.setAttribute("urlText", "go back to wiki page");
					request.setAttribute("url","/jackrabbit-jcr-demo/wiki/view");	
					
		            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
		            requestDispatcher.forward(request, response);
		            return;
				}
				
				WikiPage wikiPage = new WikiPage();
				wikiPage.setTitle(frontPage.getProperty("wiki:title").getString());
				wikiPage.setContent(frontPage.getProperty("wiki:content").getString());
				String userUUID = frontPage.getProperty("wiki:savedBy").getString();
				
				wikiPage.setSavedBy(UserManager.getUsername(userUUID,session));
				wikiPage.setVersion(versionName);
				
				if (!rootVersion.isSame(baseVersion)) {
				    frontPage.restore(baseVersion,true);
				}
				request.setAttribute("wikiPage", wikiPage);
				
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/wikiView.jsp");
	            requestDispatcher.forward(request, response);
	           
				
			} catch (RepositoryException e) {
				throw new ServletException("Couldn't retrive the wiki page. Error accessing the repository.",e);
			} finally {
				if (session != null) {
					session.logout();
				}
			}
	}   	  	    
}