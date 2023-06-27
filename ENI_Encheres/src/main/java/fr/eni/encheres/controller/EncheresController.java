package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.service.EncheresServiceArticlesVendus;
import fr.eni.encheres.service.EncheresServiceCategorie;
import fr.eni.encheres.service.EncheresServiceUtilisateur;



@Controller
public class EncheresController {
	
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;
	EncheresServiceCategorie encheresServiceCategorie;
	EncheresServiceUtilisateur encheresServiceUtilisateur;

	
	

	
	
	public EncheresController(EncheresServiceArticlesVendus encheresServiceArticlesVendus, EncheresServiceCategorie encheresServiceCategorie,EncheresServiceUtilisateur encheresServiceUtilisateur) {
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
		this.encheresServiceCategorie = encheresServiceCategorie;
		this.encheresServiceUtilisateur=encheresServiceUtilisateur;
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
	 public String register( @ModelAttribute Utilisateur utilisateur ) {
		utilisateur.setMotDePasse( "{noop}" + utilisateur.getMotDePasse() );
		encheresServiceUtilisateur.createUtilisateur( utilisateur );
        return "security/register";
    }
	
	@GetMapping("/creerarticle")
	public String creerArticle(@ModelAttribute ArticleVendu articleVendu,@ModelAttribute Retrait retrait ,Model model) {
		model.addAttribute("categories", encheresServiceCategorie.findAllCategorie());
		
		return "creerarticle";
	}
	
	
}
