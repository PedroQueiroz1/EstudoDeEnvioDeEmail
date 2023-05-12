package br.com.enviando.email.enviando_email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	private String email = Credentials.EMAIL;
	private String senha = Credentials.SENHA;
	
	@Test
	public void testeEmail() {
		
		int count = 10;
		
		/*
		 * Olhe as configurações smtp do seu email
		 */
		try {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true"); // Autorização
		properties.put("mail.smtp.starttls.enable", "true"); // Autenticação
		properties.put("mail.smtp.host", "smtp-mail.outlook.com"); // Servidor Outlook
		properties.put("mail.smtp.port", "587"); // Porta do servidor
		properties.put("mail.smtp.socketFactory.port", "587"); // Expecifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, senha);
			}
		});
		
		Address[] toUser = InternetAddress.parse("pedroqueir0z@outlook.com, pedrotestejava@outlook.com");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(email));  /*Quem está enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject("Chegou o email enviado com java");  // Assunto do Email
		message.setText("Olá! O envio do e-mail com java deu certo.");
		
		for(int i = 0; i < count; i++) {
		Transport.send(message);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
}
