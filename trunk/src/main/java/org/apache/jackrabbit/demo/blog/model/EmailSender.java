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
package org.apache.jackrabbit.demo.blog.model;

import java.util.Date;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This class is used to send emails when sending email alerts about blog comments 
 */
public class EmailSender {
	
    public static void send(String title, String commentor, String email, Date date) {

        try {
    	
        	Properties props = System.getProperties();
            props.put("mail.smtp.host", Config.MAIL_SERVER);
            Session mailsession = Session.getDefaultInstance(props, null);
            javax.mail.Message message = new MimeMessage(mailsession);

			message.setSubject("You blog entry is commented");

            message.setFrom(new InternetAddress(Config.FROM_EMAIL));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));

            DateFormat dateFormat   = new SimpleDateFormat("EEEEEE, yyyy.MM.dd 'at' HH:mm a");
            String commentDate 		= dateFormat.format(date).toString();
            
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("You blog entry titled \""+title+"\" was commented by "+commentor+ " on "+commentDate);   

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            
			} catch (MessagingException e) {
				e.printStackTrace();
			}
    }

}
