package br.com.contability.handler;

import br.com.contability.exceptions.DetalheErro;
import br.com.contability.exceptions.ObjetoComDependenciaException;
import br.com.contability.exceptions.ObjetoExistenteException;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoNaoAutorizadoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjetoComDependenciaException.class)
    public ResponseEntity<DetalheErro> objetoComDependenciaException(ObjetoComDependenciaException e,
                                                                     HttpServletRequest request) {

        DetalheErro detalheErro = new DetalheErro();
        detalheErro.setStatus(401L);
        detalheErro.setTimestamp(System.currentTimeMillis());
        detalheErro.setMensagemDesenvolvedor(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detalheErro);

    }

    @ExceptionHandler(ObjetoExistenteException.class)
    public ResponseEntity<DetalheErro> objetoExistenteException(ObjetoExistenteException e,
                                                                HttpServletRequest request) {

        DetalheErro detalheErro = new DetalheErro();
        detalheErro.setStatus(401L);
        detalheErro.setTimestamp(System.currentTimeMillis());
        detalheErro.setMensagemDesenvolvedor(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detalheErro);

    }

    @ExceptionHandler(ObjetoInexistenteException.class)
    public ResponseEntity<DetalheErro> objetoInexistenteException(ObjetoInexistenteException e,
                                                                  HttpServletRequest request) {

        DetalheErro detalheErro = new DetalheErro();
        detalheErro.setStatus(404L);
        detalheErro.setTimestamp(System.currentTimeMillis());
        detalheErro.setMensagemDesenvolvedor(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detalheErro);

    }

    @ExceptionHandler(ObjetoNaoAutorizadoException.class)
    public ResponseEntity<DetalheErro> objetoNaoAutorizado(ObjetoNaoAutorizadoException e,
                                                           HttpServletRequest request) {

        final DetalheErro detalheErro = new DetalheErro();
        detalheErro.setStatus(401L);
        detalheErro.setTimestamp(System.currentTimeMillis());
        detalheErro.setMensagemDesenvolvedor(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detalheErro);
    }
}
