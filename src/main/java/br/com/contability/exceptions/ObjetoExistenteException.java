package br.com.contability.exceptions;

public class ObjetoExistenteException extends RuntimeException {
	private static final long serialVersionUID = -6183188524594522807L;
	
	private String pagina;

	public ObjetoExistenteException() {
		super("Objeto já existente");
	}
	
	public ObjetoExistenteException(String mensagem) {
		super(mensagem);
	}
	
	public ObjetoExistenteException(String mensagem, String pagina){
		super(mensagem);
		this.pagina = pagina;
	}
	
	public String getPagina() {
		return pagina;
	}
	
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

}
