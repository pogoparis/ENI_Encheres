package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.encheres.service.EncheresServiceArticlesVendus;



@Controller
public class EncheresController {
	
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;
	
	

	
	
	public EncheresController(EncheresServiceArticlesVendus encheresServiceArticlesVendus) {
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
	}

	@GetMapping("/encheres")
	public String afficherAccueil(Model model) {
		model.addAttribute("listeArticles", encheresServiceArticlesVendus.findAllArticleVendu());
		
		
		
		return "index";
		
	}
	
	@GetMapping("/")
	public String afficherRien() {
		
		
		
		return "redirect:/encheres";
	
}
	
	@GetMapping("/login")
	public String afficherLogin() {
		
		return "login";
	}
	
	@GetMapping("/profil")
	public String afficherProfil() {
		
		return "profil";
	}
	
	@PostMapping("/login")
	public String login() {
		
		return "redirect:/encheres";
	}
	
	@GetMapping("/creerarticle")
	public String creerArticle() {
		
		return "creerarticle";
	}
	
	
}
