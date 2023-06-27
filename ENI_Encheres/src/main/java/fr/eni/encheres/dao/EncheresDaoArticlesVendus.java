package fr.eni.encheres.dao;

import java.util.List;

import fr.eni.encheres.bo.ArticleVendu;

public interface EncheresDaoArticlesVendus {
	
	List<ArticleVendu> getAllArticleVendu();

	void creerArticle(ArticleVendu article);

	ArticleVendu getArticleById(Integer id);


}
