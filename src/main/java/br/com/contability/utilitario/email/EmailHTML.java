package br.com.contability.utilitario.email;

import br.com.contability.exceptions.ObjetoNaoAutorizadoException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Locale;

@Component
public class EmailHTML implements IEnviaEmail {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine thymeleaf;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailHTML(JavaMailSender mailSender, SpringTemplateEngine thymeleaf) {
        this.mailSender = mailSender;
        this.thymeleaf = thymeleaf;
    }

    @Override
    public void envia(EmailParameters emailParameters) {

        final Locale locale = new Locale("pt");

        final Context ctx = new Context(locale);
        ctx.setVariable("codigo", emailParameters.getCodigo());
        ctx.setVariable("urlPagina", emailParameters.getUrl()); //"http://localhost:9090"
        final MimeMessage mensagem = mailSender.createMimeMessage();

        try {

            final MimeMessageHelper mensagemAjuda = new MimeMessageHelper(mensagem, false, "UTF-8");
            mensagemAjuda.setSubject(emailParameters.getAssunto());
            mensagemAjuda.setFrom(emailFrom);
            mensagemAjuda.setTo(emailParameters.getPara());

            final String corpoHtml = thymeleaf.process(emailParameters.getTemplateEmail(), ctx);
            mensagemAjuda.setText(corpoHtml, true);

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new ObjetoNaoAutorizadoException(e.getMessage());
        }
    }
}
