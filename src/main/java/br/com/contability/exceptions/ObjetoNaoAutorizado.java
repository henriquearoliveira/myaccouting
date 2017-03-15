package br.com.contability.exceptions;

import org.springframework.web.servlet.ModelAndView;

public class ObjetoNaoAutorizado extends RuntimeException {
	private static final long serialVersionUID = -6939782211030477063L;

	private ModelAndView modelAndView;

	private Object object;

	public ObjetoNaoAutorizado() {
		super("O objeto nao esta autorizado");
	}

	public ObjetoNaoAutorizado(String mensagem) {
		super(mensagem);
	}

	public ObjetoNaoAutorizado(String mensagem, ModelAndView modelAndView, Object object) {
		super(mensagem);
		this.modelAndView = modelAndView;
		this.object = object;

	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}
	
	public Object getObject() {
		return object;
	}

}
