package fr.eni.encheres.service;

import java.security.Principal;
import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresServiceEncheres {
	
	// On peut enchérir (apparition du bouton selon) certaines conditions
	public Boolean affichageDuBouton(ArticleVendu article, Principal user);
	
	public String messageBoutonEncherirIndisponible(ArticleVendu article, Principal user);
	
	//On peut cloturer la vente si les conditions sont reunies
	public Boolean affichageBoutonCloture(ArticleVendu article, Principal user);
	
	//création enchère
	public void creationEncheres(Enchere enchere);
	
	// recupération de toutes les enchères d'un article tout utilisateur confondu
	public List<Enchere> getEncheresByArticle (ArticleVendu articleVendu);
	
	// Récup de la meilleure enchère de l'article tout utilisateur confondu
	public Enchere getMeilleureEnchereByArticle (ArticleVendu article);
	
	// Récupération de toutes les enchères d'un utilisateur
	List<Enchere> getEncheresByUser(Utilisateur utilisateur);
	
	
	public void surencherir(Enchere enchere);
	
	public void conclureVente(ArticleVendu article);
	
}
