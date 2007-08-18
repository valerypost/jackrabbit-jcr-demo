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
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

public class AlertManager implements EventListener {

	private Repository repository;

	public AlertManager(Repository repo){
		repository = repo;
	}

	public void onEvent(EventIterator events) {
		
		try {
			Session session = repository.login(new SimpleCredentials("user", "password".toCharArray()));
			Node rootNode = session.getRootNode();
			
			while (events.hasNext()) {
				Event event = events.nextEvent();
				String path =  event.getPath();
				path = path.substring(1, path.length());
				Node eventNode = rootNode.getNode(path);
				
				if (eventNode.getPrimaryNodeType().getName().equals("blog:comment")) {
					
					Node commentorNode = eventNode.getProperty("blog:commenter").getNode();
					String commentor = commentorNode.getProperty("blog:nickname").getString();
					
					String title = eventNode.getParent().getProperty("blog:title").getString();
					
					Node userNode = eventNode.getParent().getParent().getParent().getParent();
					String email = userNode.getProperty("blog:email").getString();
					
					EmailSender.send(title, commentor, email, new Date());

				}
		
				
			}
			
		} catch (LoginException e1) {
			e1.printStackTrace();
		} catch (RepositoryException e1) {
			e1.printStackTrace();
		}				
	}

}
