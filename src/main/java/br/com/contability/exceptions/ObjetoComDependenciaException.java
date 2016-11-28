package br.com.contability.exceptions;

public class ObjetoComDependenciaException extends RuntimeException {
	private static final long serialVersionUID = -8697787632237739440L;
	
	private String pagina;

	public ObjetoComDependenciaException(String mensagem, String pagina){
		super(mensagem);
		this.pagina = pagina;
	}
	
	public ObjetoComDependenciaException(){
		super("Objeto com dependências");
	}
	
	public ObjetoComDependenciaException(String mensagem){
		super(mensagem);
	}
	
	public String getPagina() {
		return pagina;
	}
	
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

}
