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
		
		try {
			 
			//Login to the repository and aquire a session
			Session session = repository.login(new SimpleCredentials("username","password".toCharArray()));
			
			AtomGenerator atomGenerator = new AtomGenerator(session);
			
			Document doc = atomGenerator.generate();
			
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