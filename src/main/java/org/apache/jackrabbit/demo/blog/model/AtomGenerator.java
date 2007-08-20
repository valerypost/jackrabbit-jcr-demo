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

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class used to generate Atom syndication feeds.
 *
 */
public class AtomGenerator {
	
	private Document doc;
	private Session session;
	
	
	/**
	 * Atom generator is initialized with a session to access repository as 
	 * it need to gather date from the repository 
	 * @param session Logged in session to repository
	 */
	public AtomGenerator(Session session) {
		this.session = session;
	}
	
	/**
	 * This method generates an Atom feed when a main and a sub category given
	 * @param category Main category of the generated of the feed to generate 
	 * eg. general,user,...
	 * @param sub More specific sub category of the feed to generate eg. user uuid
	 * @return Generated atom feed 
	 * @throws DOMException if an error occured in generating the DOM XML document
	 * @throws RepositoryException if an error occuered in accessing the repository
	 */
	public Document generate(String category, String sub) throws Exception  {
		
		// Create the DOM XML document
		doc = createDomDocument();
		doc.setXmlVersion("1.0");
		doc.setXmlStandalone(true);
		
		// All the elements in the feed must have the namespace "http://www.w3.org/2005/Atom"
		Element feed = doc.createElement("feed");
		feed.setAttribute("xmlns", "http://www.w3.org/2005/Atom");
		
		// Fill the feed with the meta data required by the Atom Syndication Format.
		fillMetadata(feed, category, sub);
	
		// Create the feed element and fill it with the appropriate data 
		createFeed(feed,category,sub);
		
		doc.appendChild(feed);
		return doc;
	}
	

    /**
     * This method fiils metadata of Atom XML document 
     * @param feed Feed element of the Atom feed
     * @param category Main category of the generated of the feed to generate 
     * @param specific More specific sub category of the feed to generate
     * @throws RepositoryException if an error occurs while accessing the repository
     */
    private void fillMetadata(Element feed,String category, String specific ) throws RepositoryException{
		
    	Element title;
    	Element subtitle;
    	Element author;
    	Element id;
    	Element link;
    	Element updated;
    	
    	
    	if (category.equalsIgnoreCase("user")) {
    		
	    	Node userNode = session.getNodeByUUID(specific);
	    	String username = userNode.getName();
	    	String userNickname = userNode.getProperty("blog:nickname").getString();
	
	    	title = doc.createElement("title");
			title.setTextContent(userNickname+"'s Blog");
			
			author = doc.createElement("author");
			Element name = doc.createElement("name");
			name.setTextContent(username);
			author.appendChild(name);
			
			id = doc.createElement("id");
			id.setTextContent("jackrabbit-jcr-demo/"+username);
		
    	} else {
    		
        	title = doc.createElement("title");
    		title.setTextContent("Jackrabbit-jcr-demo");
    		
    		author = doc.createElement("author");
    		Element name = doc.createElement("name");
    		name.setTextContent("Jackrabbit community");
    		author.appendChild(name);
    		
    		id = doc.createElement("id");
    		id.setTextContent("jackrabbit-jcr-demo/recent");
    		
    	}
    	
		link = doc.createElement("link");
		link.setAttribute("href","http://www.jackrabbit.org/");
		
		subtitle = doc.createElement("subtitle");
		subtitle.setTextContent("Recent blogs");
		
   	 	updated =  doc.createElement("updated");
   	 	
   	    // Timestamps in Atom must conform to RFC 3339.
   	 	DateFormat dateFormat   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
   	 	updated.setTextContent(dateFormat.format(new Date()).toString());
		
		feed.appendChild(title);
		feed.appendChild(subtitle);
		feed.appendChild(link);
		feed.appendChild(updated);
		feed.appendChild(author);
		feed.appendChild(id);
    }
    
