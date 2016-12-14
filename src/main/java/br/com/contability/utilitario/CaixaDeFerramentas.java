package br.com.contability.utilitario;

import java.math.BigDecimal;

public class CaixaDeFerramentas {
	
	/**
	 * @param valorConversao
	 * @return
	 */
	public static BigDecimal converteStringToBidDecimal(String valorConversao){
		
		String valorFormatada = convertePontosAndVirgulas(valorConversao); 
		BigDecimal bigDecimal = new BigDecimal(valorFormatada);
		
		return bigDecimal;
	}

	/**
	 * @param valorConversao
	 * @return
	 */
	private static String convertePontosAndVirgulas(String valorConversao) {
		return valorConversao.replaceAll("\\.", "").replaceAll("\\,", ".");
	}

}
