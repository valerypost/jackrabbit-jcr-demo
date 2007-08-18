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
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
  <title>Jackrabbit-JCR-Demo Blog</title>

  <!-- comment/rate java scripts -->
  <script type="text/javascript" src="/jackrabbit-jcr-demo/scripts/comment.js"></script>
  <script type="text/javascript" src="/jackrabbit-jcr-demo/scripts/rate.js"></script>
  
  <!-- jackrabbit-jcr-demo main css -->
  <link href="/jackrabbit-jcr-demo/css/jackrabbit-jcr-demo.css" rel="stylesheet" type="text/css">

  <!-- jsp tag-lib declarations -->
  <%@ taglib uri='http://java.sun.com/jstl/core' prefix='c' %>
  <%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
  
  <!-- Atom syndication -->
  <link rel="alternate" type="application/atom+xml"title="Recent blog entries in Atom" 
   <c:choose>
	<c:when test="${userUUID eq null}"> href="/jackrabbit-jcr-demo/blog/atom/general/recent"</c:when>
    <c:otherwise>href="/jackrabbit-jcr-demo/blog/atom/user/${userUUID}"</c:otherwise>
  </c:choose>
  />
 </head>

 <body bgcolor="#5C91C1">
  <table width="100%" height="100%" border="0">
   <tr valign="top">
    <td></td>
    <td width="970" align="center">
	 <div id="tabs">
	  <ul>	
	   <li><a class="current" href="/jackrabbit-jcr-demo/blog/view">My Blog</a></li>
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
  	 <td></td>
     <td width="970" valign="top" >
	  <table width="100%" height="100%" border="0">
       <tr>
        <td  width="685" height="800" valign="top"> 
        <c:if test="${ownBlog eq true}">
	     <div class="lt"></div>
	     <div class="lbox">
		  Welcome ${username} ,
		  <h1 align="center"> Jackrabbit-jcr-demo </h1>
	     </div>       
	    </c:if>
		<c:forEach var="blogEntry" items="${blogList}">                 									
		 <table width="100%" border="0" cellpadding="0" cellspacing="10" class="blog_entry_complete">
          <tr>
           <td>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="blog_entry_inside" >
             <tr class="bodytext11grey">
              <td width="70%" align="left" class="title15">${blogEntry.title}</td>
             </tr>																															
             <tr>
              <td height="10" colspan="2"></td>
		     </tr>	
			 <tr>
			  <td> 
				<c:if test="${blogEntry.hasImage eq true}">
				 <img src="/jackrabbit-jcr-demo/repo/image/${blogEntry.UUID}" class="image"></img>
			    </c:if> 
	                     ${blogEntry.content}
			  </td>
             </tr>
             <tr>
             	<td>
             		<object codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" width="320" height="240" id="FLVPlayer">
  						<param name="movie" value="FLVPlayer_Progressive.swf" />
  						<param name="salign" value="lt" />
  						<param name="quality" value="high" />
  						<param name="scale" value="noscale" />
  						<param name="FlashVars" value="&MM_ComponentVersion=1&skinName=Corona_Skin_3&streamName=/jackrabbit-jcr-demo/repo/video/${blogEntry.UUID}&autoPlay=false&autoRewind=false" />
 					    <embed src="FLVPlayer_Progressive.swf" flashvars="&MM_ComponentVersion=1&skinName=Corona_Skin_3&streamName=/jackrabbit-jcr-demo/repo/video/${blogEntry.UUID}&autoPlay=false&autoRewind=false" quality="high" scale="noscale" width="320" height="240" name="FLVPlayer" salign="LT" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
                    </object>
             	</td>
             </tr>
             <tr>
              <td height="10" colspan="2"></td>
             </tr>
             <tr>
              <td height="25" colspan="2"  class="bodytext11grey" >												
               by <strong>${blogEntry.user}</strong>, 
               on <fmt:formatDate type="both" dateStyle="full" value="${blogEntry.createdOn.time}"/>  &nbsp;&nbsp;
			   rank :
			   <c:choose>
			   	<c:when test="${blogEntry.rate eq 0}"> Not rated yet</c:when>
			    <c:otherwise><c:forEach var='item' begin='1' end='${blogEntry.rate}'> <img src="/jackrabbit-jcr-demo/images/rankStar.gif"></img></c:forEach></c:otherwise>
			   </c:choose>
              </td>
             </tr>
              <c:if test="${blogEntry.user ne username}">
               <tr>
                <td height="10" bgcolor="#FFF"></td>
               </tr>
               <tr align="center" bgcolor="#F3F3F3">
                <td>
                 <div id="${blogEntry.UUID}C">
                  <input type='button' name='Button' value='Comment' onClick="addComment('${blogEntry.UUID}');"/>
                 </div>
                 </td>
                 </tr>
                 <tr>
                 <td height="10" bgcolor="#FFF"></td>
                 </tr>
                 <tr  align="center">
                 <td>
				 <div id="${blogEntry.UUID}R">
                  <input type='button' name='Button' value='Rate' onClick="addRate('${blogEntry.UUID}');"/>
                 </div>
                </td>
               </tr>
              </c:if>  
			  <c:if test="${blogEntry.user eq username}">
               <tr>
                <td height="10" bgcolor="#FFF"></td>
               </tr>
               <tr align="center" bgcolor="#F3F3F3">
                <td>
                  <form method="post" action="/jackrabbit-jcr-demo/blog/remove">
                   <input type="submit" name='Delete' value='Delete'/>
				   <input type="hidden" name="UUID" id="UUID" value="${blogEntry.UUID}" />
				  </form>
                </td>
               </tr>
              </c:if>    
              <c:forEach var="comment" items="${blogEntry.commentList}">
               <tr><td height="10" bgcolor="#FFF"></td></tr>
               <tr bgcolor="#999999"><td><strong>${comment.commenter}</strong> says,</td></tr>
               <tr bgcolor="#999999"><td>${comment.content}</td></tr>
              </c:forEach>                        									
            </table>
           </td>
          </tr> 											      
         </table>	
        </c:forEach>	 
	   </td>
       <td width="5"></td>
	   <td width="275" bgcolor="#CCCCCC"></td>
	   <td width="5"></td>
      </tr>
     </table>
    </td>
	<td></td>
  </tr>
  <tr valign="bottom"><td></td></tr>
 </table>
 </body>
</html>
