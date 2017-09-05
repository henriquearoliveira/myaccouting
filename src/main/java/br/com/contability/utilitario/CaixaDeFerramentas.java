package br.com.contability.utilitario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class CaixaDeFerramentas {

	/**
	 * @param valorConversao
	 * @return
	 */
	public static BigDecimal converteStringToBidDecimal(String valorConversao) {

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
	public static LocalDate calendarFromStringDate(String dateToFormat) {

		Date date = new Date();

		try {
			date = new SimpleDateFormat("MMMMM yyyy", Locale.US).parse(dateToFormat);
			;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return localDate;
	}

	/**
	 * @param dateTimeCloudinary
	 * @return LocalDateTime
	 */
	public static LocalDateTime stringToLocalDateTime(String dateTimeCloudinary) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return LocalDateTime.parse(dateTimeCloudinary.replace("T", " ").replace("Z", ""), format);

	}

	/**
	 * @param quatidadeCaracteres
	 * @return codigo
	 */
	public static String geraCodigo(int quatidadeCaracteres) {

		String codigo = UUID.randomUUID().toString().replaceAll("-", "").substring(0, (quatidadeCaracteres));

		return codigo;

	}

	/**
	 * @param request
	 * @return String PARA VINCULAR A CONFIRMAÇÃO DO LOGIN DE UM NOVO USUARIO
	 */
	public static String configureURLdesired(HttpServletRequest request, String urlParametersDesired) {
		return request.getRequestURL().toString().replace(request.getRequestURI(), urlParametersDesired);
	}

}
