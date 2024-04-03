package br.com.contability.business.resources;

import br.com.contability.business.dto.EsqueceuSenhaDTO;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CodigoUsuarioServices;
import br.com.contability.business.services.UsuarioServices;
import br.com.contability.utilitario.CaixaDeFerramentas;
import br.com.contability.utilitario.email.EmailParameters;
import br.com.contability.utilitario.email.IEnviaEmail;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/esqueceusenha")
public class EsqueceuSenhaResources {

    private final CodigoUsuarioServices services;
    private final IEnviaEmail enviaEmail;
    private final UsuarioServices usuarioServices;

    public EsqueceuSenhaResources(CodigoUsuarioServices services, IEnviaEmail enviaEmail, UsuarioServices usuarioServices) {
        this.services = services;
        this.enviaEmail = enviaEmail;
        this.usuarioServices = usuarioServices;
    }

    @GetMapping
    public ModelAndView requisitaEmail(EsqueceuSenhaDTO esqueceuSenhaDTO) {
        return new ModelAndView("EsqueceuSenha");
    }

    @PostMapping
    public ModelAndView confirmaEmail(@Valid EsqueceuSenhaDTO esqueceuSenhaDTO,
                                      RedirectAttributes redirect,
                                      HttpServletRequest request) {

        final Usuario usuario = usuarioServices.getPelo(esqueceuSenhaDTO.getEmail(), "/esqueceusenha");

        final String codigo = CaixaDeFerramentas.geraCodigo(20);
        services.insereCodigoUsuario(usuario, codigo);

        final EmailParameters email = EmailParameters.builder()
                .assunto("Ativação de cadastro no My Accounting")
                .para(esqueceuSenhaDTO.getEmail())
                .codigo(codigo)
                .url(CaixaDeFerramentas.configureURLdesired(request, "?email=" + esqueceuSenhaDTO.getEmail() + "&codigo="))
                .templateEmail("mail/RecuperaSenha")
                .build();

        enviaEmail.envia(email);

        return new ModelAndView("AlteraSenha");
    }

}
