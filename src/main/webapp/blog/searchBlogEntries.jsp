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
<title>Search blog entries</title>
<!-- InstanceEndEditable -->
<!-- InstanceBeginEditable name="head" -->
<link href="../css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />
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
        	<table width="100%" border="0" >
         	 <tr>
            	<td><div align="center">Search blog entries </div></td>
          	</tr>  
			<tr>
				<td width="5"></td>
			</tr>
		  	<tr>
              <td>
			 <form method="post" action="/jackrabbit-jcr-demo/BlogController/">
            	<table width="100%" border="0" class="plainborder-ash">
              		<tr>
                		<td width="25%">Search by Username</td>
                		<td width="75%"><input name="searchName" type="text" id="searchName" size="66" /></td>
             		 </tr>
				     <tr>
					 	 <td align="center" colspan="2">
								<input name="action" type="hidden" id="action" value="search" />
								<input name="type" type="hidden" id="type" value="ByName" />
								<input type="submit" name="Submit2" value="Search" />
								<input type="reset" name="Reset" value="Clear" />              			</td>		
            		</tr> 
            	</table>
			</form> 
			</td>
			</tr>
			<tr>
				<td width="5"></td>
			</tr>
			<tr>
              <td>
			 <form method="post" action="/jackrabbit-jcr-demo/BlogController/">
            	<table width="100%" border="0" class="plainborder-ash">
              		<tr>
                		<td width="25%">Search by Date </td>
                		<td width="75%" align="center">
						  <p>FROM &nbsp;  
						    <select name="yearFrom">
						        <option value="2007">2007</option>
						        <option value="2008">2008</option>
						        <option value="200">2009</option>
				            </select>
                		  &nbsp;
                		  <select name="monthFrom">
                		    <option value="01">January</option>
                		    <option value="02">February</option>
                		    <option value="03">March</option>
                		    <option value="04">April</option>
                		    <option value="05">May</option>
                		    <option value="06">June</option>
                		    <option value="07">July</option>
                		    <option value="08">August</option>
                		    <option value="09">September</option>
                		    <option value="10">October</option>
                		    <option value="11">November</option>
                		    <option value="12">December</option>
              		    </select>
                        &nbsp;
                        <select name="select">
                          <option value="01">01</option>
                          <option value="02">02</option>
                          <option value="03">03</option>
                          <option value="04">04</option>
                          <option value="05">05</option>
                          <option value="06">06</option>
                          <option value="07">07</option>
                          <option value="08">08</option>
                          <option value="09">09</option>
                          <option value="10">10</option>
                          <option value="11">11</option>
                          <option value="12">12</option>
                          <option value="13">13</option>
                          <option value="14">14</option>
                          <option value="15">15</option>
                          <option value="16">16</option>
                          <option value="17">17</option>
                          <option value="18">18</option>
                          <option value="19">19</option>
                          <option value="20">20</option>
                          <option value="21">21</option>
                          <option value="22">22</option>
                          <option value="23">23</option>
                          <option value="24">24</option>
                          <option value="25">25</option>
                          <option value="26">26</option>
                          <option value="27">27</option>
                          <option value="28">28</option>
                          <option value="29">29</option>
                          <option value="30">30</option>
                          <option value="31">31</option>
                        </select>
                        </p>
						  <p>&nbsp; TO &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						    <select name="yearTo">
						      <option value="2007">2007</option>
						      <option value="2008">2008</option>
						      <option value="200">2009</option>
				            </select>
&nbsp;
                		  <select name="monthTo">
                		    <option value="01">January</option>
                		    <option value="02">February</option>
                		    <option value="03">March</option>
                		    <option value="04">April</option>
                		    <option value="05">May</option>
                		    <option value="06">June</option>
                		    <option value="07">July</option>
                		    <option value="08">August</option>
                		    <option value="09">September</option>
                		    <option value="10">October</option>
                		    <option value="11">November</option>
                		    <option value="12">December</option>
              		      </select>
&nbsp;
<select name="select2">
  <option value="01">01</option>
  <option value="02">02</option>
  <option value="03">03</option>
  <option value="04">04</option>
  <option value="05">05</option>
  <option value="06">06</option>
  <option value="07">07</option>
  <option value="08">08</option>
  <option value="09">09</option>
  <option value="10">10</option>
  <option value="11">11</option>
  <option value="12">12</option>
  <option value="13">13</option>
  <option value="14">14</option>
  <option value="15">15</option>
  <option value="16">16</option>
  <option value="17">17</option>
  <option value="18">18</option>
  <option value="19">19</option>
  <option value="20">20</option>
  <option value="21">21</option>
  <option value="22">22</option>
  <option value="23">23</option>
  <option value="24">24</option>
  <option value="25">25</option>
  <option value="26">26</option>
  <option value="27">27</option>
  <option value="28">28</option>
  <option value="29">29</option>
  <option value="30">30</option>
  <option value="31">31</option>
</select>
						  </p></td>
             		 </tr>
				     <tr>
					 	 <td align="center" colspan="2">
								<input name="action" type="hidden" id="action" value="search" />
								<input name="type" type="hidden" id="type" value="ByDate" />
								<input type="submit" name="Submit2" value="Search" />
								<input type="reset" name="Reset" value="Clear" />              			</td>		
            		</tr> 
            	</table>
			</form> 
			</td>
			</tr>
			<tr>
				<td width="5"></td>
			</tr>		  	
			<tr>
              	<td>
			 		<form method="post" action="/jackrabbit-jcr-demo/BlogController/">
            			<table width="100%" border="0" class="plainborder-ash">
              				<tr>
								<td width="25%">Search by Content </td>
								<td width="75%"><input name="searchText" type="text" id="searchText" size="66" />
								</td>
             		 		</tr>
				     		<tr>
					 	 		<td align="center" colspan="2">
									<input name="action" type="hidden" id="action" value="search" />
									<input name="type" type="hidden" id="type" value="ByContent" />
									<input type="submit" name="Submit2" value="Search" />
									<input type="reset" name="Reset" value="Clear" />              			
								</td>		
            				</tr> 
            			</table>
					</form> 
				</td>
			</tr>
			
			
          </table>
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
