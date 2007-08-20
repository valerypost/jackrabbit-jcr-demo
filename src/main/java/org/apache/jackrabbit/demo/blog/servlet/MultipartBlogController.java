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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
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
import org.apache.jackrabbit.value.ValueFactoryImpl;

/**
 * Servlet implementation class for Servlet: MultipartBlogEntryController
 *
 */
 public class MultipartBlogController extends ControllerServlet {
  	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -2175972214551818679L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List parameters = null;
		String title = null;
		String content = null;
		FileItem image = null;
		
		
		// Handle the request if it's encoded in multipart
		if (ServletFileUpload.isMultipartContent(request)){
			DefaultFileItemFactory fileItemFactory = new DefaultFileItemFactory();
			ServletFileUpload fileUpload =  new ServletFileUpload(fileItemFactory);
			
			try {
				parameters = fileUpload.parseRequest(request);
				Iterator paramIter = parameters.iterator();
				
				 while (paramIter.hasNext()) {
						FileItem item = (FileItem) paramIter.next();
					
						if (item.getFieldName().equalsIgnoreCase("title")) {
							title = item.getString();
						} else if (item.getFieldName().equalsIgnoreCase("content")) {
							content = item.getString();
						} else if (item.getFieldName().equalsIgnoreCase("image")) {
							image = item;
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
			session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			String username = (String)request.getSession().getAttribute("username");
			
			// Only logged in users are allowed to create blog entries
			if (username == null) {
				//set the attributes which are required by user messae page
				request.setAttribute("msgTitle", "Authentication Required");
				request.setAttribute("msgBody", "Only logged in users are allowed to add blog entries.");
				request.setAttribute("urlText", "go back to login page");
				request.setAttribute("url","/jackrabbit-jcr-demo/blog/index.jsp");	
				
				//forward the request to user massage page
	            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
	            requestDispatcher.forward(request, response);
	            return;
				
			}
			
			
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
    		Value date = ValueFactoryImpl.getInstance().createValue(Calendar.getInstance());
    		blogEntryNode.setProperty("blog:created",date );
    		
    		// If the blog entry has an image
    		if (image.getSize() > 0) {
	    		Node imageNode = blogEntryNode.addNode("image","nt:file");
	    		Node contentNode = imageNode.addNode("jcr:content","nt:resource");
	    		contentNode.setProperty("jcr:data",image.getInputStream());
	    		contentNode.setProperty("jcr:mimeType",image.getContentType());
	    		contentNode.setProperty("jcr:lastModified",date);
    		}
    		
    		
    		// persist the changes done
    		session.save();
    		
			//set the attributes which are required by user messae page
			request.setAttribute("msgTitle", "Blog entry added succesfully");
			request.setAttribute("msgBody", "Blog entry titled \"" + title + "\" was successfully added to your blog space");
			request.setAttribute("urlText", "go back to my blog page");
			request.setAttribute("url","/jackrabbit-jcr-demo/BlogController?action=view");	
			
			//forward the request to user massage page
            RequestDispatcher requestDispatcher = this.getServletContext().getRequestDispatcher("/blog/userMessage.jsp");
            requestDispatcher.forward(request, response);
			
		} catch (RepositoryException e) {
			throw new ServletException("COuldn't save the blog entry. Error occured accessing the repository",e);
		} finally {
			if(session != null) {
				session.logout();
			}
		}
		
	}
	
	
	private String createUniqueName(String name, Node node) throws RepositoryException {
		
		String unique;
		int i = 0;	

		do {
			unique = name + (i++);
		} while (node.hasNode(unique));
		
		return unique;

	}
	
}