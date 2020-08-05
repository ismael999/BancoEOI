package es.eoi.mundobancario.service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailManager {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String to, String subject, String content, String urlPDF, String fileName) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setTo(to);
		helper.setSubject(subject);
		
		BodyPart text = new MimeBodyPart();
		text.setText(content);
		
		BodyPart pdf = new MimeBodyPart();
		pdf.setDataHandler(new DataHandler(new FileDataSource(urlPDF.concat(fileName))));
		pdf.setFileName(fileName);
		
		MimeMultipart multiPart = new MimeMultipart();
		multiPart.addBodyPart(pdf);
		multiPart.addBodyPart(text);
		
		message.setContent(multiPart);
		
		mailSender.send(message);
	}
}
