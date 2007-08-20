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

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.UserManager;


 /**
 * Servlet that handles user login 
 */
public class LoginControllerServlet extends ControllerServlet {

	
	 /**
	  * Serial version UID.
	  */
	 private static final long serialVersionUID = -6191646376561086611L;
	
	/**
	 * Handles the POST method requests
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// Extract the username and the password from the request
			String username = request.getParameter("username");
			String password = request.getParameter("password");
					
			// Login to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			// Check whether the authentication successful
			if(UserManager.login(username,password,session)) {
									
				
				// set the two attributes auth and usernames in the session to keep the authentication state
				request.getSession().setAttribute("auth", true);
				request.getSession().setAttribute("username", username);
				
	            // Forward the user to his blog
				RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/view");
	            requestDispatcher.forward(request, response);
			
	        // if authentication fails
			} else {
				
				// Send a messge to the user saying authentication was unsuccessful.
				request.setAttribute("msgTitle", "Authentication Failed");
				request.setAttribute("msgBody", "Username or password is incorrect. Please recheck the password and try to log in again");
				request.setAttribute("urlText", "go back to login page");
				request.setAttribute("url","/jackrabbit-jcr-demo/");	
				
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
			}
					
		} catch (RepositoryException e) {
			throw new ServletException("Coludn't log in to repository",e);
		} finally {
			if (session != null) {
				session.logout();
			}
		}

	}   	  	    
}