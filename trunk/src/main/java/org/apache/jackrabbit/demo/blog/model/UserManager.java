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
package org.apache.jackrabbit.demo.blog.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.jackrabbit.api.JackrabbitNodeTypeManager;
import org.apache.jackrabbit.core.NamespaceRegistryImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.demo.blog.exception.*;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;

/**
 * <code>UserManager</code> handles the management of user nodes.
 *
 */
public class UserManager {
	
	private Session session = null;
	
	
	public UserManager(Session session) {
		this.session = session;
	}
	
	/**
	 * This method adds a user node of "blog:user" to the repository
	 * @param user the <code>org.apache.jackrabbit.demo.blog.model.User</code> bean class to be added as a user
	 * @throws NonUniqueUsernameException is thrown when the given username already exists
	 */
	public void addUser(User user) throws NonUniqueUsernameException {
		
		Node blogSpaceNode;
		Node userNode;
		
		try {
			blogSpaceNode = session.getRootNode().getNode("blogRoot");
			
			// Check whether a user with a given name already exists
			if (blogSpaceNode.hasNode(user.getUsername())){
		    	throw new NonUniqueUsernameException("Username Already exists");
		    } else {
		    	//creates the user node
		    	userNode = blogSpaceNode.addNode(user.getUsername(),"blog:user");
		    }
			
		    userNode.setProperty("blog:nickname",user.getNickname());
		    userNode.setProperty("blog:email",user.getEmail());
		    userNode.setProperty("blog:password",user.getPassword());
		    
		    session.save();
        
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void addUserOCM (User user) {
		

        try {
			
	        NamespaceRegistryImpl nsReg = (NamespaceRegistryImpl)session.getWorkspace().getNamespaceRegistry();
	        nsReg.safeRegisterNamespace("ocm","http://jackrabbit.apache.org/ocm");
	
    		String[] mappingFiles = {"D:\\Development\\gsoc\\jackrabbit-jcr-demo\\jackrabbit-jcr-demo\\conf\\mapping.xml"};
    		ObjectContentManager objectContentManager = new ObjectContentManagerImpl(session, mappingFiles);
    		
	        NodeTypeManagerImpl ntTypeMgr = (NodeTypeManagerImpl) session.getWorkspace().getNodeTypeManager();
	        
	        File nodeType = new File("D:\\Development\\gsoc\\jackrabbit-jcr-demo\\jackrabbit-jcr-demo\\conf\\custom_nodetypes.xml");
	        FileInputStream nodeTypeStream;
			try {
				nodeTypeStream = new FileInputStream(nodeType);
				ntTypeMgr.registerNodeTypes(nodeTypeStream,JackrabbitNodeTypeManager.TEXT_XML,true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        // Registors the custom node types and namespaces
	        
    			
    		user.setPath("/blogRoot/"+user.getUsername());
    		
    		objectContentManager.insert(user);
    		objectContentManager.save();
       
        } catch (NamespaceException e) {
			e.printStackTrace();
		} catch (UnsupportedRepositoryOperationException e) {

		} catch (AccessDeniedException e) {

		} catch (RepositoryException e) {

		}

	}
	
	/**
	 * This methods returns all users of the system
	 * @return all the users  as an ArrayList of <code>org.apache.jackrabbit.demo.blog.model.User</code>
	 */
	public ArrayList<User> getAllUsers() {
		
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			
			QueryManager queryMgr  = session.getWorkspace().getQueryManager();
			String xPath ="/jcr:root/blogRoot/element(*,blog:user)";
	        Query query = queryMgr.createQuery(xPath,Query.XPATH);
	        QueryResult queryResult = query.execute();
	        
	        
	        for (NodeIterator iter = queryResult.getNodes(); iter.hasNext(); ) {
			
			Node node = iter.nextNode();			
				
				User user = new User();
				user.setUsername(node.getName());
				user.setNickname(node.getProperty("blog:nickname").getString());
				user.setEmail(node.getProperty("blog:email").getString());
				userList.add(user);
				
			
		    }
	              			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return userList;
		
	}
	
	/**
	 * This method handles authentication of the system
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 * @throws RepositoryException
	 */
	public static boolean login(String username, String password, Session session) throws RepositoryException{
		
			QueryManager queryMgr  = session.getWorkspace().getQueryManager();
			String xPath ="/jcr:root/blogRoot/"+username;
	        Query query = queryMgr.createQuery(xPath,Query.XPATH);
	        QueryResult queryResult = query.execute();
	        NodeIterator iter = queryResult.getNodes();
	        
			// If we get a node as a result that means user exists and we can check for password
	        if(iter.hasNext()) {
	        	
	        	Node userNode = iter.nextNode();
	        	
	        	// check whether the given password correct
	        	if (password.equals(userNode.getProperty("blog:password").getString())) {
	        		return true;
	        	} else {
	        		return false;
	        	}
	        	
	       // Username doesn't exist
	        } else {
	        	return false;
	        }
					

		
	}
	
}
