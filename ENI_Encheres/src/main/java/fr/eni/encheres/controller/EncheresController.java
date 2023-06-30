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
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.service.EncheresServiceArticlesVendus;
import fr.eni.encheres.service.EncheresServiceCategorie;
import fr.eni.encheres.service.EncheresServiceEncheres;
import fr.eni.encheres.service.EncheresServiceRetrait;
import fr.eni.encheres.service.EncheresServiceUtilisateur;

@Controller
public class EncheresController {
	
	EncheresServiceArticlesVendus encheresServiceArticlesVendus;
	EncheresServiceCategorie encheresServiceCategorie;
	EncheresServiceUtilisateur encheresServiceUtilisateur;
	EncheresServiceRetrait encheresServiceRetrait;
	EncheresServiceEncheres encheresServiceEncheres;
	
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") +  "/src/main/resources/static/Images";
	//private static final String UPLOAD_DIR ="/Images";
	
	

	public EncheresController(EncheresServiceArticlesVendus encheresServiceArticlesVendus,
			EncheresServiceCategorie encheresServiceCategorie, EncheresServiceUtilisateur encheresServiceUtilisateur,
			EncheresServiceRetrait encheresServiceRetrait, EncheresServiceEncheres encheresServiceEncheres) {
		this.encheresServiceArticlesVendus = encheresServiceArticlesVendus;
		this.encheresServiceCategorie = encheresServiceCategorie;
		this.encheresServiceUtilisateur = encheresServiceUtilisateur;
		this.encheresServiceRetrait = encheresServiceRetrait;
		this.encheresServiceEncheres = encheresServiceEncheres;
	}

	@PostMapping("/encherir")
	public String encherir(@ModelAttribute Enchere enchere,  Model model ) {
		System.out.println("ENCHERES "+enchere);
		
		encheresServiceEncheres.creationEncheres(enchere);
		encheresServiceArticlesVendus.majPrixArticle(enchere);
		encheresServiceUtilisateur.majCreditUtilisateur(enchere);
		return "redirect:/article?id="+enchere.getArticle().getNo_article();
	}
	
	
	@GetMapping("/encheres")
	public String afficherAccueil(Model model) {
		model.addAttribute("listeCategorie", encheresServiceCategorie.findAllCategorie());
		model.addAttribute("listeArticles", encheresServiceArticlesVendus.findAllArticleVendu());
		return "index";
	}
	
	@GetMapping("/")
	public String afficherRien() {
		return "redirect:/encheres";
	}
	
	@GetMapping("/login")
	public String afficherLogin(Principal principal) {
		if (principal != null ) {
			return "redirect:/encheres";
		} 	
			return "login";	
	}
	
	@GetMapping("/inscription")
	public String afficherProfil(@ModelAttribute Utilisateur utilisateur) {
		return "profil";
	}
	
	@GetMapping("/profil")
	public String afficherProfil(Principal principal , Model model) {
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
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, (articleVendu.getNo_article().toString()+".jpg") );
        fileNames.append(articleVendu.getNo_article()+".jpg");
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", fileNames.toString());
		return "redirect:/encheres";
	}
	
	@GetMapping("/details")
	public String afficherDetails(String pseudo,Model model) {
		
		Utilisateur utilisateur = encheresServiceUtilisateur.findUserByPseudo(pseudo);
		model.addAttribute("utilisateur", utilisateur);
		return "details";
	}
	
	@GetMapping("/article")
	public String afficherDetailsArticles(Integer id, Model model, Principal principal, @ModelAttribute Enchere enchere) {
		ArticleVendu article = encheresServiceArticlesVendus.findArticleById(id);
		Retrait retrait = encheresServiceRetrait.findRetraitByArticle(article);
		Enchere meilleureEnchere = encheresServiceEncheres.getMeilleureEnchereByArticle(article);
		
		if(principal!=null) {
			model.addAttribute("user", encheresServiceUtilisateur.findUserByPseudo(principal.getName()));
			
		}
		System.out.println("ENCHERE PRE VALIDATION"+enchere);
		model.addAttribute("article", article);
		model.addAttribute("retrait", retrait);
		model.addAttribute("meilleureEnchere", meilleureEnchere);
		Boolean tokenAffichage = encheresServiceEncheres.affichageDuBouton(article, principal);
		model.addAttribute("tokenAffichage", tokenAffichage);
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
