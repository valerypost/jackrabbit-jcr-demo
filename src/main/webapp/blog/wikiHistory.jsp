<%
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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>jackrabbit-jcr-demo -- wiki change history</title>

<!-- css style declarations -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />

<!-- jsp tag-lib declarations -->
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
</head>

<body bgcolor="#5C91C1">
	<table width="100%" height="100%" border="0">
		<tr valign="top">
			<td></td>
	    	<td width="970" align="center">
				<div id="tabs">
					<ul>	
						<li><a href="/jackrabbit-jcr-demo/blog/view">My Blog</a></li>
						<li><a href="/jackrabbit-jcr-demo/blog/addBlogEntry.jsp">New Entry</a></li>
						<li><a href="/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp" >Search</a></li>
						<li><a class="current" href="/jackrabbit-jcr-demo/wiki/view" >Wiki</a></li>
						<li><a href="/jackrabbit-jcr-demo/user/logout" >Logout</a></li>
					</ul>
				</div>
			</td>
			<td></td>
  		</tr> 
		<tr>
			<td  colspan="3" width="5"></td>
		</tr>
		<tr>
		  	<td></td>
            <td  width="970">
            	<table>
            		<tr>
            			<td width="685" height="800" valign="top">
					        <table width="100%">
		                      <tr>
								<td width="15%"  bgcolor="#CCCCCC">Version</td>
								<td width="55%" bgcolor="#CCCCCC">Change Note</td>
								<td width="15%" bgcolor="#CCCCCC">Saved By</td>
								<td width="15%" bgcolor="#CCCCCC">View version</td>
							  </tr>  
							  <c:forEach var="wikiPage" items="${history}">
								<tr bgcolor="#CCDDFF">
									<td>${wikiPage.version}</td>
									<td>${wikiPage.changeNote}</td>
									<td>${wikiPage.savedBy}</td>
									<td><a href="/jackrabbit-jcr-demo/wiki/view?version=${wikiPage.version}">view</a></td>
								</tr>  
							 </c:forEach>
					         </table>      
 						</td>
		 				<td width="5"></td> 
						<td width="275" bgcolor="#CCDDFF" valign="top"><jsp:include page="/blog/banners/front-right.html"/></td>
						<td width="5"></td>
					</tr>
			  </table>
    </td>
	<td></td>
  </tr>
  <tr valign="bottom">
    <td></td>
  </tr>
</table>
</body>
</html>
