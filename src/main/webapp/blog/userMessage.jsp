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
<%
     String title = (String)request.getAttribute("msgTitle");
	 String message = (String)request.getAttribute("msgBody");
	 String backURLText = (String) request.getAttribute("urlText");
	 String backURL = (String) request.getAttribute("url");

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/page.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Untitled Document</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />
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
        <td width="80%" height="600" align="center"><table width="82%"  border="0">
          <tr>
            <td  align="center" class="Title"> <%= title %> </td>
          </tr>
          <tr>
            <td align="center"> <%= message %></td>
          </tr>
          <tr>
            <td align="center"> <a href="<%=backURL%>" > <%=backURLText%> </a></td>
          </tr>
        </table></td>
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
