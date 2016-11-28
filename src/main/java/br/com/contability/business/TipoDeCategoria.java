package br.com.contability.business;

public enum TipoDeCategoria {
	
	DESPESA("Despesa"), RECEITA("Receita");
	
	private String descricao;
	
	private TipoDeCategoria(String descricao) {
		
		this.descricao = descricao;
		
	}
	
	public String getDescricao() {
		return descricao;
	}

}
