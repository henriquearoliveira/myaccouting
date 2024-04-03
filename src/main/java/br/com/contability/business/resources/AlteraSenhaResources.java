package br.com.contability.business.resources;

import br.com.contability.business.dto.AlteraSenhaDTO;
import br.com.contability.business.services.CodigoUsuarioServices;
import br.com.contability.comum.StringPaginasAndRedirect;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/alterasenha")
public class AlteraSenhaResources {

    @Autowired
    private CodigoUsuarioServices services;

    @GetMapping
    public ModelAndView solicitaAlterarSenha(@RequestParam("email") String email, @RequestParam("codigo") String codigo) {

        // CONFIGURO DESSA FORMA DEVIDO SER NECESSÁRIO SETAR O EMAIL E CÓDIGO
        // CASO CONTRARIO BASTARIA NOS PARAMETROS DO MÉTODO COLOCAR UM
        // AlteraSenha alteraSenhas
        final AlteraSenhaDTO alteraSenhaDTO = new AlteraSenhaDTO();
        alteraSenhaDTO.setEmail(email);
        alteraSenhaDTO.setCodigo(codigo);

        final ModelAndView mv = new ModelAndView("AlteraSenha");
        mv.addObject("alteraSenha", alteraSenhaDTO);

        return mv;
    }

    @PostMapping
    public ModelAndView alteraSenha(@Valid AlteraSenhaDTO alteraSenhaDTO, BindingResult result,
                                    RedirectAttributes redirect, HttpServletRequest request) {

        // FAREI DIFERENTE DEVIDO AO HTML NÃO ESTAR VINCULADO AO BOOTSTRAP NAS TELAS DE LOGIN
        if (result.hasErrors()) {
            throw new ObjetoInexistenteExceptionMessage("/alterasenha?email=" +
                    alteraSenhaDTO.getEmail() + "&codigo=" + alteraSenhaDTO.getCodigo(), "Os campos não podem ser nulos");
        }

        services.alteraSenha(alteraSenhaDTO);
        redirect.addFlashAttribute("mensagem", "Senha alterada com sucesso");
        return new ModelAndView(StringPaginasAndRedirect.LOGIN);
    }

}
