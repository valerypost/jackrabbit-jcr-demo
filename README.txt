Jackrabbit JCR Demo

               Apache Jackrabbit is a fully confirming implementation of the Content 
Repository for Java Technology API (JCR) and is the reference implementation JSR 170
Specification. Jackrabbit is a very useful content repository but a major obstacle 
for a novice JCR developer is that there is no demo application to be used as a 
reference. It would be really useful to new comers to Jackrabbit to have a reference
which shows how to build an application from the scratch on top Jackrabbit.
               Jackrabbit-jcr-demo is a demo blog application which will show how JCR 
functionalities can be used in practice and can be used as a reference to new users of 
Jackrabbit . Complexity of the application will be kept at a level that can be easily 
understood by a novice Jackrabbit developer and usage of JCR features will be emphasized. 
Jackrabbit-jcr-demo will be written adhering to Java coding standards and will contain 
clean and well readable code.  


Features
========

Adding blog entries with optional image or a video
Adding comments to blog entries
Rating blog entries
Email alerts for comments
Registering new users
Wiki page with version history

Building the application
========================

1.check out the source from svn
    svn checkout http://jackrabbit-jcr-demo.googlecode.com/svn/trunk/ jackrabbit-jcr-demo

2.use maven to build the war file
    mvn package

  One dependany jcr 1.o can't be downloaded automatically from a maven repository. It can 
  be manually downloaded from 
  http://jcp.org/aboutJava/communityprocess/final/jsr170/index.html. Then maven command can
  be used to install the jar file manually to repository.
 
  mvn install:install-file -Dfile=<path> -DgroupId=javax.jcr -DartifactId=jcr  -Dversion=1.0 -Dpackaging=jar

3.copy the war file to your J2EE application server.

Binay Download
==============

1. Binary war file can be downloaded at
    http://code.google.com/p/jackrabbit-jcr-demo/downloads/list


Configuration
==============

Deployment descriptor - /jackrabbit-jcr-demo/WEB-INF/web.xml
Jackrabbit JCR Demo web application users must configure their email servers and the from
email of the application which is used as the from email of the blog comment email alerts.
Email server must support smtp protocol on port 25.
eg.
      <init-param>
         <param-name>config.mail.mail_server</param-name>
         <param-value>mail.mrt.ac.lk</param-value>
      </init-param>
      <init-param>
         <param-name>config.mail.from_email</param-name>
         <param-value>admin@jackrabbitjcrdemo.org</param-value>
      </init-param>

Paths of the CND file of the custom node types used in the application, mapping file used by 
ocm to map node types to java beans, error code xml file which is used to keep error messages
can also be configured here as initial parameters of the 
org.apache.jackrabbit.demo.blog.servletInitServlet.




