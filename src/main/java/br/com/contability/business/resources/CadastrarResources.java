package br.com.contability.business.resources;

import br.com.contability.business.dto.CadastroDTO;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CadastrarServices;
import br.com.contability.business.services.UsuarioServices;
import br.com.contability.comum.StringPaginasAndRedirect;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarResources {

    private final CadastrarServices cadastrarServices;
    private final UsuarioServices usuarioServices;

    public CadastrarResources(CadastrarServices cadastrarServices, UsuarioServices usuarioServices) {
        this.cadastrarServices = cadastrarServices;
        this.usuarioServices = usuarioServices;
    }

    @GetMapping
    public ModelAndView novo(CadastroDTO cadastroDTO) {
        return new ModelAndView("Cadastrar");
    }

    @PostMapping()
    public ModelAndView cadastrar(@Valid CadastroDTO cadastroDTO,
                                  BindingResult result,
                                  RedirectAttributes attributes,
                                  HttpServletRequest request) {
        if (result.hasErrors())
            novo(cadastroDTO);

        final Usuario usuario = usuarioServices.insere(cadastroDTO);
        cadastrarServices.confirmaUsuario(cadastroDTO, usuario, request);
        attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso, por favor visualize o email enviado"
                + "para confirmar o cadastro");

        return new ModelAndView(StringPaginasAndRedirect.LOGIN);
    }

}
