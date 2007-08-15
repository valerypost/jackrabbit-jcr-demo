package org.apache.jackrabbit.demo.blog.model;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

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
	
	public Document generate() throws DOMException,RepositoryException  {
		
		doc = createDomDocument();
		doc.setXmlVersion("1.0");
		doc.setXmlStandalone(true);
		
		Element feed = doc.createElement("feed");
		feed.setAttribute("xmlns", "http://www.w3.org/2005/Atom");
		
		Element title = doc.createElement("title");
		title.setTextContent("Jackrabbit-JCR-Demo");
		
		Element link = doc.createElement("link");
		link.setAttribute("href","http://www.jackrabbit.org/");
		
		Element updated = doc.createElement("updated");
		updated.setTextContent((new Date()).toLocaleString());
		
		Element author = doc.createElement("author");
		Element name = doc.createElement("name");
		name.setTextContent("Jackrabbit Community");
		author.appendChild(name);
		
		Element id = doc.createElement("id");
		id.setTextContent("jackrabbit-jcr-demo"); 
		
		feed.appendChild(title);
		feed.appendChild(link);
		feed.appendChild(updated);
		feed.appendChild(author);
		feed.appendChild(id);
		
		createFeed(feed);
		
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
    	 link.setAttribute("href","http://example.org/2003/12/13/atom03");
    	 
    	 Element id = doc.createElement("id");
    	 id.setTextContent("urn:uuid:"+node.getUUID());
    	 
    	 Element updated =  doc.createElement("updated");
    	 updated.setTextContent(node.getProperty("blog:created").getDate().getTime().toGMTString());
    	 
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

    
    private void createFeed(Element feed)throws RepositoryException {
		
		// Aquire an QueryManager from the current JCR session
		QueryManager queryMgr = session.getWorkspace().getQueryManager();
		
		ValueFactory factory = session.getValueFactory();
		
		Calendar calendar = Calendar.getInstance();
		String iso8601To   = factory.createValue(calendar).getString();
		
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		String iso8601From = factory.createValue(calendar).getString();
		
		// XPath query to retrive all blog entries of the current user in the descending order by the created date
		String xPath ="/jcr:root/blogRoot//*[@blog:created > xs:dateTime('"+ iso8601From +"') and @blog:created < xs:dateTime('"+ iso8601To +"') ]" +
				"order by @blog:created descending";
        Query query = queryMgr.createQuery(xPath,Query.XPATH);
        
        QueryResult queryResult = query.execute();
        NodeIterator iter = queryResult.getNodes(); 
        
        while (iter.hasNext()) {
        	addEntry(iter.nextNode(),feed);
        }
        
    }
}
