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
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/page.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Jackrabbit-JCR-Demo Blog</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->

<!-- comment java script -->
<script type="text/javascript" src="/jackrabbit-jcr-demo/scripts/comment.js"></script>

<!-- jackrabbit-jcr-demo main css -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css">

<!-- jsp tag-lib declarations -->
<%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<!-- InstanceEndEditable -->
</head>

<body>
<table width="100%" height="100%" border="0">
  <tr valign="top">
    <td align="left"> <img src="/jackrabbit-jcr-demo/image/asf_logo.gif"/> </td>
	<td align="right"> <img src="/jackrabbit-jcr-demo/image/jackrabbitlogo.gif" /> </td>
  </tr>
  <tr>
    <td valign="top" colspan="2">
	<table width="100%" height="100%" border="0">

      <tr>
        <td width="5"></td>
		<!-- InstanceBeginEditable name="Left" -->
        <td width="100" bgcolor="#CCCCCC"></td>
		<!-- InstanceEndEditable -->
        <td width="5"></td>
		<!-- InstanceBeginEditable name="Middle" -->
        <td width="80%" height="800" valign="top">        
          <table width="100%">
			   <tr bgcolor="#CCCCCC">
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/blog/addBlogEntry.jsp">Create New Blog Entry</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/BlogController?action=view">View My Blog</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp">Search Blogs</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="#">Wiki Pages</a></td>
				   <td width="1%"></td>
                   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/LoginController?action=logout">Logout</a></td>
			   </tr>
			   <tr>
			   		<td width="5"></td>
			   </tr>
		  </table> 
		<c:forEach var="blogEntry" items="${blogList}">                 									
		<table width="100%" border="0" cellpadding="0" cellspacing="10" class="plainborder-ash">
              <tr>
                  <td>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="bodytext11grey">
                           <tr>
                              	<td width="70%" align="left" class="title15">${blogEntry.title}</td>
                            </tr>																															
                            <tr>
                                <td height="10" colspan="2"></td>
						    </tr>		                            													
                            <tr>
                            	<td colspan="2"> ${blogEntry.content}</td>
                            </tr>
		                    <tr>
                                <td height="10" colspan="2"></td>
                            </tr>
                            <tr>
                              	<td height="25" colspan="2" bgcolor="#F3F3F3" class="bodytext11grey" >												
	                                by <strong>${blogEntry.user}</strong>, 
	                                on <fmt:formatDate type="both" dateStyle="full" value="${blogEntry.createdOn.time}"/>
								</td>
                            </tr> 
                            <c:if test="${blogEntry.user ne username}">
                            <tr>
                            	<td height="10"></td>
                            </tr>
	                        <tr align="center" bgcolor="#F3F3F3">
	                            	<td>
	                            		<div id="${blogEntry.UUID}">
	                            			<input type='button' name='Button' value='Comment' onClick="addComment('${blogEntry.UUID}');"/>
	                            		</div>
	                           		 </td>
	                            </tr>
                            </c:if>                           									                            									
							<tr>
								 <td colspan="2" class="hspliter"></td>
							 </tr>
                    	</table>
                    </td>
                </tr> 											      
         </table>	
         <br/>	
         </c:forEach>	 
		</td>
		<!-- InstanceEndEditable -->
        <td width="5"></td>
		<!-- InstanceBeginEditable name="Right" -->
		<td width="100" bgcolor="#CCCCCC"></td>
		<!-- InstanceEndEditable -->
		<td width="5"></td>
      </tr>
    </table>
	</td>
  </tr>
  <tr valign="bottom">
    <td></td>
  </tr>
</table>
</body>
<!-- InstanceEnd --></html>
