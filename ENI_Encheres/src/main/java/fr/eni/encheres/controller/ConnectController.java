package fr.eni.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.service.EncheresServiceEncheres;
import fr.eni.encheres.service.EncheresServiceUtilisateur;
import jakarta.validation.Valid;

@Controller
public class ConnectController {

	EncheresServiceUtilisateur encheresServiceUtilisateur;
	EncheresServiceEncheres encheresServiceEncheres;


	// CONSTRUCTEUR
	public ConnectController(EncheresServiceUtilisateur encheresServiceUtilisateur,
			EncheresServiceEncheres encheresServiceEncheres) {
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
		this.encheresServiceEncheres = encheresServiceEncheres;
	}
	
	

	/*********** AFFICHAGE PAGE LOGIN ****************/
	@GetMapping("/login")
	public String afficherLogin(@RequestParam(value = "error", required = false) String error,Principal principal, Model model) {
		if (principal != null) {
			return "redirect:/encheres";
		} 
		if (error != null) {
	        model.addAttribute("errorMessage", "Identifiant ou mot de passe incorrect");
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

	/************* BOUTON VALIDATION REGISTER  *****************/
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult validationResult, RedirectAttributes redirectAttributes, Principal principal)  {
		
	
	if(principal!=null) {

		if ((!encheresServiceUtilisateur.isPseudoUnique(utilisateur.getPseudo())) && (utilisateur.getPseudo().equals(principal.getName()))) {
		    validationResult.rejectValue("pseudo", "pseudo.alreadyTaken", "Le pseudo est déjà pris");
		    System.out.println("if pseudo");
		}

		if ((!encheresServiceUtilisateur.isMailUnique(utilisateur.getEmail())) && (utilisateur.getEmail().equals(encheresServiceUtilisateur.findUserByPseudo(principal.getName()).getEmail()))) {
		    validationResult.rejectValue("email", "email.alreadyTaken", "Ce mail est deja associé à un compte");
		    System.out.println("if mail");
		}
//		if(validationResult.hasErrors()) {
//			return "profil";		
//		}
	}
	
		
		System.out.println(principal);
		
		if (principal == null) {
			if (!encheresServiceUtilisateur.isPseudoUnique(utilisateur.getPseudo())){
				 validationResult.rejectValue("pseudo", "pseudo.alreadyTaken", "Le pseudo est déjà pris");
			}
			
			if (!encheresServiceUtilisateur.isMailUnique(utilisateur.getEmail())){
				validationResult.rejectValue("email", "email.alreadyTaken", "Ce mail est deja associé à un compte");
			}
			
			if(validationResult.hasErrors()) {
				return "profil";		
			}
		}
		
		
		
		if(utilisateur.getPseudo()!= principal.getName()) {
			encheresServiceUtilisateur.createUtilisateur(utilisateur);
			return "redirect:/logout";
		}
			
			System.out.println("Controller bon passage");
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
	
	@GetMapping("/delete")
	public String deleteUtilisateur(@RequestParam Integer id, Model modele) {
		
		encheresServiceUtilisateur.deleteUser(id);
		return "redirect:/logout";
	}
	
	/************ AFFICHAGE PAGE MON COMPTE ********************/
	@GetMapping("/moncompte")
		public String afficherMonCompte(Principal principal, Utilisateur utilisateur, Model model) {
			utilisateur = encheresServiceUtilisateur.findUserByPseudo(principal.getName());
		model.addAttribute("utilisateur" , utilisateur);
		model.addAttribute("listeEncheres", utilisateur.getListeEncheres());
		model.addAttribute("articlesAchetes", utilisateur.getListeArticlesAchetes());
		model.addAttribute("articlesVendus", utilisateur.getListeArticlesVendus());
		model.addAttribute("serviceEncheres", encheresServiceEncheres);
			return "moncompte";
			
		}
	
}
