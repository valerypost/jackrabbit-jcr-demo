<%
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
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
<title>Welcome to Jackrabbit-JCR-demo</title>
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />

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
   <td width="970">
    <table>
   	 <tr>
   	  <td width="685" height="800" valign="top"><jsp:include page="/blog/banners/front-middle.html"/></td>
	  <td width="5"></td> 
	  <td width="275" bgcolor="#CCDDFF" valign="top">
	   <form action="/jackrabbit-jcr-demo/user/login" method="post" >
	    <table width="275">
	    <tr>
	     <td height="10"></td>
	    </tr>
	    <tr bgcolor="#316AC5" >
	     <td> <p>Username: <input  name="username" type="text" id="username" class="login" /> </p>
		  	  <p>Password: <input name="password" type="password" id="password" class="login" /> </p>
	     </td>
	    </tr> 
	    <tr  bgcolor="#316AC5">
	     <td align="center"> <input type="submit" name="Submit" value="login" /> <input name="action" type="hidden" id="action" value="login" />  </td>
	    </tr>
	    <tr  bgcolor="#316AC5">
	     <td align="center">  New User ? <a href="/jackrabbit-jcr-demo/blog/newUser.jsp">Register ...</a> </td>
	    </tr>
	   </table>
	  </form>
	  <jsp:include page="/blog/banners/front-right.html"/>
	 </td>
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
