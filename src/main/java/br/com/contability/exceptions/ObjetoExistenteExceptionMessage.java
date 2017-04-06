package br.com.contability.exceptions;

public class ObjetoExistenteExceptionMessage extends RuntimeException {
	private static final long serialVersionUID = -6183188524594522807L;

	public ObjetoExistenteExceptionMessage() {
		super("Objeto já existente");
	}

	public ObjetoExistenteExceptionMessage(String mensagem) {
		super(mensagem);

	}

}
