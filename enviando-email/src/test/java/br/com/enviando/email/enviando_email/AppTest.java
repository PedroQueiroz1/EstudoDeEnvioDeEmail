package br.com.enviando.email.enviando_email;

import org.junit.Test;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	// Necessário modificar o Credentials.EMAIL por
	// private String email = "emailaqui@email.com"
	// Necessário modificar o Credentials.SENHA por
	// private String senha = "coloqueASenhaDoSeuEmailAqui"
	private String email = Credentials.EMAIL;
	private String senha = Credentials.SENHA;

	@Test
	public void testeEmail() throws Exception {

		StringBuilder sBTextoEmail = new StringBuilder();

		sBTextoEmail.append("Olá, <br/><br/>");
		sBTextoEmail.append("Você está recebendo um e-mail. <br/><br/>");
		sBTextoEmail.append("Para acessar um link misterioso, clique no botão abaixo! <br/><br/>");
		sBTextoEmail.append("<b>Testando...</b> <br/><br/>");
		sBTextoEmail.append("<span style=\"font-size:8px\">Testando...</span> <br/><br/>");
		sBTextoEmail.append(
				"<a target=\"_blank\" href=\"https://www.google.com/search?q=do+a+barrel+roll&oq=do+a+barrel+roll&aqs=chrome..69i57j0i271.149j0j9&sourceid=chrome&ie=UTF-8\" "
						+ "style=\"" + "color:#2525a7; " + "padding: 14px 25px; " + "text-align:center; "
						+ "text-decoration: none; " + "display:inline-block; " + "border-radius:30px; "
						+ "font-size:20px; " + "font-family:courier; " + "border: 3px solid green;"
						+ "background-color:#99DA39;\">Acessar Link</a>");

		ObjetoEnviaEmail email = new ObjetoEnviaEmail("pedroqueir0z@outlook.com, pedrotestejava@outlook.com",
				"Pedro Queiroz - Teste de Envio de Email", "Chegou o email enviado com java", sBTextoEmail.toString());

		//email.enviarEmail(true);
		email.enviarEmailAnexo(true);
		
		/*
		 * Caso o e-mail não esteja sendo enviado, então coloque um tempo de espera. Mas
		 * isso só pode ser usado para testes!
		 * Thread.sleep(10000);
		 */
	}
}
