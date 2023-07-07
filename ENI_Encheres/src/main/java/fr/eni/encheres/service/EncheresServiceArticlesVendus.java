package fr.eni.encheres.service;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresServiceArticlesVendus {

	List<ArticleVendu> findAllArticleVendu();

	void createArticle(ArticleVendu article);

	ArticleVendu findArticleById(Integer id);

	void majPrixArticle(Enchere enchere);

	List<ArticleVendu> rechercheNonConnecte(String rechercheNom, Categorie categorie);

	List<ArticleVendu> rechercheConnecte(String rechercheNom, Categorie categorie, Utilisateur utilisateur,
			String optionArticle, String ventesEnCours, String ventesTerminees, String ventesNonDebutees,
			String encheresEnCours, String encheresOuvertes, String encheresGagnees);
}
