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

import org.apache.jackrabbit.api.JackrabbitNodeTypeManager;
import org.apache.jackrabbit.core.NamespaceRegistryImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.servlet.ServletRepository;
import javax.jcr.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * Servlet that initializes the jackrabbit-jcr-demo application.
 * Custom Node types are registed and the node stucture is created if not already done.
 * @since 1.0
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
    protected Session session = null;
    
	/**
     * This method does the initialization such as registoring namesspaces , custom node types and creating the node structure
     */	
    public void init() throws ServletException {
   	
        try {
        	//login to repository and aquire a JCR session
        	session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
        	
        	// Register the custom node types from the CND file if not already registered 
            registerCustomNodeTypes();
            
            // Creates the basic node structure if not created
            createNodeStructure();

            log("JACKRABBIT-JCR-DEMO initialized ...");
            
        } catch (RepositoryException e) {
            throw new ServletException(e);
        } finally {
            session.logout();
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
    	
    	try {
        		
    		Workspace ws = session.getWorkspace();
        	
            // create CND reader from file with CND definitions
            InputStream inputStream = getServletContext().getResourceAsStream(cndPath);   
	        NodeTypeManagerImpl ntTypeMgr = (NodeTypeManagerImpl)ws.getNodeTypeManager();
	        
	        // Registors the custom node types and namespaces
	        ntTypeMgr.registerNodeTypes(inputStream,JackrabbitNodeTypeManager.TEXT_X_JCR_CND,true);
	        
	        // Register a namespace to be used with in the program
	        // ex. for a username robert we can use demo:robert
	        NamespaceRegistryImpl nsReg = (NamespaceRegistryImpl)session.getWorkspace().getNamespaceRegistry();
	        nsReg.safeRegisterNamespace("demo","http://code.google.com/p/jackrabbit-jcr-demo");
	       

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
    		
    		// creates the root node of the system if not already created
    		if ( !rootNode.hasNode("blogRoot")) {
    			rootNode.addNode("blogRoot","nt:folder");
    		}
    		
    		// Created the library node if not already created
    		if (!rootNode.hasNode("library")) {
    			rootNode.addNode("library","nt:folder");
    		}
    		
    		session.save();
    		
    	} catch (RepositoryException e) {
    		throw new ServletException( "Failed to create node structure ", e);
    	}
    	
    }


}
