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

import java.util.Date;

import javax.jcr.observation.*;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Alert manager is the listener class used to generate email alerts for new comments.
 *
 */

public class AlertManager implements EventListener {

 private static Logger log = LoggerFactory.getLogger(AlertManager.class);
 
 private Repository repository;

 
 /**
  * Alert Manager must be initiated with a repository instance as it wants to connect to the repository to
  * get details(email,..) of the actors(commenter, blog entry owner) of the event.  
 * @param repo
 */
public AlertManager(Repository repo){
     repository = repo;
 }

 public void onEvent(EventIterator events) {		
 
     Session session = null;
		
     try {
         session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
         Node rootNode = session.getRootNode();
		
         while (events.hasNext()) {
             
        	 Event event = events.nextEvent();
        	 
        	 // Getting the path of the node added 
	         String path =  event.getPath();
	         
	         // Making the path a relative path by removing initial "/"
	         path = path.substring(1, path.length());
	         
	         // Acquiring the node just added
	         Node eventNode = rootNode.getNode(path);
				
	         // Checking whether the node added is a comment node
             if (eventNode.getPrimaryNodeType().getName().equals("blog:comment")) {
					
            	 //  Node Structure used in the application is as follows. 
            	 // /blogRoot[nt:folder]/user[blog:user]/<yyyy>[nt:folder]/<mm>[nt:folder]/blogEntry[blog:blogEntry]/comment [blog:Comment]
            	 
            	 // Resolving the commenter's nick name
	             Node commentorNode = eventNode.getProperty("blog:commenter").getNode();
	             String commentor = commentorNode.getProperty("blog:nickname").getString();
	             
	             // Resolving the title of the blog entry
	             String title = eventNode.getParent().getProperty("blog:title").getString();
	             
	             //Resolving the blog entry owner's email
	             Node userNode = eventNode.getParent().getParent().getParent().getParent();
	             String email = userNode.getProperty("blog:email").getString();
	             
	             // Sending the alert email to the blog entry owner 
	             EmailSender.send(title, commentor, email, new Date());
             }
         }
     } catch (RepositoryException e) {
         log.error("Alert could not be sent.",e);
     } finally {
    	 // finally logout the session only if we have successfully loged in
    	 if (session != null) {
    		 session.logout();
    	 }
     }
 }
}
