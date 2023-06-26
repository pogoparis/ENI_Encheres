package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.encheres.service.EncheresServiceArticlesVendus;



@Controller
public class EncheresController {
	
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;

	
	
	@GetMapping("/encheres")
	public String afficherAccueil(Model model) {
		model.addAttribute("listeArticles", encheresServiceArticlesVendus.findAllArticleVendu());
		
		
		
		return "index";
		
	}
	
	@GetMapping("/")
	public String afficherRien() {
		
		
		
		return "redirect:/encheres";
	
}
	
}
