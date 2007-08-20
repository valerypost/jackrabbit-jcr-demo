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

import org.apache.jackrabbit.demo.blog.exception.NonUniqueUsernameException;
import org.apache.jackrabbit.demo.blog.model.User;
import org.apache.jackrabbit.demo.blog.model.UserManager;

/**
 * Controller class used to add users to the system
 *
 */
 public class UserAddControllerServlet extends ControllerServlet {
 	
	
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = 5292911530212301687L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract the details required to add a user from the request
		String username = request.getParameter("username");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// Can't create the user node if the username is null or empty so throw an exception
		if (username == null ) {
			throw new ServletException("Username not specified in the request");
		} else if ( username.trim().equals("")) {
			throw new ServletException("Username can not be empty");
		}
		
		// Creates a user bean and fill with data
		User user = new User();
		user.setUsername(username);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setPassword(password);
		
		try {
			
			// Login to the repository
			session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
			
			// Creates a UserManager and add the user 
			UserManager userMgr = new UserManager(session);
			//userMgr.addUser(user);
			userMgr.addUserOCM(user);
			
			//set the attributes which are required by user messae page
			request.setAttribute("msgTitle", "User Registration Successful ");
			request.setAttribute("msgBody", "You have successfully registored in Jackrabbit-jcr-demo");
			request.setAttribute("urlText", "go back to loginpage");
			request.setAttribute("url","/jackrabbit-jcr-demo/");	
			
			//forward the request to user massage page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
            requestDispatcher.forward(request, response);
				
			
		} catch (NonUniqueUsernameException e){
			//set the attributes which are required by user messae page
			request.setAttribute("msgTitle", "Username Exists ");
			request.setAttribute("msgBody", "Username you have choosen is already registered in Jackrabbit-jcr-demo");
			request.setAttribute("urlText", "Try another username");
			request.setAttribute("url","/jackrabbit-jcr-demo/blog/newUser.jsp");	
			
			//forward the request to user massage page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
            requestDispatcher.forward(request, response);
					
		} catch (RepositoryException e) {
			throw new ServletException("Error occured in accessing repository",e);
		} finally{
			if (session != null ){
				session.logout();
			}
		}
		
	}   	  	    
}