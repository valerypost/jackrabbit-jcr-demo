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
import java.util.Collection;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.User;
import org.apache.jackrabbit.demo.blog.model.UserManager;

/**
 * Servlet implementation class for Servlet: UserViewControllerServlet
 *
 */
 public class UserViewControllerServlet extends ControllerServlet {
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
		Session session;
		try {
			
			// login to the repository
			session = repository.login(new SimpleCredentials("jackrabbit", "jackrabbit".toCharArray()));
			
			//  Creates a UserManager and gets the user list
			UserManager usrMgr = new UserManager(session); 
			Collection<User> usrList = usrMgr.getAllUsers();
			
			// Set the user list as a attribute and send it to user list view pae
			request.setAttribute("userList", usrList);
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/listUsers.jsp");
            requestDispatcher.forward(request, response);
			
		} catch (RepositoryException e) {
			throw new ServletException("Error occured in accessing repository",e);
		}
	}   	  	    
}