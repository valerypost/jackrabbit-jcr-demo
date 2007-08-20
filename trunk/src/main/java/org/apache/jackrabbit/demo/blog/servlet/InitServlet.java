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

import org.apache.jackrabbit.demo.blog.exception.ErrorConfig;
import org.apache.jackrabbit.demo.blog.model.Config;

import org.apache.jackrabbit.api.JackrabbitNodeTypeManager;
import org.apache.jackrabbit.core.NamespaceRegistryImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.demo.blog.model.AlertManager;
import org.apache.jackrabbit.servlet.ServletRepository;

import javax.jcr.*;
import javax.jcr.observation.Event;
import javax.jcr.observation.ObservationManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Servlet that initializes the jackrabbit-jcr-demo application.
 * Custom Node types are registed and the node stucture is created if not already done.
 */

public class InitServlet extends HttpServlet {

	 /**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
   
	/**
	 * Repository instance aquired through <code>org.apache.jackrabbit.servlet.ServletRepository</code>
	 */ 
    protected final Repository repository = new ServletRepository(this);
    
    /**
     * JCR Session
     */
    protected static Session session = null;
	
    /**
     * This method does the initialization such as registoring namesspaces , custom node types and creating the node structure
     */	
    public void init() throws ServletException {
   	
        try {
        	//login to repository and aquire a JCR session and we won't logout this session because we want the listeners to be 
        	//active through out the application
        	session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
        	
        	config();
        	
        	ErrorConfig.init();
        	
        	// Register the custom node types from the CND file if not already registered 
            registerCustomNodeTypes();
            
            // Creates the basic node structure if not created
            createNodeStructure();
            
            doInitialCheckin();
            
            // Register the event listener to listen to new comments event
            registerListeners();

            log("JACKRABBIT-JCR-DEMO: initialized ...");
            
        } catch (Exception e) {
			throw new ServletException("Jackrabbit-jcr-demo initialization failed",e);
		}
    }
    

    /**
     * Registors the custome node types for the jackrabbit-jcr-demo application from a given CND file.
     * Location of the CND file is given as an initial parameter 
     * @throws ServletException is thrown when registration not possible.
     */
    private void registerCustomNodeTypes() throws ServletException {
        
    	// location of the CND file is kept as a servelt init parameter in the web.xml
    	String cndPath = getInitParameter("cnd.path");
    	String ocmPath = getInitParameter("ocm.path");
    	
    	try {
        		
    		Workspace ws = session.getWorkspace();
        	
    		NodeTypeManagerImpl ntTypeMgr = (NodeTypeManagerImpl)ws.getNodeTypeManager();
    		
    		//Registors the custom node types and namespaces
            InputStream inputStream = getServletContext().getResourceAsStream(cndPath);   
	        ntTypeMgr.registerNodeTypes(inputStream,JackrabbitNodeTypeManager.TEXT_X_JCR_CND,true);

	        InputStream nodeTypeStream = getServletContext().getResourceAsStream(ocmPath);
			ntTypeMgr.registerNodeTypes(nodeTypeStream,JackrabbitNodeTypeManager.TEXT_XML,true);
	        
	        // Register a namespace to be used with in the program
	        // ex. for a username "nandana" we can use demo:nandana
	        NamespaceRegistryImpl nsReg = (NamespaceRegistryImpl)session.getWorkspace().getNamespaceRegistry();
	        nsReg.safeRegisterNamespace("demo","http://code.google.com/p/jackrabbit-jcr-demo");
	        nsReg.safeRegisterNamespace("ocm","http://jackrabbit.apache.org/ocm");
	        
	        log("JACKRABBIT-JCR-DEMO: Custom Node types registered  ...");
	        
        } catch (RepositoryException e) {
            throw new ServletException( "Failed to registor node types", e);
        } catch (IOException e) {
        	throw new ServletException( "Error occured while accessing the file" + cndPath, e);
        }
        
    }
    
    /**
     * Creates the node strucure is it not already created
     * 
     * @throws ServletException if error
     */
    private void createNodeStructure() throws ServletException{
    	
    	
    	try {
    		
    		Node rootNode = session.getRootNode();
    		Node admin = null;
    		
    		// creates the root node of the system if not already created
    		if ( !rootNode.hasNode("blogRoot")) {
    			Node blogRoot = rootNode.addNode("blogRoot","nt:folder");
    			
    			// Adding the admin user
    			admin = blogRoot.addNode("admin","blog:user");
    			admin.setProperty("blog:nickname","admin");
    			admin.setProperty("blog:email","nandana.cse@gmail.com");
    			admin.setProperty("blog:password","admin");
    			session.save();
    			
    			// Adding the guest user
    			Node guest = blogRoot.addNode("guest","blog:user");
    			guest.setProperty("blog:nickname","guest");
    			
    			// These properties will never be used by the system
    			guest.setProperty("blog:email","guest@guest.lk");
    			guest.setProperty("blog:password","guest");
    		}
    		
    		// Created the library node if not already created
    		if (!rootNode.hasNode("library")) {
    			rootNode.addNode("library","nt:folder");
    		}
    		
    		if (!rootNode.hasNode("wiki")) {
    			Node wiki = rootNode.addNode("wiki","nt:folder");
    			Node frontPage = wiki.addNode("frontPage", "wiki:wikiPage");
    				
    			frontPage.setProperty("wiki:title", "Front Page");
    			frontPage.setProperty("wiki:content","Type the content here");
    			frontPage.setProperty("wiki:savedBy",admin.getUUID());
 
    		}
    		
    		session.save();
    		
    		log("JACKRABBIT-JCR-DEMO: Node Structure created ...");
    		
    	} catch (RepositoryException e) {
    		throw new ServletException( "Failed to create node structure ", e);
    	}
    	
    }

    /**
     * This method registers the listener <code> org.apache.jackrabbit.demo.blog.model.AlertManager </code> with the default
     * workspace. This listener listens to events of adding new comments to blog entries.
     * @throws RepositoryException if an error occur in the repository 
     */
    private void registerListeners() throws RepositoryException{
    	
    	Workspace ws = session.getWorkspace();
    	ObservationManager observationMgr = ws.getObservationManager();
	    AlertManager alertMgr = new AlertManager(repository);
	    observationMgr.addEventListener(alertMgr,Event.NODE_ADDED,"/blogRoot",true,null,null, false);
	    
        log("JACKRABBIT-JCR-DEMO: Listeners registered ...");
	    
    }
    
    private void doInitialCheckin() throws RepositoryException {
    	
    	Node rootNode = session.getRootNode();
    	
    	Node frontPage = rootNode.getNode("wiki/frontPage");
    	
        if(frontPage.isCheckedOut()) {
               frontPage.checkin();
        }
    	
    }
    
    private void config() {
    	Config.OCM_MAPPPINGS = new String[]{getServletConfig().getServletContext().getRealPath("")+getInitParameter("config.ocm.mapping_xml.path")};
    
    	Config.MAIL_SERVER = getInitParameter("config.mail.mail_server");
    	Config.FROM_EMAIL = getInitParameter("config.mail.from_email");
    	
    	ErrorConfig.ERROR_CODE_XML = getServletConfig().getServletContext().getRealPath("")+getInitParameter("error_config.error_codes_xml.path");
    
    }
    
    
}
