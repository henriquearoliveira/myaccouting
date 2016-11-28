package br.com.contability.exceptions;

public class ObjetoNaoAutorizado extends RuntimeException {
	private static final long serialVersionUID = -6939782211030477063L;
	
	private String pagina;

	public ObjetoNaoAutorizado() {
		super("O objeto nao esta autorizado");
	}
	
	public ObjetoNaoAutorizado(String mensagem){
		super(mensagem);
	}

	public ObjetoNaoAutorizado(String mensagem, String pagina) {
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
