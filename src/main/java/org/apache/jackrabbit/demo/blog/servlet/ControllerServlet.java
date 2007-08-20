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

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.servlet.ServletRepository;


/**
 * Controller class which is used as the base class for all controller servlets
 */
abstract public class ControllerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{
	
	/**
	 * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	 */
	protected final Repository repository = new ServletRepository(this); 
	
	// JCR session used to access the repository
	protected Session session = null;
	
	
	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Delegate the work to doPost(HttpServletRequest request, HttpServletResponse response) method
		doPost(request,response);
	} 
	
	protected void responseMessage(HttpServletRequest request,HttpServletResponse response, String title, String message, String URLText, String URL) throws ServletException, IOException{
		
		//set the attributes which are required by user messae page
		request.setAttribute("msgTitle", title);
		request.setAttribute("msgBody", message);
		request.setAttribute("urlText", "go back to "+ URLText);
		request.setAttribute("url",URL);	
		
		//forward the request to user massage page
        RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
        requestDispatcher.forward(request, response);
		
	}

}
