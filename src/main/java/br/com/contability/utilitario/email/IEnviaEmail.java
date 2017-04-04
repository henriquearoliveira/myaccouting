package br.com.contability.utilitario.email;

public interface IEnviaEmail {
	
	public void envia(String assunto, String para, String codigo, String url);

}
