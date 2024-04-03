package br.com.contability.comum;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ValorIncorreto {

    private final RedirectAttributesAbstract redirectAttributesAbstract;

    public ValorIncorreto(RedirectAttributesAbstract redirectAttributesAbstract) {
        this.redirectAttributesAbstract = redirectAttributesAbstract;
    }

    /**
     * @return redirect
     */
    public ModelAndView defineRedirecionamentoComMensagem(String textoMensagemErro, String textoMensagemSucesso, String redirecionamento) {
        if (textoMensagemErro == null) {
            redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("mensagem", textoMensagemSucesso);
        } else {
            redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("erro", textoMensagemErro);
        }
        return new ModelAndView(redirecionamento);

    }

}
