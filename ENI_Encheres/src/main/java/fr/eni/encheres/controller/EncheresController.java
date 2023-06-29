package fr.eni.encheres.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
	
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
	public String creationArticle(@RequestParam("image") MultipartFile file, @ModelAttribute ArticleVendu articleVendu,@ModelAttribute Retrait retrait, Principal principal, Model model) throws IOException {
		articleVendu.setUtilisateur(encheresServiceUtilisateur.findUserByPseudo(principal.getName()));
		encheresServiceArticlesVendus.createArticle(articleVendu);
		encheresServiceRetrait.createRetrait(retrait, articleVendu);
		//images
		StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
		return "redirect:/encheres";
	}
	
	@GetMapping("/details")
	public String afficherDetails(String pseudo,Model model) {
		
		Utilisateur utilisateur = encheresServiceUtilisateur.findUserByPseudo(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		return "details";
	}
	
	@GetMapping("/article")
	public String afficherDetailsArticles(Integer id,Model model) {
		System.out.println("ON ENTRE DANS LE CONTROLLER");
		System.out.println("L'ID UTILISE EST : "+id);
		ArticleVendu article = encheresServiceArticlesVendus.findArticleById(id);
		System.out.println(article);
		Retrait retrait = encheresServiceRetrait.findRetraitByArticle(article);
		System.out.println(retrait);
		model.addAttribute("article", article);
		model.addAttribute("retrait", retrait);
		
		return "article";
	}
	
	
//	@PostMapping("/upload") 
//    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
//        StringBuilder fileNames = new StringBuilder();
//        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
//        fileNames.append(file.getOriginalFilename());
//        Files.write(fileNameAndPath, file.getBytes());
//        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
//        return "redirect:/creerarticle";
//    }
	
}
