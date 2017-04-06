package br.com.contability.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import br.com.contability.comum.StringPaginasAndRedirect;
import br.com.contability.exceptions.ObjetoComDependenciaExceptionModel;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteExceptionModel;
import br.com.contability.exceptions.ObjetoNaoAutorizadoModel;

@ControllerAdvice
public class ResourceExceptionHandlerModelAndView {
	
	@ExceptionHandler(ObjetoComDependenciaExceptionModel.class)
	public ModelAndView objetoComDependenciaExceptionModel(ObjetoComDependenciaExceptionModel e, HttpServletRequest request) {
		
		/*RedirectAttributes redirect =  RedirectAttributesAbstract.getInstance().getRedirectAttributes();
		redirect.addFlashAttribute("mensagem", "Categoria salvo com sucesso.");*/
		
		/*redirect.getFlashAttributes().entrySet().forEach(r -> {
			System.out.println(r.getKey() + " valor " + r.getValue());
		});*/
		
		ModelAndView mv = new ModelAndView("error/401");
		mv.addObject("mensagem", e.getMessage());
		
		return mv;

	}
	
	@ExceptionHandler(ObjetoExistenteExceptionModel.class)
	public RedirectView objetoExistenteExceptionModel(ObjetoExistenteExceptionModel e, HttpServletRequest request) {
/*
		ModelAndView mv = new ModelAndView("error/401");
		mv.addObject("mensagem", e.getMessage());
		
		return mv;*/
		
		System.out.println("deu algo");
		RedirectView rw = new RedirectView(StringPaginasAndRedirect.LOGIN);
	    rw.setStatusCode(HttpStatus.MOVED_PERMANENTLY); // you might not need this
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    System.out.println("deu algo2");
	    if (outputFlashMap != null){
	    	System.out.println("deu algo3");
	        outputFlashMap.put("mensagem", "teste");
	    }
	    System.out.println("deu algo4");
	    
	    return rw;

	}
	
	@ExceptionHandler(ObjetoInexistenteExceptionModel.class)
	public ModelAndView objetoInexistenteExceptionModel(ObjetoInexistenteExceptionModel e, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("error/404");
		mv.addObject("mensagem", e.getMessage());
		
		return mv;

	}
	
	@ExceptionHandler(ObjetoNaoAutorizadoModel.class)
	public ModelAndView objetoNaoAutorizadoExceptionModel(ObjetoNaoAutorizadoModel e, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView("error/404");
		mv.addObject("mensagem", e.getMessage());
		
		return mv;

	}
	
}
