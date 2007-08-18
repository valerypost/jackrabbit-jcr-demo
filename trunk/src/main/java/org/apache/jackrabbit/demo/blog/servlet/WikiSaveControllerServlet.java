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

/**
 * Servlet implementation class for Servlet: WikiSaveControllerServlet
 *
 */
 public class WikiSaveControllerServlet extends ControllerServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String changeNote = request.getParameter("changeNote");
		
        try {
        	session = repository.login(new SimpleCredentials("username","password".toCharArray()));
        	
			Node frontPage = session.getRootNode().getNode("wiki/frontPage");
			
			try {
			
				frontPage.setProperty("wiki:title", title);
				frontPage.setProperty("wiki:content",content);
				frontPage.setProperty("wiki:changeNote",changeNote);
				
				frontPage.save();
				
				Version version = frontPage.checkin();
			
			} catch (VersionException e){
		
				request.setAttribute("msgTitle", "Can't save your changes");
				request.setAttribute("msgBody", "Sorry, someone has saved changes while your edting. Please try edting again.");
				request.setAttribute("urlText", "go back to edting wiki");
				request.setAttribute("url","/jackrabbit-jcr-demo/wiki/edit");	
				
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
			}
			
			
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/wiki/view");
            requestDispatcher.forward(request, response);
			
			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

	}   	  	    
}