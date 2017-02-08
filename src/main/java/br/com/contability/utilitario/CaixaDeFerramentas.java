package br.com.contability.utilitario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
	
	/**
	 * @param dateToFormat
	 * @return
	 */
	public static Calendar calendarFromStringDate(String dateToFormat){
		
		Calendar calendar = Calendar.getInstance();
		
		Date date = new Date();
		
		try {
			date = new SimpleDateFormat("MMMMM yyyy", Locale.US).parse(dateToFormat);;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		calendar.setTime(date);
		
		return calendar;
	}

}
