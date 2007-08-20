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
import java.util.Calendar;

import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.exception.InvalidUserException;
import org.apache.jackrabbit.demo.blog.model.BlogEntry;
import org.apache.jackrabbit.demo.blog.model.BlogManager;
import org.apache.jackrabbit.demo.blog.model.UserManager;

/**
 * Controller class which handles searching of blog entries
 *
 */
 public class BlogSearchControllerServlet extends ControllerServlet {   	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5166029039910102856L;

	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	} 		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the title and the content of the blog entry from the request
		String type = request.getParameter("type");
		
		try {
			//log in to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			// Get the username of the current session. "username" attribute is set in LoginController when the user log in to the system.
			String username = (String)request.getSession().getAttribute("username");
			
			//If the user is not not logged in, set the username to guest
			if (username == null) {
				username = "guest";
				request.getSession().setAttribute("username", "guest");
			}
			
			// Get a ArrayList of blog entries of user
			ArrayList<BlogEntry> blogList = null;
			
			// Check if the search is done by username of the blog owner
			if (type.trim().equals("ByName")) {
				String searchName = request.getParameter("searchName");
				blogList = BlogManager.getByUsername(searchName,session);
				String uuid = UserManager.getUUID(searchName, session);
				
				request.setAttribute("user-uuid", uuid);
				if (searchName.equals(username)) {
					request.setAttribute("ownBlog",true);
				}
			
            // Check if the search is done by content of the blog entry
			} else if (type.trim().equals("ByContent")) {
				String searchText = request.getParameter("searchText");
				blogList = BlogManager.getByContent(searchText,session);
			
			// Check if the search is done by date of creation of the blog entry
			} else if (type.trim().equals("ByDate")) {
				
				
				String yearFromStr = request.getParameter("yearFrom");
				String monthFromStr = request.getParameter("monthFrom");
				String dateFromStr = request.getParameter("dateFrom");
				
				String yearToStr = request.getParameter("yearTo");
				String monthToStr = request.getParameter("monthTo");
				String dateToStr = request.getParameter("dateTo");
				
				int yearFrom = Integer.parseInt(yearFromStr);
				int monthFrom = Integer.parseInt(monthFromStr);
				int dateFrom = Integer.parseInt(dateFromStr);
				
				int yearTo = Integer.parseInt(yearToStr);
				int monthTo = Integer.parseInt(monthToStr);
				int dateTo =  Integer.parseInt(dateToStr);
					
				Calendar from = Calendar.getInstance();
				from.set(yearFrom,monthFrom,dateFrom);
				Calendar to = Calendar.getInstance();
				to.set(yearTo,monthTo,dateTo);
				to.add(Calendar.DATE,1);
				
				blogList = BlogManager.getByDate(username,from,to,session);
				
				
			}
			
			//TODO Do something like paging when the number of blog entries returned is large
			
			
			// If the blogs for the search is emptry, ask user whether he wants to do another search
			if (blogList.size() == 0) {
				
				//set the attributes which are required by user messae page
				request.setAttribute("msgTitle", "Blog Search");
				request.setAttribute("msgBody", "Your search doesn't contain any blog entries");
				request.setAttribute("urlText", "go back to search page");
				request.setAttribute("url","/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp");	
				
				//forward the request to user massage page
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
	            return;
				
			}
			
			// Set the blogList as a request attribute 
			request.setAttribute("blogList",blogList);
	
			// Forward the request to blog entries page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listBlogEntries.jsp");
            requestDispatcher.forward(request, response);
					

		} catch (RepositoryException e) {
			// Log the exception and throw a ServletException
			log("Error occured while accessing the repository",e);
		} catch (InvalidUserException e) {
			
			//set the attributes which are required by user messae page
			request.setAttribute("msgTitle", "Blog Search");
			request.setAttribute("msgBody", "The username you searched for doesn't exist in the system");
			request.setAttribute("urlText", "go back to search page");
			request.setAttribute("url","/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp");	
			
			//forward the request to user massage page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
            requestDispatcher.forward(request, response);

		} finally {
			if (session != null) {
				session.logout();
			}
		}
	}   	  	    
}