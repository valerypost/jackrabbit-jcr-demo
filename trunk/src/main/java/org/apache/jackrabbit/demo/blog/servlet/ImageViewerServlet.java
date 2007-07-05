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
 * This servlet is used to retrive the image of a blog entry 
 *
 */
 public class ImageViewerServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 2747580835785644007L;
	/**
	 * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	 */
	protected final Repository repository = new ServletRepository(this); 
	 
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getRequestURI();
		String prefix = "/jackrabbit-jcr-demo/repo/images/";
		
		// Extracting the UUID from the given URI
		String uuid = path.substring(prefix.length(),path.length());
		
		try {
			
            //Login to the repository and aquire a session
			Session session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			// Blog entry node is accquired using the UUID
			Node blogEntryNode = session.getNodeByUUID(uuid);
			
			
			//Check whether the blog entry really have an image node
			if (blogEntryNode.hasNode("image")){
				
					// Get the image node from the blog entry	
					Node imageNode = blogEntryNode.getNode("image");
					Node ntFileNode = imageNode.getNode("jcr:content");
					
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
		
					return;
						
			}
				
			} catch (LoginException e) {
				e.printStackTrace();
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
	
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}   	  	    
}