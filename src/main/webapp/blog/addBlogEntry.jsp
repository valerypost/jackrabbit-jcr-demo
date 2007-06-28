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
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/page.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Add a blog Entry</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
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
				   <td width="19%" align="center"> <a href="/jackrabbit-jcr-demo/BlogController.do?action=view">View My Blog</a></td>
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
          <form method="post" action="/jackrabbit-jcr-demo/BlogController/">
        	<table width="100%" border="1">
         	 <tr>
            	<td><div align="center">Add a blog entry </div></td>
          	</tr>  
		  	<tr>
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
                		<td>&nbsp;</td>
                		<td>&nbsp;</td>
              		 </tr>
            	</table>
              </td>
            </tr>
            <tr>
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
