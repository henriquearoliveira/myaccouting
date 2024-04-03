package br.com.contability.utilitario;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class CaixaDeFerramentas {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaixaDeFerramentas.class);

    /**
     * @param keyExtractor
     * @return O MAPA SÓ USA AQUI DENTRO, ELE É USADO PRA CONSEGUIR DESTINGUIR SE ADICIONA OU NÃO
     * ASSIM QUE ELE CONSEGUE FILTRAR COM O STREAM
     * <p>
     * OBJECT ESTAVA NO LUGAR DE R
     */
    public static <T, R> Predicate<T> distinctByKey(Function<? super T, R> keyExtractor) {
        final Map<R, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * @param valor
     * @return
     */
    public static BigDecimal alteraValorParaPositivo(BigDecimal valor) {

        if (valor.signum() == -1) {
            return valor.negate();
        }

        return valor;
    }

    /**
     * @param seconds SLEEP DA THREAD
     */
    public void aguardaTempoThread(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            LOGGER.error("Erro no thread sleep", e);
        }
    }

    /**
     * @param valorConversao
     * @return
     */
    public static BigDecimal converteStringToBidDecimal(String valorConversao) {

        final StringBuilder s = new StringBuilder(valorConversao);

        final String valorFormatada = s.reverse().substring(2).contains(",")
                ? convertePontosAndVirgulas(valorConversao)
                : valorConversao;

        return new BigDecimal(valorFormatada);
    }

    /**
     * @param date
     * @return
     */
    public static LocalDate converteDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
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
    public static LocalDate calendarFromStringMesAnoDate(String dateToFormat) {

        try {
            final Date date = new SimpleDateFormat("MMMMM yyyy", Locale.US).parse(dateToFormat);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param dateToFormat
     * @return
     */
    public static LocalDate calendarFromStringDiaMesAnoDate(String dateToFormat) {
        return LocalDate.parse(dateToFormat);
    }

    /**
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {

        final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime.replace("T", " ").replace("Z", ""), format);
    }

    /**
     * @return LocalDateTime
     */
    public static LocalDate stringToLocalDateEXCEL(String date) {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, format);
    }

    /**
     * @return LocalDateTime
     */
    public static LocalDate stringToLocalDateLibreOffice(String dateLibreOffice) {
        final DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date date = null;
        try {
            date = formatter.parse(dateLibreOffice);
        } catch (ParseException e) {
            LOGGER.error("Erro no parse do LocalDate", e);
        }

        return converteDateToLocalDate(date);
    }

    /**
     * @param quatidadeCaracteres
     * @return codigo
     */
    public static String geraCodigo(int quatidadeCaracteres) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, (quatidadeCaracteres));
    }

    /**
     * @param request
     * @return String PARA VINCULAR A CONFIRMAÇÃO DO LOGIN DE UM NOVO USUARIO
     */
    public static String configureURLdesired(HttpServletRequest request, String urlParametersDesired) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), urlParametersDesired);
    }
}
