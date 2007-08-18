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

import org.apache.jackrabbit.demo.blog.model.BlogEntry;
import org.apache.jackrabbit.demo.blog.model.BlogManager;

/**
 * Servlet implementation class for Servlet: BlogEntryViewController
 *
 */
 public class BlogEntryViewController extends ControllerServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uuid = request.getParameter("uuid");
		
		try {
			//log in to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			BlogEntry blogEntry = BlogManager.getByUUID(uuid,session);
			ArrayList<BlogEntry> blogList = new ArrayList<BlogEntry>();
			blogList.add(blogEntry);
			
			// Set the blogList as a request attribute 
			request.setAttribute("blogList",blogList);
			
			// Forward the request to blog entries page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listBlogEntries.jsp");
            requestDispatcher.forward(request, response);
			
			
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}   	  	    
}