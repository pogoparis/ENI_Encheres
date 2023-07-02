package fr.eni.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.service.EncheresServiceUtilisateur;
import jakarta.validation.Valid;

@Controller
public class ConnectController {

	EncheresServiceUtilisateur encheresServiceUtilisateur;

	// CONSTRUCTEUR
	public ConnectController(EncheresServiceUtilisateur encheresServiceUtilisateur) {
		super();
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
	}

	/*********** AFFICHAGE PAGE LOGIN ****************/
	@GetMapping("/login")
	public String afficherLogin(Principal principal, Model model) {
		if (principal != null) {
			return "redirect:/encheres";
		}
		if (model.containsAttribute("successMessage")) {
	        String successMessage = (String) model.getAttribute("successMessage");
	        model.addAttribute("successMessage", successMessage);
		}
		return "login";
	}

	/************** AFFICHAGE PAGE INSCRIPTION **************/
	@GetMapping("/inscription")
	public String afficherProfil(@ModelAttribute Utilisateur utilisateur) {
		return "profil";
	}

	/************* BOUTON VALIDATION REGISTER *****************/
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult validationResult, RedirectAttributes redirectAttributes) {
		if(validationResult.hasErrors()) {
			return "profil";
		}
		encheresServiceUtilisateur.createUtilisateur(utilisateur);
		redirectAttributes.addFlashAttribute("successMessage", "L'enregistrement a réussi. Veuillez vous connecter avec vos identifiants.");
		return "redirect:/login";
	}

	/****************** AFFICHAGE DETAIL UTILISATEUR (DETAIL) *******************/
	@GetMapping("/details")
	public String afficherDetails(String pseudo, Model model, Principal principal) {
		Utilisateur utilisateur = encheresServiceUtilisateur.findUserByPseudo(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		if (principal != null) {
			model.addAttribute("pseudoConnecte", principal.getName());
			return "details";
		}
		return "details";
	}

	/*********** AFFFICHAGE PAGE PROFIL (MODIF/CREATION) *****************/
	@GetMapping("/profil")
	public String afficherProfil(Principal principal, Model model) {
		Utilisateur utilisateurconnecte = encheresServiceUtilisateur.findUserByPseudo(principal.getName());
		model.addAttribute("utilisateur", utilisateurconnecte);
		return "profil";
	}
}
