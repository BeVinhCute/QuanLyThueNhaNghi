    package helper;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
    // Email: tungletest1.email@gmail.com
    // Password: nebeekfipcstxcox

    static final String from = "quanlyotofpoly@gmail.com";
    static final String password = "ufcpnxgvlefjfbhj";

    public static boolean sendEmail(String to, String tieuDe, String noiDung,String path ,String nameimg) {
        // Properties : khai báo các thuộc tính
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP HOST
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // create Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Tạo một tin nhắn
        MimeMessage msg = new MimeMessage(session);

        try {
            // Kiểu nội dung
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            // Người gửi
            msg.setFrom(from);

            // Người nhận
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Tiêu đề email
            msg.setSubject(tieuDe);

            // Quy đinh ngày gửi
            msg.setSentDate(new Date());

            // Tạo nội dung email
            MimeMultipart multipart = new MimeMultipart("related");

            // Phần thân email
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(noiDung, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Kiểm tra xem có file ảnh nào được cung cấp hay không
            if (nameimg != null && !nameimg.isEmpty()) {
                // Phần ảnh
                messageBodyPart = new MimeBodyPart();
                DataSource fds = new FileDataSource(path+nameimg);
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setHeader("Content-ID", "<image>");
                multipart.addBodyPart(messageBodyPart);
            }

            // Đặt nội dung cho email
            msg.setContent(multipart);

            // Gửi email
            Transport.send(msg);
            System.out.println("Gửi email thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Gặp lỗi trong quá trình gửi email");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Email.sendEmail("hieudzpro4444@gmail.com", "Xác Minh Tài Khoản", "123123", null,"123.png");

    }

}
