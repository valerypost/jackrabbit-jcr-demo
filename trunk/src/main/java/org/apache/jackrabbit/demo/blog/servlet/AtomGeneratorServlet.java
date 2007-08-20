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

import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.demo.blog.exception.JackrabbitJCRDemoException;
import org.apache.jackrabbit.demo.blog.model.AtomGenerator;
import org.w3c.dom.Document;

/**
 * Controller servlet which handles Atom syndication feed generation
 */
 public class AtomGeneratorServlet extends ControllerServlet {

	 /**
	 * Serial version id
	 */
	private static final long serialVersionUID = 3568776968199394035L;
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException {
			
		// Requests for the Atom feed generation would be of format
		// /jackrabbit-jcr-demo/atom/<category>/<subcategory>
		// eg. /jackrabbit-jcr-demo/atom/user/<user-node-uuid>
		
		
		// Resolving the category and the subcategory from the request URI
		// Slashes "/" are used to split category and subcategory 
		String URI = request.getRequestURI();
		int index1 = URI.lastIndexOf("/");
		int index2 = URI.lastIndexOf("/",index1-1);
		
		String subcategory = URI.substring(index1+1, URI.length());
		String category = URI.substring(index2+1,index1);
		
		try {
				 
			//Login to the repository and aquire a session
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			// Initiate an Atom Generator passing the session as it needs to access the repository to collect required data 
			AtomGenerator atomGenerator = new AtomGenerator(session);
			
			// Pass the category and subcategory and generate the feed
			Document doc = atomGenerator.generate(category,subcategory);
			
			// set the content type of the response 
			response.setContentType("application/atom+xml");
			
			// Prints the generated XML Atom feed to HTTP response 
			atomGenerator.print(doc, response.getOutputStream());
			
			
		} catch (Exception e) {
			throw new JackrabbitJCRDemoException("JJD001",e);
		} finally {
			// finally logout the session only if we have successfully loged in
			if ( session != null) {
				session.logout();
			}
		}
	}  		  	    
}