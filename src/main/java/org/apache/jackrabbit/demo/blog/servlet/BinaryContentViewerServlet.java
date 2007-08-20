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
import java.io.InputStream;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jackrabbit.servlet.ServletRepository;

/**
 * This servlet is used to retrive the binary content which are stored in the repository.
 * URI to the content should be of the format BINARY_CONTENT_PREFIX/XXX/UUID
 * BINARY_CONTENT_PREFIX is "/jackrabbit-jcr-demo/repo/"
 * XXX is the type of the content. eg: image or video
 * UUID is the UUID of the blog entry which the binay content belongs
 *
 */
 public class BinaryContentViewerServlet extends ControllerServlet {
	 
  // prefix of all URIs to binary content
  private final String BINARY_CONTENT_PREFIX = "/jackrabbit-jcr-demo/repo/";
	 
  /**
  * Serial version UID.
  */
  private static final long serialVersionUID = 2747580835785644007L;
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   String URI = request.getRequestURI();
		
   // Extracting the UUID and the type of the content from the given URI
   String reducedURI = URI.substring(BINARY_CONTENT_PREFIX.length(),URI.length());
   String type = reducedURI.substring(0,reducedURI.indexOf("/"));
   String uuid = reducedURI.substring(reducedURI.indexOf("/") + 1 ,reducedURI.length());
		
   //Removing the addtional .flv part in URIs in video requests
   if(type.trim().equalsIgnoreCase("video")) {
	uuid = uuid.substring(0, uuid.length()- ".flv".length());
   }
		
   try {
			
    //Login to the repository and aquire a session
	session = repository.login(new SimpleCredentials("username","password".toCharArray()));

	// Blog entry node is accquired using the UUID
	Node blogEntryNode = session.getNodeByUUID(uuid);
			
	//Check whether the blog entry really have an image node
	if (blogEntryNode.hasNode(type)){
			
	 // Get the image node from the blog entry	
	 Node imageNode = blogEntryNode.getNode(type);
	 Node ntFileNode = imageNode.getNode("jcr:content");
	
	 //Set the content type of the resopnse
	 if (ntFileNode.hasProperty("jcr:mimeType")) {
	  response.setContentType(ntFileNode.getProperty("jcr:mimeType").getString());
	 }
				
	 InputStream is = ntFileNode.getProperty("jcr:data").getStream();
	 ServletOutputStream out = response.getOutputStream();
	
	 int buffer = is.read();
	 while (buffer != -1) {
	  out.write(buffer);
	  buffer = is.read();
	 }
	 out.flush();		
		
	
	}
   }catch (RepositoryException e) {
	   throw new ServletException("Error in accessing the repository",e);
   } finally {
	   if (session != null) {
		   session.logout();
	   }
   }
	}
  
  	 
}