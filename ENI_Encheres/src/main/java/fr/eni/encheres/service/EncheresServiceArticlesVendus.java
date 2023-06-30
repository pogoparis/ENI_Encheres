package fr.eni.encheres.service;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

public interface EncheresServiceArticlesVendus {
	
	List<ArticleVendu> findAllArticleVendu();
	void createArticle(ArticleVendu article);
	ArticleVendu findArticleById(Integer id);
	void majPrixArticle(Enchere enchere);
//	List<ArticleVendu> findArticleByCategorie();
	List<ArticleVendu> findArticleContainNom(String rechercheNom);

}
