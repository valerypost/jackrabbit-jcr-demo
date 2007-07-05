package org.apache.jackrabbit.demo.blog.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Repository;
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
import org.apache.jackrabbit.servlet.ServletRepository;
import org.apache.jackrabbit.value.ValueFactoryImpl;

/**
 * Servlet implementation class for Servlet: MultipartBlogEntryController
 *
 */
 public class MultipartBlogController extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
  	
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -2175972214551818679L;

	/**
	 * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	 */
	protected final Repository repository = new ServletRepository(this); 

	
	/** 
	 * Method which handles the GET method requests
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}  	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List parameters = null;
		String action = null;
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
						
						if(item.getFieldName().equalsIgnoreCase("action")){
							action = item.getString();
						} else if (item.getFieldName().equalsIgnoreCase("title")) {
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
			
		
		} catch (LoginException e) {
			throw new ServletException("Login error occured",e);
		} catch (RepositoryException e) {
			throw new ServletException("Repository error occured",e);
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