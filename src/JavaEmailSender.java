import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import net.proteanit.sql.DbUtils;

public class JavaEmailSender {
 
    private String emailAddressTo = new String();
    private String msgSubject = new String();
    private String msgText = new String();

    final String USER_NAME = "noreplayatanar@gmail.com";   //User name of the Goole(gmail) account
    final String PASSSWORD = ".1234creative";  //Password of the Goole(gmail) account
    final String FROM_ADDRESS = "noreplayatanar@gmail.com";  //From addresss
    String data="Test:-";
    
    
    public void pleaseUPdate()
    {
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("SELECT  DISTINCT empName,@i := @i + 1 FROM tbl_labourdetails,(SELECT @i := 0) temp WHERE visaExpiry='1111-11-11' OR currentSite='' ");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                data=data+"----"+rs.getString(1);
            }

            con.close();


        }
        catch(Exception e)
        {

        }
    }
    public JavaEmailSender() {
    }

    public static void main(String[] args) {
      JavaEmailSender email = new JavaEmailSender();
      email.pleaseUPdate();
     //Sending test email
      email.createAndSendEmail("vijay.amballur@gmail.com", "Profile not yet Compleated",
      email.data);
    }

    public void createAndSendEmail(String emailAddressTo, String msgSubject, String msgText) {
        this.emailAddressTo = emailAddressTo;
        this.msgSubject = msgSubject;
        this.msgText = msgText;
        sendEmailMessage();
    }
  
    private void sendEmailMessage() {
     
     //Create email sending properties
     Properties props = new Properties();
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     props.put("mail.smtp.host", "smtp.gmail.com");
     props.put("mail.smtp.port", "587");
  
    Session session = Session.getInstance(props,
    new javax.mail.Authenticator() {
    protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(USER_NAME, PASSSWORD);
   }
    });

  try {

     Message message = new MimeMessage(session);
     message.setFrom(new InternetAddress(FROM_ADDRESS)); //Set from address of the email
     message.setContent(msgText,"text/html"); //set content type of the email
     
    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailAddressTo)); //Set email recipient
    
    message.setSubject(msgSubject); //Set email message subject
    Transport.send(message); //Send email message

   System.out.println("sent email successfully!");

  } catch (MessagingException e) {
       throw new RuntimeException(e);
  }
    }

    public void setEmailAddressTo(String emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public void setSubject(String subject) {
        this.msgSubject = subject;
    }

    public void setMessageText(String msgText) {
        this.msgText = msgText;
    }
  
}