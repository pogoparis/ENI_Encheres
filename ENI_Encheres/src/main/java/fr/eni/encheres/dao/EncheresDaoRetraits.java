package fr.eni.encheres.dao;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

public interface EncheresDaoRetraits {

	void createRetrait(Retrait retrait, ArticleVendu article);

	public Retrait getRetraitByArticle(ArticleVendu article);

}
