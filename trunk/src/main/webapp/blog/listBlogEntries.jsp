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
		import="java.util.ArrayList,org.apache.jackrabbit.demo.blog.model.*,java.text.*" 
		errorPage="" 
        buffer="8kb"
%>
<%
		ArrayList<BlogEntry>  blogList = (ArrayList<BlogEntry>) request.getAttribute("blogList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/page.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Jackrabbit-JCR-Demo Blog</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css">
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
        <td width="80%">
          <table width="100%">
			   <tr bgcolor="#CCCCCC">
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/blog/addBlogEntry.jsp">Create New Blog Entry</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/BlogController?action=view">View My Blog</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="#">Search Blogs</a></td>
				   <td width="1%"></td>
				   <td width="19%" align="center"> <a href="#">Wiki Pages</a></td>
				   <td width="1%"></td>
                   <td width="19%" align="center"> <a href="#">Logout</a></td>
			   </tr>
			   <tr>
			   		<td width="5"></td>
			   </tr>
		  </table>
        
		<%  for(int i = 0 ; i < blogList.size() ; i++ )   {                          
					BlogEntry blogEntry = blogList.get(i);											%>
		<table width="100%" border="0" cellpadding="0" cellspacing="10" class="plainborder-ash">
              <tr>
                  <td>
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="bodytext11grey">
                           <tr>
                              	<td width="70%" align="left" class="title15"><%= blogEntry.getTitle() %></td>
                            </tr>																															
                            <tr>
                                <td height="10" colspan="2"></td>
						    </tr>		                            													
                            <tr>
                            	<td colspan="2"> <%= blogEntry.getTitle() %> </td>
                            </tr>
		                    <tr>
                                <td height="10" colspan="2"></td>
                            </tr>
                            <tr>
                              	<td height="25" colspan="2" bgcolor="#F3F3F3" class="bodytext11grey" >												
	                                   <strong> by <%= blogEntry.getUser() %>, on 
	                                            <%= (new SimpleDateFormat("EEEEEE, yyyy.MM.dd 'at' HH:mm:ss a")).format(blogEntry.getCreatedOn().getTime()) %>
							  												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
                            </tr>	                            									                            									
							<tr>
								 <td colspan="2" class="hspliter"></td>
							 </tr>
                    	</table>
                    </td>
                </tr> 											      
         </table>			 
		<%   }                                                                 %> 	 
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
