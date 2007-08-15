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

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.exception.InvalidUserException;
import org.apache.jackrabbit.demo.blog.model.BlogEntry;
import org.apache.jackrabbit.demo.blog.model.BlogManager;

/**
 * Servlet implementation class for Servlet: BlogViewControllerServlet
 *
 */
 public class BlogViewControllerServlet extends ControllerServlet {
  	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 4885316149052515878L;


	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	} 	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// log in to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			// Get the username of the current session. "username" attribute is set in LoginController when the user log in to the system.
			String username = (String)request.getSession().getAttribute("username");
			
			// Get a ArrayList of blog entries of user
			ArrayList<BlogEntry> blogList = BlogManager.getByUsername(username,session);
			
			//TODO Do something like paging when the number of blog entries returned is large
			
			// Set the blogList as a request attribute 
			request.setAttribute("blogList",blogList);
			request.setAttribute("ownBlog",true);
			
			// Forward the request to blog entries page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listBlogEntries.jsp");
            requestDispatcher.forward(request, response);
			
			
		} catch (LoginException e) {
			// Log the exception and throw a ServletException
			log("Couldn't log in to the repository",e);
			throw new ServletException("Couldn't log in to the repository");
		} catch (RepositoryException e) {
			// Log the exception and throw a ServletException
			log("Error occured while accessing the repository",e);
			throw new ServletException("Error occured while accessing the repository");
		} catch (InvalidUserException e) {
			log("Username invalid",e);
			throw new ServletException("Username invalid");
		} finally {
			//TODO this will be called after a login exception or with null session. Handle the session logout in a better way
			session.logout();
		}
	}   	  	    
}