    /**
     * This method creates the feed element of the Atom XML document 
     * @param feed Feed element of the Atom feed
     * @param category Main category of the generated of the feed to generate 
     * @param specific More specific sub category of the feed to generate
     * @throws RepositoryException if an error occurs while accessing the repository
     */
    private void createFeed(Element feed, String category, String specific )throws RepositoryException {
		
		// Aquire an QueryManager from the current JCR session
		QueryManager queryMgr = session.getWorkspace().getQueryManager();
		
		ValueFactory factory = session.getValueFactory();
		
		Calendar calendar = Calendar.getInstance();
		String iso8601To   = factory.createValue(calendar).getString();
		
		String xPath;
		
		if (category.equalsIgnoreCase("user")) {
			
			// When the category is user, blog entries with in last 31 days are included in the feed
			calendar.add(Calendar.DAY_OF_YEAR, -31);
			String iso8601From = factory.createValue(calendar).getString();
			
			// Subcategory is used to identify which user's blog entries should be listed in the feed
	    	Node userNode = session.getNodeByUUID(specific);
	    	String username = userNode.getName();
			
			// XPath query to retrive blog entries in given period of the given user in the descending order by the created date
			xPath ="/jcr:root/blogRoot/"+username+"//*[@blog:created > xs:dateTime('"+ iso8601From +"') and @blog:created < xs:dateTime('"+ iso8601To +"') ]" +
					"order by @blog:created descending";
			
		} else {
			
			// If the category is not "user", then return the blog entries of all users created within last 7 days
			
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			String iso8601From = factory.createValue(calendar).getString();
			
			// XPath query to retrive  blog entries in given period of all users in the descending order by the created date
			xPath ="/jcr:root/blogRoot//*[@blog:created > xs:dateTime('"+ iso8601From +"') and @blog:created < xs:dateTime('"+ iso8601To +"') ]" +
					"order by @blog:created descending";
		}

        Query query = queryMgr.createQuery(xPath,Query.XPATH);
        
        QueryResult queryResult = query.execute();
        NodeIterator iter = queryResult.getNodes(); 
        
        while (iter.hasNext()) {
        	addEntry(iter.nextNode(),feed);
        }
        
    }
    
    /**
     *  This method creates and adds an entry element for a perticular blog entry to the 
     *  Atom feed when the node containg the blog entry is given. 
     * @param node JCR Node containing the data of blog Entry
     * @param feed Parent feed element of the Atom feed
     * @throws RepositoryException if an error occurs while accessing the repository
     */
    private void addEntry(javax.jcr.Node node, Element feed)throws RepositoryException {
    	
    	 Element entry = doc.createElement("entry");
    	 
    	 Element title = doc.createElement("title");
    	 title.setTextContent(node.getProperty("blog:title").getString());
    	 
    	 Element link = doc.createElement("link");
    	 link.setAttribute("href","/jackrabbit-jcr-demo/blog/viewEntry?uuid="+node.getUUID());
    	 
    	 Element id = doc.createElement("id");
    	 id.setTextContent("urn:uuid:"+node.getUUID());
    	 
    	 Element updated =  doc.createElement("updated");
    	 
    	 // Timestamps in Atom must conform to RFC 3339.
    	 DateFormat dateFormat   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    	 updated.setTextContent(dateFormat.format(node.getProperty("blog:created").getDate().getTime()).toString());        
    	 
    	 Element summary =  doc.createElement("summary");
    	 summary.setTextContent(node.getProperty("blog:content").getString());
    	 
    	 entry.appendChild(title);
    	 entry.appendChild(link);
    	 entry.appendChild(id);
    	 entry.appendChild(updated);
    	 entry.appendChild(summary);
    	
    	 feed.appendChild(entry);
    }
    
    
    /**
     * This method prints a XML document to the given output stream
     * @param doc XML document that should be printed
     * @param out Output stream to which the document should be printed
     */
    public void print(Document doc, OutputStream out) throws Exception {
    
    TransformerFactory factory = TransformerFactory.newInstance();
    factory.setAttribute("indent-number", new Integer(4));
    Transformer transformer;
	
		transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
		transformer.transform(new DOMSource(doc), new StreamResult(out));

    }
    
    /**
     * This method is used to create an empty DOM XML document
     * @return empty XML document
     */
    private Document createDomDocument()throws ParserConfigurationException{

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            return doc;
    }
}
