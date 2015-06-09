import org.vijay.common.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import net.proteanit.sql.DbUtils;

public class JavaEmailSender {
 
    private String emailAddressTo = new String();
    private String msgSubject = new String();
    private String msgText = new String();

    final String USER_NAME = "no_replay@fixtureinternational.com";   //User name of the Goole(gmail) account
    final String PASSSWORD = "Anar321?";  //Password of the Goole(gmail) account
    final String FROM_ADDRESS = "no_replay@fixtureinternational.com";  //From addresss
    Calendar currentDate = Calendar.getInstance(); //Get the current date
    SimpleDateFormat formatter= new SimpleDateFormat("dd/MMM/yyyy "); //format it as per your requirement
    String dateNow = formatter.format(currentDate.getTime());
    String subject="RP Expiry Employee List Till "+dateNow;
    String data="";
    
    
    public void pleaseUPdate()
    {
        data+="<!DOCTYPE html>";
        data+="<html>";
        
        data+="<head>";
        data+="<style>";
        data+="table, th, td {";
        data+="border: 1px solid black;";
        data+="border-collapse: collapse;";
        data+="}";
        data+="th, td {";
        data+="padding: 5px;";
        data+="}";
        data+="</style>";
        data+="</head>";

        data+="<body>";
        data+="<font face=Century Gothic size=3>Please find the below mentioned Employee RP Expiry Details!"+"</font>"+"<p>";
        connection c=new connection();
        Connection con=c.conn();
        try
        {
            data+="<table style="+"width:100%"+">";
            data+="<tr>";
            data+=" <th>Emp_Id</th>";
            data+=" <th>Emp_Name</th>";
            data+=" <th>RP-Expiry</th>";
            data+=" <th>Days_Left</th>";
            data+="</tr>";
            PreparedStatement ps=con.prepareStatement("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',idNumber '"+"ID/VisaNumber"+"',visaExpiry '"+"RP Expiry"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',DATEDIFF(visaExpiry,CURDATE())'"+"DAYS LEFT"+"' from tbl_labourdetails,(SELECT @i := 0) temp WHERE DATEDIFF(visaExpiry,CURDATE()) <30 AND STATUS !='LEFT' order by DATEDIFF(visaExpiry,CURDATE())");
            ResultSet rs=ps.executeQuery();
            while(rs.next())
            {
                data+="<tr>";
                data+="<td>"+rs.getString(2)+"</td>";
                data+="<td>"+rs.getString(3)+"</td>";
                data+="<td>"+rs.getString(5)+"</td>";	
                data+="<td>"+rs.getString(8)+"</td>";
                data+="</tr>";
            }
            data+="</table><br><br>";
            data += "<br><br><br><br><br> <label style='text-decoration: underline;background-color: yellow; color: red;' >This is a auto generated email.Please DO NOT reply to this mail. Please send your issues and enquiries to info@fixtureinternation.com OR info@anartrading.com</label>";
            data += "<br><br> <p align='justify' style='color: gray;'> The information in this email (and any attachments) is confidential. If you are not the intended recipient, you must not use or disseminate the information. If you have received this email in error, please immediately notify us by Reply command and permanently delete the original and any copies or printouts thereof. Although this email and any attachments are believed to be free of any virus or other defect that might affect any computer system into which it is received and opened, it is the responsibility of the recipient to ensure that it is virus free and no responsibility is accepted by Finacul, for any loss or damage arising in any way from its use.</p>";

            con.close();


        }
        catch(Exception e)
        {

        }
        
    }
    public JavaEmailSender() {
    }
     public void createAndSendEmail() {
        this.msgText = msgText;
        sendEmailMessage();
    }
    private void sendEmailMessage() 
    {
     
        //Create email sending properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.fixtureinternational.com");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.port", "587");
       // props.put("mail.smtp.ssl.trust", "mail.fixtureinternational.com");


  
        Session session = Session.getInstance(props,
        new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(USER_NAME, PASSSWORD);
            }
        });

        try 
        {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_ADDRESS)); //Set from address of the email
            message.setContent(data,"text/html"); //set content type of the email
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("hradmin@fixtureinternational.com,vijay.amballur@gmail.com")); //Set email recipient
            //message.setRecipients(Message.RecipientType.CC,InternetAddress.parse("shefeek@fixtureinternational.com")); //Set email recipient
            message.setSubject(subject); //Set email message subject
            message.setSentDate(currentDate.getTime());
            Transport.send(message); //Send email message
            System.out.println("sent email successfully!");

        } 
        catch (MessagingException e) 
        {
            throw new RuntimeException(e);
        }
    }
    

    public static void main(String[] args) {
        
        
        
      
     //Sending test email
      connection c=new connection();
        Connection con=c.conn();
        try
        {
            PreparedStatement ps=con.prepareStatement("select @i := @i + 1 '"+"SL.NO"+"',empId '"+"ID"+"',empName'"+"NAME"+"',idNumber '"+"ID/VisaNumber"+"',visaExpiry '"+"RP Expiry"+"',passportNumber '"+"PASSPORT#"+"',passportExpiry'"+"P.EXPIRY"+"',DATEDIFF(visaExpiry,CURDATE())'"+"DAYS LEFT"+"' from tbl_labourdetails,(SELECT @i := 0) temp WHERE DATEDIFF(visaExpiry,CURDATE()) <30 AND STATUS !='LEFT' order by DATEDIFF(visaExpiry,CURDATE())");
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                JavaEmailSender email = new JavaEmailSender();
                email.pleaseUPdate();
                email.createAndSendEmail();
            }
        }
        catch(Exception e)
        {
            
        }
      
      
    }

   
  


    
  
}