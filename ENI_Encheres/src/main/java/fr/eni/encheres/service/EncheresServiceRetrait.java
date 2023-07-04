package fr.eni.encheres.service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

public interface EncheresServiceRetrait {

	void createRetrait(Retrait retrait, ArticleVendu article);
	public Retrait findRetraitByArticle (ArticleVendu article);
	public void setRetraitParDefaut(Retrait retrait, Utilisateur utilisateur);
}
