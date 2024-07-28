package Manager;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import Security.ResetCode;

import java.util.Properties;

public class Email {

    private final String USERNAME = "sonbltse184773@fpt.edu.vn";
    private final String PASSWORD = "zqdi uyus kkqi qtvl";
    private final String WELCOME = "Welcome to stbcStore";
    private final String WELCOMEMESSAGE
            = "Welcome to stbcStore!\n"
            + "\n"
            + "We are excited to have you as a part of our shopping community. Whether you’re here to find the latest trends, discover unique products, or enjoy great deals, we’re confident you’ll love what we have to offer.\n"
            + "\n"
            + "Here are a few tips to help you get started:\n"
            + "\n"
            + "Explore Our Collections: Browse through our latest arrivals and exclusive collections [link to new arrivals or collections].\n"
            + "Special Offers: Don't miss out on our current promotions and discounts [link to offers page].\n"
            + "As a welcome gift, here’s a special discount just for you: [Discount Code]. Use it at checkout to enjoy 10% off your first purchase!\n"
            + "\n"
            + "If you have any questions or need assistance, our customer support team is here to help. You can reach us at sonbltse184773@fpt.edu.vn.\n"
            + "\n"
            + "Thank you for choosing stbcStore. Happy shopping!\n"
            + "\n"
            + "Best regards,\n"
            + "\n"
            + "SonBui\n"
            + "Website Admin\n"
            + "stbcStore\n";
    private final String RESETPASS = "Your secret password reset code.";
    private final String VERIFY = "Your verification link.";

    public void sendEmail(String address, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHTMLEmail(String address, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void sendSupportEmail(String name, String email, String phone, String body){
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sonbltse184773@fpt.edu.vn"));
            message.setSubject("Customer support");
            String content = "Email: " + email + "\n" +
                 "Name: " + name + "\n" +
                 "Phone: " + phone + "\n" +
                 body;
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void welcomeEmail(String address) {
        sendEmail(address, WELCOME, WELCOMEMESSAGE);
    }

    public void resetMail(String address, String code) {
        String content = "Use this code to reset your password\nDon't let anyone see this\n";
        sendEmail(address, RESETPASS, content + code);
    }

    public void verifyMail(String address, String code) {
        String content = "<html><body><p>Use this to verify your password</p><p><a href=\"http://localhost:8080/stbcStore/VerifyAccount?email=" + address + "&token=" + code + "\">Click here to verify</a></p></body></html>";
        sendHTMLEmail(address, VERIFY, content);
    }
}
