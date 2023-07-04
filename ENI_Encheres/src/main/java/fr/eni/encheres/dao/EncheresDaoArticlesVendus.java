package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresDaoArticlesVendus {
	
	List<ArticleVendu> getAllArticleVendu();

	void creerArticle(ArticleVendu article);

	ArticleVendu getArticleById(Integer id);
	
	void majPrixArticle(Enchere enchere);
	
	public List<ArticleVendu> getArticlesByUser (Utilisateur utilisateur);

	// Recherche non connecté
	List<ArticleVendu> getArticleContainNom(String rechercheNom);
	List<ArticleVendu> getArticleByCategorieContainNom(String rechercheNom, Categorie categorie);

	// recherche connecté
	List<ArticleVendu> getArticlesByUserAndSearch(Utilisateur utilisateur, String rechercheNom );
	List<ArticleVendu> getArticlesByUserByCategorie(Utilisateur utilisateur, Categorie categorie, String rechercheNom);

	void miseAJourEtat(ArticleVendu article);


}
