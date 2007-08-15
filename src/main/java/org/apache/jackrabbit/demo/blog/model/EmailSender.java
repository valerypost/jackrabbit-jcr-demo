package org.apache.jackrabbit.demo.blog.model;

public class EmailSender {
	
/*    public boolean send(Message aMessage) {
        try {
            String email = aMessage.getRecipient().getContactNo() + "@mms.dialog.lk";
            Properties props = System.getProperties();
            props.put(SystemConfig.getInstance().getStr("admin.messaging.mms.server"), SystemConfig.getInstance().getStr("admin.messaging.mms.host"));
            Session mailsession = Session.getDefaultInstance(props, null);
            javax.mail.Message message = new MimeMessage(mailsession);

            message.setSubject(SystemConfig.getInstance().getStr("admin.messaging.mms.subject"));

            String fromNum = (aMessage.getSender().getContactNo() == null ? SystemConfig.getInstance().getStr("admin.messaging.mms.fromNumber") : aMessage
                    .getSender().getContactNo());

            message.setFrom(new InternetAddress("\"" + fromNum + "\"" + " <" + fromNum + "@mms.dialog.lk>"));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(aMessage.getSender().getNickName()+" says:"+aMessage.getBodyContent());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            for(int i=0; i<aMessage.getAttachments().size(); i++) {
                Attachment attachment = aMessage.getAttachments().get(i);
                File att = new File("/MyDiary/view/upload/message/" + attachment.getImageID() + ".jpg");

                if (att.exists()) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(att);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(att.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            message.setContent(multipart);
            Transport.send(message);
            
            SystemLog.getInstance().getLogger("MY_DIARY_MMS").info(" [Sent] " + "MMS sent to " + aMessage.toString());
            return true;
        } catch (Exception e) {
            SystemLog.getInstance().getLogger("MY_DIARY_MMS").error(" Errr sending MMS,"+aMessage.toString(),e);
            return false;
        }
    }*/

}
