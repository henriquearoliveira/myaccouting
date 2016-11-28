package br.com.contability.handler;

//@ControllerAdvice
public class ResourceExceptionHandlerModelAndView {/*

	@ExceptionHandler(ObjetoComDependenciaException.class)
	public ModelAndView objetoComDependenciaException(ObjetoComDependenciaException e, HttpServletRequest request) {

		DetalheErro detalheErro = new DetalheErro();
		detalheErro.setStatus(401l);
		detalheErro.setTimestamp(System.currentTimeMillis());
		detalheErro.setMensagemDesenvolvedor(e.getMessage());

		ModelAndView model = new ModelAndView(e.getPagina());
		
		model.addObject("detalheErro", detalheErro);

		return model;

	}

	@ExceptionHandler(ObjetoExistenteException.class)
	public ModelAndView objetoExistenteException(ObjetoExistenteException e, HttpServletRequest request) {

		DetalheErro detalheErro = new DetalheErro();
		detalheErro.setStatus(401l);
		detalheErro.setTimestamp(System.currentTimeMillis());
		detalheErro.setMensagemDesenvolvedor(e.getMessage());

		ModelAndView model = new ModelAndView(e.getPagina());

		model.addObject("detalheErro", detalheErro);

		return model;

	}

	@ExceptionHandler(ObjetoInexistenteException.class)
	public ModelAndView objetoInexistenteException(ObjetoInexistenteException e, HttpServletRequest request) {

		DetalheErro detalheErro = new DetalheErro();
		detalheErro.setStatus(404l);
		detalheErro.setTimestamp(System.currentTimeMillis());
		detalheErro.setMensagemDesenvolvedor(e.getMessage());

		ModelAndView model = new ModelAndView(e.getPagina());
		
		model.addObject("detalheErro", detalheErro);

		return model;

	}

	@ExceptionHandler(ObjetoNaoAutorizado.class)
	public ModelAndView objetoNaoAutorizado(ObjetoNaoAutorizado e, HttpServletRequest request) {

		DetalheErro detalheErro = new DetalheErro();
		detalheErro.setStatus(401l);
		detalheErro.setTimestamp(System.currentTimeMillis());
		detalheErro.setMensagemDesenvolvedor(e.getMessage());

		ModelAndView model = new ModelAndView(e.getPagina());

		model.addObject("detalheErro", detalheErro);

		return model;

	}*/

}
