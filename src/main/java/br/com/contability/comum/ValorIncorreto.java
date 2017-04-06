package br.com.contability.comum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ValorIncorreto {
	
	@Autowired
	private RedirectAttributesAbstract redirectAttributesAbstract;

	public ValorIncorreto() {
	}
	
	/**
	 * @param redirectAttributesAbstract
	 * @param textoMensagem
	 * @return redirect
	 */
	public ModelAndView defineRedirecionamentoComMensagem(String textoMensagemErro, String textoMensagemSucesso, String redirecionamento){
		if (textoMensagemErro == null){
			redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("mensagem", textoMensagemSucesso);
		} else{
			System.out.println("aqui");
			System.out.println(redirectAttributesAbstract != null);
			redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("erro", textoMensagemErro);
		}
		System.out.println("passou");
		return new ModelAndView(redirecionamento);
		
	}

}
