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
<title>${wikiPage.title} -- jcr-jackrabbit-demo -- wiki edit</title>
  <!-- isEmpty java scripts -->
  <script type="text/javascript" src="/jackrabbit-jcr-demo/scripts/isNotEmpty.js"></script>
  <!-- jackrabbit-jcr-demo main css -->
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
					          <form action="/jackrabbit-jcr-demo/wiki/save"  method="post" onsubmit="return isEmpty();"  >
					        	<table width="100%" border="0">
									<tr bgcolor="#CCDDFF"><td colspan="2"><div id="msgArea">
									<c:if test="${msg ne null}">
									${msg}
									</c:if>	
									</div></td></tr>			         	 
									<tr bgcolor="#CCCCCC">
								 	<td width="17%">Title :</td>
					            	<td width="83%"><input type="text" name="title" id="title" value="${wikiPage.title}" size="89"/> </td>
					                </tr> 
									<tr bgcolor="#CCCCCC">
								 	<td width="17%" valign="top">Content :</td>
					            	<td width="83%" valign="top" height="400"><textarea name="content" cols="67" rows="25" id="content">${wikiPage.content}</textarea></td>
					                </tr>
									<tr bgcolor="#CCCCCC">
								 	<td width="17%">Change Note :</td>
					            	<td width="83%"><input type="text" name="changeNote" id="changeNote" size="89"/></td>
					                </tr>
									<tr>
									<td colspan="2" align="center"><input type="submit" value="Save" /><a href="/jackrabbit-jcr-demo/wiki/view"><input type="button" value="Cancel"/></a> </td>
									</tr>
					          </table>
					        </form>       
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
