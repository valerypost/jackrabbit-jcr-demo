<%@ page 
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="org.apache.jackrabbit.demo.blog.model.BlogEntry,java.util.*"
    
%>
<%
	ArrayList<BlogEntry> blogList = new ArrayList<BlogEntry>();
	
	BlogEntry first = new BlogEntry();
	first.setTitle("first");
	
	BlogEntry second = new BlogEntry();
	second.setTitle("second");
	
	BlogEntry third = new BlogEntry();
	third.setTitle("third");
	
	blogList.add(first);
	blogList.add(second);
	blogList.add(third);
	
	request.setAttribute("blog",blogList);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<c:forEach var="blogEntry" items="${blog}">
		<tr>
			<td width="300">${blogEntry.title}</td>
		</tr>		
	</c:forEach>

</table>

${ 7 + 8 } 
</body>
</html>