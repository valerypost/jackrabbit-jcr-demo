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
<title>Search blog entries</title>
<link href="../css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />
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
						<li><a class="current" href="/jackrabbit-jcr-demo/blog/searchBlogEntries.jsp" >Search</a></li>
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
						    <form method="post" action="/jackrabbit-jcr-demo/blog/search">
			            		<table width="100%" border="0" class="plainborder-ash">
			              			<tr>
				                		<td width="25%">Search by Username</td>
				                		<td width="75%"><input name="searchName" type="text" id="searchName" size="66" class="search"/></td>
			             			</tr>
							     	<tr>
								 	 	<td align="center" colspan="2">
											<input name="action" type="hidden" id="action" value="search" />
											<input name="type" type="hidden" id="type" value="ByName" />
											<input type="submit" name="Submit2" value="Search" />
											<input type="reset" name="Reset" value="Clear" /> </td>		
			            			</tr> 
								    <tr>
										<td width="5"></td>
								    </tr>
			            		</table>
							</form>
							<br/>
							
							<form method="post" action="/jackrabbit-jcr-demo/blog/search">
				            	<table width="100%" border="0" class="plainborder-ash">
				              		<tr>
				                		<td width="25%">Search by Date </td>
				                		<td width="75%" align="center">
										  <p align="left">FROM &nbsp;  
										    <select name="yearFrom">
										        <option value="2007">2007</option>
										        <option value="2008">2008</option>
										        <option value="200">2009</option>
								            </select>
				                		  &nbsp;
				                		  <select name="monthFrom">
				                		    <option value="00">January</option>
				                		    <option value="01">February</option>
				                		    <option value="02">March</option>
				                		    <option value="03">April</option>
				                		    <option value="04">May</option>
				                		    <option value="05">June</option>
				                		    <option value="06">July</option>
				                		    <option value="07">August</option>
				                		    <option value="08">September</option>
				                		    <option value="09">October</option>
				                		    <option value="10">November</option>
				                		    <option value="11">December</option>
				              		        </select>
				                        &nbsp;
				                        <select name="dateFrom" id="dateFrom">
				                          <option value="1">01</option>
				                          <option value="2">02</option>
				                          <option value="3">03</option>
				                          <option value="4">04</option>
				                          <option value="5">05</option>
				                          <option value="6">06</option>
				                          <option value="7">07</option>
				                          <option value="8">08</option>
				                          <option value="9">09</option>
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
										  <p align="left"> TO &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
										    <select name="yearTo">
										      <option value="2007">2007</option>
										      <option value="2008">2008</option>
										      <option value="2009">2009</option>
								            </select>
				&nbsp;
				                		  <select name="monthTo">
				                		    <option value="00">January</option>
				                		    <option value="01">February</option>
				                		    <option value="02">March</option>
				                		    <option value="03">April</option>
				                		    <option value="04">May</option>
				                		    <option value="05">June</option>
				                		    <option value="06">July</option>
				                		    <option value="07">August</option>
				                		    <option value="08">September</option>
				                		    <option value="09">October</option>
				                		    <option value="10">November</option>
				                		    <option value="11">December</option>
				              		      </select>
											&nbsp;
										  <select name="dateTo" id="dateTo">
											  <option value="1">01</option>
											  <option value="2">02</option>
											  <option value="3">03</option>
											  <option value="4">04</option>
											  <option value="5">05</option>
											  <option value="6">06</option>
											  <option value="7">07</option>
											  <option value="8">08</option>
											  <option value="9">09</option>
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
										</td>
				             		 </tr>
								     <tr>
									 	 <td align="center" colspan="2">
												<input name="action" type="hidden" id="action" value="search" />
												<input name="type" type="hidden" id="type" value="ByDate" />
												<input type="submit" name="Submit2" value="Search" />
												<input type="reset" name="Reset" value="Clear" />              			
										</td>		
				            		</tr> 
									<tr>
										<td width="5"></td>
									</tr>
				            	</table>
							</form>
							<br/> 

			 				<form method="post" action="/jackrabbit-jcr-demo/blog/search">
            					<table width="100%" border="0" class="plainborder-ash">
              						<tr>
										<td width="25%">Search by Content </td>
										<td width="75%"><input name="searchText" type="text" id="searchText" size="66" class="search"/></td>
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
							<br/>
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
