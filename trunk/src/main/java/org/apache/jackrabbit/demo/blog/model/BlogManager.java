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

import java.util.ArrayList;
import java.util.Calendar;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.jackrabbit.demo.blog.exception.InvalidUserException;
import org.apache.jackrabbit.value.ValueFactoryImpl;

/**
 * This class <code>BlogManager</code> handles manupulating blog entries. <code>BlogManager</code> users following node structure
 * /blogRoot [nt:folder]
 * /blogRoot/user [blog:user]
 * /blogRoot/user/<yyyy> [nt:folder]
 * /blogRoot/user/<yyyy>/<mm> [nt:folder]
 * /blogRoot/user/<yyyy>/<mm>/blogEntry [blog:blogEntry]
 * /blogRoot/user/<yyyy>/<mm>/blogEntry/comment [blog:Comment]
 *
 */
public class BlogManager {
	
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(BlogManager.class);
	
	
	public BlogManager() {
	}
	
	
	/**
	 * This method adds a blog entry to the given users blog space
	 * @param username username of the creater of the blog entry
	 * @param title title of the blog entry to be created 
	 * @param content content of the blog entry
	 * @param session JCR session to be used for adding the blogEntry node to user
	 */
	public static void addBlogEntry(String username, String title ,String content, Session session){
			
		try {

			//Only registered users are allowed to add blog entries. So "/blogRoot/<username>" already exists.
	        Node userNode = (Node)session.getItem("/blogRoot/"+username);
	        
	        if(userNode != null) {
	        	
	        	// Node to hold the current year
	        	Node yearNode;
	        	// Node to hold the current month
	        	Node monthNode;
	        	// Node to hold the blog entry to be added
	        	Node blogEntryNode;
	        	
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
	    		
	    		// creates a blog entry under the current month
	    		blogEntryNode = monthNode.addNode(title, "blog:blogEntry");
	    		blogEntryNode.setProperty("blog:title", title);
	    		blogEntryNode.setProperty("blog:content", content);
	    		Value date = ValueFactoryImpl.getInstance().createValue(Calendar.getInstance());
	    		blogEntryNode.setProperty("blog:created",date );
	    		
	    		// persist the changes done
	    		session.save();
	    		   		   	
	        } 
	        	
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param username the user whose blog entries we want to retrive
	 * @param session JCR session to access the repository
	 * @return returns an ArrayList of blog entries of the given user
	 * @throws InvalidUserException if the user is not a valid user of the system
	 */
	public static ArrayList<BlogEntry> getByUsername(String username, Session session)  throws InvalidUserException{
				
		try {
			
			// Check whether the given user is a valid user of the system
			if (!session.itemExists("/blogRoot/"+username)) {
				throw new InvalidUserException(username + " does not exist in blog space ");
			}
			
			// Aquire an QueryManager from the current JCR session
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			
			// XPath query to retrive all blog entries of the current user in the descending order by the created date
			String xPath ="/jcr:root/blogRoot/"+username+"//element(*,blog:blogEntry) order by @blog:created descending";
	        Query query = queryMgr.createQuery(xPath,Query.XPATH);
	        QueryResult queryResult = query.execute();
	        NodeIterator iter = queryResult.getNodes();  
	        
	        // Create an ArrayList of blog entries of the user 
	        ArrayList<BlogEntry> blogEntryList = new ArrayList<BlogEntry>();         
	        while(iter.hasNext()) {
	        	Node  blogEntryNode = iter.nextNode();
	        	BlogEntry blogEntry = new BlogEntry();
	        	blogEntry.setTitle(blogEntryNode.getProperty("blog:title").getString());
	        	blogEntry.setContent(blogEntryNode.getProperty("blog:content").getString());
	        	blogEntry.setCreatedOn(blogEntryNode.getProperty("blog:created").getDate());
	        	blogEntry.setUUID(blogEntryNode.getUUID());  
	        	blogEntry.setUser(username); 	    
	        	
	        	blogEntryList.add(blogEntry);
	        }
	        
	        return blogEntryList;
	        
	        
		} catch (RepositoryException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static ArrayList<BlogEntry> getByDate(String username, Calendar from, Calendar to, Session session ) {
		
		return null;
	}
	
	public static ArrayList<BlogEntry> getByContent(String content,Session session){
		
		try {
		
			// Aquire an QueryManager from the current JCR session
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			
			// XPath query to retrive all blog entries of the current user in the descending order by the created date
			String xPath ="/jcr:root/blogRoot//element(*,blog:blogEntry)[jcr:contains(@blog:content,'" + content + "')] order by @blog:created descending";
	        Query query = queryMgr.createQuery(xPath,Query.XPATH);
	        QueryResult queryResult = query.execute();
	        NodeIterator iter = queryResult.getNodes();  
	        
	        // Create an ArrayList of blog entries of the user 
	        ArrayList<BlogEntry> blogEntryList = new ArrayList<BlogEntry>();         
	        while(iter.hasNext()) {
	        	Node  blogEntryNode = iter.nextNode();
	        	BlogEntry blogEntry = new BlogEntry();
	        	blogEntry.setTitle(blogEntryNode.getProperty("blog:title").getString());
	        	blogEntry.setContent(blogEntryNode.getProperty("blog:content").getString());
	        	blogEntry.setCreatedOn(blogEntryNode.getProperty("blog:created").getDate());
	        	blogEntry.setUser(blogEntryNode.getParent().getParent().getParent().getName());
	        	blogEntry.setUUID(blogEntryNode.getUUID()); 
	        	blogEntryList.add(blogEntry);
	        }
	        
	        return blogEntryList;
        
        
		} catch (RepositoryException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void addComment(String UUID, String comment,String username, Session session){
		
		try {
			Node blogEntryNode = session.getNodeByUUID(UUID);
			Node userNode = (Node)session.getItem("/blogRoot/"+username);	
			Node commentNode =blogEntryNode.addNode("comment","blog:comment");
			
			commentNode.setProperty("blog:content",comment);
			commentNode.setProperty("blog:commenter", userNode.getUUID());
			
			session.save();
			
		} catch (ItemNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
	}

}
