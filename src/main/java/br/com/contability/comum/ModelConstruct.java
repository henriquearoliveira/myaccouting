package br.com.contability.comum;

import org.springframework.ui.Model;

public class ModelConstruct {
	
	public static Model setAttributes(Model model,String optionMenu, String optionSubMenu){
		
		model.addAttribute(optionMenu, "active treeview");
		model.addAttribute(optionSubMenu, "active");
		
		return model;
	}

}
