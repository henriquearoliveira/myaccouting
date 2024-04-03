package br.com.contability.business.resources;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.CodigoUsuarioServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.RedirectAttributesAbstract;
import br.com.contability.comum.StringPaginasAndRedirect;
import br.com.contability.relatorios.Relatorio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/login")
public class LoginResources {

    private final AuthenticationAbstract auth;
    private final CodigoUsuarioServices services;
    private final RedirectAttributesAbstract redirect;
    private final Relatorio relatorio;

    public LoginResources(AuthenticationAbstract auth, CodigoUsuarioServices services, RedirectAttributesAbstract redirect, Relatorio relatorio) {
        this.auth = auth;
        this.services = services;
        this.redirect = redirect;
        this.relatorio = relatorio;
    }

    @GetMapping
    public String login() { // pode ser também. @AuthenticationPrincipal User user

        final Usuario usuario = auth.getAutenticacao();

        if (usuario != null)
            return "redirect:index";

        return "Login";
    }

    @GetMapping("/testando")
    public ModelAndView getRpt4() {
        final Map<String, Object> parametros = new HashMap<>();
        parametros.put("ID", 3);
        return relatorio.criaRelatorio(parametros, "rpt_HelloWorld");
    }

    @GetMapping("/requestcode")
    public String loginConfirmacao(@RequestParam("codigo") String codigo, RedirectAttributes attributes) {
        redirect.setRedirectAttributes(attributes);
        services.confirmaCodigoUsuario(codigo);
        attributes.addFlashAttribute("mensagem", "Usuario ativado, por favor digite o usuario e senha");
        return StringPaginasAndRedirect.LOGIN;

    }

    // APRENDIZAGEM ABAIXO. NADA FUNCIONOU

    /*
     * @Autowired private DataSource dataSource;
     *
     * @GetMapping("/testando") public ModelAndView getRpt4() { Map<String, Object>
     * modelMap = new HashMap<>(); modelMap.put("datasource", dataSource);
     * modelMap.put("format", "pdf"); modelMap.put("ID", 3); ModelAndView
     * modelAndView = new ModelAndView("rpt_HelloWorld", modelMap); return
     * modelAndView; }
     *
     * @RequestMapping(value = "/login") public ModelAndView paginLogin(){
     *
     * ModelAndView mv = new ModelAndView("Login"); return mv;
     *
     * }
     *
     * @RequestMapping(value = "/autorizado") public String loginSucesso(){
     *
     * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     * System.out.println(auth.getName());
     *
     * return "Login";
     *
     * }
     *
     * RequestMapping(value = "/login", method = RequestMethod.POST) public
     * ModelAndView loginn(LoginUsuario loginUsuario){ // pode ser
     * também. @AuthenticationPrincipal User user
     *
     * System.out.println("passei");
     *
     * if(result.hasErrors()){ //return login(loginUsuario); }
     *
     * System.out.println(loginUsuario.getNome());
     *
     * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     * ModelAndView mv = new ModelAndView();
     *
     * System.out.println(auth.getName());
     *
     * if(auth == null){ //mv = new ModelAndView("/Login"); //caminho //return
     * mv.addObject("incorrect", true); } return mv = new ModelAndView("/usuario");
     *
     * }
     *
     * @RequestMapping("/usuario") public String login(){
     *
     *
     * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     *
     * System.out.println(" email " + auth.getName());
     *
     * System.out.println(user.getUsername());
     *
     * return ""; }
     */
}
