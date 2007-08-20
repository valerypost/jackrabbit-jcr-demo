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
<link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css" />
</head>

<body bgcolor="#5C91C1">
	<table width="100%" height="100%" border="0">
		<tr valign="top">
			<td></td>
	    	<td width="970" align="center">
				<div id="tabs">
					<ul>	
						<li><a class="inactive" href="#">My Blog</a></li>
						<li><a class="inactive" href="#">New Entry</a></li>
						<li><a class="inactive" href="#" >Search</a></li>
						<li><a class="inactive" href="#" >Wiki</a></li>
						<li><a class="inactive" href="#" >Logout</a></li>
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
        <form method="post" action="/jackrabbit-jcr-demo/user/add">
        <table width="100%" bgcolor="#CCCCCC">
          <tr>
            <td><div align="center">User Registration </div></td>
          </tr>  
		  <tr>
            <td><table width="100%" bgcolor="#FFFFFF">
              <tr>
                <td width="17%">Username</td>
                <td width="83%"><input name="username" type="text" id="username" size="50" class="search"/></td>
              </tr>
              <tr>
                <td>Nickname</td>
                <td><input name="nickname" type="text" id="nickname" size="50" class="search"/></td>
              </tr>
              <tr>
                <td>Email</td>
                <td><input name="email" type="text" id="email" size="50" class="search"/></td>
              </tr>
              <tr>
                <td>Password</td>
                <td><input name="password" type="password" id="password" size="50" class="search"/></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><div align="center">
              <input name="action" type="hidden" id="action" value="add" />
              <input type="submit" name="Submit" value="Save" />
			  <input type="reset" name="Clear" value="Clear" />
            </div></td>
          </tr>  
        </table>
        </form>     
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
