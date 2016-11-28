package br.com.contability.exceptions;

public class ObjetoInexistenteException extends RuntimeException{
	private static final long serialVersionUID = 2185739775799837242L;
	
	private String pagina;
	
	public ObjetoInexistenteException(){
		super("O objeto requisitado não existe");
	}
	
	public ObjetoInexistenteException(String mensagem){
		super(mensagem);
	}
	
	public ObjetoInexistenteException(String mensagem, String pagina){
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
