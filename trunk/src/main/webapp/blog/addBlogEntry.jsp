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
<title>Add a blog Entry</title>

<!-- jackrabbit-jcr-demo main css -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />

<!-- jsp tag-lib declarations -->
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
</head>
<c:if test="${username eq null}">
	<c:set var="msgTitle" value="Authentication Required" scope="request"/>
	<c:set var="msgBody" value="Only logged in users are allowed add blog entries." scope="request"/>
	<c:set var="urlText" value="go back to login page" scope="request"/>
	<c:set var="url" value="/jackrabbit-jcr-demo/blog/index.jsp" scope="request"/>
	
	<jsp:forward page="/blog/userMessage.jsp"></jsp:forward>
</c:if>
<body bgcolor="#5C91C1">
	<table width="100%" height="100%" border="0">
		<tr valign="top">
			<td></td>
	    	<td width="970" align="center">
				<div id="tabs">
					<ul>	
						<li><a href="/jackrabbit-jcr-demo/blog/view">My Blog</a></li>
						<li><a class="current" href="/jackrabbit-jcr-demo/blog/addBlogEntry.jsp">New Entry</a></li>
						<li><a href="/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp" >Search</a></li>
						<li><a href="/jackrabbit-jcr-demo/wiki/view" >Wiki</a></li>
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
					          <form action="/jackrabbit-jcr-demo/blog/add" method="post" enctype="multipart/form-data">
					        	<table width="100%" border="0">
					         	 <tr bgcolor="#CCCCCC">
					            	<td><div align="center">Add a blog entry </div></td>
					          	</tr>  
							  	<tr bgcolor="#CCCCCC">
					              <td>
					            	<table width="100%" border="0">
					              		<tr>
					                		<td width="17%">Title</td>
					                		<td width="83%"><input name="title" type="text" id="title" size="66" /></td>
					             		 </tr>
					              		 <tr>
					                		<td valign="top">Text</td>
					                		<td><textarea name="content" cols="50" rows="10" id="content"></textarea></td>
					              		 </tr>
					              		 <tr>
					                		<td>Image File  </td>
					                		<td><input name="image" type="file" id="image" size="66" /></td>
					              		 </tr>
										 <tr>
					                		<td>Video File  </td>
					                		<td><input name="video" type="file" id="video" size="66" /></td>
					              		 </tr>
					            	</table>
					              </td>
					            </tr>
					            <tr bgcolor="#CCCCCC">
					              <td>
					               	<div align="center">
					              		<input name="action" type="hidden" id="action" value="add" />
					              		<input type="submit" name="Submit2" value="Save" />
					              		<input type="reset" name="Reset" value="Clear" />
					            	</div>
					              </td>
					            </tr>  
					          </table>
					        </form>       
 						</td>
		 				<td width="5"></td> 
						<td width="275" bgcolor="#CCDDFF" valign="top"><jsp:include page="/blog/banners/inside-right.html"/></td>
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
