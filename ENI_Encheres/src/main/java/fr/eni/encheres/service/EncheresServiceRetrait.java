package fr.eni.encheres.service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;

public interface EncheresServiceRetrait {

	void createRetrait(Retrait retrait, ArticleVendu article);
	public Retrait findRetraitByArticle (ArticleVendu article);
}
