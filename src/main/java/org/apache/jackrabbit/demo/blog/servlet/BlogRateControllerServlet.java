package org.apache.jackrabbit.demo.blog.servlet;

import java.io.IOException;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.BlogManager;

/**
 * Servlet implementation class for Servlet: BlogRateControllerServlet
 *
 */
 public class BlogRateControllerServlet extends ControllerServlet {
  	  	    
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			   
			   String UUID = request.getParameter("UUID");
			   String rankStr = request.getParameter("rank");
			   int rank = Integer.parseInt(rankStr);
			   
			  
			   try {
				   
					//log in to the repository and aquire a session   
					session = repository.login(new SimpleCredentials("username","password".toCharArray()));
					
					// Get the username of the current session. "username" attribute is set in LoginController when the user log in to the system.
					String username = (String)request.getSession().getAttribute("username");
					
					BlogManager.rateBlogEntry(UUID,rank,username,session);
					
					//set the attributes which are required by user messae page
					request.setAttribute("msgTitle", "Rating");
					request.setAttribute("msgBody", "Blog Entry was successfully rated");
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