package br.com.contability.business.resources;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class Teste {
	
	public Model model;
	
	public RedirectAttributes redirectAttributes;

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public RedirectAttributes getRedirectAttributes() {
		return redirectAttributes;
	}

	public void setRedirectAttributes(RedirectAttributes redirectAttributes) {
		this.redirectAttributes = redirectAttributes;
	}
	

}
