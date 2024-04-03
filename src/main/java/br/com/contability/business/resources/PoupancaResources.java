package br.com.contability.business.resources;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.ContaServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/poupanca")
public class PoupancaResources {

    private final AuthenticationAbstract auth;
    private final ContaServices contaServices;

    public PoupancaResources(AuthenticationAbstract auth, ContaServices contaServices) {
        this.auth = auth;
        this.contaServices = contaServices;
    }

    @GetMapping("/lista")
    public ModelAndView lista(Model model, @RequestParam("idConta") Long idConta) {
        ModelConstruct.setAttributes(model, "activeLiPoupanca", "activeListagem");

        final Usuario usuario = auth.getAutenticacao();
        final ModelAndView mv = new ModelAndView("poupanca/Listagem");
        return contaServices.getConta(usuario, mv, idConta);
    }
}
