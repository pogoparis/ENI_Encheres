package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;

public interface EncheresDaoArticlesVendus {
	
	List<ArticleVendu> getAllArticleVendu();

	void creerArticle(ArticleVendu article);

	ArticleVendu getArticleById(Integer id);
	
	void majPrixArticle(Enchere enchere);

	List<ArticleVendu> getArticleContainNom(String rechercheNom);


}
