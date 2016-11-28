package br.com.contability.business.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.business.Usuario;
import br.com.contability.comum.AuthenticationAbstract;

@Controller
@RequestMapping(value = "/login")
public class LoginResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@RequestMapping
	public String login(){ // pode ser também. @AuthenticationPrincipal User user

		Usuario usuario = auth.getAutenticacao();
		
		if (usuario != null)
			return "redirect:index";
		
		return "Login";
		
	}
	
	//	APRENDIZAGEM ABAIXO. NADA FUNCIONOU
	/*@RequestMapping(value = "/login")
	public ModelAndView paginLogin(){
		
		ModelAndView mv = new ModelAndView("Login");
		return mv;
		
	}*/
	/*@RequestMapping(value = "/autorizado")
	public String loginSucesso(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		
		return "Login";
		
	}*/
	
	//RequestMapping(value = "/login", method = RequestMethod.POST)
	//public ModelAndView loginn(LoginUsuario loginUsuario){ // pode ser também. @AuthenticationPrincipal User user
		
		//System.out.println("passei");
		
		//if(result.hasErrors()){
			//return login(loginUsuario);
		//}
		
		//System.out.println(loginUsuario.getNome());
		
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//ModelAndView mv = new ModelAndView();
		
		//System.out.println(auth.getName());
		
//		if(auth == null){
			//mv = new ModelAndView("/Login"); //caminho
			//return mv.addObject("incorrect", true);
//		}	
//		return mv = new ModelAndView("/usuario");
		
	//}
	
	/*@RequestMapping("/usuario")
	public String login(){
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		System.out.println(" email " + auth.getName());
		
		System.out.println(user.getUsername());
		
		return "";
	}*/

}
