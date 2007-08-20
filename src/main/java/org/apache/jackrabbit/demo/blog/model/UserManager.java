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

import java.util.Collection;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;


import org.apache.jackrabbit.demo.blog.exception.*;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;


/**
 * <code>UserManager</code> handles the management of user nodes.
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
	public void addUser(User user) throws NonUniqueUsernameException, RepositoryException {
		
		Node blogSpaceNode;
		Node userNode;
		
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
        
	}
	
	
	
	public void addUserOCM (User user) throws NonUniqueUsernameException {
			
    	ObjectContentManager objectContentManager = new ObjectContentManagerImpl(session, Config.OCM_MAPPPINGS);
    	String path = "/blogRoot/"+user.getUsername();
    			
    	if (objectContentManager.objectExists(path)) {
    		throw new NonUniqueUsernameException("Username Already exists");
    	}
    			
    	user.setPath(path);
        objectContentManager.insert(user);
    	objectContentManager.save();

    		
	}
	
	/**
	 * This methods returns all users of the system
	 * @return all the users  as an ArrayList of <code>org.apache.jackrabbit.demo.blog.model.User</code>
	 */
	public  Collection<User> getAllUsers() {
			
			ObjectContentManager objectContentManager = new ObjectContentManagerImpl(session, Config.OCM_MAPPPINGS);
			QueryManager queryMgr  = objectContentManager.getQueryManager();
			Filter filter = queryMgr.createFilter(User.class);
			filter.setScope("/blogRoot/");
			Query query = queryMgr.createQuery(filter);
			
			return (Collection<User>) objectContentManager.getObjects(query);

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
		
		Node userNode = getUserNode(username,session);
	        	        	
    	// check whether the given password correct
    	if (userNode != null && password.equals(userNode.getProperty("blog:password").getString())) {
    		return true;
    	} else {
    		return false;
    	}
	        	
	
	}
	
	
	public static String getUUID(String username, Session session) throws RepositoryException{
		
		Node userNode = getUserNode(username,session);
		
		if (userNode != null) {
			return userNode.getUUID();
		} else {
			return null;
		}
	}
	
	
	public static String getUsername(String uuid, Session session) throws RepositoryException {
		
		Node userNode = session.getNodeByUUID(uuid);
		
		return userNode.getName();
	}
	
	
	private static Node getUserNode(String username, Session session) throws RepositoryException {
			
		//TODO No need of a query here. Can directly get the node by relative path
		javax.jcr.query.QueryManager queryMgr  = session.getWorkspace().getQueryManager();
		String xPath ="/jcr:root/blogRoot/"+username;
		javax.jcr.query.Query query = queryMgr.createQuery(xPath,javax.jcr.query.Query.XPATH);
	    javax.jcr.query.QueryResult queryResult = query.execute();
	    NodeIterator iter = queryResult.getNodes();
	        
	    // Username is unique and if we get a node as a result that means user exists 
	    if(iter.hasNext()) {      	
	       return iter.nextNode(); 	
	    // Username doesn't exist
	    } else {
	        return null;
	    }
	}
	
	
}
