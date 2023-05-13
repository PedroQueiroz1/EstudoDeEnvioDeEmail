package br.com.enviando.email.enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String email = Credentials.EMAIL;
	private String senha = Credentials.SENHA;

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHTML) throws Exception {
		int count = 2;
		/*
		 * Olhe as configurações smtp do seu email
		 */
		Properties properties = new Properties();

		// properties.put("mail.smtp.ssl.trust", "*"); // Aparentemente necessário para
		// caso utilize gmail
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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(email, nomeRemetente)); /* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject(assuntoEmail); // Assunto do Email

		if (envioHTML) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);
		}

		for (int i = 1; i < count; i++) {
			Transport.send(message);
		}
	}

	public void enviarEmailAnexo(boolean envioHTML) throws Exception {
		int count = 2;
		/*
		 * Olhe as configurações smtp do seu email
		 */
		Properties properties = new Properties();

		// properties.put("mail.smtp.ssl.trust", "*"); // Aparentemente necessário para
		// caso utilize gmail
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

		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(email, nomeRemetente)); /* Quem está enviando */
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject(assuntoEmail); // Assunto do Email

		/*
		 * Parte 1 do e-mail que é o texto e a descrição do e-mail.
		 */
		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);
		}

		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePDF()); // Poderia ser um certificado em PDF,
		arquivos.add(simuladorDePDF()); // Nota fiscal,
		arquivos.add(simuladorDePDF()); // Documento de texto,
		arquivos.add(simuladorDePDF()); // Imagem...

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);

		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {
			/*
			 * Parte 2 do e-mail que são os anexos em PDF
			 */
			MimeBodyPart anexoEmail = new MimeBodyPart();

			/*
			 * Onde é passado o simulador de PDF, você passa o seu arquivo gravado no banco
			 * de dados ou gravado em qualquer outro local...
			 */
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(simuladorDePDF(), "application/pdf")));
			anexoEmail.setFileName("anexoemail"+index+".pdf");

			multipart.addBodyPart(anexoEmail);
			
			index++;
		}

		message.setContent(multipart);

		for (int i = 1; i < count; i++) {
			Transport.send(message);
		}
	}

	/*
	 * Esse método simula o PDF ou qualquer arquivo que possa ser enviado no email.
	 * Pode pegar arquivo no banco de dados base64, byte[], Stream de Arquivos...
	 * pode estar em um banco de dados ou em uma pasta...
	 * 
	 * Retorna um PDF em branco com o texto do paragrafo do exemplo.
	 */
	private FileInputStream simuladorDePDF() throws Exception {
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do PDF anexo com Java Mail," + "esse texto é do PDF!!!"));
		document.close();

		return new FileInputStream(file);
	}
}
