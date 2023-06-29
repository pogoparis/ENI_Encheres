package fr.eni.encheres.controller;

import java.security.Principal;

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
import fr.eni.encheres.service.EncheresServiceRetrait;
import fr.eni.encheres.service.EncheresServiceUtilisateur;

@Controller
public class EncheresController {
	
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;
	EncheresServiceCategorie encheresServiceCategorie;
	EncheresServiceUtilisateur encheresServiceUtilisateur;
	EncheresServiceRetrait encheresServiceRetrait;

	public EncheresController(EncheresServiceArticlesVendus encheresServiceArticlesVendus, EncheresServiceCategorie encheresServiceCategorie,EncheresServiceUtilisateur encheresServiceUtilisateur, EncheresServiceRetrait encheresServiceRetrait) {
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
		this.encheresServiceCategorie = encheresServiceCategorie;
		this.encheresServiceUtilisateur=encheresServiceUtilisateur;
		this.encheresServiceRetrait=encheresServiceRetrait;
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
	
	@GetMapping("/inscription")
	public String afficherProfil(@ModelAttribute Utilisateur utilisateur) {
		System.out.println(utilisateur);
		System.out.println("ON PASSE PAR INSCRIPTION");
		return "profil";
	}
	
	@GetMapping("/profil")
	public String afficherProfil(Principal principal , Model model) {
		System.out.println("PRINCIPAL"+principal.getName());
		Utilisateur utilisateurconnecte = encheresServiceUtilisateur.findUserByPseudo(principal.getName());
		model.addAttribute("utilisateur", utilisateurconnecte);
		return "profil";
	}
	
	@PostMapping("/register")
	 public String register( @ModelAttribute Utilisateur utilisateur ) {
		encheresServiceUtilisateur.createUtilisateur(utilisateur);
        return "redirect:/login";
    }
	
	@GetMapping("/creerarticle")
	public String creerArticle(@ModelAttribute ArticleVendu articleVendu,@ModelAttribute Retrait retrait ,Model model) {
		model.addAttribute("categories", encheresServiceCategorie.findAllCategorie());
		return "creerarticle";
	}
	
	@PostMapping("/creationarticle")
	public String creationArticle(@ModelAttribute ArticleVendu articleVendu,@ModelAttribute Retrait retrait, Principal principal) {
		System.out.println(articleVendu);
		System.out.println(retrait);
		articleVendu.setUtilisateur(encheresServiceUtilisateur.findUserByPseudo(principal.getName()));
		encheresServiceArticlesVendus.createArticle(articleVendu);
		System.out.println(articleVendu);
		encheresServiceRetrait.createRetrait(retrait, articleVendu);
		System.out.println(retrait);
		return "redirect:/encheres";
	}
	
	@GetMapping("/details")
	public String afficherDetails(String pseudo,Model model) {
		
		Utilisateur utilisateur = encheresServiceUtilisateur.findUserByPseudo(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		return "details";
	}
	
	
}
