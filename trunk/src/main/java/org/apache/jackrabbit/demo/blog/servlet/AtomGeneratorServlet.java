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

import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.model.AtomGenerator;
import org.apache.jackrabbit.servlet.ServletRepository;
import org.w3c.dom.Document;

/**
 * Servlet implementation class for Servlet: AtomGeneratorServlet
 *
 */
 public class AtomGeneratorServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 3568776968199394035L;
	
	/**
	  * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	  */
	  protected final Repository repository = new ServletRepository(this); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String URI = request.getRequestURI();
		int index1 = URI.lastIndexOf("/");
		int index2 = URI.lastIndexOf("/",index1-1);
		
		String id = URI.substring(index1+1, URI.length());
		String category = URI.substring(index2+1,index1);
		
		try {
			 
			//Login to the repository and aquire a session
			Session session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			AtomGenerator atomGenerator = new AtomGenerator(session);
			
			Document doc = atomGenerator.generate(category,id);
			
			response.setContentType("application/atom+xml");
			atomGenerator.print(doc, response.getOutputStream());
			
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}   	  	    
}