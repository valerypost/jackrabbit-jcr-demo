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


/**
 *  <code>BlogEntry</code> is the bean class used to transfer blog entries.
 *
 */
public class BlogEntry {
	
	//owner of the blog entry 
	private String user; 
    //title of the blog entry
	private String title; 
    //body of the blog entry
	private String content; 
    //current rating or the blog entry
	private long rate; 
    //date of creation
	private Calendar createdOn; 
    // date of publication, at the moment not used as blogs are published as soon as they are created 
	private Calendar publishedOn;
	//date of update,  at the moment not used as updates to blogs are not allowed 
	private Calendar updatedOn; 
	//UUID of blog entry
	private String UUID;
	//Boolean which tells whether the blog entry has an image 
	private boolean hasImage;
	
	private ArrayList<Comment> commentList = new ArrayList<Comment>();
	
	
	public BlogEntry() {
		
	}
	
	/**
	 * @return returns the content of the blog entry
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to be set in the blog Entry
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * @return returns the date which blog was created as a <code>java.util.Calendar</code> object
	 */
	public Calendar getCreatedOn() {
		return createdOn;
	}
	
	/**
	 * @param createdOn sets the date which the blog entry was created 
	 */
	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * @return returns the date which blog was published as a <code>java.util.Calendar</code> object
	 */
	public Calendar getPublishedOn() {
		return publishedOn;
	}
	

	/**
	 * @param publishedOn sets the date which the blog entry was published
	 */
	public void setPublishedOn(Calendar publishedOn) {
		this.publishedOn = publishedOn;
	}
	
	/**
	 * @return returns the current rating of the blog entry
	 */
	public long getRate() {
		return rate;
	}
	
	/**
	 * @param rate the rating of the entry to be set
	 */
	public void setRate(long rate) {
		this.rate = rate;
	}
	
	
	/**
	 * @return get the title of the blog entry
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title title of the blog entry to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the date which blog was last updated as a <code>java.util.Calendar</code> object
	 */
	public Calendar getUpdatedOn() {
		return updatedOn;
	}
	
	/**
	 * @param updatedOn sets the date which the blog entry was last updated
	 */
	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	/**
	 * @return returns the owner of the blog entry
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @param user sets the owner of the blog entry
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return returns the UUID of the blog entry node
	 */
	public String getUUID() {
		return UUID;
	}

	/**
	 * @param uuid sets the UUID of the blog entry node
	 */
	public void setUUID(String uuid) {
		UUID = uuid;
	}
	
	public void addComment(Comment comment) {
		commentList.add(comment);
	}

	public ArrayList<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(ArrayList<Comment> commentList) {
		this.commentList = commentList;
	}

	public boolean isHasImage() {
		return hasImage;
	}

	public void setHasImage(boolean hasImage) {
		this.hasImage = hasImage;
	}
	

	
	
	

}
