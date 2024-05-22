import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;

public class EmailUtil {
	
	// (*) sendEmail()
	// 
    public static void sendEmail(String recepient, String mainMessage) throws MessagingException{
    	System.out.println("Sending Email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "donationsrrr@gmail.com";
        String password = "ggld lcmx xqdg cvjl ";

        Session session = Session.getInstance(properties, new Authenticator() {
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, recepient, mainMessage);
        Transport.send(message);
        System.out.println("message sent");
    }
    
    // (*) prepareMessage()
    // 
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String mainMessage) {
    	Message message = new MimeMessage(session);
    	try {
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("New Volunteer from RRR Donations");
			message.setText(mainMessage);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
    }
}