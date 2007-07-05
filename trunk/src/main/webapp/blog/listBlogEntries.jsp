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
<%@ page contentType="text/html; charset=iso-8859-1" 
		language="java" 
		errorPage="" 
        buffer="8kb"
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Jackrabbit-JCR-Demo Blog</title>

<!-- comment java script -->
<script type="text/javascript" src="/jackrabbit-jcr-demo/scripts/comment.js"></script>

<!-- jackrabbit-jcr-demo main css -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css">

<!-- jsp tag-lib declarations -->
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
</head>

<body>
	<table width="100%" height="100%" border="0">
		<tr valign="top">
		<td></td>
	    	<td width="970" align="center">
				<div id="tabs">
					<ul>	
						<li><a class="current" href="/jackrabbit-jcr-demo/BlogController?action=view">My Blog</a></li>
						<li><a href="/jackrabbit-jcr-demo/blog/addBlogEntry.jsp">New Entry</a></li>
						<li><a href="/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp" >Search</a></li>
						<li><a href="#" >Wiki</a></li>
						<li><a href="/jackrabbit-jcr-demo/LoginController?action=logout" >Logout</a></li>
					</ul>
				</div>
			</td>
			<td></td>
  		</tr>
  		<tr>
  			<td></td>
    		<td width="970" valign="top" >
				<table width="100%" height="100%" border="0">
      				<tr>
        				<td  width="685" height="800" valign="top"> 
        				<c:if test="${ownBlog eq true}">
						<div class="lt"></div>
						<div class="lbox">
						Welcome ${username}, 
						<h1 align="center"> Jackrabbit-jcr-demo </h1>
						</div>       
						</c:if>
						<c:forEach var="blogEntry" items="${blogList}">                 									
							<table width="100%" border="0" cellpadding="0" cellspacing="10" class="blog_entry_complete">
              					<tr>
                 					 <td>
                      					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="blog_entry_inside" >
                           					<tr class="bodytext11grey">
                              					<td width="70%" align="left" class="title15">${blogEntry.title}</td>
                            				</tr>																															
                           					 <tr>
                                				<td height="10" colspan="2"></td>
						    				</tr>	
						    				<tr>
						    					<td> 
						    						<c:if test="${blogEntry.hasImage eq true}">
						    							<img src="/jackrabbit-jcr-demo/repo/images/${blogEntry.UUID}" class="image"></img>
						    						</c:if> 
						    					    ${blogEntry.content}
						    					</td>
                            				</tr>
		                    				<tr>
                                				<td height="10" colspan="2"></td>
                            				</tr>
                           					 <tr>
                              					<td height="25" colspan="2"  class="bodytext11grey" >												
	                                				by <strong>${blogEntry.user}</strong>, 
	                                				on <fmt:formatDate type="both" dateStyle="full" value="${blogEntry.createdOn.time}"/>
												</td>
                            				</tr>
                            					<c:forEach var="comment" items="${blogEntry.commentList}">
                            				<tr>
                            					<td height="10" bgcolor="#FFF"></td>                            	
                            				</tr>
                            				<tr bgcolor="#F3F3F3">
                            					<td><strong>${comment.commenter}</strong> says,</td>
                            				</tr>
                            				<tr bgcolor="#F3F3F3">
                            					<td>${comment.content}</td>
                            				</tr>
                            					</c:forEach>
                               					<c:if test="${blogEntry.user ne username}">
                            				<tr>
                            					<td height="10" bgcolor="#FFF"></td>
                            				</tr>
	                       				    <tr align="center" bgcolor="#F3F3F3">
	                            				<td>
	                            					<div id="${blogEntry.UUID}">
	                            						<input type='button' name='Button' value='Comment' onClick="addComment('${blogEntry.UUID}');"/>
	                            					</div>
	                           		 		   </td>
	                            		 </tr>
                            					</c:if>                           									
                    			</table>
                    		</td>
                		</tr> 											      
        		 </table>	
         	</c:forEach>	 
	     </td>
        <td width="5"></td>
		<td width="275" bgcolor="#CCCCCC"></td>
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
