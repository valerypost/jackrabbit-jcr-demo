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
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AtomGenerator {
	
	private Document doc;
	private Session session;
	
	public AtomGenerator(Session session) {
		this.session = session;
	}
	
	public Document generate(String category, String sub) throws DOMException,RepositoryException  {
		
		doc = createDomDocument();
		doc.setXmlVersion("1.0");
		doc.setXmlStandalone(true);
		
		Element feed = doc.createElement("feed");
		feed.setAttribute("xmlns", "http://www.w3.org/2005/Atom");
		
		fillMetadata(feed, category, sub);
	
		createFeed(feed,category,sub);
		
		doc.appendChild(feed);
		return doc;
	}
	
    private Document createDomDocument() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            return doc;
        } catch (ParserConfigurationException e) {
        }
        return null;
    }
    
    private void addEntry(javax.jcr.Node node, Element feed)throws RepositoryException {
    	
    	 Element entry = doc.createElement("entry");
    	 
    	 Element title = doc.createElement("title");
    	 title.setTextContent(node.getProperty("blog:title").getString());
    	 
    	 Element link = doc.createElement("link");
    	 link.setAttribute("href","/jackrabbit-jcr-demo/blog/viewEntry?uuid="+node.getUUID());
    	 
    	 Element id = doc.createElement("id");
    	 id.setTextContent("urn:uuid:"+node.getUUID());
    	 
    	 Element updated =  doc.createElement("updated");
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
    
    
    public void print(Document doc, OutputStream out) {
    
    TransformerFactory tf = TransformerFactory.newInstance();
    tf.setAttribute("indent-number", new Integer(4));
    Transformer t;
	
    try {
		t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty(OutputKeys.METHOD, "xml");
		t.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
		t.transform(new DOMSource(doc), new StreamResult(out));
	} catch (TransformerConfigurationException e) {
		e.printStackTrace();
    } catch (TransformerException e) {
		e.printStackTrace();
	}

    }

    
    private void createFeed(Element feed, String category, String specific )throws RepositoryException {
		
		// Aquire an QueryManager from the current JCR session
		QueryManager queryMgr = session.getWorkspace().getQueryManager();
		
		ValueFactory factory = session.getValueFactory();
		
		Calendar calendar = Calendar.getInstance();
		String iso8601To   = factory.createValue(calendar).getString();
		
		String xPath;
		
		if (category.equalsIgnoreCase("user")) {
			
			calendar.add(Calendar.DAY_OF_YEAR, -31);
			String iso8601From = factory.createValue(calendar).getString();
			
	    	Node userNode = session.getNodeByUUID(specific);
	    	String username = userNode.getName();
			
			// XPath query to retrive all blog entries of the current user in the descending order by the created date
			xPath ="/jcr:root/blogRoot/"+username+"//*[@blog:created > xs:dateTime('"+ iso8601From +"') and @blog:created < xs:dateTime('"+ iso8601To +"') ]" +
					"order by @blog:created descending";
			
		} else {
			
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			String iso8601From = factory.createValue(calendar).getString();
			
			// XPath query to retrive all blog entries of the current user in the descending order by the created date
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
   	 	DateFormat dateFormat   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
   	 	updated.setTextContent(dateFormat.format(new Date()).toString());
		
		feed.appendChild(title);
		feed.appendChild(subtitle);
		feed.appendChild(link);
		feed.appendChild(updated);
		feed.appendChild(author);
		feed.appendChild(id);
    }
}
