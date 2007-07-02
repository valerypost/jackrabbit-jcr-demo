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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.demo.blog.exception.NonUniqueUsernameException;
import org.apache.jackrabbit.demo.blog.model.*;
import org.apache.jackrabbit.servlet.ServletRepository;
	

 /**
 * Servlet that controls the flow of user management tasks
 *
 */
public class UserControllerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
    
	/**
     * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
     */
    private final Repository repository = new ServletRepository(this);

	
	/** 
	 * Method which handles the GET method requests
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
    	doPost(request,response);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract the action from the request
		String action = request.getParameter("action");
		
		
		if (action.trim().equals("add")) {
				
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
					Session session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
					
					// Creates a UserManager and add the user 
					UserManager userMgr = new UserManager(session);
					userMgr.addUser(user);
					
					//set the attributes which are required by user messae page
					request.setAttribute("msgTitle", "User Registration Successful ");
					request.setAttribute("msgBody", "You have successfully registored in Jackrabbit-jcr-demo");
					request.setAttribute("urlText", "go back to loginpage");
					request.setAttribute("url","/jackrabbit-jcr-demo/index.html");	
					
					//forward the request to user massage page
		            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
		            requestDispatcher.forward(request, response);
						
					
				} catch (NonUniqueUsernameException e){
					throw new ServletException("Username is not unique",e);
				}catch (LoginException e) {
					throw new ServletException("Failed to login to repository",e);
				} catch (RepositoryException e) {
					throw new ServletException("Error occured in accessing repository",e);
				}
				
			} else if (action.trim().equals("viewAll")) {
				
				Session session;
				try {
					
					// login to the repository
					session = repository.login(new SimpleCredentials("jackrabbit", "jackrabbit".toCharArray()));
					
					//  Creates a UserManager and gets the user list
					UserManager usrMgr = new UserManager(session); 
					ArrayList<User> usrList = usrMgr.getAllUsers();
					
					// Set the user list as a attribute and send it to user list view pae
					request.setAttribute("userList", usrList);
		            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listUsers.jsp");
		            requestDispatcher.forward(request, response);
					
				} catch (LoginException e) {
					throw new ServletException("Failed to login to repository",e);
				} catch (RepositoryException e) {
					throw new ServletException("Error occured in accessing repository",e);
				}
				
				
			} else {
				throw new ServletException("Invalid action parameter");
			}


			
			
	}   	  	    
}