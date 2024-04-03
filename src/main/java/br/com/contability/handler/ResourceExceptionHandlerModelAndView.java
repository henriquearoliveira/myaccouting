package br.com.contability.handler;

import br.com.contability.exceptions.ObjetoComDependenciaExceptionModel;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteExceptionModel;
import br.com.contability.exceptions.ObjetoNaoAutorizadoModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ResourceExceptionHandlerModelAndView {

    @ExceptionHandler(ObjetoComDependenciaExceptionModel.class)
    public ModelAndView objetoComDependenciaExceptionModel(ObjetoComDependenciaExceptionModel e, HttpServletRequest request) {
        final ModelAndView mv = new ModelAndView("error/401");
        mv.addObject("mensagem", e.getMessage());
        return mv;
    }

    @ExceptionHandler(ObjetoExistenteExceptionModel.class)
    public ModelAndView objetoExistenteExceptionModel(ObjetoExistenteExceptionModel e, HttpServletRequest request) {
        final ModelAndView mv = new ModelAndView("error/401");
        mv.addObject("mensagem", e.getMessage());
        return mv;
    }

    @ExceptionHandler(ObjetoInexistenteExceptionModel.class)
    public ModelAndView objetoInexistenteExceptionModel(ObjetoInexistenteExceptionModel e, HttpServletRequest request) {
        final ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("mensagem", e.getMessage());
        return mv;
    }

    @ExceptionHandler(ObjetoNaoAutorizadoModel.class)
    public ModelAndView objetoNaoAutorizadoExceptionModel(ObjetoNaoAutorizadoModel e, HttpServletRequest request) {
        final ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("mensagem", e.getMessage());
        return mv;
    }

}
