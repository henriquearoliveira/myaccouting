package br.com.contability.handler;

import br.com.contability.exceptions.ObjetoExistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoNaoAutorizadoMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class ResourceExceptionHandlerMessage {

    @ExceptionHandler(ObjetoExistenteExceptionMessage.class)
    public RedirectView objetoExistenteExceptionMessage(ObjetoExistenteExceptionMessage e, HttpServletRequest request) {

        final RedirectView rw = new RedirectView(e.getRedirect());
        final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("erro", e.getMessage());
        return rw;
    }

    @ExceptionHandler(ObjetoInexistenteExceptionMessage.class)
    public RedirectView objetoInexistenteExceptionMessage(ObjetoInexistenteExceptionMessage e, HttpServletRequest request) {

        final RedirectView rw = new RedirectView(e.getRedirect());
        final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("erro", e.getMessage());
        return rw;
    }

    @ExceptionHandler(ObjetoNaoAutorizadoMessage.class)
    public RedirectView objetoNaoAutorizadoExceptionMessage(ObjetoNaoAutorizadoMessage e, HttpServletRequest request) {

        final RedirectView rw = new RedirectView(e.getRedirect());
        final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        outputFlashMap.put("erro", e.getMessage());
        return rw;
    }

}
