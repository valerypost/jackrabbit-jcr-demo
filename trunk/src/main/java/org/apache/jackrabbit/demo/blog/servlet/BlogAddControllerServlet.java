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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * This servlet is the controller for adding blog entries. A Title and a text content is passed
 * with the request as request parameters. Optionally an image attachment and/or a video 
 * attachment also can be present as request parameters. Requests to this servlet must be multi-part
 * encoded.  
 */
 public class BlogAddControllerServlet extends ControllerServlet {
  	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -2175972214551818679L;

	/**
	 * This methods handles POST requests and adds the blog entries according to the parameters in the 
	 * request. Request must be multi-part encoded.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List parameters = null;
		String title = null;
		String content = null;
		FileItem image = null;
		FileItem video = null;
		
		
		// Check whether the request is multipart encoded
		if (ServletFileUpload.isMultipartContent(request)){
			DefaultFileItemFactory fileItemFactory = new DefaultFileItemFactory();
			ServletFileUpload fileUpload =  new ServletFileUpload(fileItemFactory);
			
			try {
				// Parse the request and get the paramter set
				parameters = fileUpload.parseRequest(request);
				Iterator paramIter = parameters.iterator();
				
				// Resolve the parameter set
				while (paramIter.hasNext()) {
						FileItem item = (FileItem) paramIter.next();
						if (item.getFieldName().equalsIgnoreCase("title")) {
							title = item.getString();
						} else if (item.getFieldName().equalsIgnoreCase("content")) {
							content = item.getString();
						} else if (item.getFieldName().equalsIgnoreCase("image")) {
							image = item;
						} else if (item.getFieldName().equalsIgnoreCase("video")) {
							video = item;
						}
				 }
					
			} catch (FileUploadException e) {
				throw new ServletException("Error occured in processing the multipart request",e);
			}
		} else {
			throw new ServletException("Request is not a multi-part encoded request");
		}   
		
		
		try {
	        //log in to the repository and aquire a session
			Session session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			String username = (String)request.getSession().getAttribute("username");
			
			Node blogRootNode = session.getRootNode().getNode("blogRoot");
			Node userNode = blogRootNode.getNode(username);
			
        	// Node to hold the current year
        	Node yearNode;
        	// Node to hold the current month
        	Node monthNode;
        	// Node to hold the blog entry to be added
        	Node blogEntryNode;
        	// Holds the name of the blog entry node
        	String nodeName;
        	
        	// createdOn property of the blog entry is set to the current time. 
    		Calendar calendar = Calendar.getInstance();
    		String year = calendar.get(Calendar.YEAR)+"";
    		String month = calendar.get(Calendar.MONTH)+"";
    		
    		// check whether node exists for current year under usernode and creates a one if not exist
    		if(userNode.hasNode(year)){
    			yearNode = userNode.getNode(year);
        	} else {
        		yearNode = userNode.addNode(year, "nt:folder");
        	}
    		
    		// check whether node exists for current month under the current year and creates a one if not exist
    		if (yearNode.hasNode(month)) {
    			monthNode = yearNode.getNode(month);
    		} else {
    			monthNode = yearNode.addNode(month, "nt:folder");
    		}
    		
    		if(monthNode.hasNode(title)) {		
    			nodeName = createUniqueName(title,monthNode);
    		} else {
    			nodeName = title;
    		}
    			
    		// creates a blog entry under the current month
    		blogEntryNode = monthNode.addNode(nodeName, "blog:blogEntry");
    		blogEntryNode.setProperty("blog:title", title);
    		blogEntryNode.setProperty("blog:content", content);
    		Value date = session.getValueFactory().createValue(Calendar.getInstance());
    		blogEntryNode.setProperty("blog:created",date );
    		blogEntryNode.setProperty("blog:rate", 0);
    		
    		// If the blog entry has an image
    		if (image.getSize() > 0) {
    			System.out.println("Video : "+image.getContentType() );
	    		Node imageNode = blogEntryNode.addNode("image","nt:file");
	    		Node contentNode = imageNode.addNode("jcr:content","nt:resource");
	    		contentNode.setProperty("jcr:data",image.getInputStream());
	    		contentNode.setProperty("jcr:mimeType",image.getContentType());
	    		contentNode.setProperty("jcr:lastModified",date);
    		}
    		
    		// If the blog entry has a video
    		if (video.getSize() > 0) {
    			
    			System.out.println("Video : "+video.getName());
	    		Node imageNode = blogEntryNode.addNode("video","nt:file");
	    		Node contentNode = imageNode.addNode("jcr:content","nt:resource");
	    		contentNode.setProperty("jcr:data",video.getInputStream());
	    		contentNode.setProperty("jcr:mimeType",video.getContentType());
	    		contentNode.setProperty("jcr:lastModified",date);
    		}
    		
    		
    		
    		// persist the changes done
    		session.save();
    		
			//set the attributes which are required by user messae page
			request.setAttribute("msgTitle", "Blog entry added succesfully");
			request.setAttribute("msgBody", "Blog entry titled \"" + title + "\" was successfully added to your blog space");
			request.setAttribute("urlText", "go back to my blog page");
			request.setAttribute("url","/jackrabbit-jcr-demo/blog/view");	
			
			//forward the request to user massage page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
            requestDispatcher.forward(request, response);
			
		
		} catch (LoginException e) {
			throw new ServletException("Login error occured",e);
		} catch (RepositoryException e) {
			throw new ServletException("Repository error occured",e);
		}
	}
	
	/**
	 * Method that creates a unique title when the given title already exists in the parent node
	 * Parent node is the node for the current month
	 */
	private String createUniqueName(String name, Node node) throws RepositoryException {
		
		String unique;
		int i = 0;	

		do {
			unique = name + (i++);
		} while (node.hasNode(unique));
		
		return unique;

	}
	
}