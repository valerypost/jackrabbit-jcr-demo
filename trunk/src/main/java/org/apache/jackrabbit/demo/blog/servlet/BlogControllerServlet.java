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
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.exception.InvalidUserException;
import org.apache.jackrabbit.demo.blog.model.BlogEntry;
import org.apache.jackrabbit.demo.blog.model.BlogManager;
import org.apache.jackrabbit.servlet.ServletRepository;

/**
 * Servlet that controlls the flow of blog related actions
 *
 */
 public class BlogControllerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	 /**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -3589125567059897757L;
	
	/**
	 * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	 */
	protected final Repository repository = new ServletRepository(this); 
	 
	
	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	}  	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// JCR session used to access the repository
		Session session = null;
		
		// Get the action which the client want to perform from the request
		String action = request.getParameter("action");
		
		// throws an exception if no action is specified in the request
		if( action == null) {
			throw new ServletException("Action is not specified in the request");
		}
	
		// Get the username of the current session. "username" attribute is set in LoginController when the user log in to the system.
		String username = (String)request.getSession().getAttribute("username");
		
		// If the action is "add" , user wants to add a blog entry
		if(action.trim().equals("add")) {
			
			// Get the title and the content of the blog entry from the request
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			try {
				
				// log in to the repository and aquire a session
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
				BlogManager.addBlogEntry(username,title,content,session);
				
				//set the attributes which are required by user messae page
				request.setAttribute("msgTitle", "Blog entry added succesfully");
				request.setAttribute("msgBody", "Blog entry titled \"" + title + "\" was successfully added to your blog space");
				request.setAttribute("urlText", "go back to my blog page");
				request.setAttribute("url","/jackrabbit-jcr-demo/BlogController?action=view");	
				
				//forward the request to user massage page
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
				
			} catch (LoginException e) {
				// Log the exception and throw a ServletException
				log("Couldn't log in to the repository",e);
				throw new ServletException("Couldn't log in to the repository");
			} catch (RepositoryException e) {
				// Log the exception and throw a ServletException
				log("Error occured while accessing the repository",e);
				throw new ServletException("Error occured while accessing the repository");
			} finally {
				//TODO this will be called after a login exception or with null session. Handle the session logout in a better way
				session.logout();
			}
		}
		
	   if (action.trim().equals("view")) {
		   
			try {
				
				// log in to the repository and aquire a session
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
				
				// Get a ArrayList of blog entries of user
				ArrayList<BlogEntry> blogList = BlogManager.getByUsername(username,session);
				
				//TODO Do something like paging when the number of blog entries returned is large
				
				// Set the blogList as a request attribute 
				request.setAttribute("blogList",blogList);
				
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
	   
	   if (action.trim().equals("search")) {
		   
			// Get the title and the content of the blog entry from the request
			String type = request.getParameter("type");
			
			try {
				//log in to the repository and aquire a session
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
				
				// Get a ArrayList of blog entries of user
				ArrayList<BlogEntry> blogList = null;
				
				// Check if the search is done by username of the blog owner
				if (type.trim().equals("ByName")) {
					String searchName = request.getParameter("searchName");
					blogList = BlogManager.getByUsername(searchName,session);
				
                // Check if the search is done by content of the blog entry
				} else if (type.trim().equals("ByContent")) {
					String searchText = request.getParameter("searchText");
					blogList = BlogManager.getByContent(searchText,session);
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
						
				
			} catch (LoginException e) {
				// Log the exception and throw a ServletException
				log("Couldn't log in to the repository",e);
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
				session.logout();
			}
	   }
	   
	   if (action.trim().equals("comment")) {
		   
		   String UUID = request.getParameter("UUID");
		   String comment = request.getParameter("comment");
		  
		   try {
			   
				//log in to the repository and aquire a session   
				session = repository.login(new SimpleCredentials("username","password".toCharArray()));
				
				BlogManager.addComment(UUID,comment,username,session);
				
				//set the attributes which are required by user messae page
				request.setAttribute("msgTitle", "Blog Comment");
				request.setAttribute("msgBody", "Your comment was successfully added to the blog entry");
				request.setAttribute("urlText", "go back to blog page");
				request.setAttribute("url","/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp");	
				
				//forward the request to user massage page
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
			
		
		   } catch (LoginException e) {
				// Log the exception and throw a ServletException
				log("Couldn't log in to the repository",e);
				throw new ServletException("Couldn't log in to the repository",e);
		   } catch (RepositoryException e) {
				// Log the exception and throw a ServletException
				log("Error occured while accessing the repository",e);
				throw new ServletException("Error occured while accessing the repository",e);
		   } finally {
			   session.logout();
		   }
		   
		      
	   }
	   
	   

	}   	  	    
}